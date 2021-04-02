package com.store.api.pos.model.Param;

import lombok.Data;

@Data
public class PosOrderDetail {

    private String receive_name;
    private String receive_address;
    private String receive_phone;
    private String goods_specification;
    private String goods_name;
    private String send_time;
    private String send_date;
    private String send_num;
    private String store_name;
    private String order_no;
    private String description;

}