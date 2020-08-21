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

import net.iGap.proto.ProtoUserProfileGetRepresentative;
import net.iGap.request.RequestUserProfileGetRepresentative;

import static net.iGap.request.RequestUserProfileGetRepresentative.numberOfPendingRequest;

public class UserProfileGetRepresentativeResponse extends MessageHandler {

    public int actionId;
    public Object message;
    public Object identity;

    public UserProfileGetRepresentativeResponse(int actionId, Object protoClass, Object identity) {
        super(actionId, protoClass, identity);

        this.message = protoClass;
        this.actionId = actionId;
        this.identity = identity;
    }

    @Override
    public void handler() {
        super.handler();
        numberOfPendingRequest--;

        ProtoUserProfileGetRepresentative.UserProfileGetRepresentativeResponse.Builder builder = (ProtoUserProfileGetRepresentative.UserProfileGetRepresentativeResponse.Builder) message;
        ((RequestUserProfileGetRepresentative.OnRepresentReady) this.identity).onRepresent(builder.getPhoneNumber());
    }

    @Override
    public void timeOut() {
        super.timeOut();
        ((RequestUserProfileGetRepresentative.OnRepresentReady) this.identity).onFailed();
    }

    @Override
    public void error() {
        numberOfPendingRequest--;
        super.error();
        ((RequestUserProfileGetRepresentative.OnRepresentReady) this.identity).onFailed();
    }
}