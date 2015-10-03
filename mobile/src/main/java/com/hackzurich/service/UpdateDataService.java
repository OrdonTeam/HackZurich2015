package com.hackzurich.service;

import android.content.Context;

import com.hackzurich.database.TestDataRepository;
import com.hackzurich.database.TestIdRepository;
import com.hackzurich.model.data.TestData;

public final class UpdateDataService {
    private final TestDataRepository testDataRepository;
    private final TestIdRepository testIdRepository;

    public UpdateDataService(Context context) {
        testDataRepository = new TestDataRepository(context);
        testIdRepository = new TestIdRepository(context);
    }

    public void update(String questionId, boolean wasCorrect) {
        String currentTestId = testIdRepository.getCurrentTestId();
        update(testDataRepository.get(currentTestId), questionId, wasCorrect);
    }

    public void update(TestData testData, String questionId, boolean wasCorrect) {
        testData.addData(questionId, wasCorrect);
        testDataRepository.set(testData);
    }
}
