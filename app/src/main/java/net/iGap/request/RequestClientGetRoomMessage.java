/*
 * This is the source code of iGap for Android
 * It is licensed under GNU AGPL v3.0
 * You should have received a copy of the license in this archive (see LICENSE).
 * Copyright © 2017 , iGap - www.iGap.net
 * iGap Messenger | Free, Fast and Secure instant messaging application
 * The idea of the RooyeKhat Media Company - www.RooyeKhat.co
 * All rights reserved.
 */

package net.iGap.request;

import net.iGap.observers.interfaces.OnClientGetRoomMessage;
import net.iGap.proto.ProtoClientGetRoomMessage;

public class RequestClientGetRoomMessage {

    public void clientGetRoomMessage(long roomId, long messageId, OnClientGetRoomMessage onClientGetRoomMessage) {
        ProtoClientGetRoomMessage.ClientGetRoomMessage.Builder builder = ProtoClientGetRoomMessage.ClientGetRoomMessage.newBuilder();
        builder.setRoomId(roomId);
        builder.setMessageId(messageId);

        RequestWrapper requestWrapper = new RequestWrapper(604, builder, new RequestClientGetRoomMessageExtra(roomId, onClientGetRoomMessage));
        try {
            RequestQueue.sendRequest(requestWrapper);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public class RequestClientGetRoomMessageExtra {
        private long roomId;
        private OnClientGetRoomMessage onClientGetRoomMessage;

        RequestClientGetRoomMessageExtra(long roomId, OnClientGetRoomMessage onClientGetRoomMessage) {
            this.roomId = roomId;
            this.onClientGetRoomMessage = onClientGetRoomMessage;
        }

        public long getRoomId() {
            return roomId;
        }

        public OnClientGetRoomMessage getOnClientGetRoomMessage() {
            return onClientGetRoomMessage;
        }
    }

}
