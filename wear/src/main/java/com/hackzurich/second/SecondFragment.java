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

import java.util.List;

public final class SecondFragment extends CardFragment {

    public static final String QUESTION = "question";
    private Question question;
    private List<View> answerViews;
    private List<TextView> answerTextViews;
    private boolean submitted = false;

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
        answerViews = SecondFragmentInjector.getAnswerViews(getView());
        answerTextViews = SecondFragmentInjector.getAnswerTextViews(getView());
        initQuestions();
        invalidate();
    }

    private void initQuestions() {
        for (int i = 0; i < question.getAnswers().size(); i++) {
            View answerView = answerViews.get(i);
            answerView.setVisibility(View.VISIBLE);
            answerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setSelected(!v.isSelected());
                    invalidate();
                }
            });
            TextView textView = answerTextViews.get(i);
            textView.setText(question.getAnswers().get(i).getText());
        }
    }

    private void invalidate() {
        setFlipper();
    }

    private void setFlipper() {
        ViewFlipper answerButtonViewFlipper = (ViewFlipper) getView().findViewById(R.id.answerButtonViewFlipper);
        if (submitted) {
            answerButtonViewFlipper.setDisplayedChild(2);
        } else {
            if (isAnyAnswerChoosen()) {
                answerButtonViewFlipper.setDisplayedChild(1);
            } else {
                answerButtonViewFlipper.setDisplayedChild(0);
            }
        }
    }

    private boolean isAnyAnswerChoosen() {
        for (View view : answerViews) {
            if (view.isSelected())
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