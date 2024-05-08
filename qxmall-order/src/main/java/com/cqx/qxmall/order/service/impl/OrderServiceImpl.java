package com.cqx.qxmall.order.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.cqx.common.utils.R;
import com.cqx.common.vo.MemberRsepVo;
import com.cqx.qxmall.order.constant.OrderConstant;
import com.cqx.qxmall.order.feign.CartFeignService;
import com.cqx.qxmall.order.feign.MemberFeignService;
import com.cqx.qxmall.order.feign.WmsFeignService;
import com.cqx.qxmall.order.interceptor.LoginUserInterceptor;
import com.cqx.qxmall.order.vo.MemberAddressVo;
import com.cqx.qxmall.order.vo.OrderConfirmVo;
import com.cqx.qxmall.order.vo.OrderItemVo;
import com.cqx.qxmall.order.vo.SkuStockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqx.common.utils.PageUtils;
import com.cqx.common.utils.Query;

import com.cqx.qxmall.order.dao.OrderDao;
import com.cqx.qxmall.order.entity.OrderEntity;
import com.cqx.qxmall.order.service.OrderService;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {
    @Autowired
    private MemberFeignService memberFeignService;

    @Autowired
    private CartFeignService cartFeignService;

    @Autowired
    private ThreadPoolExecutor executor;

    @Autowired
    private WmsFeignService wmsFeignService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException {
        OrderConfirmVo confirmVo = new OrderConfirmVo();
        MemberRsepVo memberRespVo = LoginUserInterceptor.loginUser.get();

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        CompletableFuture<Void> addressFuture = CompletableFuture.runAsync(() -> {
            //异步让不同线程获取主线程的requestAttributes,解决上下文丢失问题
            RequestContextHolder.setRequestAttributes(requestAttributes);
            //远程获取地址列表
            List<MemberAddressVo> address = memberFeignService.getAddress(memberRespVo.getId());
            confirmVo.setAddress(address);
        }, executor);


        CompletableFuture<Void> cartFuture = CompletableFuture.runAsync(() -> {
            //异步让不同线程获取主线程的requestAttributes,解决上下文丢失问题
            RequestContextHolder.setRequestAttributes(requestAttributes);
            //获取购物车选中的购物项
            List<OrderItemVo> items = cartFeignService.getCurrentUserCartItems();
            confirmVo.setItems(items);
        }, executor).thenRunAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<OrderItemVo> items = confirmVo.getItems();
            // 获取所有商品的id
            List<Long> collect = items.stream().map(item -> item.getSkuId()).collect(Collectors.toList());

            R hasStock = wmsFeignService.getSkuHasStock(collect);
            List<SkuStockVo> data = hasStock.getData(new TypeReference<List<SkuStockVo>>() {
            });
            if (data != null) {
                // 各个商品id 与 他们库存状态的映射
                Map<Long, Boolean> stocks = data.stream().collect(Collectors.toMap(SkuStockVo::getSkuId, SkuStockVo::getHasStock));
                confirmVo.setStocks(stocks);
            }
        }, executor);

        //查询积分
        Integer integration = memberRespVo.getIntegration();
        confirmVo.setIntegration(integration);

        //其他数据类内自动计算

        //todo 防重令牌
        String token = UUID.randomUUID().toString().replace("-", "");
        confirmVo.setOrderToken(token);
        stringRedisTemplate.opsForValue().set(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberRespVo.getId(), token, 10, TimeUnit.MINUTES);

        CompletableFuture.allOf(addressFuture, cartFuture).get();

        return confirmVo;
    }

}