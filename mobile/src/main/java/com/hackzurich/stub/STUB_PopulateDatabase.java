package com.hackzurich.stub;

import android.content.Context;

import com.hackzurich.database.TestDataRepository;
import com.hackzurich.database.TestIdRepository;
import com.hackzurich.database.TestRepository;
import com.hackzurich.model.Test;
import com.hackzurich.model.data.TestData;
import com.hackzurich.model.stub.TestFactory;

public final class STUB_PopulateDatabase {
    public static void populate(Context context) {
        Test test = TestFactory.newTest();
        new TestRepository(context).set(test);
        TestIdRepository testIdRepository = new TestIdRepository(context);
        testIdRepository.add(test.getId());
        testIdRepository.setCurrentTestID(test.getId());
        new TestDataRepository(context).set(new TestData(test));
    }
}
