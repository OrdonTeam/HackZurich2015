package com.hackzurich.database;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

public class TestIdRepository {

    private final static String TEST_ID_KEY = "test_id";
    private final SharedPrefs prefs;

    public TestIdRepository(Context context) {
        this.prefs = new SharedPrefs(PreferenceManager.getDefaultSharedPreferences(context), new Gson());
    }

    public Long get() {
        return prefs.getLong(TEST_ID_KEY, 0);
    }

    public void set(Long testId) {
        prefs.putLong(TEST_ID_KEY, testId);
    }
}
