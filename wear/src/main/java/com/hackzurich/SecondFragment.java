package com.hackzurich;

import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hackzurich.model.Question;

public final class SecondFragment extends CardFragment {

    public static final String QUESTION = "question";
    private Question question;
    private final int[] answerIds = {R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4, R.id.answer5, R.id.answer6, R.id.answer7};
    private final int[] answerTextIds = {R.id.answerText1, R.id.answerText2, R.id.answerText3, R.id.answerText4, R.id.answerText5, R.id.answerText6, R.id.answerText7};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        question = (Question) getArguments().getSerializable(QUESTION);
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.second_card, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        for (int i = 0; i < question.getAnswers().size(); i++) {
            View answerView = getView().findViewById(answerIds[i]);
            answerView.setVisibility(View.VISIBLE);
            answerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setSelected(!v.isSelected());
                }
            });
            TextView textView = (TextView) getView().findViewById(answerTextIds[i]);
            textView.setText(question.getAnswers().get(i).getText());
        }
    }

    public static SecondFragment instance(Question question) {
        SecondFragment fragment = new SecondFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(QUESTION, question);
        fragment.setArguments(arguments);
        return fragment;
    }
}