package com.store.model;

import lombok.Data;

@Data
public class DeleteRecord {

    private String content;
    private String datatype;

    public DeleteRecord(String content, String datatype) {
        this.content = content;
        this.datatype = datatype;
    }

}