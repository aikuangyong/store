package com.store.api.model.Product;

import com.store.model.VegetableModel;
import com.store.model.VegetabletypeModel;

import java.util.ArrayList;
import java.util.List;

public class TypeVO {

    private String vtid;
    private String vtname;
    private String typesrc;
    private String bannersrc;
    private List<ProductVO> productList;

    public List<ProductVO> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductVO> productList) {
        this.productList = productList;
    }

    public TypeVO(VegetabletypeModel model) {
        this.vtid = model.getVtid();
        this.vtname = model.getVtname();
        this.typesrc = model.getTypesrc();
        this.bannersrc = model.getBannersrc();
        List<VegetableModel> modelList = model.getProductList();
        if (null != modelList) {
            productList = new ArrayList<>();
            for (VegetableModel vmodel : modelList) {
                ProductVO pvo = new ProductVO(vmodel);
                productList.add(pvo);
            }
        }
    }

    public TypeVO() {
    }

    public String getVtid() {
        return vtid;
    }

    public void setVtid(String vtid) {
        this.vtid = vtid;
    }

    public String getVtname() {
        return vtname;
    }

    public void setVtname(String vtname) {
        this.vtname = vtname;
    }

    public String getTypesrc() {
        return typesrc;
    }

    public void setTypesrc(String typesrc) {
        this.typesrc = typesrc;
    }

    public String getBannersrc() {
        return bannersrc;
    }

    public void setBannersrc(String bannersrc) {
        this.bannersrc = bannersrc;
    }
}