package com.hackzurich;


import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.Wearable;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;


public class GoogleObservableWrapper {

    private GoogleApiClient googleApiClient;

    Observable<DataEventBuffer> eventsEmitter(Context context){
        return client(context).flatMap(new Func1<Bundle, Observable<DataEventBuffer>>() {
            @Override
            public Observable<DataEventBuffer> call(Bundle bundle) {
                return dataChanged();
            }
        });
    }

    private Observable<DataEventBuffer> dataChanged() {
        return Observable.create(new Observable.OnSubscribe<DataEventBuffer>() {

            @Override
            public void call(final Subscriber<? super DataEventBuffer> subscriber) {

                Wearable.DataApi.addListener(googleApiClient, new DataApi.DataListener() {
                    @Override
                    public void onDataChanged(DataEventBuffer dataEventBuffer) {
                        subscriber.onNext(dataEventBuffer);
                    }
                });
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
