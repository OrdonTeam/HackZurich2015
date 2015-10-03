package com.hackzurich.model;

import java.io.Serializable;
import java.util.List;

public final class Question implements Serializable {
    private final String id;
    private final String text;
    private final List<Answer> answers;

    public Question(String id, String text, List<Answer> answers) {
        this.id = id;
        this.text = text;
        this.answers = answers;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
