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

import com.google.protobuf.ByteString;

import net.iGap.helper.HelperString;
import net.iGap.proto.ProtoFileUpload;
import net.iGap.proto.ProtoRequest;

public class RequestFileUpload {

    public interface OnFileUpload {
        void onFileUpload(double progress, long nextOffset, int nextLimit);

        void onFileUploadError(int major, int minor);
    }

    public String fileUpload(String token, long offset, byte[] bytes, OnFileUpload onFileUpload) {

        ProtoFileUpload.FileUpload.Builder fileUploadInit = ProtoFileUpload.FileUpload.newBuilder();
        fileUploadInit.setRequest(ProtoRequest.Request.newBuilder().setId(HelperString.generateKey()));
        fileUploadInit.setToken(token);
        fileUploadInit.setOffset(offset);
        fileUploadInit.setBytes(ByteString.copyFrom(bytes));

        RequestWrapper requestWrapper = new RequestWrapper(702, fileUploadInit, onFileUpload);
        try {
            return RequestQueue.sendRequest(requestWrapper);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "";
    }

}
