package com.store.model.pos;

import lombok.Data;

@Data
public class PosStockRealtime {

    private String page_no;
    private String page_size;
    private String store_name;
    private String stock;
    private String unit_name;
    private String store_code;
    private String goods_name;
    private String unit_code;
    private String goods_code;
}