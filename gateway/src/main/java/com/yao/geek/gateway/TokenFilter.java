package com.yao.geek.gateway;

import com.yao.geek.common.Constant.NumConstant;
import com.yao.geek.common.token.SecretKeyCreate;
import com.yao.geek.common.token.TokenParser;
import io.jsonwebtoken.Claims;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.Set;

/**
 * token校验
 */
@Component
public class TokenFilter implements GlobalFilter, Ordered {
    private final Set<String> whiteList = Set.of();
    @Value("${jwt.secret_key}")
    private String key;
    @Override
    @NullMarked
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 白名单获取当前path并判断
        String path = exchange.getRequest().getPath().value();
        if (whiteList.contains(path)) {
            return chain.filter(exchange);
        }

        String token = exchange.getRequest().getHeaders().getFirst(NumConstant.T_NAME);

        try {
            // 解析token
            Claims claims = TokenParser.parseToken(token, SecretKeyCreate.createSecretKey(key));

            return chain.filter(exchange.mutate()
                    .request(r -> r.header(NumConstant.T_ID, claims.getId())
                             .header(NumConstant.T_AUTHORITY, claims.get(NumConstant.T_AUTHORITY).toString()))
                            .build());
        } catch (Exception e) {
            // 抛出异常
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

    }
    @Override
    public int getOrder() {
        return -1;
    }
}
