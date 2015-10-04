package com.hackzurich;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.hackzurich.connection.Listener;
import com.hackzurich.connection.Sender;
import com.hackzurich.database.TestIdRepository;
import com.hackzurich.model.stub.TestFactory;
import com.hackzurich.service.DropBoxService;
import com.hackzurich.service.RepositoryService;
import com.hackzurich.stub.STUB_PopulateDatabase;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class HomeActivity extends Activity {

    private DropboxAPI<AndroidAuthSession> mDBApi;
    private ArrayAdapter<String> arrayAdapter;
    private DropBoxService dropBoxService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);

        STUB_PopulateDatabase.populate(this);
        dropBoxService = new DropBoxService();
        dropBoxService.init();
        new Listener(this).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Spinner spinner = (Spinner) findViewById(R.id.databaseChooseSpinner);
        List<String> names = new ArrayList<>(new TestIdRepository(this).get());
        spinner.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_text, names));
    }

    @OnClick(R.id.download)
    public void onDownload() {
        Toast.makeText(this, "On download", Toast.LENGTH_LONG).show();
        doDrop();
    }

    @OnClick(R.id.learn)
    public void onLearn() {
        StartLearn.start(this, new RepositoryService(this).getCurrentTestWrapper());
    }


    @OnClick(R.id.trigger_wear)
    public void onTriggerWear() {
        new Sender(this, TestFactory.newQuestion()).send();
    }


    void doDrop() {

        dropBoxService.getAllFiles()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<Map<String, DropboxAPI.Entry>>() {
                    @Override
                    public void call(Map<String, DropboxAPI.Entry> strings) {
                        Log.e("kasper", strings.toString());
                        dropBoxService.getSingleFile(HomeActivity.this, strings.get("/CapitalsOfEurope.json"))
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.newThread())
                                .subscribe(new Action1<String>() {
                                    @Override
                                    public void call(String s) {
                                        Log.e("kasper", "asdasd" + s);

                                    }
                                }, new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {
                                        Log.e("kasper", "error na drugim" + throwable.getMessage());
                                    }
                                });
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("kasper", throwable.getMessage());
                    }
                });

    }
}
