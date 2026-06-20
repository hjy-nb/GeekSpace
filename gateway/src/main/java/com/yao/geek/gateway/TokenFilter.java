package com.yao.geek.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yao.geek.common.Constant.NumConstant;
import com.yao.geek.common.token.SecretKeyCreate;
import com.yao.geek.common.token.TokenParser;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * token校验
 */
@Component
public class TokenFilter implements GlobalFilter, Ordered {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Set<String> whiteList = Set.of(
            "/geek/auth/login",
            "/geek/auth/register",
            "/geek/auth/code"
    );

    @Value("${jwt.secret_key}")
    private String key;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 白名单获取当前path并判断
        String path = exchange.getRequest().getPath().value();

        if (isWhiteList(path)) {
            return chain.filter(exchange);
        }

        String token = exchange.getRequest().getHeaders().getFirst(NumConstant.T_NAME);

        try {
            // 解析token
            Claims claims = TokenParser.parseToken(token, SecretKeyCreate.createSecretKey(key));

            //已经被类型擦除了
            List<?> authority = claims.get(NumConstant.T_AUTHORITY, List.class);

            String json = objectMapper.writeValueAsString(authority);

            return chain.filter(exchange.mutate()
                    .request(r -> r.header(NumConstant.T_ID, claims.getId())
                             .header(NumConstant.T_AUTHORITY, json))
                            .build());
        } catch (Exception e) {
            // 抛出异常
            return unauthorized(exchange, e.getMessage());
        }

    }
    @Override
    public int getOrder() {
        return -1;
    }

    //token验证失败
    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        byte[] bytes;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            bytes = objectMapper.writeValueAsBytes(Map.of(
                    "code", 401,
                    "message", message
            ));
        } catch (Exception e) {
            bytes = ("{\"code\":401,\"message\":\"" + message + "\"}").getBytes();
        }

        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

        return exchange.getResponse().writeWith(Mono.just(buffer))
                .then(exchange.getResponse().setComplete());
    }

    //判断是否在白名单中
    private boolean isWhiteList(String path) {
        AntPathMatcher  matcher= new AntPathMatcher();
        return whiteList.stream().anyMatch(p -> matcher.match(p, path));
    }
}
