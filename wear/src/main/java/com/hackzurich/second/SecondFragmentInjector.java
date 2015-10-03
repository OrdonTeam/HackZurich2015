package com.hackzurich.second;

import android.view.View;
import android.widget.TextView;

import com.hackzurich.R;

import java.util.ArrayList;
import java.util.List;

public final class SecondFragmentInjector {
    public static List<View> getAnswerViews(View view) {
        final int[] answerIds = {R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4, R.id.answer5, R.id.answer6, R.id.answer7};
        List<View> answerViews = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            answerViews.add(view.findViewById(answerIds[i]));
        }
        return answerViews;
    }

    public static List<TextView> getAnswerTextViews(View view) {
        List<TextView> answerTextViews = new ArrayList<>();
        final int[] answerTextIds = {R.id.answerText1, R.id.answerText2, R.id.answerText3, R.id.answerText4, R.id.answerText5, R.id.answerText6, R.id.answerText7};
        for (int i = 0; i < 7; i++) {
            answerTextViews.add((TextView) view.findViewById(answerTextIds[i]));
        }
        return answerTextViews;
    }
}
