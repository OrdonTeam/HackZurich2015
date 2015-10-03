package com.hackzurich;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.hackzurich.model.Question;
import com.hackzurich.model.communication.EventMetadata;

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

    Observable<DataEvent> emitEvents() {
        return client(context).flatMap(new Func1<Bundle, Observable<DataEvent>>() {
            @Override
            public Observable<DataEvent> call(Bundle bundle) {
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

    public void sendRequest(Question question) {
        byte[] bytes = question.toByteArray();
        PutDataMapRequest dataMap = PutDataMapRequest.create(EventMetadata.MOBILE_EVENT_PATH);
        dataMap.getDataMap().putByteArray(EventMetadata.CONTENTS, bytes);
        dataMap.getDataMap().putDouble(EventMetadata.TIMESTAMP, System.currentTimeMillis());
        PutDataRequest request = dataMap.asPutDataRequest();
        Wearable.DataApi.putDataItem(googleApiClient, request);
    }


    private Observable<DataEvent> dataChanged() {
        return Observable.create(new Observable.OnSubscribe<DataEvent>() {
            @Override
            public void call(final Subscriber<? super DataEvent> subscriber) {
                dataListener = new DataApi.DataListener() {
                    @Override
                    public void onDataChanged(DataEventBuffer dataEventBuffer) {
                        for (DataEvent event : dataEventBuffer) {
                            Log.d("kasper", "Event received: " + event.getDataItem().getUri());
                            String eventUri = event.getDataItem().getUri().toString();
                            if (eventUri.contains(EventMetadata.WEAR_EVENT_PATH)) {
                                subscriber.onNext(event);
                            }
                        }
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
