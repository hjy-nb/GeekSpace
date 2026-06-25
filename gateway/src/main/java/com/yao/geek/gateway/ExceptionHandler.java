package com.yao.geek.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yao.geek.common.log.GetLogger;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.util.Map;

@Component
@Order(-2)
public class ExceptionHandler implements ErrorWebExceptionHandler {
    private static final Logger E_LOGGER= GetLogger.getErrorLogger();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    @Nonnull
    public Mono<Void> handle(@Nonnull ServerWebExchange exchange, @Nonnull Throwable throwable) {
        String massage;

        System.out.println("进入异常处理");

        E_LOGGER.error("token 认证失败,{}", throwable.getMessage());

        if (throwable instanceof SignatureException) {
            massage = "Token签名无效";
        } else if(throwable instanceof ExpiredJwtException){
            massage = "Token已过期";
        } else if(throwable instanceof MalformedJwtException){
            massage = "Token格式错误";
        } else if(throwable instanceof JwtException){
            massage = "Token无效";
        } else {
            massage = "Token认证失败";
        }

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.valueOf("application/json;charset=UTF-8"));

        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(Map.of(
                    "code", 401,
                    "message", massage
            ));
        } catch (Exception e) {
            bytes = "{\"code\":401,\"message\":\"Token认证失败\"}".getBytes(StandardCharsets.UTF_8);
        }

        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

        return exchange.getResponse().writeWith(Mono.just(buffer));

    }
}
