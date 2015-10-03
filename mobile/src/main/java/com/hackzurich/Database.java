package com.hackzurich;

import com.hackzurich.model.Question;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private final List<Question> questions = new ArrayList<>();
    private String name;
    private String path;

    public Database(String name, String path) {
        this.name = name;
        this.path = path;

        parseQuestions();
    }

    private void parseQuestions() {

    }
}
