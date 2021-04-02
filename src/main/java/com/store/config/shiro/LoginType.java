package com.store.config.shiro;

import lombok.Data;
import org.apache.commons.lang.enums.ValuedEnum;

/**
 * @author licfe
 * @version 1.0
 * @decription 用户登录类型
 * @date 2018/8/26 0026
 */
public enum LoginType {

    PWD("1","密码登录"), SMS("2","短信登录"), WECHAT("3","微信登录");

    private String value;

    private String name;

    public String getValue(){
        return this.value;
    }

    public String getName(){
        return this.name;
    }

    LoginType(String value,String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.value;
    }

    /**
     * 根据名称获取枚举
     * @param value
     * @return
     */
    public static LoginType getEnumByValue(String value) {
        for (LoginType type : LoginType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return null;
    }
}
