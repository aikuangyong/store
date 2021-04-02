package com.store.model;

import com.alibaba.fastjson.JSON;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SiteinfomationModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("id","主键");
        excelHead.put("infoname","名称");
        excelHead.put("infocontent","内容");
        return excelHead;
    }

    private String id;
    private String infoname;
    private String infocontent;

    /**
     * 获取主键
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * 获取名称
     * @return
     */
    public String getInfoname() {
        return infoname;
    }

    /**
     * 设置名称
     * @param infoname
     */
    public void setInfoname(String infoname) {
        this.infoname = infoname;
    }
    /**
     * 获取内容
     * @return
     */
    public String getInfocontent() {
        return infocontent;
    }

    /**
     * 设置内容
     * @param infocontent
     */
    public void setInfocontent(String infocontent) {
        this.infocontent = infocontent;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}