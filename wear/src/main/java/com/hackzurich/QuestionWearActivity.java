package com.hackzurich;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;

import rx.functions.Action1;


public class QuestionWearActivity extends WearableActivity {
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_wear_activity);

        new GoogleObservableWrapper().eventsEmitter(this).subscribe(
                new Action1<DataEventBuffer>() {
                    @Override
                    public void call(DataEventBuffer dataEvents) {
                        onDataEventBufferReceived(dataEvents);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("kasper", throwable.getMessage());
                    }
                });
    }

    private void onDataEventBufferReceived(DataEventBuffer dataEvents) {
        for (DataEvent event : dataEvents) {
            Log.d("[DEBUG] onDataChanged",
                    "Event received: " + event.getDataItem().getUri());

            String eventUri = event.getDataItem().getUri().toString();

            if (eventUri.contains("/myapp/myevent")) {

                DataMapItem dataItem = DataMapItem.fromDataItem(event.getDataItem());
                String[] data = dataItem.getDataMap().getStringArray("contents");

                Log.d("[DEBUG] Dev", "Sending timeline to the listener + " + data);

            }
        }
    }
}
