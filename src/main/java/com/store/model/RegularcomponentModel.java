package com.store.model;

import com.alibaba.fastjson.JSON;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegularcomponentModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("rcid","主键");
        excelHead.put("rid","外键,关联定期购");
        excelHead.put("row","第几行");
        excelHead.put("col","第几列");
        excelHead.put("val","值");
        excelHead.put("createtime","创建时间");
        return excelHead;
    }

    private String rcid;
    private String rid;
    private String row;
    private String col;
    private String val;
    private Date createtime;

    /**
     * 获取主键
     * @return
     */
    public String getRcid() {
        return rcid;
    }

    /**
     * 设置主键
     * @param rcid
     */
    public void setRcid(String rcid) {
        this.rcid = rcid;
    }
    /**
     * 获取外键,关联定期购
     * @return
     */
    public String getRid() {
        return rid;
    }

    /**
     * 设置外键,关联定期购
     * @param rid
     */
    public void setRid(String rid) {
        this.rid = rid;
    }
    /**
     * 获取第几行
     * @return
     */
    public String getRow() {
        return row;
    }

    /**
     * 设置第几行
     * @param row
     */
    public void setRow(String row) {
        this.row = row;
    }
    /**
     * 获取第几列
     * @return
     */
    public String getCol() {
        return col;
    }

    /**
     * 设置第几列
     * @param col
     */
    public void setCol(String col) {
        this.col = col;
    }
    /**
     * 获取值
     * @return
     */
    public String getVal() {
        return val;
    }

    /**
     * 设置值
     * @param val
     */
    public void setVal(String val) {
        this.val = val;
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