/*
 * This is the source code of iGap for Android
 * It is licensed under GNU AGPL v3.0
 * You should have received a copy of the license in this archive (see LICENSE).
 * Copyright © 2017 , iGap - www.iGap.net
 * iGap Messenger | Free, Fast and Secure instant messaging application
 * The idea of the Kianiranian Company - www.kianiranian.com
 * All rights reserved.
 */

package net.iGap.response;

import net.iGap.G;
import net.iGap.module.accountManager.DbManager;
import net.iGap.proto.ProtoUserAvatarDelete;
import net.iGap.realm.RealmAvatar;

public class UserAvatarDeleteResponse extends MessageHandler {

    public int actionId;
    public Object message;
    public String identity;

    public UserAvatarDeleteResponse(int actionId, Object protoClass, String identity) {
        super(actionId, protoClass, identity);
        this.message = protoClass;
        this.identity = identity;
        this.actionId = actionId;
    }

    @Override
    public void handler() {
        super.handler();

        final ProtoUserAvatarDelete.UserAvatarDeleteResponse.Builder userAvatarDeleteResponse = (ProtoUserAvatarDelete.UserAvatarDeleteResponse.Builder) message;

        if (G.onUserAvatarDelete != null) {
            G.onUserAvatarDelete.onUserAvatarDelete(userAvatarDeleteResponse.getId(), identity);
        } else {
            DbManager.getInstance().doRealmTransaction(realm -> {
                RealmAvatar.deleteAvatar(realm, userAvatarDeleteResponse.getId());
            });
        }

        G.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (G.onUserInfoMyClient != null) {
                    G.onUserInfoMyClient.onUserInfoMyClient();
                }
            }
        }, 1000);
    }

    @Override
    public void timeOut() {
        super.timeOut();
    }

    @Override
    public void error() {
        super.error();
        if (G.onUserAvatarDelete != null) {
            G.onUserAvatarDelete.onUserAvatarDeleteError();
        }
    }
}


