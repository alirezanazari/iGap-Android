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

import net.iGap.observers.interfaces.OnUserProfileSetNickNameResponse;
import net.iGap.proto.ProtoUserProfileNickname;

public class RequestUserProfileSetNickname {

    public void userProfileNickName(String nickName, OnUserProfileSetNickNameResponse callback) {
        ProtoUserProfileNickname.UserProfileSetNickname.Builder userProfileNickName = ProtoUserProfileNickname.UserProfileSetNickname.newBuilder();
        userProfileNickName.setNickname(nickName);

        RequestWrapper requestWrapper = new RequestWrapper(105, userProfileNickName, callback);
        try {
            RequestQueue.sendRequest(requestWrapper);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}