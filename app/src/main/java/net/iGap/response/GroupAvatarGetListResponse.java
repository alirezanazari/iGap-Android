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

import net.iGap.module.accountManager.DbManager;
import net.iGap.proto.ProtoGlobal;
import net.iGap.proto.ProtoGroupAvatarGetList;
import net.iGap.realm.RealmAvatar;

public class GroupAvatarGetListResponse extends MessageHandler {

    public int actionId;
    public Object message;
    public String identity;

    public GroupAvatarGetListResponse(int actionId, Object protoClass, String identity) {
        super(actionId, protoClass, identity);
        this.message = protoClass;
        this.identity = identity;
        this.actionId = actionId;
    }

    @Override
    public void handler() {
        super.handler();
        final ProtoGroupAvatarGetList.GroupAvatarGetListResponse.Builder builder = (ProtoGroupAvatarGetList.GroupAvatarGetListResponse.Builder) message;
        final long ownerId = Long.parseLong(identity);
        DbManager.getInstance().doRealmTransaction(realm -> {
            RealmAvatar.deleteAllAvatars(ownerId, realm);
            for (ProtoGlobal.Avatar avatar : builder.getAvatarList()) {
                RealmAvatar.putOrUpdate(realm, ownerId, avatar);
            }
        });
    }

    @Override
    public void timeOut() {
        super.timeOut();
    }

    @Override
    public void error() {
        super.error();
    }
}


