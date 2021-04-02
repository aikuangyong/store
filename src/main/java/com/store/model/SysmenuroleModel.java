package com.store.model;

import com.alibaba.fastjson.JSON;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysmenuroleModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("rmid","主键");
        excelHead.put("menuname","菜单名称");
        excelHead.put("menuicon","菜单图标");
        excelHead.put("parentid","父级ID");
        excelHead.put("url","请求地址");
        excelHead.put("orderseq","排序号");
        excelHead.put("roleid","权限主键");
        excelHead.put("menuid","菜单主键");
        excelHead.put("roledetails","权限分配");
        return excelHead;
    }

    private List<SysmenuroleModel> children = null;

    public List<SysmenuroleModel> getChildren() {
        return children;
    }

    public void setChildren(List<SysmenuroleModel> children) {
        this.children = children;
    }

    private String rmid;
    private String menuname;
    private String menuicon;
    private String parentid;
    private String url;
    private Integer orderseq;
    private String roleid;
    private String menuid;
    private String roledetails;

    /**
     * 获取主键
     * @return
     */
    public String getRmid() {
        return rmid;
    }

    /**
     * 设置主键
     * @param rmid
     */
    public void setRmid(String rmid) {
        this.rmid = rmid;
    }
    /**
     * 获取菜单名称
     * @return
     */
    public String getMenuname() {
        return menuname;
    }

    /**
     * 设置菜单名称
     * @param menuname
     */
    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }
    /**
     * 获取菜单图标
     * @return
     */
    public String getMenuicon() {
        return menuicon;
    }

    /**
     * 设置菜单图标
     * @param menuicon
     */
    public void setMenuicon(String menuicon) {
        this.menuicon = menuicon;
    }
    /**
     * 获取父级ID
     * @return
     */
    public String getParentid() {
        return parentid;
    }

    /**
     * 设置父级ID
     * @param parentid
     */
    public void setParentid(String parentid) {
        this.parentid = parentid;
    }
    /**
     * 获取请求地址
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置请求地址
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * 获取排序号
     * @return
     */
    public Integer getOrderseq() {
        return orderseq;
    }

    /**
     * 设置排序号
     * @param orderseq
     */
    public void setOrderseq(Integer orderseq) {
        this.orderseq = orderseq;
    }
    /**
     * 获取权限主键
     * @return
     */
    public String getRoleid() {
        return roleid;
    }

    /**
     * 设置权限主键
     * @param roleid
     */
    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }
    /**
     * 获取菜单主键
     * @return
     */
    public String getMenuid() {
        return menuid;
    }

    /**
     * 设置菜单主键
     * @param menuid
     */
    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }
    /**
     * 获取权限分配
     * @return
     */
    public String getRoledetails() {
        return roledetails;
    }

    /**
     * 设置权限分配
     * @param roledetails
     */
    public void setRoledetails(String roledetails) {
        this.roledetails = roledetails;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}