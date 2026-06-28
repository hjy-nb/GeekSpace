package com.yao.geek.gateway;

import com.yao.geek.common.constant.NumConstant;
import com.yao.geek.common.token.SecretKeyCreate;
import com.yao.geek.common.token.TokenParser;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Set;

/**
 * token校验
 */
@Component
public class TokenFilter implements GlobalFilter, Ordered {
    private final Set<String> whiteList = Set.of(
            "/geek/auth/login",
            "/geek/auth/register",
            "/geek/auth/code"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("进入token过滤器");
        // 白名单获取当前path并判断
        String path = exchange.getRequest().getPath().value();

        if (isWhiteList(path)) {
            return chain.filter(exchange);
        }

        String token = exchange.getRequest().getHeaders().getFirst(NumConstant.T_NAME);

        // 解析token
        Claims claims = TokenParser.parseToken(token, SecretKeyCreate.createSecretKey());

        //已经被类型擦除了
        List<?> authority = claims.get(NumConstant.T_AUTHORITY, List.class);
        Long id= claims.get(NumConstant.T_ID, Long.class);

        String json = String.join(",", authority.stream().map(Object::toString).toList());

        return chain.filter(exchange.mutate()
                .request(r -> r.header(NumConstant.T_ID, id.toString())
                        .header(NumConstant.T_AUTHORITY, json))
                .build());

    }
    @Override
    public int getOrder() {
        return -1;
    }

    //判断是否在白名单中
    private boolean isWhiteList(String path) {
        AntPathMatcher  matcher= new AntPathMatcher();
        return whiteList.stream().anyMatch(p -> matcher.match(p, path));
    }
}
