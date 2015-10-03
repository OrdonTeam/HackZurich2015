package com.hackzurich.database;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.hackzurich.model.Test;

public class TestRepository {

    private final static String TEST_PREFIX_KEY = "test";

    private final SharedPrefs prefs;

    public TestRepository(Context context) {
        this.prefs = new SharedPrefs(PreferenceManager.getDefaultSharedPreferences(context), new Gson());
    }

    public Test get(long id) {
        return (Test) prefs.readFromPrefs(TEST_PREFIX_KEY + id, Test.class);
    }

    public void set(Test test) {
        prefs.saveToPrefs(TEST_PREFIX_KEY + test.getId(), test, Test.class);
    }
}
