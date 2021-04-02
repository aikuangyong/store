package com.store.model;

import com.alibaba.fastjson.JSON;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AreasModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("id","");
        excelHead.put("areaid","");
        excelHead.put("area","");
        excelHead.put("cityid","");
        return excelHead;
    }

    private Integer id;
    private String areaid;
    private String area;
    private String cityid;

    /**
     * 获取
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * 获取
     * @return
     */
    public String getAreaid() {
        return areaid;
    }

    /**
     * 设置
     * @param areaid
     */
    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }
    /**
     * 获取
     * @return
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置
     * @param area
     */
    public void setArea(String area) {
        this.area = area;
    }
    /**
     * 获取
     * @return
     */
    public String getCityid() {
        return cityid;
    }

    /**
     * 设置
     * @param cityid
     */
    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}