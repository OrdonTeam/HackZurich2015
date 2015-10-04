package com.hackzurich.service;

import android.content.Context;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;
import com.google.gson.Gson;
import com.hackzurich.model.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class DropBoxService {

    static private final String APP_KEY = "pzinws405fx9h6f";
    static private final String APP_SECRET = "jlhtc6v52g7o8e5";

    private DropboxAPI<AndroidAuthSession> mDBApi;

    public void init() {
        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys);
        mDBApi = new DropboxAPI<>(session);
        mDBApi.getSession().setOAuth2AccessToken("Wqmw-JGwSIAAAAAAAAAABoUgO-KGvzixClt9sc-J4NL_K_VUzlgZTCpDcLtGbGcM");
    }

    public Observable<String> getSingleFile(final Context context, final DropboxAPI.Entry entry) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String content = getFileContent(entry, context);
                    subscriber.onNext(content);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public Observable<Map<String, DropboxAPI.Entry>> getAllFiles() {
        return Observable.create(new Observable.OnSubscribe<Map<String, DropboxAPI.Entry>>() {
            @Override
            public void call(Subscriber<? super Map<String, DropboxAPI.Entry>> subscriber) {
                try {
                    Map<String, DropboxAPI.Entry> filesFromDropBox = getFilesFromDropBox();
                    subscriber.onNext(filesFromDropBox);
                    subscriber.onCompleted();
                } catch (DropboxException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    private Map<String, DropboxAPI.Entry> getFilesFromDropBox() throws DropboxException {
        DropboxAPI.Entry dirent = mDBApi.metadata("/", 1000, null, true, null);
        Map<String, DropboxAPI.Entry> map = new HashMap<>();
        for (DropboxAPI.Entry ent : dirent.contents) {
            map.put(ent.path, ent);
        }
        return map;
    }

    public File createTmpFile(Context context, String name) throws IOException {
        return File.createTempFile(name, null, context.getCacheDir());
    }

    private String getFileContent(final DropboxAPI.Entry fileSelected, Context context) throws Exception {
        File localFile = createTmpFile(context, "tmpName");
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        DropboxAPI.DropboxInputStream dropboxInputStream;
        try {
            dropboxInputStream = mDBApi.getFileStream(fileSelected.path, fileSelected.rev);
            bufferedInputStream = new BufferedInputStream(dropboxInputStream);
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(localFile));

            byte[] buffer = new byte[4096];
            int read;
            while (true) {
                read = bufferedInputStream.read(buffer);
                if (read <= 0) {
                    break;
                }
                bufferedOutputStream.write(buffer, 0, read);
            }
        } finally {
            if (bufferedOutputStream != null) {
                bufferedOutputStream.close();
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
            }
        }
        return extractContentFromLocalCopy(localFile.getAbsolutePath());
    }

    private static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    private static String extractContentFromLocalCopy(String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        fin.close();
        return ret;
    }


    public void download(Context context, Collection<DropboxAPI.Entry> entries) {
        final TestDownloadService testDownloadService = new TestDownloadService(context);
        for (DropboxAPI.Entry entry : entries) {
            getSingleFile(context, entry)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String json) {
                            Log.e("kasper", "asdasd" + json);
                            Test test = new Gson().fromJson(json, Test.class);
                            testDownloadService.add(test);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Log.e("kasper", "error na drugim" + throwable.getMessage());
                        }
                    });
        }
    }
}
