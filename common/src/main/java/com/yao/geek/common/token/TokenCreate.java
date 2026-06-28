package com.yao.geek.common.token;

import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import com.yao.geek.common.constant.NumConstant;
import java.util.Date;
import java.util.List;

/**
 * token生成
 */
public class TokenCreate {
    public static String createToken(Long id, List<String>  authorities, SecretKey secretkey) {
        return  Jwts.builder()
                .claim(NumConstant.T_ID,id)
                .claim(NumConstant.T_AUTHORITY,authorities)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + NumConstant.T_EXIST_TIME_S))
                .signWith(secretkey)
                .compact();
    }
}
