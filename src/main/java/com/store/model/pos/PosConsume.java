package com.store.model.pos;

import lombok.Data;

@Data
public class PosConsume {

    private String user_id;
    private String other_id;
    private String storeCode;
    private String brandId;
    private String openId;
    private String mobile;
    private String wxConsumeNo;
    private String orderNo;
    private String price;
    private String remark;
    private String oldOrderNo;

    private Integer page_size;
    //1、在线充值 2、充值码兑换 3、消费 4、退款
    private Integer businessType;
    //充值码/第三方交易号/订单编号/退单编号
    private String businessNo;
    private String changeMoney;

}