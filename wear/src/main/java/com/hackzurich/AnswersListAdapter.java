package com.hackzurich;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackzurich.model.Question;

public final class AnswersListAdapter extends WearableListView.Adapter {
    private final LayoutInflater inflater;
    private final Question question;

    public AnswersListAdapter(Context context, Question question) {
        this.inflater = LayoutInflater.from(context);
        this.question = question;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.answer_layout, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return question.getAnswers().size();
    }

    private class Holder extends WearableListView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }
}
