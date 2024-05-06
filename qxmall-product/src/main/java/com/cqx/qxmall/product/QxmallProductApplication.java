package com.cqx.qxmall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@EnableCaching
@EnableFeignClients(basePackages = "com.cqx.qxmall.product.feign")
@MapperScan("com.cqx.qxmall.product.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class QxmallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(QxmallProductApplication.class, args);
    }

}
