package com.hackzurich;

import android.view.View;
import android.widget.TextView;

import com.hackzurich.model.Answer;

/**
 * Created by karola on 03.10.15.
 */
public class AnswerItem {
    private static final int ASCII_CODE_FOR_UP_CASE = 65;

    private Answer answer;
    private boolean choosen;

    public AnswerItem(Answer answer) {
        this.answer = answer;
    }

    public void bindLayout(View view, int position) {
        setAnswerItemSentence(view);
        setAnswerItemMark(view, position);
    }

    private void setAnswerItemSentence(View view) {
        TextView answerSentenceTextView = (TextView) view.findViewById(R.id.answerSentenceTextView);
        answerSentenceTextView.setText(answer.getText());
    }

    private void setAnswerItemMark(View view, int position) {
        TextView answerMarkTextView = (TextView) view.findViewById(R.id.answerMarkTextView);
        String mark = generateMark(position);

        answerMarkTextView.setText(mark);
    }

    private String generateMark(int position) {
        String mark = String.valueOf(position + ASCII_CODE_FOR_UP_CASE);
        mark = mark + ".";
        return mark;
    }

    public void setBackgroundColor() {

    }
}
