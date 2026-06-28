package com.yao.geek.common.token;

import com.yao.geek.common.constant.NumConstant;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

/**
 * 密钥生成
 */
public class SecretKeyCreate {
    public static SecretKey createSecretKey() {
        return Keys.hmacShaKeyFor(NumConstant.T_CONTENT.getBytes());
    }
}
