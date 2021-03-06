/*
 * This is the source code of iGap for Android
 * It is licensed under GNU AGPL v3.0
 * You should have received a copy of the license in this archive (see LICENSE).
 * Copyright © 2017 , iGap - www.iGap.net
 * iGap Messenger | Free, Fast and Secure instant messaging application
 * The idea of the Kianiranian Company - www.kianiranian.com
 * All rights reserved.
 */

package net.iGap.request;

import net.iGap.proto.ProtoChannelPinMessage;

public class RequestChannelPinMessage {

    public void channelPinMessage(long roomId, long messageId) {
        ProtoChannelPinMessage.ChannelPinMessage.Builder builder = ProtoChannelPinMessage.ChannelPinMessage.newBuilder();
        builder.setRoomId(roomId);
        builder.setMessageId(messageId);

        RequestWrapper requestWrapper = new RequestWrapper(427, builder);
        try {
            RequestQueue.sendRequest(requestWrapper);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
