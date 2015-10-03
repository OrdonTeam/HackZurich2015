package com.hackzurich.model;

import java.io.Serializable;
import java.util.List;

public final class Test implements Serializable {

    private final long id;
    private final List<Question> questions;

    public Test(long id, List<Question> questions) {
        this.id = id;
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public long getId() {
        return id;
    }
}
