package com.cqx.qxmall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com.cqx.qxmall.ware.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class QxmallWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(QxmallWareApplication.class, args);
    }

}
