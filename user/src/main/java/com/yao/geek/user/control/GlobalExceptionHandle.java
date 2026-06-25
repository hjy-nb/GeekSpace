package com.yao.geek.user.control;

import com.yao.geek.common.exception.FuException;
import com.yao.geek.common.log.GetLogger;
import com.yao.geek.common.status.StatusCode;
import com.yao.geek.user.model.result.Result;
import org.slf4j.Logger;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Objects;

/**
 * 全局异常处理
 */
public class GlobalExceptionHandle {
    private final static Logger E_LOGGER = GetLogger.getErrorLogger();
    @ExceptionHandler(FuException.class)
    public Result<?> handleException(FuException e) {
        E_LOGGER.error("异常：{}", e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleException(MethodArgumentNotValidException e) {
        E_LOGGER.error("字段校验异常：{}", e.getMessage());
        return Result.error(StatusCode.FIELD_ERROR.getCode(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }
}
