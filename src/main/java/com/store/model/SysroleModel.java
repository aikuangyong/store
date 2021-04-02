package com.store.model;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@ApiModel("角色信息")
public class SysroleModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("roleid","主键");
        excelHead.put("rolename","角色名称");
        excelHead.put("registertime","注册时间");
        excelHead.put("valid","是否有限");
        return excelHead;
    }

    @ApiModelProperty("角色ID")
    @NonNull
    private String roleid;

    @ApiModelProperty("角色名称")
    private String rolename;

    @ApiModelProperty("创建时间")
    private Date registertime;

    @ApiModelProperty("是否有效")
    private Integer valid;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}