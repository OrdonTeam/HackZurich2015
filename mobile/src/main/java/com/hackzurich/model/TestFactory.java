package com.hackzurich.model;

import java.util.ArrayList;
import java.util.List;

public final class TestFactory {

    public static Test newTest() {
        return new Test(newQuestions());
    }

    private static List<Question> newQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What colour is mars", newAnswers()));
        return questions;
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
