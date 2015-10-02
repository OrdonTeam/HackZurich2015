package com.hackzurich;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;


public class QuestionWearActivity extends WearableActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, DataApi.DataListener {

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_wear_activity);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();

        googleApiClient.connect();

    }

    @Override
    public void onConnected(Bundle bundle) {

        Wearable.DataApi.addListener(googleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {

        for (DataEvent event: dataEvents) {

            Log.d("[DEBUG] onDataChanged",
                    "Event received: " + event.getDataItem().getUri());

            String eventUri = event.getDataItem().getUri().toString();

            if (eventUri.contains ("/myapp/myevent")) {

                DataMapItem dataItem = DataMapItem.fromDataItem (event.getDataItem());
                String[] data = dataItem.getDataMap().getStringArray("contents");

                Log.d("[DEBUG] Dev", "Sending timeline to the listener + " + data);

            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
