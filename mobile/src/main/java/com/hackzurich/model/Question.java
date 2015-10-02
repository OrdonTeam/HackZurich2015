package com.hackzurich.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public final class Question implements Parcelable {
    private final String text;
    private final List<Answer> answers;

    public Question(String text, List<Answer> answers) {
        this.text = text;
        this.answers = answers;
    }

    public String getText() {
        return text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeTypedList(answers);
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in.readString(), in.createTypedArrayList(Answer.CREATOR));
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
