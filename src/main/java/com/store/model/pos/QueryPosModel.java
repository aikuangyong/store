package com.store.model.pos;

import com.store.api.pos.model.Param.PageModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryPosModel extends PageModel {

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

    private String sendTime;

    private String orderId;

    private String shopId;


    @ApiModelProperty(value = "2019-01-03",notes = "查询结束时间;yyyy-MM_dd")
    private String order_no;

}