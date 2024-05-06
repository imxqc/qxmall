package com.cqx.qxmall.cart.service;

import com.cqx.qxmall.cart.vo.CartItem;

import java.util.concurrent.ExecutionException;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/5/6 21:32
 */
public interface CartService {
    /**
     * 将商品添加到购物车
     */
    CartItem addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;
}
