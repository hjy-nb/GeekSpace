package com.yao.geek.common.sha256;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

/**
 * 获取字符串的SHA-256哈希值
 */
public class GetHashSha256 {
    public static String getHashSha256(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] hashByte=md.digest(str.getBytes(StandardCharsets.UTF_8));

        return HexFormat.of().formatHex(hashByte);
    }
}
