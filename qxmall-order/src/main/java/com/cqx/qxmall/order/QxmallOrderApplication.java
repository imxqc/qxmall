package com.cqx.qxmall.order;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableAspectJAutoProxy//aop的aspectj动态代理
@EnableRedisHttpSession
@EnableRabbit
@EnableFeignClients
@MapperScan("com.cqx.qxmall.order.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class QxmallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(QxmallOrderApplication.class, args);
    }

}
