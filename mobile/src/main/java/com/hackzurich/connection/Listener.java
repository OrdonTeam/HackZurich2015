package com.hackzurich.connection;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;
import com.hackzurich.model.communication.EventMetadata;
import com.hackzurich.service.UpdateDataService;

public final class Listener implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private final GoogleApiClient googleApiClient;
    private final UpdateDataService updateDataService;
    private DataApi.DataListener dataListener = new DataListener();

    public Listener(Context context) {
        this.updateDataService = new UpdateDataService(context);
        this.googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();
    }

    public void start() {
        googleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Wearable.DataApi.addListener(googleApiClient, dataListener);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("GoogleApiClient", "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("GoogleApiClient", "onConnectionFailed: " + connectionResult.toString());
    }

    private class DataListener implements DataApi.DataListener {
        @Override
        public void onDataChanged(DataEventBuffer dataEventBuffer) {
            Log.e("onDataChanged", "dataEventBuffer:" + dataEventBuffer.toString());
            for (DataEvent event : dataEventBuffer) {
                handleEvent(event);
            }
        }

        private void handleEvent(DataEvent event) {
            Log.d("handleEvent", "DataEvent: " + event.getDataItem().getUri());
            String eventUri = event.getDataItem().getUri().toString();
            if (eventUri.contains(EventMetadata.MOBILE_EVENT_PATH)) {
                DataMapItem dataItem = DataMapItem.fromDataItem(event.getDataItem());
                String questionId = dataItem.getDataMap().getString(EventMetadata.QUESTION_ID);
                boolean wasCorrect = dataItem.getDataMap().getBoolean(EventMetadata.WAS_CORRECT, false);
                updateDataService.update(questionId, wasCorrect);
                Log.d("kasper", "mobile has receive a question + " + questionId);
            }
        }
    }
}
