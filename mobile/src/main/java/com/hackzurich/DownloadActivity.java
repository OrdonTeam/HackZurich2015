package com.hackzurich;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.hackzurich.file.FilesAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public final class DownloadActivity extends Activity {

    @Bind(R.id.filesList)
    ListView filesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_activity);
        ButterKnife.bind(this);
        filesList.setAdapter(new FilesAdapter(files()));
    }

    private List<String> files() {
        return Arrays.asList("CapitalsOfEurope","WorldWarII");
    }
}
