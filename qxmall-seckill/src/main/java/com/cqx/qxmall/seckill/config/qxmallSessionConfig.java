package com.cqx.qxmall.seckill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/5/6 14:48
 */
@Configuration
public class qxmallSessionConfig {

    /**
     * 设置cookie的名字,作用域等信息
     * @return
     */
    @Bean
    public CookieSerializer cookieSerializer(){
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();

        cookieSerializer.setDomainName("qxmall.com");
        cookieSerializer.setCookieName("QXSESSION");

        return cookieSerializer;
    }

    /**
     * 将fastjson设为序列化器
     * @return
     */
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer(){
        return new GenericJackson2JsonRedisSerializer();
    }
}
