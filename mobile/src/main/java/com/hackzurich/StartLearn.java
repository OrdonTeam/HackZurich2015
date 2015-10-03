package com.hackzurich;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.hackzurich.model.TestWrapper;

public final class StartLearn extends Activity {

    public static final String TEST_KEY = "test_wrapper";

    private TestWrapper testWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_learn_activity);
        testWrapper = getIntent().getParcelableExtra(TEST_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("test", testWrapper.toString());
    }

    public static void start(Context context, TestWrapper test) {
        Intent intent = new Intent(context, StartLearn.class);
        intent.putExtra(TEST_KEY, test);
        context.startActivity(intent);
    }
}
