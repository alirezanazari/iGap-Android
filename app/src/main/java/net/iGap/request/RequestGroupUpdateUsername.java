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

import net.iGap.observers.interfaces.OnGroupUpdateUsername;
import net.iGap.proto.ProtoGroupUpdateUsername;

public class RequestGroupUpdateUsername {

    public void groupUpdateUsername(long roomId, String username, OnGroupUpdateUsername callback) {

        ProtoGroupUpdateUsername.GroupUpdateUsername.Builder builder = ProtoGroupUpdateUsername.GroupUpdateUsername.newBuilder();
        builder.setRoomId(roomId);
        builder.setUsername(username);

        RequestWrapper requestWrapper = new RequestWrapper(322, builder, callback);
        try {
            RequestQueue.sendRequest(requestWrapper);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
