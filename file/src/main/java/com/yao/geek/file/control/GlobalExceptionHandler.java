package com.yao.geek.file.control;

import com.yao.geek.common.exception.FuException;
import com.yao.geek.common.log.GetLogger;
import com.yao.geek.common.status.StatusCode;
import com.yao.geek.file.model.result.Result;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger E_LOGGER = GetLogger.getErrorLogger();
    @ExceptionHandler(FuException.class)
    public Result<Void> handleException(FuException e) {
        E_LOGGER.error("异常：{}", e.getMessage());

        return Result.error(e.getCode(), e.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleException(MethodArgumentNotValidException e) {
        E_LOGGER.error("字段校验异常：{}", e.getMessage());

        return Result.error(StatusCode.FIELD_ERROR.getCode(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Void> handleException(ConstraintViolationException e) {
        E_LOGGER.error("字段校验异常 ：{}", e.getMessage());

        return Result.error(StatusCode.FIELD_ERROR.getCode(), Objects.requireNonNull(e.getConstraintViolations().stream().findFirst().orElse(null)).getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<Void> handleException(MissingServletRequestParameterException e) {
        E_LOGGER.error("字段转换异常 ：{}", e.getMessage());

        return Result.error(StatusCode.FIELD_TRANSFER_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(NoSuchAlgorithmException.class)
    public Result<Void> handleException(NoSuchAlgorithmException e) {
        E_LOGGER.error("文章内容获取哈希值异常 ：{}", e.getMessage());

        return Result.error(StatusCode.SHA256_ERROR);
    }

    //参数上不能指定排除异常
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        E_LOGGER.error("异常：{}", e.getMessage());

        return Result.error(StatusCode.UNKNOW_ERROR);
    }
}
