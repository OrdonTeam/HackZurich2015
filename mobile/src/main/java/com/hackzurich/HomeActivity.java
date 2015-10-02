package com.hackzurich;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();

        googleApiClient.connect();

    }

    @OnClick(R.id.download)
    public void onDownload() {
        Toast.makeText(this, "On download", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.learn)
    public void onLearn() {
        startActivity(new Intent(this, StartLearn.class));
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
