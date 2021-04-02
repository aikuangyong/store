package com.store.model.pos;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class CounsemeDetailVO {
    public static final String SUB = "1";
    public static final String ADD = "2";

    private Date counsumeDate;
    private String dateStr;
    private Double price;
    private String type;
    private String typeStr;
    public static final Map<String,String> typeMap = new HashMap<>();
    static {
        typeMap.put("1","消费");
        typeMap.put("2","充值");
    }
}