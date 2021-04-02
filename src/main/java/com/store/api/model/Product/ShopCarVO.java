package com.store.api.model.Product;

import com.store.model.ShopcarModel;
import com.store.utils.DateUtil;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class ShopCarVO {

    private String carid;
    private String productid;
    private String userid;
    private Integer productcount;
    private String createtime;
    private String productname;
    private String productimg;
    private String price;
    private String content;
    private String vaild;

    public ShopCarVO(ShopcarModel model){
        this.carid = model.getCarid();
        this.productid = model.getProductid();
        this.userid = model.getUserid();
        this.productcount = model.getProductcount();
        if(null != model.getCreatetime()){
            this.createtime = DateUtil.sdfTime.format(model.getCreatetime());
        }else{
            this.createtime = "";
        }
        this.productname = model.getProductname();
        this.productimg = model.getProductimg();
        this.price = model.getPrice();
        this.content = model.getContent();
        this.vaild = model.getValid();
    }

    public String getVaild() {
        return vaild;
    }

    public void setVaild(String vaild) {
        this.vaild = vaild;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public ShopCarVO(){}

    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getProductcount() {
        return productcount;
    }

    public void setProductcount(Integer productcount) {
        this.productcount = productcount;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductimg() {
        return productimg;
    }

    public void setProductimg(String productimg) {
        this.productimg = productimg;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}