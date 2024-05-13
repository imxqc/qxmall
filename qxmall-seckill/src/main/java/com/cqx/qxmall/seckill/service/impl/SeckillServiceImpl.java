package com.cqx.qxmall.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqx.common.utils.R;
import com.cqx.qxmall.seckill.feign.CouponFeignService;
import com.cqx.qxmall.seckill.feign.ProductFeignService;
import com.cqx.qxmall.seckill.service.SeckillService;
import com.cqx.qxmall.seckill.to.SeckillSkuRedisTo;
import com.cqx.qxmall.seckill.vo.SeckillSessionsWithSkus;
import com.cqx.qxmall.seckill.vo.SkuInfoVo;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/5/13 11:19
 */
@Service
public class SeckillServiceImpl implements SeckillService {
    @Autowired
    private CouponFeignService couponFeignService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private RedissonClient redissonClient;

    private final String SESSION_CACHE_PREFIX = "seckill:sessions:";

    private final String SKUKILL_CACHE_PREFIX = "seckill:skus:";

    private final String SKUSTOCK_SEMAPHONE = "seckill:stock:"; // +商品随机码
    @Override
    public void uploadSeckillSkuLatest3Days() {
        // 1.扫描最近三天要参加秒杀的商品
        R r = couponFeignService.getLate3DaySession();
        if(r.getCode() == 0){
            List<SeckillSessionsWithSkus> sessions = r.getData(new TypeReference<List<SeckillSessionsWithSkus>>() {});
            // 2.缓存活动信息
            saveSessionInfo(sessions);
            // 3.缓存活动的关联的商品信息
            saveSessionSkuInfo(sessions);
        }
    }

    @Override
    public List<SeckillSkuRedisTo> getCurrentSeckillSkus() {

        // 1.确定当前时间属于那个秒杀场次
        long time = new Date().getTime();
        // 定义一段受保护的资源
            Set<String> keys = stringRedisTemplate.keys(SESSION_CACHE_PREFIX + "*");
            for (String key : keys) {
                // seckill:sessions:1593993600000_1593995400000
                String replace = key.replace("seckill:sessions:", "");
                String[] split = replace.split("_");
                long start = Long.parseLong(split[0]);
                long end = Long.parseLong(split[1]);
                if(time >= start && time <= end){
                    // 2.获取这个秒杀场次的所有商品信息
                    List<String> range = stringRedisTemplate.opsForList().range(key, 0, 100);
                    BoundHashOperations<String, String, String> hashOps = stringRedisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
                    List<String> list = hashOps.multiGet(range);
                    if(list != null){
                        return list.stream().map(item -> {
                            SeckillSkuRedisTo redisTo = JSON.parseObject(item, SeckillSkuRedisTo.class);
//						redisTo.setRandomCode(null);
                            return redisTo;
                        }).collect(Collectors.toList());
                    }
                    break;
                }
            }
        return null;
    }

    @Override
    public SeckillSkuRedisTo getSkuSeckillInfo(Long skuId) {
        BoundHashOperations<String, String, String> hashOps = stringRedisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
        Set<String> keys = hashOps.keys();

        if(keys != null && keys.size() > 0){
            String regx = "\\d-" + skuId;
            for (String key : keys) {
                if(Pattern.matches(regx, key)){
                    String json = hashOps.get(key);
                    SeckillSkuRedisTo to = JSON.parseObject(json, SeckillSkuRedisTo.class);
                    // 处理一下随机码
                    long current = new Date().getTime();

                    if(current <= to.getStartTime() || current >= to.getEndTime()){
                        to.setRandomCode(null);
                    }
                    return to;
                }
            }
        }
        return null;
    }

    private void saveSessionInfo(List<SeckillSessionsWithSkus> sessions){
        if(sessions != null){
            sessions.stream().forEach(session -> {
                long startTime = session.getStartTime().getTime();

                long endTime = session.getEndTime().getTime();
                String key = SESSION_CACHE_PREFIX + startTime + "_" + endTime;
                Boolean hasKey = stringRedisTemplate.hasKey(key);
                if(!hasKey){
                    // 获取所有商品id
                    List<String> collect = session.getRelationSkus().stream().map(item -> item.getPromotionSessionId() + "-" + item.getSkuId()).collect(Collectors.toList());
                    // 缓存活动信息
                    stringRedisTemplate.opsForList().leftPushAll(key, collect);
                }
            });
        }
    }

    private void saveSessionSkuInfo(List<SeckillSessionsWithSkus> sessions){
        if(sessions != null){
            sessions.stream().forEach(session -> {
                BoundHashOperations<String, Object, Object> ops = stringRedisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
                session.getRelationSkus().stream().forEach(seckillSkuVo -> {
                    // 1.商品的随机码
                    String randomCode = UUID.randomUUID().toString().replace("-", "");
                    if(!ops.hasKey(seckillSkuVo.getPromotionSessionId() + "-" + seckillSkuVo.getSkuId())){
                        // 2.缓存商品
                        SeckillSkuRedisTo redisTo = new SeckillSkuRedisTo();
                        BeanUtils.copyProperties(seckillSkuVo, redisTo);
                        // 3.sku的基本数据 sku的秒杀信息
                        R info = productFeignService.skuInfo(seckillSkuVo.getSkuId());
                        if(info.getCode() == 0){
                            SkuInfoVo skuInfo = info.getData("skuInfo", new TypeReference<SkuInfoVo>() {});
                            redisTo.setSkuInfoVo(skuInfo);
                        }
                        // 4.设置当前商品的秒杀信息
                        redisTo.setStartTime(session.getStartTime().getTime());
                        redisTo.setEndTime(session.getEndTime().getTime());

                        redisTo.setRandomCode(randomCode);

                        ops.put(seckillSkuVo.getPromotionSessionId() + "-" + seckillSkuVo.getSkuId(), JSON.toJSONString(redisTo));
                        // 如果当前这个场次的商品库存已经上架就不需要上架了

                        // 5.使用库存作为分布式信号量  限流
                        RSemaphore semaphore = redissonClient.getSemaphore(SKUSTOCK_SEMAPHONE + randomCode);
                        semaphore.trySetPermits(seckillSkuVo.getSeckillCount().intValue());
                    }
                });
            });
        }
    }
}
