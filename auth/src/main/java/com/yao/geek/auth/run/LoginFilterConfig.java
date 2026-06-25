package com.yao.geek.auth.run;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yao.geek.common.Constant.NumConstant;
import com.yao.geek.common.log.GetLogger;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 登录过滤器配置
 */
@Component
public class LoginFilterConfig extends OncePerRequestFilter {
    private static final Logger B_LOGGER = GetLogger.getBusinessLogger();

    //用geteway放入的id判断
    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        B_LOGGER.info("进入登录过滤器");

        String id = request.getHeader(NumConstant.T_ID);

        if(StringUtils.hasText(id)){
            String authority = request.getHeader(NumConstant.T_AUTHORITY);

            List<String> authorityList = Arrays.asList(
                    authority.split(",")
            );

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
