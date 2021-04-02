package com.store.api.model.data;

import com.store.model.StoreModel;
import lombok.Data;

import java.util.Date;

@Data
public class StoreVO {

    private String storeid;
    private String storeno;
    private String poseno;
    private String storeaddress;
    private String lat;
    private String lng;
    private String storephone;
    private String storename;
    //1:在范围;2:不在范围
    private Integer distributeScope; //1:在范围;2:不在范围
    private Integer storeRange; //距离

    public StoreVO(StoreModel model) {
        this.storeid = model.getStoreid();
        this.storeno = model.getStoreno();
        this.poseno = model.getPoseno();
        this.storeaddress = model.getStoreaddress();
        this.storephone = model.getStorephone();
        this.storename = model.getStorename();
        this.lat = model.getLat();
        this.lng = model.getLng();
    }

    public StoreVO(StoreModel model,Integer distributeScope,Integer storeRange) {
        this.storeid = model.getStoreid();
        this.storeno = model.getStoreno();
        this.poseno = model.getPoseno();
        this.storeaddress = model.getStoreaddress();
        this.storephone = model.getStorephone();
        this.storename = model.getStorename();
        this.lat = model.getLat();
        this.lng = model.getLng();
        this.distributeScope = distributeScope;
        this.storeRange = storeRange;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public StoreVO() {
    }

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getStoreno() {
        return storeno;
    }

    public void setStoreno(String storeno) {
        this.storeno = storeno;
    }

    public String getPoseno() {
        return poseno;
    }

    public void setPoseno(String poseno) {
        this.poseno = poseno;
    }

    public String getStoreaddress() {
        return storeaddress;
    }

    public void setStoreaddress(String storeaddress) {
        this.storeaddress = storeaddress;
    }

    public String getStorephone() {
        return storephone;
    }

    public void setStorephone(String storephone) {
        this.storephone = storephone;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

}