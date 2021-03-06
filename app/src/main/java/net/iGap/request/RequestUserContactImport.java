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

import net.iGap.G;
import net.iGap.module.structs.StructListOfContact;
import net.iGap.proto.ProtoUserContactsImport;

import java.util.List;

public class RequestUserContactImport {

    public static final String KEY = "AddContact";

    public void contactImport(List<StructListOfContact> itemContactList, boolean force, boolean getContactList) {
        RequestWrapper requestWrapper = new RequestWrapper(106, contact(itemContactList, force), getContactList);
        try {
            RequestQueue.sendRequest(requestWrapper);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void contactImport(List<StructListOfContact> itemContactList, boolean force, String key) {
        RequestWrapper requestWrapper = new RequestWrapper(106, contact(itemContactList, force), key);
        try {
            RequestQueue.sendRequest(requestWrapper);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void contactImport(List<StructListOfContact> itemContactList, boolean force) {
        RequestWrapper requestWrapper = new RequestWrapper(106, contact(itemContactList, force));
        try {
            RequestQueue.sendRequest(requestWrapper);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private ProtoUserContactsImport.UserContactsImport.Builder contact(List<StructListOfContact> itemContactList, boolean force) {
        ProtoUserContactsImport.UserContactsImport.Builder userContactsImport = ProtoUserContactsImport.UserContactsImport.newBuilder();

        for (int i = 0; i < itemContactList.size(); i++) {
            String phone = itemContactList.get(i).getPhone();
            String first_name = itemContactList.get(i).getFirstName();
            String last_name = itemContactList.get(i).getLastName();

            ProtoUserContactsImport.UserContactsImport.Contact.Builder contact = ProtoUserContactsImport.UserContactsImport.Contact.newBuilder();

            contact.setPhone(phone);
            contact.setFirstName(first_name);
            contact.setLastName(last_name);

            userContactsImport.setForce(force);
            userContactsImport.addContacts(i, contact);

        }

        if (G.localHashContact != null) userContactsImport.setContactHash(G.localHashContact);

        return userContactsImport;
    }
}