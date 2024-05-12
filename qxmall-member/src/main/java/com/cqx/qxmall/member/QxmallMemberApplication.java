package com.cqx.qxmall.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@EnableFeignClients(basePackages = "com.cqx.qxmall.member.feign")
@MapperScan("com.cqx.qxmall.member.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class QxmallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(QxmallMemberApplication.class, args);
    }

}
