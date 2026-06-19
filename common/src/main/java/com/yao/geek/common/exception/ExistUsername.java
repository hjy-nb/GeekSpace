package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * 用户名已存在
 */
public class ExistUsername extends FuException {
    public ExistUsername(String message) {
        super(message);
    }

    public ExistUsername(StatusCode cause) {
        super(cause);
    }

}
