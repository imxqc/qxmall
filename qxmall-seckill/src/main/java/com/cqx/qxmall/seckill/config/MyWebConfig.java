package com.cqx.qxmall.seckill.config;

import com.cqx.qxmall.seckill.interceptor.LoginUserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/5/7 21:59
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginUserInterceptor()).addPathPatterns("/**");
    }
}
