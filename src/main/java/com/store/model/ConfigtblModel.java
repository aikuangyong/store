package com.store.model;

import com.alibaba.fastjson.JSON;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConfigtblModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("id","");
        excelHead.put("comments","内容");
        excelHead.put("datatype","类型");
        excelHead.put("createtime","");
        return excelHead;
    }

    private Integer id;
    private String comments;
    private String datatype;
    private Date createtime;

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
     * 获取内容
     * @return
     */
    public String getComments() {
        return comments;
    }

    /**
     * 设置内容
     * @param comments
     */
    public void setComments(String comments) {
        this.comments = comments;
    }
    /**
     * 获取类型
     * @return
     */
    public String getDatatype() {
        return datatype;
    }

    /**
     * 设置类型
     * @param datatype
     */
    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }
    /**
     * 获取
     * @return
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置
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