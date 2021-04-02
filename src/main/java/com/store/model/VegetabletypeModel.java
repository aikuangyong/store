package com.store.model;

import com.alibaba.fastjson.JSON;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VegetabletypeModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("vtid","主键");
        excelHead.put("vtname","分类名称");
        excelHead.put("createtime","创建时间");
        excelHead.put("orderseq","排序");
        excelHead.put("bannerimg","BANNER图");
        excelHead.put("typeimg","分类图");
        return excelHead;
    }

    private String bannersrc;

    private String typesrc;

    public String getBannersrc() {
        return bannersrc;
    }

    public void setBannersrc(String bannersrc) {
        this.bannersrc = bannersrc;
    }

    public String getTypesrc() {
        return typesrc;
    }

    public void setTypesrc(String typesrc) {
        this.typesrc = typesrc;
    }

    private String vtid;
    private String vtname;
    private Date createtime;
    private Integer orderseq;
    private String bannerimg;
    private String typeimg;

    private List<VegetableModel> productList;

    public List<VegetableModel> getProductList() {
        return productList;
    }

    public void setProductList(List<VegetableModel> productList) {
        this.productList = productList;
    }
    /**
     * 获取主键
     * @return
     */
    public String getVtid() {
        return vtid;
    }

    /**
     * 设置主键
     * @param vtid
     */
    public void setVtid(String vtid) {
        this.vtid = vtid;
    }
    /**
     * 获取分类名称
     * @return
     */
    public String getVtname() {
        return vtname;
    }

    /**
     * 设置分类名称
     * @param vtname
     */
    public void setVtname(String vtname) {
        this.vtname = vtname;
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
     * 获取排序
     * @return
     */
    public Integer getOrderseq() {
        return orderseq;
    }

    /**
     * 设置排序
     * @param orderseq
     */
    public void setOrderseq(Integer orderseq) {
        this.orderseq = orderseq;
    }
    /**
     * 获取BANNER图
     * @return
     */
    public String getBannerimg() {
        return bannerimg;
    }

    /**
     * 设置BANNER图
     * @param bannerimg
     */
    public void setBannerimg(String bannerimg) {
        this.bannerimg = bannerimg;
    }
    /**
     * 获取分类图
     * @return
     */
    public String getTypeimg() {
        return typeimg;
    }

    /**
     * 设置分类图
     * @param typeimg
     */
    public void setTypeimg(String typeimg) {
        this.typeimg = typeimg;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}