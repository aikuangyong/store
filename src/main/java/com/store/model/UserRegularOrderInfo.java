package com.store.model;

import io.swagger.annotations.ApiModelProperty;

public class UserRegularOrderInfo {

    private String regularno;
    private String suborderid;
    @ApiModelProperty(value="含量",name="unit",required=false)
    private String unit;
    private String orderprice;
    private String sendcount;
    private String sendtype;
    private String sendweek;
    private String donestagetime;
    private String startsendtime;
    private String sendtime;
    private String surplus;
    private String status;
    private String userid;
    private String tradetime;
    private String orderno;
    private String suborderno;
    private String addressid;

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    public String getSuborderno() {
        return suborderno;
    }

    public void setSuborderno(String suborderno) {
        this.suborderno = suborderno;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }


    public String getTradetime() {
        return tradetime;
    }

    public void setTradetime(String tradetime) {
        this.tradetime = tradetime;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRegularno() {
        return regularno;
    }

    public void setRegularno(String regularno) {
        this.regularno = regularno;
    }

    public String getSuborderid() {
        return suborderid;
    }

    public void setSuborderid(String suborderid) {
        this.suborderid = suborderid;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOrderprice() {
        return orderprice;
    }

    public void setOrderprice(String orderprice) {
        this.orderprice = orderprice;
    }

    public String getSendcount() {
        return sendcount;
    }

    public void setSendcount(String sendcount) {
        this.sendcount = sendcount;
    }

    public String getSendtype() {
        return sendtype;
    }

    public void setSendtype(String sendtype) {
        this.sendtype = sendtype;
    }

    public String getSendweek() {
        return sendweek;
    }

    public void setSendweek(String sendweek) {
        this.sendweek = sendweek;
    }

    public String getDonestagetime() {
        return donestagetime;
    }

    public void setDonestagetime(String donestagetime) {
        this.donestagetime = donestagetime;
    }

    public String getStartsendtime() {
        return startsendtime;
    }

    public void setStartsendtime(String startsendtime) {
        this.startsendtime = startsendtime;
    }

    public String getSurplus() {
        return surplus;
    }

    public void setSurplus(String surplus) {
        this.surplus = surplus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}