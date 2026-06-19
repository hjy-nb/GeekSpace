package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * 手机号已存在
 */
public class ExistPhone extends FuException {
    public ExistPhone(String message) {
        super(message);
    }

    public ExistPhone(StatusCode cause){
        super(cause);
    }
}
