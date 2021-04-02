package com.store.model;

import java.util.List;

public class SendDay {
    private int id;

    private String fieldKey;
    private String fieldValue;
    private List<String> sendDay;

    public List<String> getSendDay() {
        return sendDay;
    }

    public void setSendDay(List<String> sendDay) {
        this.sendDay = sendDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}