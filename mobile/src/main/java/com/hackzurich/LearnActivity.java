package com.hackzurich;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hackzurich.model.TestWrapper;
import com.hackzurich.model.data.QuestionDataStatus;
import com.hackzurich.model.stub.TestFactory;

import java.util.List;

public final class LearnActivity extends Activity {

    public static final String TEST_WRAPPER = "test_wrapper";
    public static final String QUESTION_TYPES = "question_types";
    public static final int REQUEST_CODE = 123;

    private TestWrapper testWrapper;
    private List<QuestionDataStatus> questionsTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testWrapper = (TestWrapper) getIntent().getSerializableExtra(TEST_WRAPPER);
        questionsTypes = QuestionDataStatuses.getList(getIntent().getSerializableExtra(QUESTION_TYPES));
    }

    @Override
    protected void onResume() {
        super.onResume();
        startActivityForResult(QuestionActivity.getIntent(this, TestFactory.newQuestion()), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                testWrapper.addData(
                        data.getStringExtra(QuestionActivity.QUESTION_ID),
                        data.getBooleanExtra(QuestionActivity.CORRECT, false)
                );
            } else {
                finish();
            }
        }
    }

    public static void start(Context context, TestWrapper testWrapper, List<QuestionDataStatus> questionsTypes) {
        Intent intent = new Intent(context, LearnActivity.class);
        intent.putExtra(TEST_WRAPPER, testWrapper);
        intent.putExtra(QUESTION_TYPES, QuestionDataStatuses.of(questionsTypes));
        context.startActivity(intent);
    }
}
