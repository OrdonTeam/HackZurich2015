package com.hackzurich;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;

public class QuestionWearActivity extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_wear_activity);
    }
}
