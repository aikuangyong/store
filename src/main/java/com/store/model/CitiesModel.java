package com.store.model;

import com.alibaba.fastjson.JSON;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CitiesModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("id","");
        excelHead.put("cityid","");
        excelHead.put("city","");
        excelHead.put("provinceid","");
        return excelHead;
    }

    private Integer id;
    private String cityid;
    private String city;
    private String provinceid;

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
    /**
     * 获取
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }
    /**
     * 获取
     * @return
     */
    public String getProvinceid() {
        return provinceid;
    }

    /**
     * 设置
     * @param provinceid
     */
    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}