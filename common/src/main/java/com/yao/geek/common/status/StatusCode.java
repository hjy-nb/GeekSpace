package com.yao.geek.common.status;

import lombok.Builder;
import lombok.Data;

/**
 * 状态码
 */
public enum StatusCode {
    //auth
    USERNAME_ERROR("10001", "用户名不存在"),
    PASSWORD_ERROR("10002", "密码错误"),
    PHONE_EXIST("10003", "手机号已存在"),
    EMAIL_EXIST("10004", "邮箱已存在"),
    USERNAME_EXIST("10005", "用户名已存在"),
    CODE_ERROR("10006", "验证码错误"),
    CODE_GET_ERROR("10007", "获取验证码失败");

    private String code;
    private String message;

    StatusCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
