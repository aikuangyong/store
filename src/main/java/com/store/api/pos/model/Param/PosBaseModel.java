package com.store.api.pos.model.Param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PosBaseModel {

    //    @ApiModelProperty(name = "appid",value = "f93fb45f-ed25-466a-9215-d0d93a809f98", hidden = false, required = false, notes = "返回数目,取值大于 0 且小于 200，默认值为 50")
    @ApiModelProperty(name = "appid", value = "商户号ID", allowableValues = "f93fb45f-ed25-466a-9215-d0d93a809f98", hidden = false, required = true)
    private String appid;
    //2194BE12F45556DDE33E8139C1CC5518
    @ApiModelProperty(hidden = true)
    private String secret;
    //    @ApiModelProperty(name = "ts", value = "20180903162310", hidden = false, required = false, notes = "时间戳。时间戳格式为：yyyyMMddHHmmss")
    @ApiModelProperty(name = "ts", value = "时间戳。时间戳格式为：yyyyMMddHHmmss", allowableValues = "20180903162310", hidden = false, required = true, notes = "时间戳。时间戳格式为：yyyyMMddHHmmss")
    private String ts;

    @ApiModelProperty(name = "v", value = "版本号", allowableValues = "2.0", hidden = false, required = true, notes = "")
    private String v;
    @ApiModelProperty(name = "sign", value = "签名，对字符串进行md5签名(32位);appid+secret+ts+v", allowableValues = "09b00f724ab2f485b9a22f2e2a20ad32", hidden = false, required = true, notes = "签名，对字符串进行md5签名(32位);appid+secret+ts+v")
    private String sign;
}