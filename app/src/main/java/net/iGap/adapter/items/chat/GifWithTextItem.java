/*
 * This is the source code of iGap for Android
 * It is licensed under GNU AGPL v3.0
 * You should have received a copy of the license in this archive (see LICENSE).
 * Copyright © 2017 , iGap - www.iGap.net
 * iGap Messenger | Free, Fast and Secure instant messaging application
 * The idea of the Kianiranian Company - www.kianiranian.com
 * All rights reserved.
 */

package net.iGap.adapter.items.chat;

import android.content.SharedPreferences;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.iGap.R;
import net.iGap.adapter.MessagesAdapter;
import net.iGap.fragments.FragmentChat;
import net.iGap.messageprogress.MessageProgress;
import net.iGap.module.AppUtils;
import net.iGap.module.ReserveSpaceGifImageView;
import net.iGap.module.SHP_SETTING;
import net.iGap.module.enums.LocalFileType;
import net.iGap.observers.interfaces.IMessageItem;
import net.iGap.proto.ProtoGlobal;

import java.io.File;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;

import static android.content.Context.MODE_PRIVATE;

public class GifWithTextItem extends AbstractMessage<GifWithTextItem, GifWithTextItem.ViewHolder> {

    public GifWithTextItem(MessagesAdapter<AbstractMessage> mAdapter, ProtoGlobal.Room.Type type, IMessageItem messageClickListener) {
        super(mAdapter, true, type, messageClickListener);
    }

    @Override
    public void onPlayPauseGIF(ViewHolder holder, String localPath) throws ClassCastException {
        super.onPlayPauseGIF(holder, localPath);

        AppUtils.setProgresColor(holder.progress.progressBar);

        holder.progress.withDrawable(R.mipmap.photogif, true);

        GifDrawable gifDrawable = (GifDrawable) holder.image.getDrawable();
        if (gifDrawable != null) {
            if (gifDrawable.isPlaying()) {
                gifDrawable.pause();
                holder.progress.setVisibility(View.VISIBLE);
            } else {
                gifDrawable.start();
                holder.progress.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getType() {
        return R.id.chatSubLayoutGifWithText;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.chat_sub_layout_message;
    }

    @Override
    public void onLoadThumbnailFromLocal(final ViewHolder holder, final String tag, final String localPath, LocalFileType fileType) {
        super.onLoadThumbnailFromLocal(holder, tag, localPath, fileType);
        holder.image.setImageURI(Uri.fromFile(new File(localPath)));

        if (fileType == LocalFileType.FILE) {
            SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences(SHP_SETTING.FILE_NAME, MODE_PRIVATE);
            if (sharedPreferences.getInt(SHP_SETTING.KEY_AUTOPLAY_GIFS, SHP_SETTING.Defaults.KEY_AUTOPLAY_GIFS) == 1) {
                holder.progress.setVisibility(View.GONE);
            } else {
                if (holder.image.getDrawable() instanceof GifDrawable) {
                    GifDrawable gifDrawable = (GifDrawable) holder.image.getDrawable();
                    // to get first frame
                    gifDrawable.stop();
                    holder.progress.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void bindView(final ViewHolder holder, List payloads) {
        super.bindView(holder, payloads);

        setTextIfNeeded(holder.messageView);

        holder.progress.setOnLongClickListener(getLongClickPerform(holder));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FragmentChat.isInSelectionMode) {
                    holder.itemView.performLongClick();
                } else {
                    holder.progress.performClick();
                }
            }
        });

        holder.image.setOnLongClickListener(getLongClickPerform(holder));
    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    protected static class ViewHolder extends ChatItemWithTextHolder implements IThumbNailItem, IProgress {

        protected ReserveSpaceGifImageView image;
        protected MessageProgress progress;

        public ViewHolder(View view) {
            super(view);

            boolean withText = true;
            FrameLayout frameLayout = new FrameLayout(view.getContext());
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));

            image = new ReserveSpaceGifImageView(view.getContext());
            image.setId(R.id.thumbnail);
            FrameLayout.LayoutParams layout_758 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            image.setLayoutParams(layout_758);

            frameLayout.addView(image);
            progress = getProgressBar(view.getContext(), 0);

            frameLayout.addView(progress, new FrameLayout.LayoutParams(i_Dp(R.dimen.dp60), i_Dp(R.dimen.dp60), Gravity.CENTER));

            getContentBloke().addView(frameLayout);

            if (withText) {
                setLayoutMessageContainer();
            }
        }

        @Override
        public ImageView getThumbNailImageView() {
            return image;
        }

        @Override
        public MessageProgress getProgress() {
            return progress;
        }
    }
}
