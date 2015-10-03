package com.hackzurich.database;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.hackzurich.model.data.TestData;

public class TestDataRepository {

    private final static String TEST_DATA_PREFIX_KEY = "test_data";

    private final SharedPrefs prefs;

    public TestDataRepository(Context context) {
        this.prefs = new SharedPrefs(PreferenceManager.getDefaultSharedPreferences(context), new Gson());
    }

    public TestData get(String id) {
        return (TestData) prefs.readFromPrefs(TEST_DATA_PREFIX_KEY + id, TestData.class);
    }

    public void set(TestData testData) {
        prefs.saveToPrefs(TEST_DATA_PREFIX_KEY + testData.getTestId(), testData, TestData.class);
    }
}
