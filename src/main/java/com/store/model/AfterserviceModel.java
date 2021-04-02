package com.store.model;

import com.alibaba.fastjson.JSON;
import com.store.api.model.order.OrderInfoVO;
import com.store.model.config.ImageinfoModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.*;

@Data
public class AfterserviceModel extends PageModel {

    public Map<String, String> excelHead() {
        Map<String, String> excelHead = new LinkedHashMap<>();
        excelHead.put("backpayno", "退款号");
        excelHead.put("orderno", "订单编号");
        excelHead.put("nickname", "昵称");
        excelHead.put("reason", "申请原因");
        excelHead.put("phoneno", "手机号码");
        excelHead.put("applytime", "申请时间");
        excelHead.put("status", "审核状态");
        excelHead.put("backpaystatus", "退款状态");
        return excelHead;
    }

    @ApiModelProperty(value = "主键", required = true)
    private String serviceid;
    @ApiModelProperty(value = "外键", required = true)
    private String userid;
    @ApiModelProperty(value = "申请原因", required = true)
    private String reason;
    @ApiModelProperty(value = "申请时间", required = true)
    private Date applytime;
    @ApiModelProperty(value = "手机号码", required = true)
    private String phoneno;
    @ApiModelProperty(value = "审核状态", required = true)
    private String status;
    @ApiModelProperty(value = "退款状态", required = true)
    private String backpaystatus;
    @ApiModelProperty(value = "订单编号", required = true)
    private String orderno;
    @ApiModelProperty(value = "子订单编号", required = true)
    private String detailid;
    @ApiModelProperty(value = "退款号", required = true)
    private String backpayno;
    @ApiModelProperty(value = "售后内容", required = true)
    private String content;
    @ApiModelProperty(value = "", required = true)
    private String imggrp;
    @ApiModelProperty(value = "", required = true)
    private String nickname;

    private String vegetablename;
    private String vegetablecontent;
    private String vegetableimg;
    private String tradetime;
    private String orderstatus;

    private Date beginapplytime;
    private Date endapplytime;


    @ApiModelProperty(value = "", hidden = true)
    private OrderInfoVO orderinfoVo;

    private List<ImageinfoModel> imageList;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}