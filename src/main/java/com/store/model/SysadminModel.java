package com.store.model;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.Serializable;
import java.util.*;

/**
 * 管理员信息
 * @author licfe
 */
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@ApiModel("管理员信息")
public class SysadminModel extends PageModel implements Serializable {

    /**
     * Excel的列名
     * @return
     */
    public Map<String,String> excelHead(){
        Map<String, String> excelHead = new HashMap<>(0);
        excelHead.put("adminid","主键");
        excelHead.put("username","管理员姓名");
        excelHead.put("role","所属角色");
        excelHead.put("store","所属门店");
        excelHead.put("registertime","注册时间");
        excelHead.put("valid","是否有效");
        excelHead.put("loginno","登录账号");
        excelHead.put("password","密码");
        return excelHead;
    }

    private String storename;
    private String rolename;

    @ApiModelProperty("主键ID")
    private String adminid;

    @ApiModelProperty("管理员名称")
    private String username;

    @ApiModelProperty("所属角色")
    private String role;

    @ApiModelProperty("所属门店")
    private String store;

    @ApiModelProperty("注册时间")
    private Date registertime;

    @ApiModelProperty("是否有效")
    private String valid;

    @ApiModelProperty("登录名")
    @NonNull
    private String loginno;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("验证码")
    private String verifyCode;

    @ApiModelProperty("Token")
    private String usertoken;

    @ApiModelProperty(value = "用户所有的角色",hidden = true)
    private Set<String> roles = new HashSet<>(0);

    @ApiModelProperty(value = "用户所有的权限",hidden = true)
    private Set<String> perms = new HashSet<>(0);

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}