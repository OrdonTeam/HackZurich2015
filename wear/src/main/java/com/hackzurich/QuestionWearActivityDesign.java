package com.hackzurich;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.GridViewPager;

import com.hackzurich.model.Question;
import com.hackzurich.model.stub.TestFactory;

public final class QuestionWearActivityDesign extends WearableActivity {

    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_wear_activity);
        question = TestFactory.newQuestion();
        GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        pager.setAdapter(new QuestionWearPagerAdapter(getFragmentManager(), question));
    }
}
