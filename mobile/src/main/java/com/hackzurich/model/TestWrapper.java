package com.hackzurich.model;

import com.hackzurich.model.data.TestData;

import java.io.Serializable;

public final class TestWrapper implements Serializable {
    private final Test test;
    private final TestData testData;

    public TestWrapper(Test test, TestData testData) {
        this.test = test;
        this.testData = testData;
    }

    public TestData getTestData() {
        return testData;
    }
}