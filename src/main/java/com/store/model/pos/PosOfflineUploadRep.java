package com.store.model.pos;

import lombok.Data;

import java.util.List;

@Data
public class PosOfflineUploadRep {

    private String member_phone;
    private String create_time;
    private String total_fee;
    private String paid_fee;
    private String store_code;
    private String order_no;
    private String pay_type;
    private String pay_time;
    private String remark;
    private List<PosOfflineUploadGoodsRep> goods;

}