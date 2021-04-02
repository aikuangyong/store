package com.store.model;

import com.alibaba.fastjson.JSON;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProvincesModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("id","");
        excelHead.put("provinceid","");
        excelHead.put("province","");
        return excelHead;
    }

    private Integer id;
    private String provinceid;
    private String province;

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
    /**
     * 获取
     * @return
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置
     * @param province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}