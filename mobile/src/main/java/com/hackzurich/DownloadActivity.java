package com.hackzurich;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.dropbox.client2.DropboxAPI;
import com.hackzurich.file.FilesAdapter;
import com.hackzurich.model.DropBoxFiles;
import com.hackzurich.service.DropBoxService;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public final class DownloadActivity extends Activity {

    @Bind(R.id.filesList)
    ListView filesList;

    private FilesAdapter filesAdapter = new FilesAdapter();
    private DropBoxFiles dropBoxFiles;
    private DropBoxService dropBoxService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_activity);
        ButterKnife.bind(this);
        filesList.setAdapter(filesAdapter);
        dropBoxService = new DropBoxService();
        dropBoxService.init();
        dropBoxService.getAllFiles()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<Map<String, DropboxAPI.Entry>>() {
                    @Override
                    public void call(Map<String, DropboxAPI.Entry> files) {
                        dropBoxFiles = new DropBoxFiles(files);
                        filesAdapter.addAll(dropBoxFiles);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("kasper", throwable.getMessage());
                    }
                });

    }

    @OnClick(R.id.download)
    public void download() {
        dropBoxService.download(this, filesAdapter.getSelected());
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, DownloadActivity.class));
    }
}
