package com.hackzurich;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;

import com.hackzurich.model.stub.TestFactory;

import rx.functions.Action1;


public class QuestionWearActivity extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_wear_activity);

        sendSthToMobile();
    }


    private void sendSthToMobile() {
        MessageSender.getInstance(this).subscribe(new Action1<MessageSender>() {
            @Override
            public void call(MessageSender messageSender) {
                messageSender.sendRequest(TestFactory.newQuestion());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("kasper", "sth goes wrong" + throwable.getMessage());
            }
        });
    }
}
