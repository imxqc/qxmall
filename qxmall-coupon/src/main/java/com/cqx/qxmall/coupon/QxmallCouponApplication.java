package com.cqx.qxmall.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@MapperScan("com.cqx.qxmall.coupon.dao")
@SpringBootApplication
public class QxmallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(QxmallCouponApplication.class, args);
    }

}
