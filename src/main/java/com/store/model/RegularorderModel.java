package com.store.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.*;

@Data
public class RegularorderModel extends PageModel {

    public Map<String, String> excelHead() {
        Map<String, String> excelHead = new LinkedHashMap<>();
        excelHead.put("orderno", "订单编号");
        excelHead.put("productname", "商品名称");
        excelHead.put("nickname", "昵称");
        excelHead.put("phoneno", "电话号码");
        excelHead.put("specification", "规格");
        excelHead.put("price", "价格");
        excelHead.put("surplus", "剩余次数");
        excelHead.put("tradetime", "下单时间");
        return excelHead;
    }

    private String orderid;
    private String orderno;
    private String userid;
    private String productname;
    private String specification;
    private Double price;
    private Date tradetime;
    private String regularid;
    private String nickname;
    private String phoneno;
    private String addressid;
    private String sendstarttime;
    private String paytype;
    private Date paytime;
    private String cardno;
    private String surplus;
    private Date begintime;
    private Date endtime;


    public String getSendstarttime() {
        return sendstarttime;
    }

    public void setSendstarttime(String sendstarttime) {
        this.sendstarttime = sendstarttime;
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    private RegularorderdetailModel regularorderdetailModel;

    private List<RegularsuborderModel> subOrderList;

    public List<RegularsuborderModel> getSubOrderList() {
        return subOrderList;
    }

    public void setSubOrderList(List<RegularsuborderModel> subOrderList) {
        this.subOrderList = subOrderList;
    }

    public RegularorderdetailModel getRegularorderdetailModel() {
        return regularorderdetailModel;
    }

    public void setRegularorderdetailModel(RegularorderdetailModel regularorderdetailModel) {
        this.regularorderdetailModel = regularorderdetailModel;
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
     * 获取外键，用户编号
     *
     * @return
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置外键，用户编号
     *
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取商品名称
     *
     * @return
     */
    public String getProductname() {
        return productname;
    }

    /**
     * 设置商品名称
     *
     * @param productname
     */
    public void setProductname(String productname) {
        this.productname = productname;
    }

    /**
     * 获取规格
     *
     * @return
     */
    public String getSpecification() {
        return specification;
    }

    /**
     * 设置规格
     *
     * @param specification
     */
    public void setSpecification(String specification) {
        this.specification = specification;
    }

    /**
     * 获取价格
     *
     * @return
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 设置价格
     *
     * @param price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * 获取下单时间
     *
     * @return
     */
    public Date getTradetime() {
        return tradetime;
    }

    /**
     * 设置下单时间
     *
     * @param tradetime
     */
    public void setTradetime(Date tradetime) {
        this.tradetime = tradetime;
    }

    /**
     * 获取外键,菜品ID
     *
     * @return
     */
    public String getRegularid() {
        return regularid;
    }

    /**
     * 设置外键,菜品ID
     *
     * @param regularid
     */
    public void setRegularid(String regularid) {
        this.regularid = regularid;
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
     * 获取电话号码
     *
     * @return
     */
    public String getPhoneno() {
        return phoneno;
    }

    /**
     * 设置电话号码
     *
     * @param phoneno
     */
    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}