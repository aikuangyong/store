package com.store.config.shiro;

/**
 * @author licfe
 * @version 1.0
 * @decription 用户类型
 * @date 2018/8/26 0026
 */
public enum UserType {

    USER("User"),  ADMIN("Admin");

    private String type;

    UserType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
