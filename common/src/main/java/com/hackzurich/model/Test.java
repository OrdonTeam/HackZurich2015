package com.hackzurich.model;

import java.io.Serializable;
import java.util.List;

public final class Test implements Serializable {

    private final String id;
    private final List<Question> questions;

    public Test(String id, List<Question> questions) {
        this.id = id;
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public String getId() {
        return id;
    }

    public Question getQuestionsWithId(String questionId) {
        for(Question question : questions) {
            if(question.getId().equals(questionId)) {
                return question;
            }
        }
        throw new RuntimeException("Question with id " + questionId + " not found");
    }
}
