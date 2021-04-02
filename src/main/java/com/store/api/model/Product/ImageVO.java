package com.store.api.model.Product;

import com.store.model.config.ImageinfoModel;

public class ImageVO {

    private String href;

    private Integer imageseq;

    public ImageVO(ImageinfoModel model) {
        href = model.getSrc();
        this.imageseq = model.getImageseq();
    }

    public Integer getImageseq() {
        return imageseq;
    }

    public void setImageseq(Integer imageseq) {
        this.imageseq = imageseq;
    }

    public ImageVO() {

    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}