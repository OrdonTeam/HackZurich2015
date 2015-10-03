package com.hackzurich.model.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class QuestionData implements Serializable {
    private final QuestionDataStatus status;
    private final List<Boolean> history;

    private QuestionData(QuestionDataStatus status) {
        this.status = status;
        this.history = new ArrayList<>();
    }

    private QuestionData(List<Boolean> history) {
        this.status = QuestionDataStatus.getFor(history);
        this.history = history;
    }

    public QuestionDataStatus getStatus() {
        return status;
    }

    public static QuestionData claen() {
        return new QuestionData(QuestionDataStatus.HARD);
    }

    public QuestionData add(boolean wasCorrect) {
        List<Boolean> newHistory = new ArrayList<>();
        newHistory.addAll(history);
        newHistory.add(wasCorrect);
        return new QuestionData(newHistory);
    }
}
