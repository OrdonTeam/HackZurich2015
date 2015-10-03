package com.hackzurich;


import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.hackzurich.model.communication.EventMetadata;
import com.hackzurich.model.stub.TestFactory;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;


public class GoogleObservableWrapper {

    private final Context context;
    private GoogleApiClient googleApiClient;
    private DataApi.DataListener dataListener;

    public GoogleObservableWrapper(Context context) {
        this.context = context;
    }

    Observable<DataEventBuffer> emitEvents() {
        return client(context).flatMap(new Func1<Bundle, Observable<DataEventBuffer>>() {
            @Override
            public Observable<DataEventBuffer> call(Bundle bundle) {
                return dataChanged();
            }
        });
    }

    void onPause() {
        if (googleApiClient != null) {
            Wearable.DataApi.removeListener(googleApiClient, dataListener);
            googleApiClient.disconnect();
        }
    }

    public void sendRequest() {
        byte[] bytes = TestFactory.newQuestion().toByteArray();
        PutDataMapRequest dataMap = PutDataMapRequest.create(EventMetadata.MOBILE_EVENT_PATH);
        dataMap.getDataMap().putByteArray(EventMetadata.CONTENTS, bytes);
        dataMap.getDataMap().putDouble(EventMetadata.TIMESTAMP, System.currentTimeMillis());
        PutDataRequest request = dataMap.asPutDataRequest();
        Wearable.DataApi.putDataItem(googleApiClient, request);
    }


    private Observable<DataEventBuffer> dataChanged() {
        return Observable.create(new Observable.OnSubscribe<DataEventBuffer>() {
            @Override
            public void call(final Subscriber<? super DataEventBuffer> subscriber) {
                dataListener = new DataApi.DataListener() {
                    @Override
                    public void onDataChanged(DataEventBuffer dataEventBuffer) {
                        subscriber.onNext(dataEventBuffer);
                    }
                };
                Wearable.DataApi.addListener(googleApiClient, dataListener);
            }
        });
    }

    private Observable<Bundle> client(final Context context) {
        return Observable.create(new Observable.OnSubscribe<Bundle>() {
            @Override
            public void call(final Subscriber<? super Bundle> subscriber) {
                googleApiClient = new GoogleApiClient.Builder(context)
                        .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                            @Override
                            public void onConnected(Bundle bundle) {
                                subscriber.onNext(bundle);
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
                                subscriber.onError(new RuntimeException(connectionResult.toString()));
                            }
                        })
                        .addApi(Wearable.API)
                        .build();
                googleApiClient.connect();
            }
        });
    }
}
