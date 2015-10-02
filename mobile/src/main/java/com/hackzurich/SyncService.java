package com.hackzurich;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import rx.Observable;

public class SyncService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private GoogleApiClient googleApiClient;

    public SyncService(Context context) {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();
    }

    public void sync(){
        googleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        String[] contents = new String[]{"data1", "data2", "data3"};
        PutDataMapRequest dataMap = PutDataMapRequest.create("/myapp/myevent");
        dataMap.getDataMap().putStringArray("contents", contents);
        dataMap.getDataMap().putDouble("timestamp", System.currentTimeMillis());

        PutDataRequest request = dataMap.asPutDataRequest();

        Wearable.DataApi
                .putDataItem(googleApiClient, request);
        Log.e("kasper", "success");
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
