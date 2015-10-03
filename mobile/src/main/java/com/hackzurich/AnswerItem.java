package com.hackzurich;

import android.view.View;
import android.widget.TextView;

import com.hackzurich.model.Answer;

public class AnswerItem {
    private static final int ASCII_CODE_FOR_UP_CASE = 65;

    private Answer answer;
    private boolean choosen;
    private boolean submitted;

    public AnswerItem(Answer answer) {
        this.answer = answer;
    }

    public void bindLayout(View view, int position) {
        setOnClickListener(view);
        setAnswerItemSentence(view);
        setAnswerItemMark(view, position);
        setBackgroundColor(view.findViewById(R.id.answerField));
    }

    private void setOnClickListener(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosen = !choosen;
                setBackgroundColor(view.findViewById(R.id.answerField));
            }
        });
    }

    public void setBackgroundColor(View view) {
        if (choosen) {
            view.setBackgroundResource(R.drawable.blue_button_background);
            if (submitted) {
                if (answer.isCorrect()) {
                    view.setBackgroundResource(R.drawable.green_button_background);
                } else {
                    view.setBackgroundResource(R.drawable.red_button_background);
                }
            }
        } else {
            view.setBackgroundResource(R.drawable.grey_button_background);
            if (submitted) {
                if (answer.isCorrect()) {
                    view.setBackgroundResource(R.drawable.green_button_background);
                }
            }
        }
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
        String mark = String.valueOf(Character.toChars(position + ASCII_CODE_FOR_UP_CASE));
        mark = mark + ".";
        return mark;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public boolean isChoosen() {
        return choosen;
    }

    public boolean isCorrect() {
        return answer.isCorrect();
    }
}
