package com.store.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class CustomerModel extends PageModel {

    public Map<String, String> excelHead() {
        Map<String, String> excelHead = new LinkedHashMap<>();
        excelHead.put("userid", "用户编号");
        excelHead.put("nickname", "昵称");
        excelHead.put("phoneno", "手机号码");
        excelHead.put("logintype", "登录方式");
        excelHead.put("cardCount", "打折卡");
        excelHead.put("registertime", "注册时间");
        return excelHead;
    }

    @ApiModelProperty(hidden = true)
    private String uid;

    @ApiModelProperty(hidden = true)
    private String usertoken;

    @ApiModelProperty(hidden = true)
    private String thridid;

    @ApiModelProperty(hidden = true)
    private String userid;

    @ApiModelProperty(hidden = true)
    private String password;

    @ApiModelProperty(hidden = true)
    private String nickname;

    @ApiModelProperty(hidden = true)
    private String icon;
    @ApiModelProperty(hidden = true)
    private String phoneno;

    @ApiModelProperty(hidden = true)
    private String oldphone;

    @ApiModelProperty(value = "登录方式", hidden = true, notes = "1:密码;2:短信;3:微信")
    private String logintype;

    @ApiModelProperty(hidden = true)
    private String constno;

    @ApiModelProperty(hidden = true)
    private String discount;

    @ApiModelProperty(hidden = true)
    private Double balance;

    @ApiModelProperty(hidden = true)
    private String cardCount;

    @ApiModelProperty(hidden = true)
    private Date registertime;

    private String verifycode;

    @ApiModelProperty(hidden = true)
    private Integer valid;

    private String clienttype;
}