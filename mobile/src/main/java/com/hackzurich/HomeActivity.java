package com.hackzurich;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.OnClick;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
    }

    @OnClick(R.id.download)
    public void onDownload() {
        Toast.makeText(this,"On download",Toast.LENGTH_LONG).show();
    }
}
