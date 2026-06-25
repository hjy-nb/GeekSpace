package com.yao.geek.user.common;

import com.yao.geek.common.Constant.NumConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * feign调用时添加请求头
 */
@Component
public class RequestAdd implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 获取当前存在本地线程内的请求对象
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();

        String id = request.getHeader(NumConstant.T_ID);
        String authority = request.getHeader(NumConstant.T_AUTHORITY);

        if(id != null){
            requestTemplate.header(NumConstant.T_ID,id)
                    .header(NumConstant.T_AUTHORITY,authority)  ;
        }
    }
}
