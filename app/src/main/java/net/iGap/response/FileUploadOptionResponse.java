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

import net.iGap.proto.ProtoFileUploadOption;
import net.iGap.request.RequestFileUploadOption;

public class FileUploadOptionResponse extends MessageHandler {

    public int actionId;
    public Object message;
    public Object identity;

    public FileUploadOptionResponse(int actionId, Object protoClass, Object identity) {
        super(actionId, protoClass, identity);

        this.message = protoClass;
        this.actionId = actionId;
        this.identity = identity;
    }

    @Override
    public void handler() {
        super.handler();
        ProtoFileUploadOption.FileUploadOptionResponse.Builder fp = (ProtoFileUploadOption.FileUploadOptionResponse.Builder) message;
        ((RequestFileUploadOption.OnFileUploadOption) identity).onFileUploadOption(fp.getFirstBytesLimit(), fp.getLastBytesLimit(), fp.getMaxConnection());
    }

    @Override
    public void timeOut() {
        super.timeOut();
    }

    @Override
    public void error() {
        super.error();
        ((RequestFileUploadOption.OnFileUploadOption) identity).onFileUploadOptionError(majorCode, minorCode);
    }

}


