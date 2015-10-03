package com.hackzurich.database;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.Set;

public class TestIdRepository {

    private final static String TEST_ID_KEY = "test_id";
    private final SharedPrefs prefs;

    public TestIdRepository(Context context) {
        this.prefs = new SharedPrefs(PreferenceManager.getDefaultSharedPreferences(context), new Gson());
    }

    public Set<String> get() {
        return prefs.getStringSet(TEST_ID_KEY);
    }

    public void set(Set<String> testIds) {
        prefs.putStringSet(TEST_ID_KEY, testIds);
    }
}
