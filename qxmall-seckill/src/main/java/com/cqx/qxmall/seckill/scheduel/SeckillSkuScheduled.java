package com.cqx.qxmall.seckill.scheduel;

import com.cqx.qxmall.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>Title: SeckillSkuScheduled</p>
 * Description：秒杀商品定时上架		[秒杀的定时任务调度]
 * date：2020/7/6 17:28
 */
@Slf4j
@Service
public class SeckillSkuScheduled {
    @Autowired
    SeckillService seckillService;

    @Autowired
    RedissonClient redissonClient;

    private final String upload_lock = "seckill:upload:lock";

    @Scheduled(cron = "*/10 * * * * ?")
    public void uploadSeckillSkuLatest3Days(){
        log.info("上架秒杀的商品信息...");
        RLock lock = redissonClient.getLock(upload_lock);
        lock.lock();

        try {
            seckillService.uploadSeckillSkuLatest3Days();
        } finally {
            lock.unlock();
        }
    }
}
