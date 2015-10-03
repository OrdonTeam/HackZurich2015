package com.hackzurich;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hackzurich.model.TestWrapper;
import com.hackzurich.model.data.QuestionDataStatus;
import com.hackzurich.model.data.TestData;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @Bind(R.id.start_learn)
    View startLearnView;

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
        easyView.setText("Easy (" + testData.countOf(EASY) + ")");
        mediumView.setText("Medium (" + testData.countOf(MEDIUM) + ")");
        hardView.setText("Hard (" + testData.countOf(HARD) + ")");
    }

    @OnClick({R.id.easy, R.id.medium, R.id.hard})
    public void onEasy(View view) {
        view.setSelected(!view.isSelected());
        startLearnView.setEnabled(isAtLeastOneQuestion());
    }

    private boolean isAtLeastOneQuestion() {
        return countSelectedQuestion() > 0;
    }

    @OnClick(R.id.start_learn)
    public void onLearn() {
        List<QuestionDataStatus> questionsTypes = addSelectedTypes();
        LearnActivity.start(this, testWrapper, questionsTypes);
        finish();
    }

    private int countSelectedQuestion() {
        TestData testData = testWrapper.getTestData();
        int count = 0;
        if (easyView.isSelected()) {
            count += testData.countOf(EASY);
        }
        if (mediumView.isSelected()) {
            count += testData.countOf(EASY);
        }
        if (hardView.isSelected()) {
            count += testData.countOf(HARD);
        }
        return count;
    }

    private List<QuestionDataStatus> addSelectedTypes() {
        List<QuestionDataStatus> questionsTypes = new ArrayList<>();
        if (easyView.isSelected()) {
            questionsTypes.add(QuestionDataStatus.EASY);
        }
        if (mediumView.isSelected()) {
            questionsTypes.add(QuestionDataStatus.MEDIUM);
        }
        if (hardView.isSelected()) {
            questionsTypes.add(QuestionDataStatus.HARD);
        }
        return questionsTypes;
    }

    public static void start(Context context, TestWrapper test) {
        Intent intent = new Intent(context, StartLearn.class);
        intent.putExtra(TEST_KEY, test);
        context.startActivity(intent);
    }
}
