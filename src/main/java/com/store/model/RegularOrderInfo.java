package com.store.model;

import lombok.Data;

@Data
public class RegularOrderInfo {

    private Integer issend;
    private String timestagetext;
    private String timestage;
    private String orderno;
    private String vegetablename;
    private String vid;
    private String userid;
    private String configdate;
    private String imghref;
    private String content;
    private String choiceproduct;
    private String paytype;
    private String cardno;
    private String paytime;
    private String surplus;

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getChoiceproduct() {
        return choiceproduct;
    }

    public void setChoiceproduct(String choiceproduct) {
        this.choiceproduct = choiceproduct;
    }

    public Integer getIssend() {
        return issend;
    }

    public void setIssend(Integer issend) {
        this.issend = issend;
    }

    public String getTimestagetext() {
        return timestagetext;
    }

    public void setTimestagetext(String timestagetext) {
        this.timestagetext = timestagetext;
    }

    public String getTimestage() {
        return timestage;
    }

    public void setTimestage(String timestage) {
        this.timestage = timestage;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getVegetablename() {
        return vegetablename;
    }

    public void setVegetablename(String vegetablename) {
        this.vegetablename = vegetablename;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getConfigdate() {
        return configdate;
    }

    public void setConfigdate(String configdate) {
        this.configdate = configdate;
    }

    public String getImghref() {
        return imghref;
    }

    public void setImghref(String imghref) {
        this.imghref = imghref;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}