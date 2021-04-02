package com.store.model.config;

import com.alibaba.fastjson.JSON;
import com.store.model.PageModel;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ImageinfoModel extends PageModel {

    public Map<String, String> excelHead() {
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("imgid", "主键");
        excelHead.put("src", "访问路径");
        excelHead.put("filepath", "文件路径");
        excelHead.put("filetype", "文件类型");
        excelHead.put("filename", "文件名");
        return excelHead;
    }


    private List<String> imgGrpList;

    public List<String> getImgGrpList() {
        return imgGrpList;
    }

    public void setImgGrpList(List<String> imgGrpList) {
        this.imgGrpList = imgGrpList;
    }

    private String imgid;
    private String src;
    private String filepath;
    private String filetype;
    private String filename;
    private String imggrp;
    private Integer imageseq;
    private String imghref;

    public Integer getImageseq() {
        return imageseq;
    }

    public void setImageseq(Integer imageseq) {
        this.imageseq = imageseq;
    }

    public String getImggrp() {
        return imggrp;
    }

    public void setImggrp(String imggrp) {
        this.imggrp = imggrp;
    }

    /**
     * 获取主键
     *
     * @return
     */
    public String getImgid() {
        return imgid;
    }

    /**
     * 设置主键
     *
     * @param imgid
     */
    public void setImgid(String imgid) {
        this.imgid = imgid;
    }

    /**
     * 获取访问路径
     *
     * @return
     */
    public String getSrc() {
        return src;
    }

    /**
     * 设置访问路径
     *
     * @param src
     */
    public void setSrc(String src) {
        this.src = src;
    }

    /**
     * 获取文件路径
     *
     * @return
     */
    public String getFilepath() {
        return filepath;
    }

    /**
     * 设置文件路径
     *
     * @param filepath
     */
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    /**
     * 获取文件类型
     *
     * @return
     */
    public String getFiletype() {
        return filetype;
    }

    /**
     * 设置文件类型
     *
     * @param filetype
     */
    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    /**
     * 获取文件名
     *
     * @return
     */
    public String getFilename() {
        return filename;
    }

    /**
     * 设置文件名
     *
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}