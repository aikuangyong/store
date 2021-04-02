package com.store.model;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopcarModel extends PageModel {

    public Map<String, String> excelHead() {
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("carid", "主键");
        excelHead.put("productid", "外键商品编号");
        excelHead.put("userid", "用户ID");
        excelHead.put("productcount", "商品数量");
        excelHead.put("createtime", "创建时间");
        return excelHead;
    }

    @ApiModelProperty(value = "主键", required = true)
    private String carid;
    @ApiModelProperty(value = "外键商品编号", required = true)
    private String productid;
    @ApiModelProperty(value = "用户ID", required = true)
    private String userid;
    @ApiModelProperty(value = "商品数量", required = true)
    private Integer productcount;
    @ApiModelProperty(value = "创建时间", required = true)
    private Date createtime;

    private String productname;
    private String productimg;
    private String price;
    private String content;
    private String valid;
    private List<VegetableModel> vlist;

    public List<VegetableModel> getVlist() {
        return vlist;
    }

    public void setVlist(List<VegetableModel> vlist) {
        this.vlist = vlist;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
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

    /**
     * 获取主键
     *
     * @return
     */
    public String getCarid() {
        return carid;
    }

    /**
     * 设置主键
     *
     * @param carid
     */
    public void setCarid(String carid) {
        this.carid = carid;
    }

    /**
     * 获取外键商品编号
     *
     * @return
     */
    public String getProductid() {
        return productid;
    }

    /**
     * 设置外键商品编号
     *
     * @param productid
     */
    public void setProductid(String productid) {
        this.productid = productid;
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置用户ID
     *
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取商品数量
     *
     * @return
     */
    public Integer getProductcount() {
        return productcount;
    }

    /**
     * 设置商品数量
     *
     * @param productcount
     */
    public void setProductcount(Integer productcount) {
        this.productcount = productcount;
    }

    /**
     * 获取创建时间
     *
     * @return
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置创建时间
     *
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}