package com.hackzurich;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.GridViewPager;

import com.hackzurich.model.Question;

public final class QuestionWearActivityDesign extends WearableActivity {

    private static final String QUESTION = "question";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_wear_activity);
        Question question = (Question) getIntent().getSerializableExtra(QUESTION);
        GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        pager.setAdapter(new QuestionWearPagerAdapter(getFragmentManager(), question));
    }

    public static void start(Context context, Question question) {
        Intent intent = new Intent(context, QuestionWearActivityDesign.class);
        //TODO: ADD REQUIRED FLAGS WHEN STARTED FROM SERVICE
        intent.putExtra(QUESTION, question);
        context.startActivity(intent);
    }
}
