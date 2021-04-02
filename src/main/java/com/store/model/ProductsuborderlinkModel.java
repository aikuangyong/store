package com.store.model;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class ProductsuborderlinkModel extends PageModel {

    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("id","主键");
        excelHead.put("vid","菜品ID");
        excelHead.put("suborderid","子订单ID");
        excelHead.put("createtime","创建时间");
        return excelHead;
    }

    @ApiModelProperty(value = "主键", required = true)
    private Integer id;
    @ApiModelProperty(value = "菜品ID", required = true)
    private String vid;
    @ApiModelProperty(value = "子订单ID", required = true)
    private String suborderid;
    @ApiModelProperty(value = "创建时间", required = true)
    private Date createtime;

    /**
     * 获取主键
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * 获取菜品ID
     * @return
     */
    public String getVid() {
        return vid;
    }

    /**
     * 设置菜品ID
     * @param vid
     */
    public void setVid(String vid) {
        this.vid = vid;
    }
    /**
     * 获取子订单ID
     * @return
     */
    public String getSuborderid() {
        return suborderid;
    }

    /**
     * 设置子订单ID
     * @param suborderid
     */
    public void setSuborderid(String suborderid) {
        this.suborderid = suborderid;
    }
    /**
     * 获取创建时间
     * @return
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置创建时间
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}