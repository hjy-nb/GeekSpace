package com.yao.geek.comment.model.result;

import com.yao.geek.common.status.StatusCode;
import lombok.Builder;
import lombok.Data;

/**
 * 统一返回结果
 */
@Data
@Builder
public class Result<T> {
    private String code;
    private String message;
    private T data;
    private boolean success;

    // 成功
    public static <T> Result<T> ok(T data) {
        return Result.<T>builder()
                .code("200")
                .message("成功")
                .data(data)
                .success(true)
                .build();
    }

    // 失败
    public static <T> Result<T> error(String code, String message) {
        return Result.<T>builder()
                .code(code)
                .message(message)
                .data(null)
                .success(false)
                .build();
    }
    public static <T> Result<T> error(StatusCode statusCode) {
        return error(statusCode.getCode(), statusCode.getMessage());
    }
}
