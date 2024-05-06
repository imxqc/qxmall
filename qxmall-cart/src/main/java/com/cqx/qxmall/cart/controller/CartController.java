package com.cqx.qxmall.cart.controller;

import com.cqx.common.constant.AuthServerConstant;
import com.cqx.qxmall.cart.interceptor.CartInterceptor;
import com.cqx.qxmall.cart.service.CartService;
import com.cqx.qxmall.cart.vo.CartItem;
import com.cqx.qxmall.cart.vo.UserInfoTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ExecutionException;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/5/6 21:32
 */
@Controller
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;


    /**
     * 添加商品到购物车
     * 	RedirectAttributes: 会自动将数据添加到url后面
     */
    @GetMapping("/addToCart")
    public String addToCart(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num, RedirectAttributes redirectAttributes, Model model) throws ExecutionException, InterruptedException {

        CartItem cartItem = cartService.addToCart(skuId, num);

        model.addAttribute("item",cartItem);
        redirectAttributes.addAttribute("skuId", skuId);
        // 重定向到成功页面
        return "success";
    }


    /**
     * 浏览器有一个名为user-key的cookie, 用于标识用户身份,一个月后过期
     * 第一次使用购物车功能会创建一个user-key, 浏览器会保存该cookie,每次访问都会带上该cookie
     *
     * @param session
     * @return
     */
    @GetMapping("/cart.html")
    public String cartListPage(HttpSession session) {
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        System.out.println("userInfoTo = " + userInfoTo);

        return "cartList";
    }
}
