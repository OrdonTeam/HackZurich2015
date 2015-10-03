package com.hackzurich.model.data;

import java.io.Serializable;
import java.util.List;

public enum QuestionDataStatus implements Serializable {
    EASY, MEDIUM, HARD;

    public static QuestionDataStatus getFor(List<Boolean> history) {
        if (history.isEmpty()) return HARD;
        int correctCount = 0;
        int wrongCount = 0;
        for (Boolean wasCorrect : history) {
            if (wasCorrect) {
                correctCount++;
            } else {
                wrongCount++;
            }
        }
        if (wrongCount > correctCount || correctCount == 0)
            return HARD;
        if (wrongCount > 0)
            return MEDIUM;
        return EASY;
    }
}
