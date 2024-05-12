package com.cqx.qxmall.member.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Feign拦截器
 *
 * @author xqc
 * @version 1.0
 * @date 2024/5/8 10:05
 */
@Configuration
public class qxFeignConfig {
    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                //获取调用feign接口的请求 RequestContextHolder底层使用ThreadLocal
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes != null) {
                    HttpServletRequest request = attributes.getRequest();

                    //获取请求中的Cookie
                    String cookie = request.getHeader("Cookie");
                    //新请求加上Cookie
                    requestTemplate.header("Cookie", cookie);
                }
            }

        };
    }
}
