package com.hackzurich;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.hackzurich.adapter.AnswerGroupAdapter;
import com.hackzurich.model.Answer;

public class QuestionActivity extends Activity {

    AnswerGroupAdapter answerGroupAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.question_activity);
        addAnswerListAdapter();
        addFooterButtonsListeners();
        addExampleAnswers();
    }

    private void addAnswerListAdapter() {
        answerGroupAdapter = new AnswerGroupAdapter();
        ListView answerListView = (ListView) findViewById(R.id.answerListView);
        answerListView.setAdapter(answerGroupAdapter);
    }

    private void addFooterButtonsListeners() {
        addDoNotKnowButtonListener();
        addConfirmAnswerButtonListener();
    }

    private void addDoNotKnowButtonListener() {
        Button doNotKnowButton = (Button) findViewById(R.id.doNotKnowButton);
        doNotKnowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doNotKnowButtonClicked();
            }
        });
    }

    private void doNotKnowButtonClicked() {

    }

    private void addConfirmAnswerButtonListener() {
        Button confirmAnswerButton = (Button) findViewById(R.id.confirmAnswerButton);
        confirmAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmAnswerButtonClicked();
            }
        });
    }

    private void confirmAnswerButtonClicked() {
        answerGroupAdapter.setSubmitted();
    }

    private void addExampleAnswers() {
        answerGroupAdapter.addAnswerItem(new AnswerItem(new Answer("bleble",true)));
        answerGroupAdapter.addAnswerItem(new AnswerItem(new Answer("blebleble",false)));
    }
}
