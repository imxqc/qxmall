package com.cqx.qxmall.cart.service;

import com.cqx.qxmall.cart.vo.Cart;
import com.cqx.qxmall.cart.vo.CartItem;

import java.util.List;
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

    CartItem getCartItem(Long skuId);

    Cart getCart() throws ExecutionException, InterruptedException;

    /**
     * 清空购物车
     */
    void clearCart(String cartKey);

    void checkItem(Long skuId, Integer check);

    void changeItemCount(Long skuId, Integer num);

    void deleteItem(Long skuId);

    List<CartItem> getUserCartItems();

}
