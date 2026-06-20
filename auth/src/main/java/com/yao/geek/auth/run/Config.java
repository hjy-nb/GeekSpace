package com.yao.geek.auth.run;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类
 */
@Configuration
@EnableWebSecurity
public class Config implements WebMvcConfigurer {
    private final LoginFilterConfig loginFilterConfig;
    public Config(LoginFilterConfig loginFilterConfig) {
        this.loginFilterConfig = loginFilterConfig;
    }
    // 浏览器跨域同源配置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许所有路径
                .allowedOriginPatterns("*")  // 允许所有源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 允许所有方法
                .allowedHeaders("*")  // 允许所有头
                .allowCredentials(true)  // 允许携带凭证
                .maxAge(3600); // 缓存时间
    }

    @Bean  // 密码加密
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 安全过滤器链
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)  /// 禁用csrf
            .authorizeHttpRequests(auth->auth
                    .requestMatchers("/geek/auth/login")   //放行登录
                    .permitAll()
                    .requestMatchers("/geek/auth/register") //放行注册
                    .permitAll()
                    .requestMatchers("/geek/auth/code")  //放行获取验证码
                    .permitAll()
                    .anyRequest()  // 其他请求都需要认证
                    .authenticated())
                .logout(AbstractHttpConfigurer::disable)  // 禁用登出
                .formLogin(AbstractHttpConfigurer::disable)  // 禁用表单登录
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // 禁用session
                .httpBasic(AbstractHttpConfigurer::disable)  // 禁用httpBasic
                .rememberMe(AbstractHttpConfigurer::disable)  // 禁用记住我
                .addFilterBefore(loginFilterConfig, UsernamePasswordAuthenticationFilter.class)  // 添加登录过滤器
                .exceptionHandling(ex->ex
                        .authenticationEntryPoint((request, response, authException)->{  //接收登录失败异常
                            response.setContentType("application/json");  // 返回json
                            response.setStatus(401);  // 状态码
                            response.getWriter().write("{\"code\":\"401\",\"message\":\"用户未登录\"}");  // 返回信息
                        }));

        return http.build();
    }
}
