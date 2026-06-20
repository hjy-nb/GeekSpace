package com.yao.geek.common.status;

import lombok.Getter;

/**
 * 状态码
 */
@Getter
public enum StatusCode {
    //登录错误
    LOGIN_ERROR("401", "用户未登录"),
    //auth
    USERNAME_ERROR("10001", "用户名不存在"),
    PASSWORD_ERROR("10002", "密码错误"),
    PHONE_EXIST("10003", "手机号已存在"),
    EMAIL_EXIST("10004", "邮箱已存在"),
    USERNAME_EXIST("10005", "用户名已存在"),
    CODE_ERROR("10006", "验证码错误"),
    CODE_GET_ERROR("10007", "获取验证码失败");

    private final String code;
    private final String message;

    StatusCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
