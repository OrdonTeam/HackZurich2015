package com.hackzurich.model.data;

import java.io.Serializable;

public final class QuestionData implements Serializable {
    private final QuestionDataStatus status;

    public QuestionData(QuestionDataStatus status) {
        this.status = status;
    }

    public QuestionDataStatus getStatus() {
        return status;
    }

    public static QuestionData claen() {
        return new QuestionData(QuestionDataStatus.HARD);
    }
}
