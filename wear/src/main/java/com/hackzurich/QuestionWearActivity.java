package com.hackzurich;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;

import com.hackzurich.model.stub.TestFactory;


public class QuestionWearActivity extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_wear_activity);

        sendSthToMobile();
    }


    private void sendSthToMobile() {
        MessageSender.sendRequest(this, TestFactory.newQuestion());
    }
}
