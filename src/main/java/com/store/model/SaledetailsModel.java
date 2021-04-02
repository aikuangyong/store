package com.store.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class SaledetailsModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new LinkedHashMap<>();
        excelHead.put("orderno","订单号");
        excelHead.put("nickname","昵称");
        excelHead.put("phoneno","手机号码");
        excelHead.put("storename","门店名称");
        excelHead.put("bussinestype","交易类型");
        excelHead.put("paytype","支付方式");
        excelHead.put("bussinesprice","金额");
        excelHead.put("paytime","交易时间");
        return excelHead;
    }

    private String detailid;
    private String orderno;
    private String nickname;
    private String phoneno;
    private String storename;
    private String bussinestype;
    private String paytype;
    private Double bussinesprice;
    private Date paytime;
    private Date startpaytime;
    private Date endpaytime;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}