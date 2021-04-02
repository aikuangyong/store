package com.store.model;

import com.alibaba.fastjson.JSON;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoopinfoModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("loopid","主键");
        excelHead.put("loopname","轮播图名称");
        excelHead.put("position","轮播位置1首页2菜单");
        excelHead.put("imagesrc","轮播图");
        excelHead.put("looptype","轮播图类型1无内容2链接3富文本");
        excelHead.put("loopcontent","轮播图内容");
        excelHead.put("createtime","上传时间");
        return excelHead;
    }

    private String loopid;
    private String loopname;
    private String position;
    private String imagesrc;
    private String showsrc;
    private String looptype;
    private String loopcontent;
    private Date createtime;
    private Integer loopseq;

    public Integer getLoopseq() {
        return loopseq;
    }

    public void setLoopseq(Integer loopseq) {
        this.loopseq = loopseq;
    }

    public String getShowsrc() {
        return showsrc;
    }

    public void setShowsrc(String showsrc) {
        this.showsrc = showsrc;
    }

    /**
     * 获取主键
     * @return
     */
    public String getLoopid() {
        return loopid;
    }

    /**
     * 设置主键
     * @param loopid
     */
    public void setLoopid(String loopid) {
        this.loopid = loopid;
    }
    /**
     * 获取轮播图名称
     * @return
     */
    public String getLoopname() {
        return loopname;
    }

    /**
     * 设置轮播图名称
     * @param loopname
     */
    public void setLoopname(String loopname) {
        this.loopname = loopname;
    }
    /**
     * 获取轮播位置1首页2菜单
     * @return
     */
    public String getPosition() {
        return position;
    }

    /**
     * 设置轮播位置1首页2菜单
     * @param position
     */
    public void setPosition(String position) {
        this.position = position;
    }
    /**
     * 获取轮播图
     * @return
     */
    public String getImagesrc() {
        return imagesrc;
    }

    /**
     * 设置轮播图
     * @param imagesrc
     */
    public void setImagesrc(String imagesrc) {
        this.imagesrc = imagesrc;
    }
    /**
     * 获取轮播图类型1无内容2链接3富文本
     * @return
     */
    public String getLooptype() {
        return looptype;
    }

    /**
     * 设置轮播图类型1无内容2链接3富文本
     * @param looptype
     */
    public void setLooptype(String looptype) {
        this.looptype = looptype;
    }
    /**
     * 获取轮播图内容
     * @return
     */
    public String getLoopcontent() {
        return loopcontent;
    }

    /**
     * 设置轮播图内容
     * @param loopcontent
     */
    public void setLoopcontent(String loopcontent) {
        this.loopcontent = loopcontent;
    }
    /**
     * 获取上传时间
     * @return
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置上传时间
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