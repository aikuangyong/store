package com.${scheml}.model;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class ${name?cap_first}Model extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        <#list modelList as modelObj>
        excelHead.put("${modelObj.property}","${modelObj.comment}");
        </#list>
        return excelHead;
    }

    <#list modelList as modelObj>
    @ApiModelProperty(value = "${modelObj.comment}", required = true)
    private ${modelObj.javaType} ${modelObj.property?uncap_first};
    </#list>

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}