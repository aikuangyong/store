package com.store.model;

import com.alibaba.fastjson.JSON;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StoreModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("storeid","主键");
        excelHead.put("storeno","门店编号");
        excelHead.put("poseno","POS终端号");
        excelHead.put("storeaddress","门店地址");
        excelHead.put("storephone","门店电话");
        excelHead.put("storename","门店名称");
        excelHead.put("createtime","创建时间");
        excelHead.put("valid","是否有限");
        return excelHead;
    }

    private String storeid;
    private String storeno;
    private String poseno;
    private String storeaddress;
    private String lat;
    private String lng;
    private String storephone;
    private String storename;
    private Date createtime;
    private Integer valid;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    /**
     * 获取主键
     * @return
     */
    public String getStoreid() {
        return storeid;
    }

    /**
     * 设置主键
     * @param storeid
     */
    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }
    /**
     * 获取门店编号
     * @return
     */
    public String getStoreno() {
        return storeno;
    }

    /**
     * 设置门店编号
     * @param storeno
     */
    public void setStoreno(String storeno) {
        this.storeno = storeno;
    }
    /**
     * 获取POS终端号
     * @return
     */
    public String getPoseno() {
        return poseno;
    }

    /**
     * 设置POS终端号
     * @param poseno
     */
    public void setPoseno(String poseno) {
        this.poseno = poseno;
    }
    /**
     * 获取门店地址
     * @return
     */
    public String getStoreaddress() {
        return storeaddress;
    }

    /**
     * 设置门店地址
     * @param storeaddress
     */
    public void setStoreaddress(String storeaddress) {
        this.storeaddress = storeaddress;
    }
    /**
     * 获取门店电话
     * @return
     */
    public String getStorephone() {
        return storephone;
    }

    /**
     * 设置门店电话
     * @param storephone
     */
    public void setStorephone(String storephone) {
        this.storephone = storephone;
    }
    /**
     * 获取门店名称
     * @return
     */
    public String getStorename() {
        return storename;
    }

    /**
     * 设置门店名称
     * @param storename
     */
    public void setStorename(String storename) {
        this.storename = storename;
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
     * 获取是否有限
     * @return
     */
    public Integer getValid() {
        return valid;
    }

    /**
     * 设置是否有限
     * @param valid
     */
    public void setValid(Integer valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}