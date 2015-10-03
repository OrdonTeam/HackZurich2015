package com.hackzurich;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.hackzurich.model.stub.TestFactory;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends Activity {

    private SyncService syncService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);

        syncService = new SyncService(this);
        syncService.sync();
    }

    @Override
    protected void onPause() {
        super.onPause();
        syncService.onPause();
    }

    @OnClick(R.id.download)
    public void onDownload() {
        Toast.makeText(this, "On download", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.learn)
    public void onLearn() {
        StartLearn.start(this, TestFactory.newTestWrapper());
    }

}
