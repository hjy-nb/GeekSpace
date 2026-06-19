package com.yao.geek.auth.control;

import com.yao.geek.auth.model.result.Result;
import com.yao.geek.common.exception.FuException;
import com.yao.geek.common.status.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FuException.class)
    public Result<?> handleException(FuException e) {
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public Result<?> handleException(IOException e) {
        return Result.error(StatusCode.CODE_GET_ERROR);
    }
}
