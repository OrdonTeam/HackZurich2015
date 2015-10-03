package com.hackzurich;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.hackzurich.model.TestWrapper;
import com.hackzurich.model.data.TestData;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.hackzurich.model.data.QuestionDataStatus.EASY;
import static com.hackzurich.model.data.QuestionDataStatus.HARD;
import static com.hackzurich.model.data.QuestionDataStatus.MEDIUM;

public final class StartLearn extends Activity {

    public static final String TEST_KEY = "test_wrapper";

    private TestWrapper testWrapper;

    @Bind(R.id.easy)
    TextView easyView;
    @Bind(R.id.medium)
    TextView mediumView;
    @Bind(R.id.hard)
    TextView hardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_learn_activity);
        ButterKnife.bind(this);
        testWrapper = (TestWrapper) getIntent().getSerializableExtra(TEST_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TestData testData = testWrapper.getTestData();
        hardView.setText("Easy (" + testData.countOf(EASY) + ")");
        hardView.setText("Medium (" + testData.countOf(MEDIUM) + ")");
        hardView.setText("Hard (" + testData.countOf(HARD) + ")");
    }

    public static void start(Context context, TestWrapper test) {
        Intent intent = new Intent(context, StartLearn.class);
        intent.putExtra(TEST_KEY, test);
        context.startActivity(intent);
    }
}
