package com.hackzurich.model;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

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


    public byte[] toByteArray() {
        return SerializationUtils.serialize(this);
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public static Question fromBytes(byte[] data) {
        return (Question) SerializationUtils.deserialize(data);
    }
}
