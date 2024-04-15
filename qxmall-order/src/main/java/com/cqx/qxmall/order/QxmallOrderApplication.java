package com.cqx.qxmall.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com.cqx.qxmall.order.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class QxmallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(QxmallOrderApplication.class, args);
    }

}
