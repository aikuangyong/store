package com.store.api.pos.model.Param;

import lombok.Data;

import java.util.List;

@Data
public class PosGoodInfo {

    private String goods_img;
    private String goods_name;
    private String goods_code;
    private String goods_price;
    private String goods_count;

    public PosGoodInfo(PosOrderInfo orderInfo){
        this.goods_img = orderInfo.getGoods_img();
        this.goods_name = orderInfo.getGoods_name();
        this.goods_code = orderInfo.getGoods_code();
        this.goods_price = orderInfo.getGoods_price();

    }
}