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
import net.iGap.helper.avatar.AvatarHandler;
import net.iGap.helper.avatar.ParamWithAvatarType;
import net.iGap.proto.ProtoChannelAvatarDelete;

public class ChannelAvatarDeleteResponse extends MessageHandler {

    public int actionId;
    public Object message;
    public String identity;

    public ChannelAvatarDeleteResponse(int actionId, Object protoClass, String identity) {
        super(actionId, protoClass, identity);

        this.message = protoClass;
        this.actionId = actionId;
        this.identity = identity;
    }

    @Override
    public void handler() {
        super.handler();

        ProtoChannelAvatarDelete.ChannelAvatarDeleteResponse.Builder builder = (ProtoChannelAvatarDelete.ChannelAvatarDeleteResponse.Builder) message;
        if (G.onChannelAvatarDelete != null) {
            G.onChannelAvatarDelete.onChannelAvatarDelete(builder.getRoomId(), builder.getId());
        } else {
            if (G.currentActivity != null && !G.currentActivity.isFinishing()) {
                G.currentActivity.avatarHandler.avatarDelete(new ParamWithAvatarType(null, builder.getRoomId()).avatarType(AvatarHandler.AvatarType.ROOM), builder.getId());
            }
        }
    }

    @Override
    public void timeOut() {
        super.timeOut();
    }

    @Override
    public void error() {
        super.error();
    }
}


