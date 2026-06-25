package com.yao.geek.common.token;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * 原始密码生成
 */
public class KeyCreate {
    public static String createKey() {
        // 生成HS256密钥
        SecretKey secretKey = Jwts.SIG.HS256.key().build();
        // 将密钥转换为字符串
        return Base64.getEncoder()
                .encodeToString(secretKey.getEncoded());
    }
}
