package com.yao.geek.common.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;

/**
 * 解析token
 */
public class TokenParser {
    public static Claims parseToken(String token, SecretKey secretKey) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
