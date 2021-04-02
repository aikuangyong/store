package com.store.api.pos.model.Param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageModel {

    @ApiModelProperty(value = "50", hidden = false, required = false, notes = "返回数目,取值大于 0 且小于 200，默认值为 50")
    private int page_size = 50;
    @ApiModelProperty(value = "0", hidden = false, required = false, notes = "分页编号,默认值 0")
    private int page_no = 0;

}