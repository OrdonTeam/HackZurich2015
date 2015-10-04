package com.hackzurich.service;

import android.content.Context;

import com.hackzurich.database.TestDataRepository;
import com.hackzurich.database.TestIdRepository;
import com.hackzurich.database.TestRepository;
import com.hackzurich.model.Test;
import com.hackzurich.model.data.TestData;

public final class TestDownloadService {
    private final TestRepository testRepository;
    private final TestIdRepository testIdRepository;
    private final TestDataRepository testDataRepository;

    public TestDownloadService(Context context) {
        testRepository = new TestRepository(context);
        testIdRepository = new TestIdRepository(context);
        testDataRepository = new TestDataRepository(context);
    }

    public void add(Test test) {
        testRepository.set(test);
        TestData testData = new TestData(test);
        testDataRepository.set(testData);
        testIdRepository.add(test.getId());
    }
}
