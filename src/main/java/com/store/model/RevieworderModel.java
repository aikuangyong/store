package com.store.model;

import com.alibaba.fastjson.JSON;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RevieworderModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("roid","主键");
        excelHead.put("userid","用户编号");
        excelHead.put("nickname","昵称");
        excelHead.put("phoneno","手机号码");
        excelHead.put("cardno","打折卡号");
        excelHead.put("cardcost","打折卡力度");
        excelHead.put("beforebalance","原余额");
        excelHead.put("afterbalance","调整后余额");
        excelHead.put("reviewer","操作人账号");
        excelHead.put("store","所属门店");
        excelHead.put("edittime","操作时间");
        excelHead.put("createtime","创建时间");
        excelHead.put("orderno","订单编号");
        excelHead.put("status","状态");
        return excelHead;
    }

    private String roid;
    private String userid;
    private String nickname;
    private String phoneno;
    private String cardno;
    private String cardcost;
    private Double beforebalance;
    private Double afterbalance;
    private String reviewer;
    private String store;
    private Date edittime;
    private Date createtime;
    private String orderno;
    private String status;

    /**
     * 获取主键
     * @return
     */
    public String getRoid() {
        return roid;
    }

    /**
     * 设置主键
     * @param roid
     */
    public void setRoid(String roid) {
        this.roid = roid;
    }
    /**
     * 获取用户编号
     * @return
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置用户编号
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }
    /**
     * 获取昵称
     * @return
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置昵称
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    /**
     * 获取手机号码
     * @return
     */
    public String getPhoneno() {
        return phoneno;
    }

    /**
     * 设置手机号码
     * @param phoneno
     */
    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
    /**
     * 获取打折卡号
     * @return
     */
    public String getCardno() {
        return cardno;
    }

    /**
     * 设置打折卡号
     * @param cardno
     */
    public void setCardno(String cardno) {
        this.cardno = cardno;
    }
    /**
     * 获取打折卡力度
     * @return
     */
    public String getCardcost() {
        return cardcost;
    }

    /**
     * 设置打折卡力度
     * @param cardcost
     */
    public void setCardcost(String cardcost) {
        this.cardcost = cardcost;
    }
    /**
     * 获取原余额
     * @return
     */
    public Double getBeforebalance() {
        return beforebalance;
    }

    /**
     * 设置原余额
     * @param beforebalance
     */
    public void setBeforebalance(Double beforebalance) {
        this.beforebalance = beforebalance;
    }
    /**
     * 获取调整后余额
     * @return
     */
    public Double getAfterbalance() {
        return afterbalance;
    }

    /**
     * 设置调整后余额
     * @param afterbalance
     */
    public void setAfterbalance(Double afterbalance) {
        this.afterbalance = afterbalance;
    }
    /**
     * 获取操作人账号
     * @return
     */
    public String getReviewer() {
        return reviewer;
    }

    /**
     * 设置操作人账号
     * @param reviewer
     */
    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }
    /**
     * 获取所属门店
     * @return
     */
    public String getStore() {
        return store;
    }

    /**
     * 设置所属门店
     * @param store
     */
    public void setStore(String store) {
        this.store = store;
    }
    /**
     * 获取操作时间
     * @return
     */
    public Date getEdittime() {
        return edittime;
    }

    /**
     * 设置操作时间
     * @param edittime
     */
    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }
    /**
     * 获取创建时间
     * @return
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置创建时间
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
    /**
     * 获取订单编号
     * @return
     */
    public String getOrderno() {
        return orderno;
    }

    /**
     * 设置订单编号
     * @param orderno
     */
    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }
    /**
     * 获取状态
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}