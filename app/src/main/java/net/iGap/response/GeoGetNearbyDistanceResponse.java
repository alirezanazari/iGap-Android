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
import net.iGap.module.accountManager.AccountManager;
import net.iGap.module.accountManager.DbManager;
import net.iGap.observers.interfaces.OnInfo;
import net.iGap.proto.ProtoGeoGetNearbyDistance;
import net.iGap.realm.RealmGeoNearbyDistance;
import net.iGap.realm.RealmRegisteredInfo;

public class GeoGetNearbyDistanceResponse extends MessageHandler {

    public int actionId;
    public Object message;
    public String identity;

    public GeoGetNearbyDistanceResponse(int actionId, Object protoClass, String identity) {
        super(actionId, protoClass, identity);

        this.message = protoClass;
        this.actionId = actionId;
        this.identity = identity;
    }

    @Override
    public void handler() {
        super.handler();

        ProtoGeoGetNearbyDistance.GeoGetNearbyDistanceResponse.Builder builder = (ProtoGeoGetNearbyDistance.GeoGetNearbyDistanceResponse.Builder) message;

        for (final ProtoGeoGetNearbyDistance.GeoGetNearbyDistanceResponse.Result result : builder.getResultList()) {
            if (AccountManager.getInstance().getCurrentUser().getId() != result.getUserId()) { // don't show my account
                RealmRegisteredInfo.getRegistrationInfo(result.getUserId(), new OnInfo() {
                    @Override
                    public void onInfo(Long registeredId) {
                        DbManager.getInstance().doRealmTransaction(realm -> {
                            RealmGeoNearbyDistance geoNearbyDistance = new RealmGeoNearbyDistance();
                            geoNearbyDistance.setUserId(result.getUserId());
                            geoNearbyDistance.setHasComment(result.getHasComment());
                            geoNearbyDistance.setDistance(result.getDistance());
                            realm.copyToRealmOrUpdate(geoNearbyDistance);
                        });
                    }
                });
            }
        }

        G.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (G.onMapUsersGet != null) {
                    G.onMapUsersGet.onMapUsersGet();
                }
            }
        }, 250);


    }

    @Override
    public void timeOut() {
        super.timeOut();
    }

    @Override
    public void error() {
        super.error();
        if (G.onMapUsersGet != null) {
            G.onMapUsersGet.onMapUsersGet();
        }
    }
}


