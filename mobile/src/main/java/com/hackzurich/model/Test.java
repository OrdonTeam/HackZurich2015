package com.hackzurich.model;

import java.io.Serializable;
import java.util.List;

public final class Test implements Serializable {
    private final List<Question> questions;

    public Test(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
