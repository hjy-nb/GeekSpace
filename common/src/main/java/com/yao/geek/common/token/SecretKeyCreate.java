package com.yao.geek.common.token;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

/**
 * 密钥生成
 */
public class SecretKeyCreate {
    public static SecretKey createSecretKey(String key) {
        return Keys.hmacShaKeyFor(key.getBytes());
    }
}
