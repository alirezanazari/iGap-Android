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

import net.iGap.observers.interfaces.OnGroupRemoveUsername;
import net.iGap.proto.ProtoError;
import net.iGap.proto.ProtoGroupRemoveUsername;
import net.iGap.realm.RealmRoom;

public class GroupRemoveUsernameResponse extends MessageHandler {

    public int actionId;
    public Object message;
    public Object identity;

    public GroupRemoveUsernameResponse(int actionId, Object protoClass, Object identity) {
        super(actionId, protoClass, identity);

        this.message = protoClass;
        this.actionId = actionId;
        this.identity = identity;
    }

    @Override
    public void handler() {
        super.handler();
        ProtoGroupRemoveUsername.GroupRemoveUsernameResponse.Builder builder = (ProtoGroupRemoveUsername.GroupRemoveUsernameResponse.Builder) message;
        RealmRoom.setPrivate(builder.getRoomId());
        if (identity instanceof OnGroupRemoveUsername) {
            ((OnGroupRemoveUsername) identity).onGroupRemoveUsername(builder.getRoomId());
        } else {
            throw new ClassCastException("identity is must be: " + OnGroupRemoveUsername.class.getName());
        }
    }

    @Override
    public void timeOut() {
        super.timeOut();
    }

    @Override
    public void error() {
        super.error();
        ProtoError.ErrorResponse.Builder errorResponse = (ProtoError.ErrorResponse.Builder) message;
        int majorCode = errorResponse.getMajorCode();
        int minorCode = errorResponse.getMinorCode();
        if (identity instanceof OnGroupRemoveUsername) {
            ((OnGroupRemoveUsername) identity).onError(majorCode, minorCode);
        } else {
            throw new ClassCastException("identity is must be: " + OnGroupRemoveUsername.class.getName());
        }
    }
}


