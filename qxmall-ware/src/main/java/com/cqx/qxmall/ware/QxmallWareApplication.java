package com.cqx.qxmall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableRabbit
@EnableFeignClients
@EnableTransactionManagement
@MapperScan("com.cqx.qxmall.ware.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class QxmallWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(QxmallWareApplication.class, args);
    }

}
