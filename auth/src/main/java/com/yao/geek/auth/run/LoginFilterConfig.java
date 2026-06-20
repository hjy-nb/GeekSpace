package com.yao.geek.auth.run;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yao.geek.common.Constant.NumConstant;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * 登录过滤器配置
 */
@Component
public class LoginFilterConfig extends OncePerRequestFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    //用geteway放入的id判断
    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        String id = request.getHeader(NumConstant.T_ID);

        if(StringUtils.hasText(id)){
            String authority = request.getHeader(NumConstant.T_AUTHORITY);

            //SimpleGrantedAuthority没有无参构造函数，不能直接反序列化
            List<String> authorityList = objectMapper.readValue(authority,
                    new TypeReference<List<String>>(){});

            //转为权限数组
            List<SimpleGrantedAuthority> authorities=authorityList.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            SecurityContextHolder.getContext().
                    setAuthentication(new UsernamePasswordAuthenticationToken(id, null, authorities)); //第一个参数表登录对象，第二个参数表登录凭证，第三个参数表权限
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }
}
