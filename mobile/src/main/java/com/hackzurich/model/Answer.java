package com.hackzurich.model;

import android.os.Parcel;
import android.os.Parcelable;

public final class Answer implements Parcelable {
    private final String text;
    private final boolean correct;

    public Answer(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
    }

    public String getText() {
        return text;
    }

    public boolean isCorrect() {
        return correct;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeInt(correct ? 1 : 0);
    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel in) {
            return new Answer(in.readString(), in.readInt() == 1);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };
}
