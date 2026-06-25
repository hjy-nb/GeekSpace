package com.yao.geek.auth.control;

import com.yao.geek.auth.model.result.Result;
import com.yao.geek.common.exception.FuException;
import com.yao.geek.common.log.GetLogger;
import com.yao.geek.common.status.StatusCode;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.Objects;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger E_LOGGER = GetLogger.getErrorLogger();
    @ExceptionHandler(FuException.class)
    public Result<?> handleException(FuException e) {
        E_LOGGER.error("异常：{}", e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public Result<?> handleException(IOException e) {
        E_LOGGER.error("异常：{}", e.getMessage());
        return Result.error(StatusCode.CODE_GET_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleException(MethodArgumentNotValidException e) {
        E_LOGGER.error("字段校验异常：{}", e.getMessage());
        return Result.error(StatusCode.FIELD_ERROR.getCode(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleException(ConstraintViolationException e) {
        E_LOGGER.error("字段校验异常 ：{}", e.getMessage());

        return Result.error(StatusCode.FIELD_ERROR.getCode(), Objects.requireNonNull(e.getConstraintViolations().stream().findFirst().orElse(null)).getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<?> handleException(MissingServletRequestParameterException e) {
        E_LOGGER.error("字段转换异常 ：{}", e.getMessage());

        return Result.error(StatusCode.FIELD_TRANSFER_ERROR.getCode(), e.getMessage());
    }
}
