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

import net.iGap.proto.ProtoClientJoinByUsername;

public class RequestClientJoinByUsername {

    public interface OnClientJoinByUsername {
        void onClientJoinByUsernameResponse();

        void onError(int majorCode, int minorCode);
    }

    public void clientJoinByUsername(String username, OnClientJoinByUsername onClientJoinByUsername) {

        ProtoClientJoinByUsername.ClientJoinByUsername.Builder builder = ProtoClientJoinByUsername.ClientJoinByUsername.newBuilder();
        builder.setUsername(username);

        RequestWrapper requestWrapper = new RequestWrapper(609, builder, onClientJoinByUsername);
        try {
            RequestQueue.sendRequest(requestWrapper);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void clientJoinByUsername(String username, long roomId) {

        ProtoClientJoinByUsername.ClientJoinByUsername.Builder builder = ProtoClientJoinByUsername.ClientJoinByUsername.newBuilder();
        builder.setUsername(username);

        RequestWrapper requestWrapper = new RequestWrapper(609, builder, roomId);
        try {
            RequestQueue.sendRequest(requestWrapper);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
