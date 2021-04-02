package com.store.model;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class SysmenuModel extends PageModel {

    private String menuid;
    private String title;
    private String icon;
    private String parentid;
    private String href;
    private boolean spread = false;
    private String roleid;

    private List<SysmenuModel> children = null;

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    public List<SysmenuModel> getChildren() {
        return children;
    }

    public void setChildren(List<SysmenuModel> children) {
        this.children = children;
    }
}