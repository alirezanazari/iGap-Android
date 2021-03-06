/*
 * This is the source code of iGap for Android
 * It is licensed under GNU AGPL v3.0
 * You should have received a copy of the license in this archive (see LICENSE).
 * Copyright © 2017 , iGap - www.iGap.net
 * iGap Messenger | Free, Fast and Secure instant messaging application
 * The idea of the Kianiranian Company - www.kianiranian.com
 * All rights reserved.
 */

package net.iGap.observers.interfaces;

import android.view.View;

import net.iGap.fragments.emoji.struct.StructIGSticker;
import net.iGap.module.structs.StructMessageInfo;
import net.iGap.realm.RealmRoomMessage;

public interface IMessageItem {
    /**
     * open means open for files and play for videos
     */
    void onOpenClick(View view, StructMessageInfo message, int pos);

    void onContainerClick(View view, StructMessageInfo message, int pos);

    void onSenderAvatarClick(View view, StructMessageInfo message, int pos);

    void onUploadOrCompressCancel(View view, StructMessageInfo message, int pos);

    void onFailedMessageClick(View view, StructMessageInfo message, int pos);

    void onReplyClick(RealmRoomMessage replyMessage);

    void onForwardClick(StructMessageInfo message);

    void onForwardFromCloudClick(StructMessageInfo message);

    void onDownloadAllEqualCashId(String token, String messageId);

    void onItemShowingMessageId(StructMessageInfo messageInfo);

    //void onVoteClick(StructMessageInfo message, String vote, ProtoGlobal.RoomMessageReaction reaction);

    void onPlayMusic(String messageId);

    boolean getShowVoteChannel();

    void sendFromBot(Object realmRoomMessage);

    void onOpenLinkDialog(String url);

    void onActiveGiftStickerClick(StructIGSticker structIGSticker, int mode, StructMessageInfo structMessage);
}
