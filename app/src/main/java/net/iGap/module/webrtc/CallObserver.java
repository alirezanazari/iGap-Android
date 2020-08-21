/*
 * This is the source code of iGap for Android
 * It is licensed under GNU AGPL v3.0
 * You should have received a copy of the license in this archive (see LICENSE).
 * Copyright © 2017 , iGap - www.iGap.net
 * iGap Messenger | Free, Fast and Secure instant messaging application
 * The idea of the Kianiranian Company - www.kianiranian.com
 * All rights reserved.
 */

package net.iGap.module.webrtc;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

import net.iGap.G;
import net.iGap.R;
import net.iGap.activities.ActivityCall;
import net.iGap.helper.HelperError;
import net.iGap.module.enums.CallState;
import net.iGap.observers.interfaces.ISignalingAccept;
import net.iGap.observers.interfaces.ISignalingCandidate;
import net.iGap.observers.interfaces.ISignalingErrore;
import net.iGap.observers.interfaces.ISignalingLeave;
import net.iGap.observers.interfaces.ISignalingOffer;
import net.iGap.observers.interfaces.ISignalingRinging;
import net.iGap.observers.interfaces.ISignalingSessionHold;
import net.iGap.proto.ProtoSignalingLeave;
import net.iGap.proto.ProtoSignalingOffer;
import net.iGap.request.RequestSignalingRinging;

import org.webrtc.IceCandidate;
import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;

import static net.iGap.G.iSignalingAccept;
import static net.iGap.G.iSignalingCandidate;
import static net.iGap.G.iSignalingLeave;
import static net.iGap.G.iSignalingOffer;
import static net.iGap.G.iSignalingRinging;
import static net.iGap.G.iSignalingSessionHold;
import static org.webrtc.SessionDescription.Type.ANSWER;
import static org.webrtc.SessionDescription.Type.OFFER;

@Deprecated
public class CallObserver implements ISignalingOffer, ISignalingErrore, ISignalingRinging, ISignalingAccept, ISignalingCandidate, ISignalingLeave, ISignalingSessionHold {

    public CallObserver() {
        iSignalingOffer = this;
        iSignalingRinging = this;
        iSignalingAccept = this;
        iSignalingCandidate = this;
        iSignalingLeave = this;
        iSignalingSessionHold = this;
        G.iSignalingErrore = this;
    }

    // done
    @Override
    public void onOffer(final long called_userId, ProtoSignalingOffer.SignalingOffer.Type type, final String callerSdp) {

        if (type == ProtoSignalingOffer.SignalingOffer.Type.SECRET_CHAT || type == ProtoSignalingOffer.SignalingOffer.Type.SCREEN_SHARING) {
            return;
        }
        WebRTC.getInstance().setCallType(type);
        if (type == ProtoSignalingOffer.SignalingOffer.Type.VIDEO_CALLING) {
            G.isVideoCallRinging = true;
        }

        new RequestSignalingRinging().signalingRinging();

        G.handler.post(new Runnable() {
            @Override
            public void run() {


                WebRTC.getInstance().peerConnectionInstance().setRemoteDescription(new SdpObserver() {
                    @Override
                    public void onCreateSuccess(SessionDescription sessionDescription) {

                    }

                    @Override
                    public void onSetSuccess() {
//                        CallSelectFragment.call(called_userId, true, type);
                    }

                    @Override
                    public void onCreateFailure(String s) {
                        Log.i("WWW", "onOffer onCreateFailure : " + s);
                    }

                    @Override
                    public void onSetFailure(String s) {
                        Log.i("WWW", "onOffer onSetFailure : " + s);
                    }
                }, new SessionDescription(OFFER, callerSdp));
            }

        });
    }

    // done
    @Override
    public void onAccept(final String called_sdp) {
        G.handler.post(new Runnable() {
            @Override
            public void run() {
                WebRTC.getInstance().setOfferLocalDescription();

                WebRTC.getInstance().peerConnectionInstance().setRemoteDescription(new SdpObserver() {
                    @Override
                    public void onCreateSuccess(SessionDescription sessionDescription) {

                    }

                    @Override
                    public void onSetSuccess() {

                        G.isVideoCallRinging = false;
                        try {
                            AudioManager am = (AudioManager) G.context.getSystemService(Context.AUDIO_SERVICE);
                            G.mainRingerMode = am.getRingerMode();
                            G.appChangeRinggerMode = true;
                            am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                        } catch (Exception e) {
                        }
                        if (G.videoCallListener != null) {

                            G.videoCallListener.notifyBackgroundChange();
                        }
                        Log.i("WWW", "onSetSuccess");
                    }

                    @Override
                    public void onCreateFailure(String s) {
                        Log.i("WWW", "onAccept onCreateFailure : " + s);
                    }

                    @Override
                    public void onSetFailure(String s) {
                        Log.i("WWW", "onAccept onSetFailure : " + s);
                    }
                }, new SessionDescription(ANSWER, called_sdp));
            }
        });
    }

    //done
    @Override
    public void onCandidate(final String peerSdpMId, final int peerSdpMLineIndex, final String peerCandidate) {
        G.handler.post(new Runnable() {
            @Override
            public void run() {
                Log.i("WWW_Candidate", "onCandidate server : " + peerCandidate);
                WebRTC.getInstance().peerConnectionInstance().addIceCandidate(new IceCandidate(peerSdpMId, peerSdpMLineIndex, peerCandidate));

            }
        });
    }

    //done
    @Override
    public void onLeave(final ProtoSignalingLeave.SignalingLeaveResponse.Type type) {

        try {
            G.isWebRtcConnected = false;
            G.handler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    WebRTC.getInstance().close();
                    try {
                        AudioManager am = (AudioManager) G.context.getSystemService(Context.AUDIO_SERVICE);
                        G.appChangeRinggerMode = false;
                        am.setRingerMode(G.mainRingerMode);
                    } catch (Exception e) {
                    }


                    if (G.iSignalingCallBack != null) {
                        G.isVideoCallRinging = false;
                        switch (type) {

                            case REJECTED:
                                G.iSignalingCallBack.onStatusChanged(CallState.REJECT);
                                break;
                            case NOT_ANSWERED:
                                G.iSignalingCallBack.onStatusChanged(CallState.NOT_ANSWERED);
                                break;
                            case UNAVAILABLE:
                                G.iSignalingCallBack.onStatusChanged(CallState.UNAVAILABLE);
                                break;
                            case TOO_LONG:
                                G.iSignalingCallBack.onStatusChanged(CallState.TOO_LONG);
                                break;
                        }
                    }

                    if (G.onCallLeaveView != null) {
                        G.onCallLeaveView.onLeaveView("");

                    }
                }
            }, 2000);
        } catch (Exception e) {
            // TODO: 5/9/2020 should not be here 
            WebRTC.getInstance().leaveCall();
        }


    }

    // done
    @Override
    public void onRinging() {

        G.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (G.iSignalingCallBack != null) {
                    G.iSignalingCallBack.onStatusChanged(CallState.RINGING);
                }
            }
        }, 1000);
    }

    //done
    @Override
    public void onHold(Boolean hold) {
        G.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (G.iSignalingCallBack != null) {
                    if (hold) {
                        G.iSignalingCallBack.onStatusChanged(CallState.ON_HOLD);
                        if (G.onHoldBackgroundChanegeListener != null) {
                            G.onHoldBackgroundChanegeListener.notifyBakcgroundChanege(true);
                        }
                    } else {
                        G.iSignalingCallBack.onStatusChanged(CallState.CONNECTED);
                        if (G.onHoldBackgroundChanegeListener != null) {
                            G.onHoldBackgroundChanegeListener.notifyBakcgroundChanege(false);
                        }
                    }

                }
            }
        }, 1000);
    }

    @Override
    public void onErrore(int major, int minor) {
        ActivityCall.allowOpenCall = false;
        String message = G.context.getString(R.string.e_call_permision);

        switch (major) {

            case 904:

                if (minor == 6) {
                    message = G.context.getString(R.string.e_904_6);
                    if (G.iSignalingCallBack != null) {
                        G.iSignalingCallBack.onStatusChanged(CallState.UNAVAILABLE);
                    }
                } else if (minor == 7) {
                    message = G.context.getString(R.string.e_904_7);
                    if (G.iSignalingCallBack != null) {
                        G.iSignalingCallBack.onStatusChanged(CallState.UNAVAILABLE);
                    }
                } else if (minor == 8) {
                    message = G.context.getString(R.string.e_904_8);
                    if (G.iSignalingCallBack != null) {
                        G.iSignalingCallBack.onStatusChanged(CallState.UNAVAILABLE);
                    }
                } else if (minor == 9) {
                    message = G.context.getString(R.string.e_904_9);
                    if (G.iSignalingCallBack != null) {
                        G.iSignalingCallBack.onStatusChanged(CallState.BUSY);
                    }
                } else {
                    if (G.iSignalingCallBack != null) {
                        G.iSignalingCallBack.onStatusChanged(CallState.UNAVAILABLE);
                    }
                }
                break;
            case 905:
            case 906:
                message = G.context.getString(R.string.e_906_1);
                break;

        }

        if (G.onCallLeaveView != null) {
            G.onCallLeaveView.onLeaveView("error");
        }

        final String finalMessage = message;
        G.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                HelperError.showSnackMessage(finalMessage, false);
            }
        }, 2500);


    }
}
