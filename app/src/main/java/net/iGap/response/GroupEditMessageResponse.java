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
import net.iGap.helper.HelperEditMessage;
import net.iGap.proto.ProtoError;
import net.iGap.proto.ProtoGroupEditMessage;

public class GroupEditMessageResponse extends MessageHandler {

    public int actionId;
    public Object message;
    public String identity;

    public GroupEditMessageResponse(int actionId, Object protoClass, String identity) {
        super(actionId, protoClass, identity);
        this.message = protoClass;
        this.identity = identity;
        this.actionId = actionId;
    }

    @Override
    public void handler() {
        super.handler();
        ProtoGroupEditMessage.GroupEditMessageResponse.Builder builder = (ProtoGroupEditMessage.GroupEditMessageResponse.Builder) message;
        HelperEditMessage.editMessage(builder.getRoomId(), builder.getMessageId(), builder.getMessageVersion(), builder.getMessageType(), builder.getMessage(), builder.getResponse());
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

        if (G.onChatEditMessageResponse != null) {
            G.onChatEditMessageResponse.onError(majorCode, minorCode);
        }
    }
}


