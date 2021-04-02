package com.store.model;

import com.alibaba.fastjson.JSON;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UsertokenModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("id","主键");
        excelHead.put("userid","用户主键");
        excelHead.put("logintime","登录时间");
        excelHead.put("token","用户token");
        excelHead.put("createtime","创建时间");
        return excelHead;
    }

    private Integer id;
    private String userid;
    private Long logintime;
    private String token;
    private Date createtime;

    /**
     * 获取主键
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * 获取用户主键
     * @return
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置用户主键
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }
    /**
     * 获取登录时间
     * @return
     */
    public Long getLogintime() {
        return logintime;
    }

    /**
     * 设置登录时间
     * @param logintime
     */
    public void setLogintime(Long logintime) {
        this.logintime = logintime;
    }
    /**
     * 获取用户token
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置用户token
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}