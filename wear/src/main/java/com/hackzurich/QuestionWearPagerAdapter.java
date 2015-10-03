package com.hackzurich;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.wearable.view.FragmentGridPagerAdapter;

import com.hackzurich.first.FirstFragment;
import com.hackzurich.model.Question;
import com.hackzurich.second.SecondFragment;

public final class QuestionWearPagerAdapter extends FragmentGridPagerAdapter {
    private Question question;

    public QuestionWearPagerAdapter(FragmentManager fragmentManager, Question question) {
        super(fragmentManager);
        this.question = question;
    }

    @Override
    public Fragment getFragment(int row, int col) {
        if (col == 0) {
            return FirstFragment.instance(question);
        } else {
            return SecondFragment.instance(question);
        }
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount(int i) {
        return 2;
    }
}
