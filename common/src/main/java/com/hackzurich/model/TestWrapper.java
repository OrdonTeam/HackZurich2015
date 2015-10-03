package com.hackzurich.model;

import com.hackzurich.model.data.QuestionDataStatus;
import com.hackzurich.model.data.TestData;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public final class TestWrapper implements Serializable {
    private static final Random random = new Random();
    private final Test test;
    private final TestData testData;

    public TestWrapper(Test test, TestData testData) {
        this.test = test;
        this.testData = testData;
    }

    public TestData getTestData() {
        return testData;
    }

    public void addData(String questionId, boolean wasCorrect) {
        testData.addData(questionId, wasCorrect);
    }

    public Question getRandomQuestionFor(List<QuestionDataStatus> questionsTypes) {
        List<String> ids = testData.getQuestionsIdsInTypes(questionsTypes);
        int questionIndex = generateRandomQuestionIndex(ids.size());
        String questionId = ids.get(questionIndex);
        return test.getQuestionsWithId(questionId);
    }

    private int generateRandomQuestionIndex(int size) {
        return new Random().nextInt(size);
    }
}