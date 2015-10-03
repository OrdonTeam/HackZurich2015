package com.hackzurich.database;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class SharedPrefs {

    private SharedPreferences sharedPrefs;
    private Gson gson;

    public SharedPrefs(SharedPreferences sharedPrefs, Gson gson) {
        this.sharedPrefs = sharedPrefs;
        this.gson = gson;
    }

    Object readFromPrefs(String key, Type type) {
        String str = sharedPrefs.getString(key, null);
        if (str != null) {
            return gson.fromJson(str, type);
        }
        return null;
    }

    void saveToPrefs(String key, Object value, Type type) {
        String str = null;
        if (value != null) {
            str = gson.toJson(value, type);
        }
        sharedPrefs.edit()
                .putString(key, str)
                .apply();
    }

    void remove(String... keys) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        for (String key : keys) {
            editor.remove(key);
        }
        editor.apply();
    }

    long getLong(String key, long defValue) {
        return sharedPrefs.getLong(key, defValue);
    }

    void putLong(String key, long value) {
        sharedPrefs.edit()
                .putLong(key, value)
                .apply();
    }
}
