package com.store.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class ReviewregularModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("rrid","主键");
        excelHead.put("userid","用户ID");
        excelHead.put("nickname","用户昵称");
        excelHead.put("phoneno","手机号码");
        excelHead.put("specification","规格");
        excelHead.put("price","价格");
        excelHead.put("surplus","剩余次数");
        excelHead.put("reviewer","操作人ID");
        excelHead.put("createtime","创建时间");
        excelHead.put("edittime","操作时间");
        excelHead.put("status","状态");//1:未审核;2:已通过;3:未通过
        excelHead.put("store","门店编号");
        return excelHead;
    }

    private String rrid;
    private String userid;
    private String nickname;
    private String phoneno;
    private String specification;
    private Double price;
    private Integer surplus;
    private String reviewer;
    private Date createtime;
    private Date edittime;
    private String status;
    private String store;
    private String storename;
    private String orderno;

    /**
     * 获取主键
     * @return
     */
    public String getRrid() {
        return rrid;
    }

    /**
     * 设置主键
     * @param rrid
     */
    public void setRrid(String rrid) {
        this.rrid = rrid;
    }
    /**
     * 获取用户ID
     * @return
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置用户ID
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }
    /**
     * 获取用户昵称
     * @return
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置用户昵称
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
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
     * 获取规格
     * @return
     */
    public String getSpecification() {
        return specification;
    }

    /**
     * 设置规格
     * @param specification
     */
    public void setSpecification(String specification) {
        this.specification = specification;
    }
    /**
     * 获取价格
     * @return
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 设置价格
     * @param price
     */
    public void setPrice(Double price) {
        this.price = price;
    }
    /**
     * 获取剩余次数
     * @return
     */
    public Integer getSurplus() {
        return surplus;
    }

    /**
     * 设置剩余次数
     * @param surplus
     */
    public void setSurplus(Integer surplus) {
        this.surplus = surplus;
    }
    /**
     * 获取操作人ID
     * @return
     */
    public String getReviewer() {
        return reviewer;
    }

    /**
     * 设置操作人ID
     * @param reviewer
     */
    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
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
    /**
     * 获取操作时间
     * @return
     */
    public Date getEdittime() {
        return edittime;
    }

    /**
     * 设置操作时间
     * @param edittime
     */
    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }
    /**
     * 获取状态
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * 获取门店编号
     * @return
     */
    public String getStore() {
        return store;
    }

    /**
     * 设置门店编号
     * @param store
     */
    public void setStore(String store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}