/*
 * This is the source code of iGap for Android
 * It is licensed under GNU AGPL v3.0
 * You should have received a copy of the license in this archive (see LICENSE).
 * Copyright © 2017 , iGap - www.iGap.net
 * iGap Messenger | Free, Fast and Secure instant messaging application
 * The idea of the RooyeKhat Media Company - www.RooyeKhat.co
 * All rights reserved.
 */

package net.iGap.response;

import net.iGap.observers.interfaces.OnUserProfileSetRepresentative;
import net.iGap.proto.ProtoError;
import net.iGap.proto.ProtoUserProfileRepresentative;

public class UserProfileSetRepresentativeResponse extends MessageHandler {

    public int actionId;
    public Object message;
    public Object identity;

    public UserProfileSetRepresentativeResponse(int actionId, Object protoClass, Object identity) {
        super(actionId, protoClass, identity);
        this.message = protoClass;
        this.actionId = actionId;
        this.identity = identity;
    }

    @Override
    public void handler() {
        super.handler();
        ProtoUserProfileRepresentative.UserProfileSetRepresentativeResponse.Builder builder = (ProtoUserProfileRepresentative.UserProfileSetRepresentativeResponse.Builder) message;
        if (identity instanceof OnUserProfileSetRepresentative) {
            ((OnUserProfileSetRepresentative) identity).onSetRepresentative(builder.getPhoneNumber());
        } else {
            throw new ClassCastException("identity must be: " + OnUserProfileSetRepresentative.class.getName());
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
        if (identity instanceof OnUserProfileSetRepresentative) {
            ((OnUserProfileSetRepresentative) identity).onErrorSetRepresentative(majorCode, minorCode);
        } else {
            throw new ClassCastException("identity must be: " + OnUserProfileSetRepresentative.class.getName());
        }
    }
}