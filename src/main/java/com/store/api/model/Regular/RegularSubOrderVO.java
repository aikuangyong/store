package com.store.api.model.Regular;

import lombok.Data;

import java.util.Date;

@Data
public class RegularSubOrderVO {
    private String suborderid;
    private String regularno;
    private String suborderno;
    private String userid;
    private String receivename;
    private String receivephone;
    private String unit;
    private String stagetimetext;
    private String status;
    private Date donetime;
    private String receiveaddress;
    private String productname;
    private String vegetableid;
    private String sendtime;
}