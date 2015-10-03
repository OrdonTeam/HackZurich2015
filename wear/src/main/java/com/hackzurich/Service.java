package com.hackzurich;

import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;
import com.hackzurich.model.Question;
import com.hackzurich.model.communication.EventMetadata;

public class Service extends WearableListenerService {
    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {

        for (DataEvent event : dataEvents) {
            Log.d("kasper", "Event received: " + event.getDataItem().getUri());
            String eventUri = event.getDataItem().getUri().toString();
            if (eventUri.contains(EventMetadata.WEAR_EVENT_PATH)) {
                DataMapItem dataItem = DataMapItem.fromDataItem(event.getDataItem());
                byte[] data = dataItem.getDataMap().getByteArray(EventMetadata.CONTENTS);
                Question question = Question.fromBytes(data);
                Log.d("kasper", "watch has receive a question + " + question);

            }
        }
        super.onDataChanged(dataEvents);
    }

}
