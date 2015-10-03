package com.hackzurich.model.data;

import com.hackzurich.model.Question;
import com.hackzurich.model.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TestData implements Serializable {

    private String testId;
    private Map<String, QuestionData> dataMap = new HashMap<>();

    public TestData(Test test) {
        testId = test.getId();
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
        dataMap.put(questionId, newQuestionData);
    }

    public String getTestId() {
        return testId;
    }

    public List<String> getQuestionsIdsInTypes(List<QuestionDataStatus> questionsTypes) {
        List<String> ids = new ArrayList<>();

        for (Map.Entry<String, QuestionData> entry : dataMap.entrySet()) {
            if(questionsTypes.contains(entry.getValue().getStatus())){
                ids.add(entry.getKey());
            }
        }
        return ids;
    }
}
