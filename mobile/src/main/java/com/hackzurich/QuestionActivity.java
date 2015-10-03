package com.hackzurich;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.hackzurich.adapter.AnswerGroupAdapter;
import com.hackzurich.model.Answer;
import com.hackzurich.model.Question;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionActivity extends Activity {

    public static final String QUESTION = "question";
    public static final String QUESTION_ID = "question_id";
    public static final String CORRECT = "correct";

    private AnswerGroupAdapter answerGroupAdapter = new AnswerGroupAdapter();
    private Question question;

    @Bind(R.id.answerListView)
    ListView answerListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.question_activity);
        ButterKnife.bind(this);
        setAdapter();
    }

    private void setAdapter() {
        question = (Question) getIntent().getSerializableExtra(QUESTION);
        for (Answer answer : question.getAnswers()) {
            answerGroupAdapter.addAnswerItem(new AnswerItem(answer));
        }
        answerGroupAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                onDataSetChange();
            }
        });
        answerListView.setAdapter(answerGroupAdapter);
    }

    private void onDataSetChange() {

        ViewFlipper answerButtonViewFlipper = (ViewFlipper) findViewById(R.id.answerButtonViewFlipper);

        if(answerGroupAdapter.isAnyAnswerChoosen()) {
            if(answerGroupAdapter.isSubmitted()) {
                answerButtonViewFlipper.setDisplayedChild(2);
            }
            else {
                answerButtonViewFlipper.setDisplayedChild(1);
            }
        }
        else {
            answerButtonViewFlipper.setDisplayedChild(0);
        }

    }

    @OnClick(R.id.confirmAnswerButton)
    public void confirmAnswerButtonClicked() {
        answerGroupAdapter.setSubmitted();
    }

    @OnClick(R.id.doNotKnowButton)
    public void doNotKnowButtonClicked() {
        answerGroupAdapter.setSubmitted();
    }

    @OnClick(R.id.nextButton)
    public void onNext() {
        Intent data = new Intent();
        data.putExtra(QUESTION_ID,question.getId());
        data.putExtra(CORRECT, answerGroupAdapter.wasCorrect());
        setResult(RESULT_OK, data);
        finish();
    }

    public static Intent getIntent(Context context, Question question) {
        Intent intent = new Intent(context, QuestionActivity.class);
        intent.putExtra(QUESTION, question);
        return intent;
    }
}
