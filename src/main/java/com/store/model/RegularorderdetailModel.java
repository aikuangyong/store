package com.store.model;

import com.alibaba.fastjson.JSON;
import com.store.utils.EncryptUtils;
import lombok.Data;

import java.util.*;

@Data
public class RegularorderdetailModel extends PageModel {

    public Map<String, String> excelHead() {
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("detailid", "主键");
        excelHead.put("content", "备注信息");
        excelHead.put("userid", "订单金额");
        excelHead.put("nickname", "用户名");
        excelHead.put("phoneno", "手机号码");
        excelHead.put("orderprice", "订单金额");
        excelHead.put("paytype", "支付方式(1支付宝2微信3打折卡)");
        excelHead.put("unit", "燕窝含量");
        excelHead.put("timestagetext", "配送时间段字符串");
        excelHead.put("startsendtime", "配送开始日期");
        excelHead.put("sendcount", "配送次数");
        excelHead.put("sendday", "配送方式");
        excelHead.put("surplus", "剩余次数");
        excelHead.put("receiveaddress", "收货地址");
        excelHead.put("sendweek", "自定义天");
        return excelHead;
    }

    private Integer consumeCount;
    private String detailid;
    private String orderno;
    private String orderid;
    private String content;
    private String userid;
    private String nickname;
    private String phoneno;
    private Double orderprice;
    private String paytype;
    private String unit;
    private String timestagetext;
    private String timestage;
    private Date startsendtime;
    private Integer sendcount;
    private String sendday;
    private Integer surplus;
    private String receiveaddress;
    private String sendweek;
    private String saleprice;
    private String tradetime;
    private String orderstatus ;
    private String storeid;

    private RegularDate regularDate;

    public RegularDate getRegularDate() {
        return regularDate;
    }

    public void setRegularDate(RegularDate regularDate) {
        this.regularDate = regularDate;
    }

    public RegularorderdetailModel() {
    }

    public RegularorderdetailModel(RegularorderdetailModel detailModel) {
        Random random = new Random();
        this.detailid = EncryptUtils.md5(UUID.randomUUID().toString() + random.nextInt(Integer.MAX_VALUE)).toString();
        this.orderid = detailModel.orderid;
        this.content = detailModel.getContent();
        this.userid = detailModel.getUserid();
        this.nickname = detailModel.getNickname();
        this.phoneno = detailModel.getPhoneno();
        this.orderprice = detailModel.getOrderprice();
        this.paytype = detailModel.getPaytype();
        this.unit = detailModel.getUnit();
        this.timestagetext = detailModel.getTimestagetext();
        this.timestage = detailModel.getTimestage();
        this.startsendtime = detailModel.getStartsendtime();
        this.sendcount = detailModel.getSendcount();
        this.sendday = detailModel.getSendday();
        this.surplus = detailModel.getSurplus();
        this.receiveaddress = detailModel.getReceiveaddress();
        this.sendweek = detailModel.getSendweek();
        this.saleprice = detailModel.getSaleprice();
    }

    /**
     * 获取主键
     *
     * @return
     */
    public String getDetailid() {
        return detailid;
    }

    /**
     * 设置主键
     *
     * @param detailid
     */
    public void setDetailid(String detailid) {
        this.detailid = detailid;
    }

    /**
     * 获取外键,关联订单
     *
     * @return
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * 设置外键,关联订单
     *
     * @param orderid
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    /**
     * 获取备注信息
     *
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置备注信息
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取订单金额
     *
     * @return
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置订单金额
     *
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取用户名
     *
     * @return
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置用户名
     *
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
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
     * 获取支付方式(1支付宝2微信3打折卡)
     *
     * @return
     */
    public String getPaytype() {
        return paytype;
    }

    /**
     * 设置支付方式(1支付宝2微信3打折卡)
     *
     * @param paytype
     */
    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    /**
     * 获取燕窝含量
     *
     * @return
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置燕窝含量
     *
     * @param unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 获取配送时间段字符串
     *
     * @return
     */
    public String getTimestagetext() {
        return timestagetext;
    }

    /**
     * 设置配送时间段字符串
     *
     * @param timestagetext
     */
    public void setTimestagetext(String timestagetext) {
        this.timestagetext = timestagetext;
    }

    /**
     * 获取配送时间段
     *
     * @return
     */
    public String getTimestage() {
        return timestage;
    }

    /**
     * 设置配送时间段
     *
     * @param timestage
     */
    public void setTimestage(String timestage) {
        this.timestage = timestage;
    }

    /**
     * 获取配送开始日期
     *
     * @return
     */
    public Date getStartsendtime() {
        return startsendtime;
    }

    /**
     * 设置配送开始日期
     *
     * @param startsendtime
     */
    public void setStartsendtime(Date startsendtime) {
        this.startsendtime = startsendtime;
    }

    /**
     * 获取配送次数
     *
     * @return
     */
    public Integer getSendcount() {
        return sendcount;
    }

    /**
     * 设置配送次数
     *
     * @param sendcount
     */
    public void setSendcount(Integer sendcount) {
        this.sendcount = sendcount;
    }

    /**
     * 获取配送方式
     *
     * @return
     */
    public String getSendday() {
        return sendday;
    }

    /**
     * 设置配送方式
     *
     * @param sendday
     */
    public void setSendday(String sendday) {
        this.sendday = sendday;
    }

    /**
     * 获取剩余次数
     *
     * @return
     */
    public Integer getSurplus() {
        return surplus;
    }

    /**
     * 设置剩余次数
     *
     * @param surplus
     */
    public void setSurplus(Integer surplus) {
        this.surplus = surplus;
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
     * 获取自定义天
     *
     * @return
     */
    public String getSendweek() {
        return sendweek;
    }

    /**
     * 设置自定义天
     *
     * @param sendweek
     */
    public void setSendweek(String sendweek) {
        this.sendweek = sendweek;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}