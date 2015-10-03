package com.hackzurich.service;

import android.content.Context;

import com.hackzurich.database.TestDataRepository;
import com.hackzurich.model.data.TestData;

public final class UpdateDataService {
    private final TestDataRepository testDataRepository;

    public UpdateDataService(Context context) {
        testDataRepository = new TestDataRepository(context);
    }

    public void update(TestData testData, String questionId, boolean wasCorrect) {
        testData.addData(questionId,wasCorrect);
        testDataRepository.set(testData);
    }
}
