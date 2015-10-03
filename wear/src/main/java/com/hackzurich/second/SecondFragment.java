package com.hackzurich.second;

import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.hackzurich.R;
import com.hackzurich.model.Question;

import java.util.ArrayList;
import java.util.List;

public final class SecondFragment extends CardFragment {

    public static final String QUESTION = "question";
    private Question question;
    private final int[] answerIds = {R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4, R.id.answer5, R.id.answer6, R.id.answer7};
    private final int[] answerTextIds = {R.id.answerText1, R.id.answerText2, R.id.answerText3, R.id.answerText4, R.id.answerText5, R.id.answerText6, R.id.answerText7};
    private final List<View> answerViews = new ArrayList<>();
    private final List<View> answerTextViews = new ArrayList<>();

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
        findViews();
        initQuestions();
        setFlipper();
    }

    private void findViews() {
        for (int i = 0; i < question.getAnswers().size(); i++) {
            answerViews.add(getView().findViewById(answerIds[i]));
        }
        for (int i = 0; i < question.getAnswers().size(); i++) {
            answerTextViews.add(getView().findViewById(answerTextIds[i]));
        }
    }

    private void initQuestions() {
        for (int i = 0; i < question.getAnswers().size(); i++) {
            View answerView = getView().findViewById(answerIds[i]);
            answerView.setVisibility(View.VISIBLE);
            answerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setSelected(!v.isSelected());
                    setFlipper();
                }
            });
            TextView textView = (TextView) getView().findViewById(answerTextIds[i]);
            textView.setText(question.getAnswers().get(i).getText());
        }
    }

    private void setFlipper() {
        ViewFlipper answerButtonViewFlipper = (ViewFlipper) getView().findViewById(R.id.answerButtonViewFlipper);
        if (isSubmitted()) {
            answerButtonViewFlipper.setDisplayedChild(2);
        } else {
            if (isAnyAnswerChoosen()) {
                answerButtonViewFlipper.setDisplayedChild(1);
            } else {
                answerButtonViewFlipper.setDisplayedChild(0);
            }
        }
    }

    private boolean isSubmitted() {
        return false;
    }

    private boolean isAnyAnswerChoosen() {
        for (View view : answerViews) {
            if(view.isSelected())
                return true;
        }
        return false;
    }

    public static SecondFragment instance(Question question) {
        SecondFragment fragment = new SecondFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(QUESTION, question);
        fragment.setArguments(arguments);
        return fragment;
    }
}