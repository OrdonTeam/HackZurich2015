package com.hackzurich;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class QuestionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);
        addHeader();
        insertAnswers();
        addFooter();
    }

    private void addHeader() {

    }

    private void insertAnswers() {

    }

    private void addFooter() {

    }
}
