package com.cqx.qxmall.seckill.service;

import com.cqx.qxmall.seckill.to.SeckillSkuRedisTo;

import java.util.List;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/5/13 11:19
 */
public interface SeckillService {
    void uploadSeckillSkuLatest3Days();

    List<SeckillSkuRedisTo> getCurrentSeckillSkus();

    SeckillSkuRedisTo getSkuSeckillInfo(Long skuId);
}
