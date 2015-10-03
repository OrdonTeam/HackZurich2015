package com.hackzurich.first;

import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hackzurich.R;
import com.hackzurich.model.Question;

public final class FirstFragment extends CardFragment {

    public static final String QUESTION = "question";
    private Question question;
    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        question = (Question) getArguments().getSerializable(QUESTION);
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.first_card, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        textView = (TextView) getView().findViewById(R.id.question_text);
        textView.setText(question.getText());
    }

    public static FirstFragment instance(Question question) {
        FirstFragment fragment = new FirstFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(QUESTION, question);
        fragment.setArguments(arguments);
        return fragment;
    }
}
