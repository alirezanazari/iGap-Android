package net.iGap.adapter.payment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.iGap.R;
import net.iGap.model.paymentPackage.FavoriteNumber;

import java.text.DecimalFormat;
import java.util.List;


public class ChargeHistoryNumberAdapter extends RecyclerView.Adapter<ChargeHistoryNumberAdapter.ContactNumberViewHolder> {
    private List<FavoriteNumber> historyNumberList;
    private IOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(IOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ChargeHistoryNumberAdapter(List<FavoriteNumber> historyNumberList) {
        this.historyNumberList = historyNumberList;
    }

    @NonNull
    @Override
    public ContactNumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_charge_history, parent, false);
        return new ContactNumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactNumberViewHolder holder, int position) {
        holder.bindNUmber(historyNumberList.get(position));
    }

    @Override
    public int getItemCount() {
        return historyNumberList.size();
    }

    class ContactNumberViewHolder extends RecyclerView.ViewHolder {
        private TextView amount;
        private TextView phoneNumber;

        ContactNumberViewHolder(@NonNull View itemView) {
            super(itemView);
            phoneNumber = itemView.findViewById(R.id.number_contact);
            amount = itemView.findViewById(R.id.amount_contact);

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClicked(getAdapterPosition());
                }
            });
        }

        void bindNUmber(FavoriteNumber historyNumber) {
            phoneNumber.setText(historyNumber.getPhoneNumber());
            DecimalFormat df = new DecimalFormat(",###");
            if (historyNumber.getAmount() == null)
                return;

            String price = df.format(historyNumber.getAmount());

            amount.setText(String.format("%s %s", price, itemView.getContext().getResources().getString(R.string.rial)));
        }
    }

    public interface IOnItemClickListener {
        void onItemClicked(int position);
    }

    public List<FavoriteNumber> getHistoryNumberList() {
        return historyNumberList;
    }
}
