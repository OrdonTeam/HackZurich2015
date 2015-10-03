package com.hackzurich.database;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hackzurich.model.Question;

import java.lang.reflect.Type;
import java.util.List;

public class QuestionsRepository {

    private final static String QUESTIONS_KEY = "questions";
    private static final Type questionsListType = new TypeToken<List<Question>>() {
    }.getType();

    private final SharedPrefs prefs;

    public QuestionsRepository(Context context) {
        this.prefs = new SharedPrefs(PreferenceManager.getDefaultSharedPreferences(context), new Gson());
    }

    List<Question> get() {
        return (List<Question>) prefs.readFromPrefs(QUESTIONS_KEY, questionsListType);
    }

    void set(List<Question> questions) {
        prefs.saveToPrefs(QUESTIONS_KEY, questions, questionsListType);
    }
}
