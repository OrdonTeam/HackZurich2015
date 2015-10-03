package com.hackzurich;

import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackzurich.model.Question;

public final class SecondFragment extends CardFragment {

    public static final String QUESTION = "question";
    private Question question;

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
        WearableListView listView = (WearableListView) getView().findViewById(R.id.answer_list);
        listView.setAdapter(new AnswersListAdapter(getActivity(), question));
    }

    public static SecondFragment instance(Question question) {
        SecondFragment fragment = new SecondFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(QUESTION, question);
        fragment.setArguments(arguments);
        return fragment;
    }
}