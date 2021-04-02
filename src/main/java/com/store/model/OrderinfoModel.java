package com.store.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.*;

@Data
public class OrderinfoModel extends PageModel {

    public Map<String, String> excelHead() {
        Map<String, String> excelHead = new LinkedHashMap<>();
        excelHead.put("nickname", "昵称");
        excelHead.put("orderno", "订单号");
        excelHead.put("createtime", "下单时间");
        excelHead.put("sendtime", "配送时间");
        excelHead.put("orderstatus", "订单状态");
        excelHead.put("sendtype", "配送方式");
        excelHead.put("saleprice", "实际金额");
        return excelHead;
    }


    private String orderid;
    private String sendinfo;
    private String phoneno;
    private String storeid;
    private String storename;
    private String storephone;
    private String storeaddress;

    private String receiveaddress;
    private String vegetable;
    private String vegetableimage;
    private String orderstatus;
    private String receivephone;
    private String receiveuser;
    private String nickname;
    private String orderno;
    private Date createtime;
    private String sendtime;
    private String sendtype;
    private Double saleprice;
    private Double orderprice;
    private String userid;
    private String addressid;
    private String paytype;
    private String cardno;
    private Date paytime;
    private Long begincreatetime;
    private Long endcreatetime;

    private List<OrderdetailModel> detailList;

    public String getReceiveuser() {
        return receiveuser;
    }

    public void setReceiveuser(String receiveuser) {
        this.receiveuser = receiveuser;
    }

    public List<OrderdetailModel> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<OrderdetailModel> detailList) {
        this.detailList = detailList;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    /**
     * 获取主键
     *
     * @return
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * 设置主键
     *
     * @param orderid
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    /**
     * 获取配送方式
     *
     * @return
     */
    public String getSendtype() {
        return sendtype;
    }

    /**
     * 设置配送方式
     *
     * @param sendtype
     */
    public void setSendtype(String sendtype) {
        this.sendtype = sendtype;
    }

    /**
     * 获取下单时间
     *
     * @return
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置下单时间
     *
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取配送信息
     *
     * @return
     */
    public String getSendinfo() {
        return sendinfo;
    }

    /**
     * 设置配送信息
     *
     * @param sendinfo
     */
    public void setSendinfo(String sendinfo) {
        this.sendinfo = sendinfo;
    }

    /**
     * 获取手机号码
     *
     * @return
     */
    public String getPhoneno() {
        return phoneno;
    }

    /**
     * 设置手机号码
     *
     * @param phoneno
     */
    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    /**
     * 获取订单金额
     *
     * @return
     */
    public Double getOrderprice() {
        return orderprice;
    }

    /**
     * 设置订单金额
     *
     * @param orderprice
     */
    public void setOrderprice(Double orderprice) {
        this.orderprice = orderprice;
    }

    /**
     * 获取实际金额
     *
     * @return
     */
    public Double getSaleprice() {
        return saleprice;
    }

    /**
     * 设置实际金额
     *
     * @param saleprice
     */
    public void setSaleprice(Double saleprice) {
        this.saleprice = saleprice;
    }

    /**
     * 获取外键关联门店表
     *
     * @return
     */
    public String getStoreid() {
        return storeid;
    }

    /**
     * 设置外键关联门店表
     *
     * @param storeid
     */
    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    /**
     * 获取门店名称
     *
     * @return
     */
    public String getStorename() {
        return storename;
    }

    /**
     * 设置门店名称
     *
     * @param storename
     */
    public void setStorename(String storename) {
        this.storename = storename;
    }

    /**
     * 获取收货地址
     *
     * @return
     */
    public String getReceiveaddress() {
        return receiveaddress;
    }

    /**
     * 设置收货地址
     *
     * @param receiveaddress
     */
    public void setReceiveaddress(String receiveaddress) {
        this.receiveaddress = receiveaddress;
    }

    /**
     * 获取配送时间
     *
     * @return
     */
    public String getSendtime() {
        return sendtime;
    }

    /**
     * 设置配送时间
     *
     * @param sendtime
     */
    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    /**
     * 获取配送菜品
     *
     * @return
     */
    public String getVegetable() {
        return vegetable;
    }

    /**
     * 设置配送菜品
     *
     * @param vegetable
     */
    public void setVegetable(String vegetable) {
        this.vegetable = vegetable;
    }

    /**
     * 获取菜品图片
     *
     * @return
     */
    public String getVegetableimage() {
        return vegetableimage;
    }

    /**
     * 设置菜品图片
     *
     * @param vegetableimage
     */
    public void setVegetableimage(String vegetableimage) {
        this.vegetableimage = vegetableimage;
    }

    /**
     * 获取订单状态
     *
     * @return
     */
    public String getOrderstatus() {
        return orderstatus;
    }

    /**
     * 设置订单状态
     *
     * @param orderstatus
     */
    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    /**
     * 获取提货电话
     *
     * @return
     */
    public String getReceivephone() {
        return receivephone;
    }

    /**
     * 设置提货电话
     *
     * @param receivephone
     */
    public void setReceivephone(String receivephone) {
        this.receivephone = receivephone;
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}