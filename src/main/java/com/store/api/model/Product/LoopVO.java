package com.store.api.model.Product;

import com.store.model.LoopinfoModel;
import com.store.utils.ConstantVariable;
import lombok.Data;

@Data
public class LoopVO {

    private String src;
    //轮播图类型1无内容2链接3富文本;type=select
    private String looptype;
    //轮播内容
    private String loopcontent;

    private String position;

    public LoopVO(LoopinfoModel model) {
        this.src = model.getShowsrc();
        this.looptype = model.getLooptype();
        if (null == model.getLoopcontent()) {
            this.loopcontent = "";
        } else {
            if ("3".equals(looptype)) {
                this.loopcontent = ConstantVariable.APP_IMG_STYLE + model.getLoopcontent().replaceAll("class=\"rich-img\" src=\"2", "class=\"rich-img\" src=\"" + ConstantVariable.IMG_HOST_URL + "2");
            } else {
                this.loopcontent = model.getLoopcontent().replaceAll("class=\"rich-img\" src=\"2", "class=\"rich-img\" src=\"" + ConstantVariable.IMG_HOST_URL + "2");
            }
        }
        this.position = model.getPosition();
    }

    public LoopVO() {
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getLooptype() {
        return looptype;
    }

    public void setLooptype(String looptype) {
        this.looptype = looptype;
    }

    public String getLoopcontent() {
        return loopcontent;
    }

    public void setLoopcontent(String loopcontent) {
        this.loopcontent = loopcontent;
    }
}