package com.hackzurich;

import com.hackzurich.model.data.QuestionDataStatus;

import java.io.Serializable;
import java.util.List;

public final class QuestionDataStatuses implements Serializable {
    private final List<QuestionDataStatus> statuses;

    public QuestionDataStatuses(List<QuestionDataStatus> statuses) {
        this.statuses = statuses;
    }

    public List<QuestionDataStatus> getStatuses() {
        return statuses;
    }

    public static QuestionDataStatuses of(List<QuestionDataStatus> questionDataStatuses) {
        return new QuestionDataStatuses(questionDataStatuses);
    }

    public static List<QuestionDataStatus> getList(Serializable questionDataStatuses) {
        return ((QuestionDataStatuses) questionDataStatuses).getStatuses();
    }
}
