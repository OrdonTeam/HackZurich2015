package com.hackzurich;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.hackzurich.adapter.AnswerGroupAdapter;
import com.hackzurich.model.Answer;
import com.hackzurich.model.Question;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionActivity extends Activity {

    public static final String QUESTION = "question";

    private AnswerGroupAdapter answerGroupAdapter = new AnswerGroupAdapter();

    @Bind(R.id.answerListView)
    ListView answerListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);
        ButterKnife.bind(this);
        setAdapter();
    }

    private void setAdapter() {
        Question question = (Question) getIntent().getSerializableExtra(QUESTION);
        for (Answer answer : question.getAnswers()) {
            answerGroupAdapter.addAnswerItem(new AnswerItem(answer));
        }
        answerListView.setAdapter(answerGroupAdapter);
    }

    @OnClick(R.id.confirmAnswerButton)
    public void confirmAnswerButtonClicked() {
        answerGroupAdapter.setSubmitted();
    }

    @OnClick(R.id.doNotKnowButton)
    public void doNotKnowButtonClicked() {

    }

    public static Intent getIntent(Context context, Question question) {
        Intent intent = new Intent(context, QuestionActivity.class);
        intent.putExtra(QUESTION, question);
        return intent;
    }
}
