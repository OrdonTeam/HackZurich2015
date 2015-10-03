package com.hackzurich.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hackzurich.AnswerItem;
import com.hackzurich.R;

import java.util.ArrayList;
import java.util.List;

public class AnswerGroupAdapter extends BaseAdapter {

    private final List<AnswerItem> answerGroup = new ArrayList<>();
    private boolean submitted;

    public AnswerGroupAdapter() {

    }

    public void addAnswerItem(AnswerItem answerItem) {
        answerGroup.add(answerItem);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return answerGroup.size();
    }

    @Override
    public AnswerItem getItem(int position) {
        return answerGroup.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View oldView, ViewGroup viewGroup) {
        final AnswerItem answerItem = answerGroup.get(position);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.answer_field, viewGroup, false);
        answerItem.bindLayout(view, position);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!submitted) {
                    answerItem.changeChoosen();
                    notifyDataSetChanged();
                }
            }
        });
        return view;
    }

    public void setSubmitted() {
        for (AnswerItem answerItem : answerGroup) {
            answerItem.submit();
        }
        submitted = true;
        notifyDataSetChanged();
    }

    public boolean wasCorrect() {
        for (AnswerItem answerItem : answerGroup) {
            if (answerItem.isChoosen() != answerItem.isCorrect()) {
                return false;
            }
        }
        return true;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public boolean isAnyAnswerChoosen() {
        for (AnswerItem answerItem : answerGroup) {
            if (answerItem.isChoosen()) {
                return true;
            }
        }
        return false;
    }
}
