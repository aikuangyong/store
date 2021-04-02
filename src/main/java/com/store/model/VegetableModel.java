package com.store.model;

import com.alibaba.fastjson.JSON;
import com.store.model.config.ImageinfoModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

@Data
public class VegetableModel extends PageModel {

    public Map<String, String> excelHead() {
        Map<String, String> excelHead = new LinkedHashMap<>();
        excelHead.put("vegetablename", "菜品名称");
        excelHead.put("posno", "菜品编号");
        excelHead.put("typename", "菜品分类");
        excelHead.put("content", "菜品简介");
        excelHead.put("price", "价格");
        excelHead.put("createtime", "创建时间");
        return excelHead;
    }

    private String vid;
    private String vegetablename;
    private String posno;
    private String skuno;
    private String typeid;
    private String typename;
    private Integer orderseq;
    private String content;
    private String vegetableimg;
    private String detailcontent;
    private String price;
    private Date createtime;
    private String valid;
    private String isregular;
    private List<ImageinfoModel> imageList;
    private String isIndex;
    private Integer salecount;
    private String thumimg;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date begincreatetime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date endcreatetime;


    public String getThumimg() {
        return thumimg;
    }

    public void setThumimg(String thumimg) {
        this.thumimg = thumimg;
    }

    public Integer getSalecount() {
        return salecount;
    }

    public void setSalecount(Integer salecount) {
        this.salecount = salecount;
    }

    /**
     * 是否首页:1:否;2:是
     */
    public String getIsIndex() {
        return isIndex;
    }

    /**
     * 是否首页:1:否;2:是
     *
     * @param isIndex
     */
    public void setIsIndex(String isIndex) {
        this.isIndex = isIndex;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public List<ImageinfoModel> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageinfoModel> imageList) {
        this.imageList = imageList;
    }

    /**
     * 获取主键
     *
     * @return
     */
    public String getVid() {
        return vid;
    }

    /**
     * 设置主键
     *
     * @param vid
     */
    public void setVid(String vid) {
        this.vid = vid;
    }

    /**
     * 获取菜品名称
     *
     * @return
     */
    public String getVegetablename() {
        return vegetablename;
    }

    /**
     * 设置菜品名称
     *
     * @param vegetablename
     */
    public void setVegetablename(String vegetablename) {
        this.vegetablename = vegetablename;
    }

    /**
     * 获取菜品编号
     *
     * @return
     */
    public String getPosno() {
        return posno;
    }

    /**
     * 设置菜品编号
     *
     * @param posno
     */
    public void setPosno(String posno) {
        this.posno = posno;
    }

    /**
     * 获取菜品分类
     *
     * @return
     */
    public String getTypeid() {
        return typeid;
    }

    /**
     * 设置菜品分类
     *
     * @param typeid
     */
    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    /**
     * 获取排序
     *
     * @return
     */
    public Integer getOrderseq() {
        return orderseq;
    }

    /**
     * 设置排序
     *
     * @param orderseq
     */
    public void setOrderseq(Integer orderseq) {
        this.orderseq = orderseq;
    }

    /**
     * 获取菜品简介
     *
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置菜品简介
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取图片
     *
     * @return
     */
    public String getVegetableimg() {
        return vegetableimg;
    }

    /**
     * 设置图片
     *
     * @param vegetableimg
     */
    public void setVegetableimg(String vegetableimg) {
        this.vegetableimg = vegetableimg;
    }

    /**
     * 获取详细描述
     *
     * @return
     */
    public String getDetailcontent() {
        return detailcontent;
    }

    /**
     * 设置详细描述
     *
     * @param detailcontent
     */
    public void setDetailcontent(String detailcontent) {
        this.detailcontent = detailcontent;
    }

    /**
     * 获取价格
     *
     * @return
     */
    public String getPrice() {
        return price;
    }

    /**
     * 设置价格
     *
     * @param price
     */
    public void setPrice(String price) {
        this.price = price;
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

    /**
     * 获取是否有效
     *
     * @return
     */
    public String getValid() {
        return valid;
    }

    /**
     * 设置是否有效;是否有效:1:有效;2:无效
     *
     * @param valid
     */
    public void setValid(String valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}