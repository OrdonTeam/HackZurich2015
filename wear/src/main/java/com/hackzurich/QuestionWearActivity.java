package com.hackzurich;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataMapItem;
import com.hackzurich.model.Question;
import com.hackzurich.model.communication.EventMetadata;
import com.hackzurich.model.stub.TestFactory;

import rx.Subscription;
import rx.functions.Action1;


public class QuestionWearActivity extends WearableActivity {
    private Subscription subscription;
    private GoogleObservableWrapper googleObservableWrapper = new GoogleObservableWrapper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_wear_activity);
    }

    private void subscribeToNewEvents() {
        subscription = googleObservableWrapper
                .emitEvents()
                .subscribe(onSuccess(), onError());
    }

    @NonNull
    private Action1<Throwable> onError() {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("kasper", throwable.getMessage());
            }
        };
    }

    @NonNull
    private Action1<DataEvent> onSuccess() {
        return new Action1<DataEvent>() {
            @Override
            public void call(DataEvent dataEvent) {
                onEventReceived(dataEvent);
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
        googleObservableWrapper.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        subscribeToNewEvents();
    }

    private void onEventReceived(DataEvent event) {
        DataMapItem dataItem = DataMapItem.fromDataItem(event.getDataItem());
        byte[] data = dataItem.getDataMap().getByteArray(EventMetadata.CONTENTS);
        Question question = Question.fromBytes(data);
        Log.d("kasper", "watch has receive a question + " + question);

        googleObservableWrapper.sendRequest(TestFactory.newQuestion());
    }
}
