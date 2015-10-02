package com.hackzurich.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public final class Test implements Parcelable {
    private final List<Question> questions;

    public Test(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(questions);
    }

    public static final Creator<Test> CREATOR = new Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel in) {
            return new Test(in.createTypedArrayList(Question.CREATOR));
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };
}
