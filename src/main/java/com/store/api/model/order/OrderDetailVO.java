package com.store.api.model.order;

import com.store.model.OrderdetailModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderDetailVO {

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
    @ApiModelProperty(value = "订单编号", required = true)
    private String orderno;
    private String producecontent;

    public OrderDetailVO(OrderdetailModel model) {
        this.detailid = model.getDetailid();
        this.vegetable = model.getVegetable();
        this.vegetablename = model.getVegetablename();
        this.imagesrc = model.getImagesrc();
        this.salecount = model.getSalecount();
        this.saleprice = model.getSaleprice();
        this.orderno = model.getOrderno();
        this.producecontent = model.getProducecontent();
    }

    public OrderDetailVO() {
    }

    public String getDetailid() {
        return detailid;
    }

    public void setDetailid(String detailid) {
        this.detailid = detailid;
    }

    public String getVegetable() {
        return vegetable;
    }

    public void setVegetable(String vegetable) {
        this.vegetable = vegetable;
    }

    public String getVegetablename() {
        return vegetablename;
    }

    public void setVegetablename(String vegetablename) {
        this.vegetablename = vegetablename;
    }

    public String getImagesrc() {
        return imagesrc;
    }

    public void setImagesrc(String imagesrc) {
        this.imagesrc = imagesrc;
    }

    public Integer getSalecount() {
        return salecount;
    }

    public void setSalecount(Integer salecount) {
        this.salecount = salecount;
    }

    public Double getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(Double saleprice) {
        this.saleprice = saleprice;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }
}