package com.store.api.pos.model.Param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PosOrderInfo extends PageModel {

    @ApiModelProperty(hidden = true)
    private String total_fee;
    @ApiModelProperty(hidden = true)
    private String paid_fee;
    @ApiModelProperty(hidden = true)
    private String coupon_fee;

    @ApiModelProperty(hidden = true)
    private String order_no;

    @ApiModelProperty(hidden = true)
    private String order_id;

    @ApiModelProperty(hidden = true)
    private String pay_type;

    @ApiModelProperty(hidden = true)
    private String pay_time;

    @ApiModelProperty(hidden = true)
    private String send_type;

    @ApiModelProperty(hidden = true)
    private String remark;

    @ApiModelProperty(hidden = true)
    private String nick_name;

    @ApiModelProperty(hidden = true)
    private List<PosGoodInfo> goods;

    @ApiModelProperty(hidden = true)
    private String goods_name;

    @ApiModelProperty(hidden = true)
    private String goods_img;

    @ApiModelProperty(hidden = true)
    private String goods_count;

    private String goods_code;
    private String goods_price;

    @ApiModelProperty(hidden = true)
    private String store_name;

    @ApiModelProperty(hidden = true)
    private String sale_price;

    @ApiModelProperty(value = "1",notes = "商户 code/门店 code")
    private String store_code;

    @ApiModelProperty(value = "1",notes = "手机号")
    private String mobile;

    @ApiModelProperty(value = "2018-01-03",notes = "查询开始时间;yyyy-MM_dd")
    private String start_time;

    @ApiModelProperty(value = "2019-01-03",notes = "查询结束时间;yyyy-MM_dd")
    private String end_time;

    @ApiModelProperty(value = "",notes = "订单状态,为空则查询所有订单;")
    private String order_status;

    private String send_time;

}