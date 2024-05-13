package com.cqx.qxmall.seckill.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/4/24 20:31
 */
@Configuration
public class MyRedissonConfig {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() throws IOException{
        //创建配置
        Config config = new Config();
        //设置单节点redis  地址前需加redis://
        config.useSingleServer().setAddress("redis://192.168.149.100:6379");

        //创建redissonclient实例
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
