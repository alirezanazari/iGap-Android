/*
 * This is the source code of iGap for Android
 * It is licensed under GNU AGPL v3.0
 * You should have received a copy of the license in this archive (see LICENSE).
 * Copyright © 2017 , iGap - www.iGap.net
 * iGap Messenger | Free, Fast and Secure instant messaging application
 * The idea of the Kianiranian Company - www.kianiranian.com
 * All rights reserved.
 */

package net.iGap.realm;

import net.iGap.helper.HelperString;
import net.iGap.module.AppUtils;
import net.iGap.module.StringListParcelConverter;
import net.iGap.module.accountManager.DbManager;
import net.iGap.proto.ProtoGlobal;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.net_iGap_realm_RealmRoomMessageContactRealmProxy;

@Parcel(implementations = {net_iGap_realm_RealmRoomMessageContactRealmProxy.class}, value = Parcel.Serialization.BEAN, analyze = {RealmRoomMessageContact.class})
public class RealmRoomMessageContact extends RealmObject {

    private String firstName;
    private String lastName;
    private String nickName;
    private RealmList<RealmString> phones = new RealmList<>();
    private RealmList<RealmString> emails = new RealmList<>();
    @PrimaryKey
    private long id;

    public static RealmRoomMessageContact put(Realm realm, final ProtoGlobal.RoomMessageContact input) {
        RealmRoomMessageContact messageContact = realm.createObject(RealmRoomMessageContact.class, AppUtils.makeRandomId());
        messageContact.setLastName(input.getLastName());
        messageContact.setFirstName(input.getFirstName());
        messageContact.setNickName(input.getNickname());
        for (String phone : input.getPhoneList()) {
            messageContact.addPhone(phone);
        }
        for (String email : input.getEmailList()) {
            messageContact.addEmail(email);
        }

        return messageContact;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        try {
            this.firstName = firstName;
        } catch (Exception e) {
            this.firstName = HelperString.getUtf8String(firstName);
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        try {
            this.lastName = lastName;
        } catch (Exception e) {
            this.lastName = HelperString.getUtf8String(lastName);
        }
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        try {
            this.nickName = nickName;
        } catch (Exception e) {
            this.nickName = HelperString.getUtf8String(nickName);
        }
    }

    public RealmList<RealmString> getPhones() {
        return phones;
    }

    @ParcelPropertyConverter(StringListParcelConverter.class)
    public void setPhones(RealmList<RealmString> phones) {
        this.phones = phones;
    }

    public void addPhone(String phone) {
        DbManager.getInstance().doRealmTask(realm -> {
            phones.add(RealmString.string(realm, phone));
        });
    }

    public void addEmail(String email) {
        DbManager.getInstance().doRealmTask(realm -> {
            phones.add(RealmString.string(realm, email));
        });
    }

    public String getLastPhoneNumber() {
        if (phones == null || phones.isEmpty()) {
            return null;
        }
        return phones.last().getString();
    }

    public RealmList<RealmString> getEmails() {
        return emails;
    }

    @ParcelPropertyConverter(StringListParcelConverter.class)
    public void setEmails(RealmList<RealmString> emails) {
        this.emails = emails;
    }
}
