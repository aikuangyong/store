package com.store.model.pos;

import lombok.Data;

@Data
public class PosOfflineUploadGoodsRep {
    private String goods_code;
    private String sku_code;
    private Integer price;
    private Integer dismount_price;
    private Integer count;
}