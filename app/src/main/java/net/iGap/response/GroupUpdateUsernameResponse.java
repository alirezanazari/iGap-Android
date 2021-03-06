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

import net.iGap.observers.interfaces.OnGroupUpdateUsername;
import net.iGap.proto.ProtoError;
import net.iGap.proto.ProtoGroupUpdateUsername;
import net.iGap.realm.RealmRoom;

public class GroupUpdateUsernameResponse extends MessageHandler {

    public int actionId;
    public Object message;
    public Object identity;

    public GroupUpdateUsernameResponse(int actionId, Object protoClass, Object identity) {
        super(actionId, protoClass, identity);
        this.message = protoClass;
        this.actionId = actionId;
        this.identity = identity;
    }

    @Override
    public void handler() {
        super.handler();
        ProtoGroupUpdateUsername.GroupUpdateUsernameResponse.Builder builder = (ProtoGroupUpdateUsername.GroupUpdateUsernameResponse.Builder) message;
        RealmRoom.updateUsername(builder.getRoomId(), builder.getUsername());
        if (identity instanceof OnGroupUpdateUsername) {
            ((OnGroupUpdateUsername) identity).onGroupUpdateUsername(builder.getRoomId(), builder.getUsername());
        } else {
            throw new ClassCastException("identity must be: " + OnGroupUpdateUsername.class.getName());
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
        if (identity instanceof OnGroupUpdateUsername) {
            ((OnGroupUpdateUsername) identity).onError(errorResponse.getMajorCode(), errorResponse.getMinorCode(), errorResponse.getMajorCode());
        } else {
            throw new ClassCastException("identity must be: " + OnGroupUpdateUsername.class.getName());
        }
    }
}


