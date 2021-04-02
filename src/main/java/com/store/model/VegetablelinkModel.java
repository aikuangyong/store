package com.store.model;

import com.alibaba.fastjson.JSON;
import com.store.model.config.ImageinfoModel;
import lombok.Data;

import java.util.*;

@Data
public class VegetablelinkModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new LinkedHashMap<>();
        excelHead.put("configtime","日期");
        excelHead.put("productname","商品名称");
        excelHead.put("producttype","商品分类");
        excelHead.put("createtime","添加时间");
        return excelHead;
    }

    private String vlid;
    private String islink;
    private String vegetable;
    private String regular;
    private Date configtime;
    private Date beginconfigtime;
    private Date endconfigtime;
    private String productname;
    private String productid;
    private String producttype;
    private String imagedata;
    private String saleprice ;
    private String salecount;
    private String configdate;
    private String posno;
    private String price;
    private Date createtime;

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getIslink() {
        return islink;
    }

    public void setIslink(String islink) {
        this.islink = islink;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getBeginconfigtime() {
        return beginconfigtime;
    }

    public void setBeginconfigtime(Date beginconfigtime) {
        this.beginconfigtime = beginconfigtime;
    }

    public Date getEndconfigtime() {
        return endconfigtime;
    }

    public void setEndconfigtime(Date endconfigtime) {
        this.endconfigtime = endconfigtime;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
    }

    public String getImagedata() {
        return imagedata;
    }

    public void setImagedata(String imagedata) {
        this.imagedata = imagedata;
    }

    public String getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(String saleprice) {
        this.saleprice = saleprice;
    }

    public String getSalecount() {
        return salecount;
    }

    public void setSalecount(String salecount) {
        this.salecount = salecount;
    }

    public String getPosno() {
        return posno;
    }

    public void setPosno(String posno) {
        this.posno = posno;
    }

    /**
     * 获取主键
     * @return
     */
    public String getVlid() {
        return vlid;
    }

    /**
     * 设置主键
     * @param vlid
     */
    public void setVlid(String vlid) {
        this.vlid = vlid;
    }
    /**
     * 获取菜品外键
     * @return
     */
    public String getVegetable() {
        return vegetable;
    }

    /**
     * 设置菜品外键
     * @param vegetable
     */
    public void setVegetable(String vegetable) {
        this.vegetable = vegetable;
    }
    /**
     * 获取定期购外键
     * @return
     */
    public String getRegular() {
        return regular;
    }

    /**
     * 设置定期购外键
     * @param regular
     */
    public void setRegular(String regular) {
        this.regular = regular;
    }
    /**
     * 获取配送日期
     * @return
     */
    public Date getConfigtime() {
        return configtime;
    }

    /**
     * 设置配送日期
     * @param configtime
     */
    public void setConfigtime(Date configtime) {
        this.configtime = configtime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}