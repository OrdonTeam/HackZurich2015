package com.hackzurich.connection;

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

public final class Sender implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private final GoogleApiClient googleApiClient;
    private final Question question;

    public Sender(Context context, Question question) {
        this.question = question;
        this.googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();
    }

    public void send() {
        googleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        byte[] bytes = question.toByteArray();
        Wearable.DataApi.putDataItem(googleApiClient, wrapWithPutDataRequest(bytes));
    }

    private PutDataRequest wrapWithPutDataRequest(byte[] bytes) {
        PutDataMapRequest dataMap = PutDataMapRequest.create(EventMetadata.WEAR_EVENT_PATH);
        dataMap.getDataMap().putByteArray(EventMetadata.CONTENTS, bytes);
        dataMap.getDataMap().putDouble(EventMetadata.TIMESTAMP, System.currentTimeMillis());
        return dataMap.asPutDataRequest();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("GoogleApiClient", "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("GoogleApiClient", "onConnectionFailed: " + connectionResult.toString());
    }
}
