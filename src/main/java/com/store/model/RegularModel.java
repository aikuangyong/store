package com.store.model;

import com.alibaba.fastjson.JSON;
import com.store.model.config.ImageinfoModel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegularModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("rid","主键");
        excelHead.put("regularname","商品名称");
        excelHead.put("content","商品简述");
        excelHead.put("regularimg","商品图片");
        excelHead.put("regulardetails","商品详情");
        excelHead.put("component","含量");
        excelHead.put("createtime","创建时间");
        excelHead.put("valid","是否禁用启用");
        excelHead.put("vegetablelink","关联菜单");
        return excelHead;
    }

    private String rid;
    private String regularname;
    private String content;
    private String regularimg;
    private String regulardetails;
    private String component;
    private List<RegularcomponentModel> componentList;
    private List<ImageinfoModel> imagelist;
    private Date createtime;
    private String valid;
    private String vegetablelink;

    public List<ImageinfoModel> getImagelist() {
        return imagelist;
    }

    public void setImagelist(List<ImageinfoModel> imagelist) {
        this.imagelist = imagelist;
    }

    public List<RegularcomponentModel> getComponentList() {
        return componentList;
    }

    public void setComponentList(List<RegularcomponentModel> componentList) {
        this.componentList = componentList;
    }

    /**
     * 获取主键
     * @return
     */
    public String getRid() {
        return rid;
    }

    /**
     * 设置主键
     * @param rid
     */
    public void setRid(String rid) {
        this.rid = rid;
    }
    /**
     * 获取商品名称
     * @return
     */
    public String getRegularname() {
        return regularname;
    }

    /**
     * 设置商品名称
     * @param regularname
     */
    public void setRegularname(String regularname) {
        this.regularname = regularname;
    }
    /**
     * 获取商品简述
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置商品简述
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * 获取商品图片
     * @return
     */
    public String getRegularimg() {
        return regularimg;
    }

    /**
     * 设置商品图片
     * @param regularimg
     */
    public void setRegularimg(String regularimg) {
        this.regularimg = regularimg;
    }
    /**
     * 获取商品详情
     * @return
     */
    public String getRegulardetails() {
        return regulardetails;
    }

    /**
     * 设置商品详情
     * @param regulardetails
     */
    public void setRegulardetails(String regulardetails) {
        this.regulardetails = regulardetails;
    }
    /**
     * 获取含量
     * @return
     */
    public String getComponent() {
        return component;
    }

    /**
     * 设置含量
     * @param component
     */
    public void setComponent(String component) {
        this.component = component;
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
     * 获取是否禁用启用
     * @return
     */
    public String getValid() {
        return valid;
    }

    /**
     * 设置是否禁用启用
     * @param valid
     */
    public void setValid(String valid) {
        this.valid = valid;
    }
    /**
     * 获取关联菜单
     * @return
     */
    public String getVegetablelink() {
        return vegetablelink;
    }

    /**
     * 设置关联菜单
     * @param vegetablelink
     */
    public void setVegetablelink(String vegetablelink) {
        this.vegetablelink = vegetablelink;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}