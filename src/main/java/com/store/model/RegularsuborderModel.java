package com.store.model;

import com.alibaba.fastjson.JSON;
import com.store.utils.CodeUtil;
import com.store.utils.EncryptUtils;
import lombok.Data;

import java.util.*;

@Data
public class RegularsuborderModel extends RegularDate {

    public Map<String, String> excelHead() {
        Map<String, String> excelHead = new LinkedHashMap<>();
        excelHead.put("regularno", "主订单编号");
        excelHead.put("productname", "商品名称");
        excelHead.put("receivename", "收货人");
        excelHead.put("receivephone", "手机号码");
        excelHead.put("unit", "燕窝含量");
        excelHead.put("stagetime", "配送开始时段");
        excelHead.put("status", "状态");//(1待配送2配送中3配送完成)
        excelHead.put("sendtime", "配送日期");
        return excelHead;
    }

    private String suborderid;
    private String regularno;
    private String suborderno;
    private String userid;
    private String receivename;
    private String receivephone;
    private String unit;
    private String beginstagetime;
    private String endstagetime;
    private String stagetime;
    private String status;
    private String oldStatus;
    private Date donetime;
    private String receiveaddress;
    private String productname;
    private String vegetableid;
    private String sendtime;
    private String addressid;
    private Date beginsendtime;
    private Date endsendtime;
    private String stid;
    private String eqSendTime;

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    private List<RegularOrderInfo> userOrderList = new ArrayList<>();

    public List<RegularOrderInfo> getUserOrderList() {
        return userOrderList;
    }

    public void setUserOrderList(List<RegularOrderInfo> userOrderList) {
        this.userOrderList = userOrderList;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getVegetableid() {
        return vegetableid;
    }

    public void setVegetableid(String vegetableid) {
        this.vegetableid = vegetableid;
    }

    public RegularsuborderModel() {
    }

    public RegularsuborderModel(RegularorderModel regularorderModel) {
        Random random = new Random();
        this.suborderid = EncryptUtils.md5(UUID.randomUUID().toString() + random.nextInt(Integer.MAX_VALUE)).toString();
        this.suborderno = CodeUtil.getOrderNo(3);
        this.regularno = regularorderModel.getOrderno();
        this.userid = regularorderModel.getUserid();
        this.receivename = regularorderModel.getNickname();
        this.receivephone = regularorderModel.getPhoneno();
        this.unit = regularorderModel.getRegularorderdetailModel().getUnit();
        this.beginstagetime = regularorderModel.getRegularorderdetailModel().getTimestagetext().split("-")[0];
        this.endstagetime = regularorderModel.getRegularorderdetailModel().getTimestagetext().split("-")[1];
        this.receiveaddress = regularorderModel.getRegularorderdetailModel().getReceiveaddress();
        this.productname = regularorderModel.getProductname();
        this.addressid = regularorderModel.getAddressid();
    }

    /**
     * 获取主键
     *
     * @return
     */
    public String getSuborderid() {
        return suborderid;
    }

    /**
     * 设置主键
     *
     * @param suborderid
     */
    public void setSuborderid(String suborderid) {
        this.suborderid = suborderid;
    }

    /**
     * 获取主订单编号
     *
     * @return
     */
    public String getRegularno() {
        return regularno;
    }

    /**
     * 设置主订单编号
     *
     * @param regularno
     */
    public void setRegularno(String regularno) {
        this.regularno = regularno;
    }

    /**
     * 获取订单编号
     *
     * @return
     */
    public String getSuborderno() {
        return suborderno;
    }

    /**
     * 设置订单编号
     *
     * @param suborderno
     */
    public void setSuborderno(String suborderno) {
        this.suborderno = suborderno;
    }

    /**
     * 获取用户外键
     *
     * @return
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置用户外键
     *
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取收货人
     *
     * @return
     */
    public String getReceivename() {
        return receivename;
    }

    /**
     * 设置收货人
     *
     * @param receivename
     */
    public void setReceivename(String receivename) {
        this.receivename = receivename;
    }

    /**
     * 获取手机号码
     *
     * @return
     */
    public String getReceivephone() {
        return receivephone;
    }

    /**
     * 设置手机号码
     *
     * @param receivephone
     */
    public void setReceivephone(String receivephone) {
        this.receivephone = receivephone;
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
     * 获取配送开始时段
     *
     * @return
     */
    public String getBeginstagetime() {
        return beginstagetime;
    }

    /**
     * 设置配送开始时段
     *
     * @param beginstagetime
     */
    public void setBeginstagetime(String beginstagetime) {
        this.beginstagetime = beginstagetime;
    }

    /**
     * 获取配送结束时段
     *
     * @return
     */
    public String getEndstagetime() {
        return endstagetime;
    }

    /**
     * 设置配送结束时段
     *
     * @param endstagetime
     */
    public void setEndstagetime(String endstagetime) {
        this.endstagetime = endstagetime;
    }

    /**
     * 获取状态(1待配送2配送中3配送完成)
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态(1待配送2配送中3配送完成)
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取完成时间
     *
     * @return
     */
    public Date getDonetime() {
        return donetime;
    }

    /**
     * 设置完成时间
     *
     * @param donetime
     */
    public void setDonetime(Date donetime) {
        this.donetime = donetime;
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}