package com.store.model;

import com.alibaba.fastjson.JSON;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SendtimeconfigModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("id","主键");
        excelHead.put("starttime","开始时间");
        excelHead.put("endtime","结束时间");
        excelHead.put("groupseq","分组序号");
        excelHead.put("createtime","创建时间");
        return excelHead;
    }

    private Integer id;
    private String starttime;
    private String endtime;
    private String groupseq;
    private Date createtime;

    private String showtext;

    public String getShowtext() {
        return showtext;
    }

    public void setShowtext(String showtext) {
        this.showtext = showtext;
    }

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
     * 获取开始时间
     * @return
     */
    public String getStarttime() {
        return starttime;
    }

    /**
     * 设置开始时间
     * @param starttime
     */
    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }
    /**
     * 获取结束时间
     * @return
     */
    public String getEndtime() {
        return endtime;
    }

    /**
     * 设置结束时间
     * @param endtime
     */
    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
    /**
     * 获取分组序号
     * @return
     */
    public String getGroupseq() {
        return groupseq;
    }

    /**
     * 设置分组序号
     * @param groupseq
     */
    public void setGroupseq(String groupseq) {
        this.groupseq = groupseq;
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