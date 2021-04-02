package com.store.model;

import com.alibaba.fastjson.JSON;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BindinfoModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("bid","主键");
        excelHead.put("thirdid","第三方ID");
        excelHead.put("phoneno","手机号码");
        excelHead.put("bindtype","绑定类型");
        return excelHead;
    }

    private String bid;
    private String thirdid;
    private String phoneno;
    private String bindtype;

    /**
     * 获取主键
     * @return
     */
    public String getBid() {
        return bid;
    }

    /**
     * 设置主键
     * @param bid
     */
    public void setBid(String bid) {
        this.bid = bid;
    }
    /**
     * 获取第三方ID
     * @return
     */
    public String getThirdid() {
        return thirdid;
    }

    /**
     * 设置第三方ID
     * @param thirdid
     */
    public void setThirdid(String thirdid) {
        this.thirdid = thirdid;
    }
    /**
     * 获取手机号码
     * @return
     */
    public String getPhoneno() {
        return phoneno;
    }

    /**
     * 设置手机号码
     * @param phoneno
     */
    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
    /**
     * 获取绑定类型
     * @return
     */
    public String getBindtype() {
        return bindtype;
    }

    /**
     * 设置绑定类型
     * @param bindtype
     */
    public void setBindtype(String bindtype) {
        this.bindtype = bindtype;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}