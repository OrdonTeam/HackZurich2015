package com.hackzurich;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.hackzurich.connection.Listener;
import com.hackzurich.connection.Sender;
import com.hackzurich.database.TestIdRepository;
import com.hackzurich.model.TestWrapper;
import com.hackzurich.model.data.QuestionDataStatus;
import com.hackzurich.service.RepositoryService;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends Activity {

    private TestIdRepository testIdRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);

//        STUB_PopulateDatabase.populate(this);
        new Listener(this).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Spinner spinner = (Spinner) findViewById(R.id.databaseChooseSpinner);
        testIdRepository = new TestIdRepository(HomeActivity.this);
        final List<String> names = new ArrayList<>(testIdRepository.get());
        spinner.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_text, names));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                testIdRepository.setCurrentTestID(names.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String currentTestId = testIdRepository.getCurrentTestId();
        for (String id : names) {
            if (id.equals(currentTestId)) {
                spinner.setSelection(names.indexOf(id), false);
            }
        }
    }

    @OnClick(R.id.download)
    public void onDownload() {
        DownloadActivity.start(this);
    }

    @OnClick(R.id.learn)
    public void onLearn() {
        StartLearn.start(this, new RepositoryService(this).getCurrentTestWrapper());
    }


    @OnClick(R.id.trigger_wear)
    public void onTriggerWear() {
        RepositoryService repositoryService = new RepositoryService(this);
        TestWrapper testWrapper = repositoryService.getCurrentTestWrapper();
        new Sender(this, testWrapper.getRandomQuestionFor(QuestionDataStatus.HARD)).send();
    }
}
