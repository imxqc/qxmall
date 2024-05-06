package com.cqx.qxmall.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@EnableRedisHttpSession
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class QxmallAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(QxmallAuthServerApplication.class, args);
    }

}
