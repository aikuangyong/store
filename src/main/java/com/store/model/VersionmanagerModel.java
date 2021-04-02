package com.store.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class VersionmanagerModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("vid","主键");
        excelHead.put("imagegrp","图片");
        excelHead.put("apksrc","APK路径");
        excelHead.put("versionno","版本号");
        excelHead.put("requiredupdate","必须更新");
        excelHead.put("createtime","创建时间");
        return excelHead;
    }

    private String vid;
    private String imagegrp;
    private String apksrc;
    private String versionno;
    private Integer requiredupdate;
    private Date createtime;
    private Integer seq;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}