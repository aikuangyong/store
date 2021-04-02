package com.store.model;

import java.util.Date;

public class AdminUserModel extends PageModel {

    private String userName;

    private String passWord;

    private String verifyCode;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取
     * @return
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * 设置
     * @param passWord
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}