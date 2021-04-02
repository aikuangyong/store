package com.store.model.pos;

import lombok.Data;

@Data
public class PosPay {

    private int user_id;
    private String other_id;
    private String storeCode;
    private String brandId;
    private String openId;
    private String mobile;
    private String wxConsumeNo;
    private String orderNo;
    private Double price;
    private String remark;
    private String oldOrderNo;

}