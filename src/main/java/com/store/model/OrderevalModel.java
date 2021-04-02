package com.store.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class OrderevalModel extends PageModel {

    public Map<String, String> excelHead() {
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("evalid", "主键");
        excelHead.put("userid", "用户编号");
        excelHead.put("evalscore", "评分");
        excelHead.put("evalcontent", "评价内容");
        excelHead.put("vegetable", "菜品编号");
        excelHead.put("vegetablename", "菜品内容");
        excelHead.put("evaltime", "评价时间");
        excelHead.put("usericon", "用户头像");
        excelHead.put("vegetableimg", "菜品图片");
        excelHead.put("createtime", "创建时间");
        excelHead.put("nickname", "昵称");
        excelHead.put("orderno", "订单编号");
        excelHead.put("valid", "是否显示");
        return excelHead;
    }

    private String evalid;
    private String userid;
    private String evalscore;
    private String evalcontent;
    private String vegetable;
    private String vegetablename;
    private String vegetablecontent;
    private Date evaltime;
    private String usericon;
    private String vegetableimg;
    private String imggrp;
    private Date createtime;
    private String nickname;
    private String orderno;
    private String valid;
    private Integer istop;

    /**
     * 获取主键
     *
     * @return
     */
    public String getEvalid() {
        return evalid;
    }

    /**
     * 设置主键
     *
     * @param evalid
     */
    public void setEvalid(String evalid) {
        this.evalid = evalid;
    }

    /**
     * 获取用户编号
     *
     * @return
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置用户编号
     *
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取评分
     *
     * @return
     */
    public String getEvalscore() {
        return evalscore;
    }

    /**
     * 设置评分
     *
     * @param evalscore
     */
    public void setEvalscore(String evalscore) {
        this.evalscore = evalscore;
    }

    /**
     * 获取评价内容
     *
     * @return
     */
    public String getEvalcontent() {
        return evalcontent;
    }

    /**
     * 设置评价内容
     *
     * @param evalcontent
     */
    public void setEvalcontent(String evalcontent) {
        this.evalcontent = evalcontent;
    }

    /**
     * 获取菜品编号
     *
     * @return
     */
    public String getVegetable() {
        return vegetable;
    }

    /**
     * 设置菜品编号
     *
     * @param vegetable
     */
    public void setVegetable(String vegetable) {
        this.vegetable = vegetable;
    }

    /**
     * 获取菜品内容
     *
     * @return
     */
    public String getVegetablename() {
        return vegetablename;
    }

    /**
     * 设置菜品内容
     *
     * @param vegetablename
     */
    public void setVegetablename(String vegetablename) {
        this.vegetablename = vegetablename;
    }

    /**
     * 获取评价时间
     *
     * @return
     */
    public Date getEvaltime() {
        return evaltime;
    }

    /**
     * 设置评价时间
     *
     * @param evaltime
     */
    public void setEvaltime(Date evaltime) {
        this.evaltime = evaltime;
    }

    /**
     * 获取用户头像
     *
     * @return
     */
    public String getUsericon() {
        return usericon;
    }

    /**
     * 设置用户头像
     *
     * @param usericon
     */
    public void setUsericon(String usericon) {
        this.usericon = usericon;
    }

    /**
     * 获取菜品图片
     *
     * @return
     */
    public String getVegetableimg() {
        return vegetableimg;
    }

    /**
     * 设置菜品图片
     *
     * @param vegetableimg
     */
    public void setVegetableimg(String vegetableimg) {
        this.vegetableimg = vegetableimg;
    }

    /**
     * 获取创建时间
     *
     * @return
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置创建时间
     *
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取昵称
     *
     * @return
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置昵称
     *
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取订单编号
     *
     * @return
     */
    public String getOrderno() {
        return orderno;
    }

    /**
     * 设置订单编号
     *
     * @param orderno
     */
    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    /**
     * 获取是否显示
     *
     * @return
     */
    public String getValid() {
        return valid;
    }

    /**
     * 设置是否显示
     *
     * @param valid
     */
    public void setValid(String valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}