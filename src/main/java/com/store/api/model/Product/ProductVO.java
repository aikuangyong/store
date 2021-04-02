package com.store.api.model.Product;

import com.store.model.VegetableModel;
import com.store.model.config.ImageinfoModel;
import com.store.utils.ConstantVariable;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductVO {

    private String vid;
    private String content;
    private String detailcontent;
    private String price;
    private String vegetablename;
    private String posno;
    private String isIndex;
    private String salecount;
    //1:是定期购;0:不是定期购
    private String isregular;
    private String thumimg;
    private List<ImageVO> imageList;


    public ProductVO() {
    }

    public ProductVO(VegetableModel model) {
        this.vid = model.getVid();
        this.content = model.getContent();
        if (null == model.getDetailcontent()) {
            this.detailcontent = "";
        } else {
            this.detailcontent = ConstantVariable.APP_IMG_STYLE + model.getDetailcontent().replaceAll("class=\"rich-img\" src=\"2", "class=\"rich-img\" src=\"" + ConstantVariable.IMG_HOST_URL + "2");
        }
        this.price = model.getPrice();
        this.vegetablename = model.getVegetablename();
        this.posno = model.getPosno();
        this.thumimg = model.getThumimg();
        this.isregular = model.getIsregular();
        this.salecount = null == model.getSalecount() ? "0" : "" + model.getSalecount();
        List<ImageinfoModel> imageModelList = model.getImageList();
        if (null != imageModelList) {
            imageList = new ArrayList<>();
            for (ImageinfoModel imageinfoModel : imageModelList) {
                ImageVO imageVO = new ImageVO(imageinfoModel);
                imageList.add(imageVO);
            }
        }
    }

    public String getSalecount() {
        return salecount;
    }

    public void setSalecount(String salecount) {
        this.salecount = salecount;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDetailcontent() {
        return detailcontent;
    }

    public void setDetailcontent(String detailcontent) {
        this.detailcontent = detailcontent;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVegetablename() {
        return vegetablename;
    }

    public void setVegetablename(String vegetablename) {
        this.vegetablename = vegetablename;
    }

    public String getPosno() {
        return posno;
    }

    public void setPosno(String posno) {
        this.posno = posno;
    }

    public String getIsIndex() {
        return isIndex;
    }

    public void setIsIndex(String isIndex) {
        this.isIndex = isIndex;
    }

    public List<ImageVO> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageVO> imageList) {
        this.imageList = imageList;
    }
}