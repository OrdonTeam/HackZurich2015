package com.hackzurich.service;

import android.content.Context;

import com.hackzurich.database.TestDataRepository;
import com.hackzurich.database.TestIdRepository;
import com.hackzurich.database.TestRepository;
import com.hackzurich.model.Test;
import com.hackzurich.model.TestWrapper;
import com.hackzurich.model.data.TestData;

public final class RepositoryService {

    private TestIdRepository testIdRepository;
    private TestRepository testRepository;
    private TestDataRepository testDataRepository;

    public RepositoryService(Context context) {
        testIdRepository = new TestIdRepository(context);
        testRepository = new TestRepository(context);
        testDataRepository = new TestDataRepository(context);
    }

    public TestWrapper getCurrentTestWrapper(){
        String currentTestId = testIdRepository.getCurrentTestId();
        Test test = testRepository.get(currentTestId);
        TestData testData = testDataRepository.get(currentTestId);
        return new TestWrapper(test,testData);
    }
}
