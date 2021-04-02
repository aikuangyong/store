package com.store.config.shiro;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author lvshuncai
 * @version 1.0
 * @decription TODO
 * @date 2018/8/26 0026
 */
public class CustomizedToken extends UsernamePasswordToken {

    /**
     * 用户类型，普通用户登录或管理员登录
     */
    @Getter
    @Setter
    private UserType userType;

    /**
     * 用户登录类型，密码登录，短信登录，微信登录，默认密码登录
     */
    @Getter
    @Setter
    private LoginType loginType;

    @Getter
    @Setter
    private String verifyCode;

    public CustomizedToken(final String username, final String password,String verifyCode, UserType userType,LoginType loginType) {
        super(username,password);
        this.userType = userType;
        this.verifyCode = verifyCode;
        this.loginType = loginType;
    }
}