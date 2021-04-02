package com.store.model;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageModel extends BaseModel {

    @ApiModelProperty(hidden = true)
    private int pageSize = 15;

    @ApiModelProperty(hidden = true)
    private int pageNow = 0;

    @ApiModelProperty(hidden = true)
    private int pageNumber = 1;

    @ApiModelProperty(hidden = true)
    private int dataCount;

    public void noPage(){
        this.pageNow = 0;
        this.pageSize = Integer.MAX_VALUE;
    }
}