package com.hackzurich;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.hackzurich.connection.Listener;
import com.hackzurich.connection.Sender;
import com.hackzurich.model.stub.TestFactory;
import com.hackzurich.service.RepositoryService;
import com.hackzurich.stub.STUB_PopulateDatabase;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);

        STUB_PopulateDatabase.populate(this);

        new Listener(this).start();
    }

    @OnClick(R.id.download)
    public void onDownload() {
        Toast.makeText(this, "On download", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.learn)
    public void onLearn() {
        StartLearn.start(this, new RepositoryService(this).getCurrentTestWrapper());
    }


    @OnClick(R.id.trigger_wear)
    public void onTriggerWear() {
        new Sender(this, TestFactory.newQuestion());
    }
}
