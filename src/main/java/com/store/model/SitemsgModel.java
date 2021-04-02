package com.store.model;

import com.alibaba.fastjson.JSON;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SitemsgModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("msgid","主键");
        excelHead.put("createtime","添加时间");
        excelHead.put("msgtitle","消息标题");
        excelHead.put("msgcontent","消息内容");
        return excelHead;
    }

    private String msgid;
    private Date createtime;
    private String msgtitle;
    private String msgcontent;

    /**
     * 获取主键
     * @return
     */
    public String getMsgid() {
        return msgid;
    }

    /**
     * 设置主键
     * @param msgid
     */
    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }
    /**
     * 获取添加时间
     * @return
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置添加时间
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
    /**
     * 获取消息标题
     * @return
     */
    public String getMsgtitle() {
        return msgtitle;
    }

    /**
     * 设置消息标题
     * @param msgtitle
     */
    public void setMsgtitle(String msgtitle) {
        this.msgtitle = msgtitle;
    }
    /**
     * 获取消息内容
     * @return
     */
    public String getMsgcontent() {
        return msgcontent;
    }

    /**
     * 设置消息内容
     * @param msgcontent
     */
    public void setMsgcontent(String msgcontent) {
        this.msgcontent = msgcontent;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}