package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
* 用户邮箱已存在
 */
public class ExistEmail extends FuException {
    public ExistEmail(String message) {
        super(message);
    }

    public ExistEmail(StatusCode code) {
        super(code);
    }
}
