package com.hackzurich.model.stub;

import com.hackzurich.model.Answer;
import com.hackzurich.model.Question;
import com.hackzurich.model.Test;
import com.hackzurich.model.TestWrapper;
import com.hackzurich.model.data.TestData;

import java.util.ArrayList;
import java.util.List;

public final class TestFactory {

    public static TestWrapper newTestWrapper() {
        Test test = newTest();
        TestData testData = new TestData(test);
        return new TestWrapper(test, testData);
    }

    public static Test newTest() {
        return new Test(123, newQuestions());
    }

    private static List<Question> newQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(newQuestion());
        return questions;
    }

    public static Question newQuestion() {
        return new Question("1", "What colour is mars", newAnswers());
    }

    private static List<Answer> newAnswers() {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("Red", true));
        answers.add(new Answer("Blue", false));
        answers.add(new Answer("Green", false));
        answers.add(new Answer("White", false));
        return answers;
    }
}
