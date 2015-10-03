package com.hackzurich.model.data;

import com.hackzurich.model.Question;
import com.hackzurich.model.Test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class TestData implements Serializable {
    Map<String, QuestionData> dataMap = new HashMap<>();

    public TestData(Test test) {
        for (Question question : test.getQuestions()) {
            dataMap.put(question.getId(), QuestionData.claen());
        }
    }

    public int countOf(QuestionDataStatus status) {
        int count = 0;
        for (QuestionData questionData : dataMap.values()) {
            if (questionData.getStatus() == status)
                count++;
        }
        return count;
    }

    public void addData(String questionId, boolean wasCorrect) {
        QuestionData oldQuestionData = dataMap.get(questionId);
        QuestionData newQuestionData = oldQuestionData.add(wasCorrect);
        dataMap.put(questionId,newQuestionData);
    }
}
