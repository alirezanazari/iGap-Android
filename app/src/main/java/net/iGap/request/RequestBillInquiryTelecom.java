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

import net.iGap.fragments.inquiryBill.BillInquiryResponse;
import net.iGap.observers.interfaces.GeneralResponseCallBack;
import net.iGap.proto.ProtoBillInquiryTelecom;

public class RequestBillInquiryTelecom {

    public void billInquiryTelecom(int provinceCode, long phoneNumber, GeneralResponseCallBack<BillInquiryResponse> callback) {
        ProtoBillInquiryTelecom.BillInquiryTelecom.Builder builder = ProtoBillInquiryTelecom.BillInquiryTelecom.newBuilder();
        builder.setProvinceCode(provinceCode);
        builder.setTelephoneNumber(phoneNumber);

        RequestWrapper requestWrapper = new RequestWrapper(9201, builder, callback);
        try {
            RequestQueue.sendRequest(requestWrapper);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}