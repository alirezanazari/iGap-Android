package org.paygear.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import net.iGap.R;

import org.paygear.WalletActivity;
import org.paygear.model.PaymentResult;
import org.paygear.utils.SettingHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ir.radsense.raadcore.model.KeyValue;
import ir.radsense.raadcore.utils.RaadCommonUtils;
import ir.radsense.raadcore.utils.Typefaces;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Software1 on 9/18/2017.
 */

public class PaymentResultDialog extends DialogFragment {


    private PaymentResult mResult;
    private LinearLayout receiptLayout;

    private View.OnClickListener listener;
    private String colorAppBar;

    public static PaymentResultDialog newInstance(PaymentResult result) {
        PaymentResultDialog fragment = new PaymentResultDialog();
        Bundle args = new Bundle();
        args.putSerializable("PaymentResult", result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mResult = (PaymentResult) getArguments().getSerializable("PaymentResult");
        }
    }

    public void setListener(View.OnClickListener listener, String colorAppBar) {
        this.listener = listener;
        this.colorAppBar = colorAppBar;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_dialog_payment_result, container, false);

        receiptLayout = view.findViewById(R.id.receipt);
        View statusView = view.findViewById(R.id.status_view);
        TextView statusTitle = view.findViewById(R.id.status_title);
        statusTitle.setTextColor(Color.parseColor(WalletActivity.textTitleTheme));

        TextView priceTitle = view.findViewById(R.id.price_title);
        TextView priceValue = view.findViewById(R.id.price_value);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            statusView.setBackgroundColor(Color.parseColor(WalletActivity.primaryColor));
            statusTitle.setTextColor(Color.WHITE);
        }

        TextView saveButton = view.findViewById(R.id.save_button);
        ViewCompat.setBackground(saveButton, getButtonSelector());
        saveButton.setTextColor(ContextCompat.getColorStateList(getActivity(), R.color.text_selector_dark));
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReceipt();
                if (WalletActivity.refreshLayout != null)
                    WalletActivity.refreshLayout.setRefreshLayout(true);
            }
        });

        TextView closeButton = view.findViewById(R.id.close_button);
        ViewCompat.setBackground(closeButton, getButtonSelector());
        closeButton.setTextColor(ContextCompat.getColorStateList(getActivity(), R.color.text_selector_dark));
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(v);
                dismiss();

                if (WalletActivity.refreshLayout != null)
                    WalletActivity.refreshLayout.setRefreshLayout(true);

                if (WalletActivity.intent != null)
                    if (WalletActivity.intent.getBooleanExtra("IsP2P", false)) {
                        Intent returnIntent = WalletActivity.intent;
                        returnIntent.putExtra("result", mResult);
                        if (getActivity() == null) {
                            dismiss();
                            return;
                        }
                        getActivity().setResult(RESULT_OK, returnIntent);
                        if (getActivity() instanceof WalletActivity)
                            getActivity().finish();
                    }
            }
        });

        Typefaces.setTypeface(getActivity(), Typefaces.IRAN_MEDIUM, statusTitle, priceTitle, priceValue);
        Typefaces.setTypeface(getActivity(), Typefaces.IRAN_LIGHT, saveButton, closeButton);

        RecyclerView mList = view.findViewById(R.id.list);

        if (mResult.result != null)
            mList.setAdapter(new ListItemAdapter());

        priceValue.setText(RaadCommonUtils.formatPrice(mResult.amount, true));
        playSound();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);

        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), android.R.color.transparent));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(window.getAttributes());
        lp.width = RaadCommonUtils.getPx(280, getActivity());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }


    private StateListDrawable getButtonSelector() {
        int corner = RaadCommonUtils.getPx(20, getActivity());
        int stroke = RaadCommonUtils.getPx(1, getActivity());
        Drawable normalDrawable = RaadCommonUtils.getRectShape(
                ContextCompat.getColor(getActivity(), R.color.A4), corner, stroke);

        Drawable pressedDrawable = RaadCommonUtils.getRectShape(
                Color.BLACK, corner, 0);

        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
        states.addState(new int[]{}, normalDrawable);
        return states;
    }

    private void playSound() {
        //SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
        //soundPool.play(soundPool.load(getActivity(), R.raw.payment_success_sound, 1), 1.0f, 1.0f, 1, 0, 1f);
        try {
            MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.payment_success_sound);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveReceipt() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (result != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                return;
            }
        }

        receiptLayout.setDrawingCacheEnabled(true);
        receiptLayout.buildDrawingCache();

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Raad");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
                return;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US).format(new Date());
        String fileName = "receipt_" + mResult.invoiceNumber + "_" + timeStamp + ".jpg";
        File savedFile = RaadCommonUtils.saveBitmap(receiptLayout.getDrawingCache(), new File(mediaStorageDir, fileName));
        if (savedFile != null) {
            Toast.makeText(getActivity(), R.string.receipt_saved, Toast.LENGTH_SHORT).show();
            if (listener != null)
                listener.onClick(null);
            dismiss();
            if (WalletActivity.intent != null)
                if (WalletActivity.intent.getBooleanExtra("IsP2P", false)) {
                    Intent returnIntent = WalletActivity.intent;
                    returnIntent.putExtra("result", mResult);
                    getActivity().setResult(RESULT_OK, returnIntent);
                    if (getActivity() instanceof WalletActivity)
                        getActivity().finish();
                }
        } else {
            Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveReceipt();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_dialog_payment_result_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            KeyValue keyValue = mResult.result[position];
            holder.title.setText(keyValue.key);
            if (SettingHelper.getString(getContext(), SettingHelper.APP_LANGUAGE, "en").equals("fa")) {
                holder.value.setText(RaadCommonUtils.getPersianNumber(keyValue.value));
            } else {
                holder.value.setText(keyValue.value);
            }
        }

        @Override
        public int getItemCount() {
            return mResult.result.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView title;
            TextView value;

            public ViewHolder(View view) {
                super(view);
                title = view.findViewById(R.id.title);
                value = view.findViewById(R.id.value);
                Typefaces.setTypeface(getActivity(), Typefaces.IRAN_MEDIUM, title);
                Typefaces.setTypeface(getActivity(), Typefaces.IRAN_LIGHT, value);
            }
        }


    }

}
