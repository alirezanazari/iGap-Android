/*
 * This is the source code of iGap for Android
 * It is licensed under GNU AGPL v3.0
 * You should have received a copy of the license in this archive (see LICENSE).
 * Copyright © 2017 , iGap - www.iGap.net
 * iGap Messenger | Free, Fast and Secure instant messaging application
 * The idea of the Kianiranian Company - www.kianiranian.com
 * All rights reserved.
 */

package net.iGap.observers.interfaces;

import net.iGap.proto.ProtoGlobal;
import net.iGap.proto.ProtoResponse;
import net.iGap.request.RequestClientGetRoomList;

import java.util.List;

public interface OnClientGetRoomListResponse {
    void onClientGetRoomList(List<ProtoGlobal.Room> roomList, ProtoResponse.Response response, RequestClientGetRoomList.IdentityGetRoomList identity);

    void onClientGetRoomListError(int majorCode, int minorCode);

    void onClientGetRoomListTimeout();
}
