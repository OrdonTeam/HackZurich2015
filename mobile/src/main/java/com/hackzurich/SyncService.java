package com.hackzurich;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.hackzurich.model.communication.EventMetadata;
import com.hackzurich.model.stub.TestFactory;

public class SyncService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private DataApi.DataListener dataListener;

    public SyncService(Context context) {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();
    }

    public void sync() {
        googleApiClient.connect();
    }

    public void onPause() {
        if (googleApiClient.isConnected()) {
            Wearable.DataApi.removeListener(googleApiClient, dataListener);
        }
        googleApiClient.disconnect();

    }

    @Override
    public void onConnected(Bundle bundle) {
        sendRequest();
        dataListener = new DataApi.DataListener() {
            @Override
            public void onDataChanged(DataEventBuffer dataEventBuffer) {
                Log.e("kasper", "nowa wiadomosc" + dataEventBuffer.toString());
            }
        };
        Wearable.DataApi.addListener(googleApiClient, dataListener);
        Log.e("kasper", "success");
    }

    private void sendRequest() {
        PutDataMapRequest dataMap = PutDataMapRequest.create(EventMetadata.WEAR_EVENT_PATH);
        byte[] bytes = TestFactory.newQuestion().toByteArray();
        dataMap.getDataMap().putByteArray(EventMetadata.CONTENTS, bytes);
        dataMap.getDataMap().putDouble(EventMetadata.TIMESTAMP, System.currentTimeMillis());
        PutDataRequest request = dataMap.asPutDataRequest();
        Wearable.DataApi.putDataItem(googleApiClient, request);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("kasper", "suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("kasper", "failed" + connectionResult.toString());
    }
}
