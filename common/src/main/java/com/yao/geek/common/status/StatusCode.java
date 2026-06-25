package com.yao.geek.common.status;

import lombok.Getter;

/**
 * 状态码
 */
@Getter
public enum StatusCode {
    //登录错误
    LOGIN_ERROR("401", "用户未登录"),
    AUTH_ERROR("403", "权限不足"),
    FIELD_ERROR("00007", "字段校验异常"),
    //auth
    USERNAME_ERROR("10001", "用户名不存在"),
    PASSWORD_ERROR("10002", "密码错误"),
    PHONE_EXIST("10003", "手机号已存在"),
    EMAIL_EXIST("10004", "邮箱已存在"),
    USERNAME_EXIST("10005", "用户名已存在"),
    CODE_ERROR("10006", "验证码错误"),
    CODE_GET_ERROR("10007", "获取验证码失败"),

    //user
    CREATE_DEFAULT_USER_ERROR("20001", "创建默认用户失败"),
    DELETE_USER_ROLE_ERROR("20002", "删除用户角色失败"),
    DELETE_USER_DETAIL_ERROR("20003", "删除用户信息失败"),
    DELETE_USER_ATTENTION_ERROR("20004", "删除用户所有关注失败"),
    NO_ATTENTION("20005", "用户未关注"),
    HAVE_ATTENTION("20006", "用户已关注"),
    FIELD_TRANSFER_ERROR("20007", "字段转换异常");
    //

    private final String code;
    private final String message;

    StatusCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
