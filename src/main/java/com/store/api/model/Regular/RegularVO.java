package com.store.api.model.Regular;

import com.store.api.model.Product.ImageVO;
import com.store.model.RegularModel;
import com.store.model.RegularcomponentModel;
import com.store.model.config.ImageinfoModel;
import com.store.utils.ConstantVariable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegularVO {
    private String rid;
    private String regularname;
    private String content;
    private String regulardetails;
    private String component;
    private List<RegularcomponentModel> componentList;
    private List<ImageVO> imagelist;
    private Date createtime;
    private String valid;
    private String vegetablelink;

    public RegularVO(RegularModel model) {
        this.rid = model.getRid();
        this.regularname = model.getRegularname();
        if (null == model.getContent()) {
            this.content = "";
        } else {
            this.content = model.getContent().replaceAll("class=\"rich-img\" src=\"2", "class=\"rich-img\" src=\"" + ConstantVariable.IMG_HOST_URL + "2");
        }

        this.imagelist = new ArrayList<>();
        if (null != imagelist) {
            for (ImageinfoModel imageModel : model.getImagelist()) {
                this.imagelist.add(new ImageVO(imageModel));
            }
        }
        this.regulardetails = model.getRegulardetails();
        if (null == model.getRegulardetails()) {
            this.regulardetails = "";
        } else {
            this.regulardetails = model.getRegulardetails().replaceAll("class=\"rich-img\" src=\"2", "class=\"rich-img\" src=\"" + ConstantVariable.IMG_HOST_URL + "2");
        }
        this.component = model.getComponent();
        this.componentList = model.getComponentList();
        this.createtime = model.getCreatetime();
        this.valid = model.getValid();
        this.vegetablelink = model.getVegetablelink();
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getRegularname() {
        return regularname;
    }

    public void setRegularname(String regularname) {
        this.regularname = regularname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRegulardetails() {
        return regulardetails;
    }

    public void setRegulardetails(String regulardetails) {
        this.regulardetails = regulardetails;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public List<RegularcomponentModel> getComponentList() {
        return componentList;
    }

    public void setComponentList(List<RegularcomponentModel> componentList) {
        this.componentList = componentList;
    }

    public List<ImageVO> getImagelist() {
        return imagelist;
    }

    public void setImagelist(List<ImageVO> imagelist) {
        this.imagelist = imagelist;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getVegetablelink() {
        return vegetablelink;
    }

    public void setVegetablelink(String vegetablelink) {
        this.vegetablelink = vegetablelink;
    }
}