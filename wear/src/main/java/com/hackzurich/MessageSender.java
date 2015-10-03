package com.hackzurich;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.hackzurich.model.Question;
import com.hackzurich.model.communication.EventMetadata;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class MessageSender {

    private GoogleApiClient googleApiClient;
    private final Context context;

    public MessageSender(Context context) {
        this.context = context;
    }

    private Observable<MessageSender> connect() {
        return Observable.create(new Observable.OnSubscribe<MessageSender>() {
            @Override
            public void call(final Subscriber<? super MessageSender> subscriber) {
                googleApiClient = new GoogleApiClient.Builder(context)
                        .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                            @Override
                            public void onConnected(Bundle bundle) {
                                subscriber.onNext(MessageSender.this);
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onConnectionSuspended(int i) {
                                subscriber.onError(new RuntimeException("suspended and i=" + i));
                            }
                        })
                        .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(ConnectionResult connectionResult) {
                                Log.e("kasper", connectionResult.toString());
                                subscriber.onError(new RuntimeException(connectionResult.toString()));
                            }
                        })
                        .addApi(Wearable.API)
                        .build();
                googleApiClient.connect();
            }
        });
    }

    public static void sendRequest(Context context, final Question question) {
        new MessageSender(context).connect().subscribe(new Action1<MessageSender>() {
            @Override
            public void call(MessageSender messageSender) {
                messageSender.sendImpl(question);
                messageSender.googleApiClient.disconnect();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("kasper", "sth goes wrong" + throwable.getMessage());
            }
        });
    }

    private void sendImpl(Question question) {
        byte[] bytes = question.toByteArray();
        PutDataMapRequest dataMap = PutDataMapRequest.create(EventMetadata.MOBILE_EVENT_PATH);
        dataMap.getDataMap().putByteArray(EventMetadata.CONTENTS, bytes);
        dataMap.getDataMap().putDouble(EventMetadata.TIMESTAMP, System.currentTimeMillis());
        PutDataRequest request = dataMap.asPutDataRequest();
        Wearable.DataApi.putDataItem(googleApiClient, request);
    }
}
