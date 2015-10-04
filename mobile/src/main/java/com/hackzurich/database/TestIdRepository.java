package com.hackzurich.database;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

public class TestIdRepository {

    private final static String TEST_ID_KEY = "test_id";
    public static final String CURRENT_TEST_ID = "current_test_id";
    private final SharedPrefs prefs;

    public TestIdRepository(Context context) {
        this.prefs = new SharedPrefs(PreferenceManager.getDefaultSharedPreferences(context), new Gson());
    }

    public Set<String> get() {
        Set<String> ids = prefs.getStringSet(TEST_ID_KEY);
        if (ids == null) {
            ids = new HashSet<>();
        }
        return ids;
    }

    public void set(Set<String> testIds) {
        prefs.putStringSet(TEST_ID_KEY, testIds);
    }

    public String getCurrentTestId() {
        return (String) prefs.readFromPrefs(CURRENT_TEST_ID, String.class);
    }

    public void setCurrentTestID(String testId) {
        prefs.saveToPrefs(CURRENT_TEST_ID, testId, String.class);
    }

    public void add(String testId) {
        Set<String> ids = get();
        ids.add(testId);
        set(ids);
    }
}
