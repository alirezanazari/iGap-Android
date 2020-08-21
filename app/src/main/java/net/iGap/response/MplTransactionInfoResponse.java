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
import net.iGap.proto.ProtoMplTransactionInfo;

public class MplTransactionInfoResponse extends MessageHandler {

    public int actionId;
    public Object message;
    public String identity;

    public MplTransactionInfoResponse(int actionId, Object protoClass, String identity) {
        super(actionId, protoClass, identity);

        this.message = protoClass;
        this.actionId = actionId;
        this.identity = identity;
    }

    @Override
    public void handler() {
        super.handler();
        ProtoMplTransactionInfo.MplTransactionInfoResponse.Builder builder = (ProtoMplTransactionInfo.MplTransactionInfoResponse.Builder) message;

        if (G.onMplTransactionInfo != null)
            G.onMplTransactionInfo.onMplTransAction(builder.getTransaction(), builder.getStatus());
    }

    @Override
    public void timeOut() {
        super.timeOut();
        if (G.onMplTransactionInfo != null)
            G.onMplTransactionInfo.onError();
    }

    @Override
    public void error() {
        super.error();
        if (G.onMplTransactionInfo != null)
            G.onMplTransactionInfo.onError();
    }
}