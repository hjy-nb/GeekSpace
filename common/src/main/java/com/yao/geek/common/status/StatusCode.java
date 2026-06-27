package com.yao.geek.common.status;

import lombok.Getter;

/**
 * 状态码
 */
@Getter
public enum StatusCode {
    //公共
    LOGIN_ERROR("401", "用户未登录"),
    AUTH_ERROR("403", "权限不足"),
    FIELD_ERROR("00001", "字段校验异常"),
    UNKNOW_ERROR("00000", "未知异常"),
    FIELD_TRANSFER_ERROR("00002", "字段转换异常"),

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

    //blog
    ARTICLE_IS_NOT_ORIGINAL("30001", "文章不是原创"),
    ARTICLE_EXIST("30002", "文章已存在"),
    NULL_ARTICLE_CONTENT("30003", "文章内容为空"),
    DOUBLE_PUBLISH("30004", "文章已发布"),
    DOUBLE_PRIVATE("30005", "文章已设为私密"),
    SHA256_ERROR("30006", "文章内容获取哈希值异常"),
    ARTICLE_NOT_EXIST("30007", "文章不存在"),
    TAG_NOT_EXIST("30008", "标签不存在"),
    TAG_EXIST("30009", "标签已存在"),
    ARTICLE_ID_NULL("30010", "文章ID为空"),
    TAG_ID_NULL("30011", "标签ID为空"),
    CATEGORY_NOT_EXIST("30012", "分类不存在"),
    CATEGORY_EXIST("30013", "分类已存在");

    private final String code;
    private final String message;

    StatusCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
