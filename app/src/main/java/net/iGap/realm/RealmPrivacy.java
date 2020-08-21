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

import net.iGap.module.accountManager.DbManager;
import net.iGap.proto.ProtoGlobal;
import net.iGap.request.RequestUserPrivacyGetRule;
import net.iGap.request.RequestUserPrivacySetRule;

import io.realm.Realm;
import io.realm.RealmObject;

public class RealmPrivacy extends RealmObject {

    private String whoCanSeeMyAvatar;
    private String whoCanInviteMeToChannel;
    private String whoCanInviteMeToGroup;
    private String whoCanSeeMyLastSeen;
    private String whoCanVoiceCallToMe;
    private String whoCanVideoCallToMe;

    public static void updatePrivacy(final String avatar, final String channel, final String Group, final String lastSeen, final String voiceCall, final String videoCall) {
        DbManager.getInstance().doRealmTask(_realm -> {
            _realm.executeTransactionAsync(realm -> {
                final RealmPrivacy realmPrivacy = realm.where(RealmPrivacy.class).findFirst();
                if (realmPrivacy != null) {
                    if (avatar.length() > 0) realmPrivacy.setWhoCanSeeMyAvatar(avatar);
                    if (channel.length() > 0) realmPrivacy.setWhoCanInviteMeToChannel(channel);
                    if (Group.length() > 0) realmPrivacy.setWhoCanInviteMeToGroup(Group);
                    if (lastSeen.length() > 0) realmPrivacy.setWhoCanSeeMyLastSeen(lastSeen);
                    if (voiceCall.length() > 0) realmPrivacy.setWhoCanVoiceCallToMe(voiceCall);
                    if (videoCall.length() > 0) realmPrivacy.setWhoCanVideoCallToMe(videoCall);
                } else {
                    RealmPrivacy realmPrivacy1 = realm.createObject(RealmPrivacy.class);

                    if (avatar.length() > 0) realmPrivacy1.setWhoCanSeeMyAvatar(avatar);
                    if (channel.length() > 0) realmPrivacy1.setWhoCanInviteMeToChannel(channel);
                    if (Group.length() > 0) realmPrivacy1.setWhoCanInviteMeToGroup(Group);
                    if (lastSeen.length() > 0) realmPrivacy1.setWhoCanSeeMyLastSeen(lastSeen);
                    if (voiceCall.length() > 0) realmPrivacy1.setWhoCanVoiceCallToMe(voiceCall);
                    if (videoCall.length() > 0) realmPrivacy1.setWhoCanVideoCallToMe(videoCall);
                }
            });
        });
    }

    public static void fillWithDefaultValues(Realm.Transaction.OnSuccess callback) {
        DbManager.getInstance().doRealmTask(_realm -> {
            _realm.executeTransactionAsync(realm -> {
                final RealmPrivacy realmPrivacy1 = realm.where(RealmPrivacy.class).findFirst();
                if (realmPrivacy1 == null) {
                    RealmPrivacy realmPrivacy = realm.createObject(RealmPrivacy.class);
                    String role = ProtoGlobal.PrivacyLevel.ALLOW_ALL.toString();
                    realmPrivacy.setWhoCanSeeMyAvatar(role);
                    realmPrivacy.setWhoCanInviteMeToChannel(role);
                    realmPrivacy.setWhoCanInviteMeToGroup(role);
                    realmPrivacy.setWhoCanSeeMyLastSeen(role);
                    realmPrivacy.setWhoCanVoiceCallToMe(role);
                    realmPrivacy.setWhoCanVideoCallToMe(role);
                }
            }, callback);
        });
    }

    public static void sendUpdatePrivacyToServer(ProtoGlobal.PrivacyType privacyType, ProtoGlobal.PrivacyLevel privacyLevel) {

        new RequestUserPrivacySetRule().userPrivacySetRule(privacyType, privacyLevel);
    }

    public static void getUpdatePrivacyFromServer() {

        new RequestUserPrivacyGetRule().userPrivacyGetRule(ProtoGlobal.PrivacyType.AVATAR);
        new RequestUserPrivacyGetRule().userPrivacyGetRule(ProtoGlobal.PrivacyType.CHANNEL_INVITE);
        new RequestUserPrivacyGetRule().userPrivacyGetRule(ProtoGlobal.PrivacyType.GROUP_INVITE);
        new RequestUserPrivacyGetRule().userPrivacyGetRule(ProtoGlobal.PrivacyType.USER_STATUS);
        new RequestUserPrivacyGetRule().userPrivacyGetRule(ProtoGlobal.PrivacyType.VOICE_CALLING);
        new RequestUserPrivacyGetRule().userPrivacyGetRule(ProtoGlobal.PrivacyType.VIDEO_CALLING);
    }

    public static void updateRealmPrivacy(ProtoGlobal.PrivacyType privacyType, ProtoGlobal.PrivacyLevel privacyLevel) {

        switch (privacyType) {

            case AVATAR:
                RealmPrivacy.updatePrivacy(privacyLevel.toString(), "", "", "", "", "");
                break;
            case CHANNEL_INVITE:
                RealmPrivacy.updatePrivacy("", privacyLevel.toString(), "", "", "", "");
                break;
            case GROUP_INVITE:
                RealmPrivacy.updatePrivacy("", "", privacyLevel.toString(), "", "", "");
                break;
            case USER_STATUS:
                RealmPrivacy.updatePrivacy("", "", "", privacyLevel.toString(), "", "");
                break;
            case VOICE_CALLING:
                RealmPrivacy.updatePrivacy("", "", "", "", privacyLevel.toString(), "");
                break;
            case VIDEO_CALLING:
                RealmPrivacy.updatePrivacy("", "", "", "", "", privacyLevel.toString());
                break;

        }
    }

    public String getWhoCanSeeMyAvatar() {
        return whoCanSeeMyAvatar;
    }

    public void setWhoCanSeeMyAvatar(String whoCanSeeMyAvatar) {
        this.whoCanSeeMyAvatar = whoCanSeeMyAvatar;
    }

    public String getWhoCanSeeMyLastSeen() {
        return whoCanSeeMyLastSeen;
    }

    public void setWhoCanSeeMyLastSeen(String whoCanSeeMyLastSeen) {
        this.whoCanSeeMyLastSeen = whoCanSeeMyLastSeen;
    }

    public String getWhoCanInviteMeToGroup() {
        return whoCanInviteMeToGroup;
    }

    public void setWhoCanInviteMeToGroup(String whoCanInviteMeToGroup) {
        this.whoCanInviteMeToGroup = whoCanInviteMeToGroup;
    }

    //********************************************************************************************************************************************

    public String getWhoCanInviteMeToChannel() {
        return whoCanInviteMeToChannel;
    }

    public void setWhoCanInviteMeToChannel(String whoCanInviteMeToChannel) {
        this.whoCanInviteMeToChannel = whoCanInviteMeToChannel;
    }

    public String getWhoCanVoiceCallToMe() {
        return whoCanVoiceCallToMe;
    }

    public void setWhoCanVoiceCallToMe(String whoCanVoiceCallToMe) {
        this.whoCanVoiceCallToMe = whoCanVoiceCallToMe;
    }

    public String getWhoCanVideoCallToMe() {
        return whoCanVideoCallToMe;
    }

    public void setWhoCanVideoCallToMe(String whoCanVideoCallToMe) {
        this.whoCanVideoCallToMe = whoCanVideoCallToMe;
    }
}
