package com.store.model;

import com.alibaba.fastjson.JSON;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SmsinfoModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("smsid","主键");
        excelHead.put("phoneno","手机号码");
        excelHead.put("sendtime","发送时间");
        excelHead.put("verifycode","验证码");
        return excelHead;
    }

    private String smsid;
    private String phoneno;
    private Date sendtime;
    private String verifycode;
    //短信类型1注册2忘记密码3绑定账号
    private String smstype;
    private String resultMsg;

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    /**
     * 短信类型1注册2忘记密码3绑定账号
     * @return
     */
    public String getSmstype() {
        return smstype;
    }

    /**
     * 短信类型1注册2忘记密码3绑定账号
     * @param smstype
     */
    public void setSmstype(String smstype) {
        this.smstype = smstype;
    }

    /**
     * 获取主键
     * @return
     */
    public String getSmsid() {
        return smsid;
    }

    /**
     * 设置主键
     * @param smsid
     */
    public void setSmsid(String smsid) {
        this.smsid = smsid;
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
     * 获取发送时间
     * @return
     */
    public Date getSendtime() {
        return sendtime;
    }

    /**
     * 设置发送时间
     * @param sendtime
     */
    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }
    /**
     * 获取验证码
     * @return
     */
    public String getVerifycode() {
        return verifycode;
    }

    /**
     * 设置验证码
     * @param verifycode
     */
    public void setVerifycode(String verifycode) {
        this.verifycode = verifycode;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}