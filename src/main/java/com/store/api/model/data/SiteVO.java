package com.store.api.model.data;

import com.store.model.SiteinfomationModel;

import java.util.Date;

public class SiteVO {


    private String id;
    private String infoname;
    private String infocontent;

    public SiteVO(SiteinfomationModel model) {
        this.id = model.getId();
        this.infoname = model.getInfoname();
        this.infocontent = model.getInfocontent();
    }

    public SiteVO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfoname() {
        return infoname;
    }

    public void setInfoname(String infoname) {
        this.infoname = infoname;
    }

    public String getInfocontent() {
        return infocontent;
    }

    public void setInfocontent(String infocontent) {
        this.infocontent = infocontent;
    }
}