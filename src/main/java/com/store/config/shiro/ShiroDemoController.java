package com.store.config.shiro;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Api("Shiro权限管理Demo，仅适合静态权限")
@Controller
@RequestMapping("/shiro/demo")
public class ShiroDemoController {

    @ApiOperation(value = "所有用户匿名访问",notes = "由于ShiroConfig中配置了该路径可以匿名访问，所以这接口不需要登录就能访问")
    @GetMapping("/hello")
    public String hello() {
        return "Hello Shiro";
    }

    @ApiOperation(value = "未登录是可匿名访问，登录后不能访问！",notes ="由于ShiroConfig中配置了该路径可以匿名访问，所以这接口不需要登录就能访问,如果配置了可以匿名访问，那这里在没有登录的时候可以访问，但是用户登录后就不能访问")
    @RequiresGuest
    @GetMapping("/guest")
    public String guest() {
        return "我未登录时可以访问，登录后不能访问！";
    }

    @ApiOperation(value = "必须是用户才能访问，类似必须登录才能访问！")
    @RequiresUser
    @GetMapping("/user")
    public String user() {
        return "我是站点的用户！";
    }

    @ApiOperation(value = "必须拥有[user:add]权限才能访问！")
    @RequiresPermissions("user:add")
    @GetMapping("/user/add")
    public String addUser() {
        return "我有权限添加用户！";
    }

    @ApiOperation(value = "必须拥有[user:add,user:update,user:del]权限才能访问！")
    @RequiresPermissions({"user:add","user:update","user:del"})
    @GetMapping("/user/save")
    public String saveUser() {
        return "我有权限添加、更新、删除用户！";
    }

    @ApiOperation(value = "必须拥有[user:add,user:update,user:del]权限才能访问！")
    @RequiresPermissions(value = {"user:add","user:update","user:del"},logical=Logical.OR)
    @GetMapping("/user/one")
    public String oneUser() {
        return "我添加、更新、删除权限中其中一种的权限！";
    }

    @ApiOperation(value = "必须拥有[java]的角色才能访问！")
    @RequiresRoles("java")
    @GetMapping("/java")
    public String java() {
        return "我是Java程序员！";
    }

    @ApiOperation(value = "必须拥有[java,php]的角色才能访问！")
    @RequiresRoles({"java","php"})
    @GetMapping("/java/and/php")
    public String javaAndPhp() {
        return "我是Java程序员，同时也是PHP程序员！";
    }

    @ApiOperation(value = "拥有[java,php]中的一个的角色才能访问！")
    @RequiresRoles(value = {"java","php"},logical=Logical.OR)
    @GetMapping("/java/or/php")
    public String javaOrPhp() {
        return "我是PHP程序员，满足Java或PHP的条件";
    }
}