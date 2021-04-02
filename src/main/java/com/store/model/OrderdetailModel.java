package com.store.model;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class OrderdetailModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("detailid","主键");
        excelHead.put("vegetable","菜品外键");
        excelHead.put("vegetablename","菜品名称");
        excelHead.put("imagesrc","菜品图片");
        excelHead.put("salecount","购买数量");
        excelHead.put("saleprice","销售价格");
        excelHead.put("orderno","订单编号");
        return excelHead;
    }

    @ApiModelProperty(value = "主键", required = true)
    private String detailid;
    @ApiModelProperty(value = "菜品外键", required = true)
    private String vegetable;
    @ApiModelProperty(value = "菜品名称", required = true)
    private String vegetablename;
    @ApiModelProperty(value = "菜品图片", required = true)
    private String imagesrc;
    @ApiModelProperty(value = "购买数量", required = true)
    private Integer salecount;
    @ApiModelProperty(value = "销售价格", required = true)
    private Double saleprice;

    private String paidprice;
    @ApiModelProperty(value = "订单编号", required = true)
    private String orderno;
    @ApiModelProperty(value = "菜品内容", required = true)
    private String producecontent;

    /**
     * 获取主键
     * @return
     */
    public String getDetailid() {
        return detailid;
    }

    /**
     * 设置主键
     * @param detailid
     */
    public void setDetailid(String detailid) {
        this.detailid = detailid;
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
     * 获取菜品名称
     * @return
     */
    public String getVegetablename() {
        return vegetablename;
    }

    /**
     * 设置菜品名称
     * @param vegetablename
     */
    public void setVegetablename(String vegetablename) {
        this.vegetablename = vegetablename;
    }
    /**
     * 获取菜品图片
     * @return
     */
    public String getImagesrc() {
        return imagesrc;
    }

    /**
     * 设置菜品图片
     * @param imagesrc
     */
    public void setImagesrc(String imagesrc) {
        this.imagesrc = imagesrc;
    }
    /**
     * 获取购买数量
     * @return
     */
    public Integer getSalecount() {
        return salecount;
    }

    /**
     * 设置购买数量
     * @param salecount
     */
    public void setSalecount(Integer salecount) {
        this.salecount = salecount;
    }
    /**
     * 获取销售价格
     * @return
     */
    public Double getSaleprice() {
        return saleprice;
    }

    /**
     * 设置销售价格
     * @param saleprice
     */
    public void setSaleprice(Double saleprice) {
        this.saleprice = saleprice;
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}