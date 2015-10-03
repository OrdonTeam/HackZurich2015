package com.hackzurich.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hackzurich.AnswerItem;
import com.hackzurich.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karola on 03.10.15.
 */
public class AnswerGroupAdapter extends BaseAdapter {

    public final List<AnswerItem> answerGroup = new ArrayList<>();

    public AnswerGroupAdapter() {

    }

    public void addAnswerItem(AnswerItem answerItem)
    {
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
        AnswerItem answerItem = answerGroup.get(position);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.answer_field,viewGroup,false);
        answerItem.bindLayout(view, position);

        return view;
    }

    public void setSubmitted()
    {
        for(AnswerItem answerItem : answerGroup) {
            answerItem.setSubmitted(true);
        }
        notifyDataSetChanged();
    }
}
