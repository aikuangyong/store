package com.store.model;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class SalecountModel extends PageModel {

    public Map<String, String> excelHead() {
        Map<String, String> excelHead = new LinkedHashMap<>();
        excelHead.put("scid", "主键");
        excelHead.put("productName", "菜品名称");
        excelHead.put("productNo", "菜品编号");
        excelHead.put("productName", "门店名称");
        excelHead.put("productName", "销售数量");
        excelHead.put("productName", "销售价格(元)");
        return excelHead;
    }

    private String productName;
    private String productNo;
    private String storeName;
    private String storeId;
    private String saleCount;
    private String salePrice;
    private String startPayTime;
    private String endPayTime;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}