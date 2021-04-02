package com.store.model;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class RegularHistoryModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("orderno","主键");
        excelHead.put("orderinfo","订单信息");
        return excelHead;
    }

    @ApiModelProperty(value = "主键", required = true)
    private String orderno;
    @ApiModelProperty(value = "订单信息", required = true)
    private String orderinfo;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}