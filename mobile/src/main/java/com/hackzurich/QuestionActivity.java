package com.hackzurich;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

        addExampleAnswers();
    }

    private void addAnswerListAdapter() {
        answerGroupAdapter = new AnswerGroupAdapter();
        ListView answerListView = (ListView) findViewById(R.id.answerListView);
        answerListView.setAdapter(answerGroupAdapter);
    }

    private void addExampleAnswers() {
        answerGroupAdapter.addAnswerItem(new AnswerItem(new Answer("bleble",true)));
        answerGroupAdapter.addAnswerItem(new AnswerItem(new Answer("blebleble",false)));
    }
}
