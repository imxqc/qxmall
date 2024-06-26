package com.cqx.qxmall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqx.common.to.mq.SecKillOrderTo;
import com.cqx.common.utils.PageUtils;
import com.cqx.qxmall.order.entity.OrderEntity;
import com.cqx.qxmall.order.vo.OrderConfirmVo;
import com.cqx.qxmall.order.vo.OrderSubmitVo;
import com.cqx.qxmall.order.vo.PayVo;
import com.cqx.qxmall.order.vo.SubmitOrderResponseVo;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * ����
 *
 * @author cqx
 * @email mnixqc@163.com
 * @date 2024-04-13 21:51:42
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException;

    SubmitOrderResponseVo submitOrder(OrderSubmitVo submitVo);

    OrderEntity getOrderByOrderSn(String orderSn);

    void closeOrder(OrderEntity entity);

    PayVo getOrderPay(String orderSn);

    PageUtils queryPageWithItem(Map<String, Object> params);

    void createSecKillOrder(SecKillOrderTo secKillOrderTo);
}

