package com.hackzurich.stub;

import android.content.Context;

import com.hackzurich.database.TestDataRepository;
import com.hackzurich.database.TestIdRepository;
import com.hackzurich.database.TestRepository;
import com.hackzurich.model.Test;
import com.hackzurich.model.data.TestData;
import com.hackzurich.model.stub.TestFactory;

import java.util.HashSet;

public final class STUB_PopulateDatabase {
    public static void populate(Context context) {
        Test test = TestFactory.newTest();
        new TestRepository(context).set(test);
        HashSet<String> testIds = new HashSet<>();
        testIds.add(test.getId());
        new TestIdRepository(context).set(testIds);
        new TestIdRepository(context).setCurrentTestID(test.getId());
        new TestDataRepository(context).set(new TestData(test));
    }
}
