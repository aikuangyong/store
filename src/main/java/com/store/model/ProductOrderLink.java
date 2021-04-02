package com.store.model;

import lombok.Data;

import java.util.Date;

@Data
public class ProductOrderLink {
    private String vid;
    private String suborderid;
    private Date createtime;

}