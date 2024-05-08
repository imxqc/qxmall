package com.cqx.qxmall.cart.controller;

import com.cqx.common.constant.AuthServerConstant;
import com.cqx.qxmall.cart.interceptor.CartInterceptor;
import com.cqx.qxmall.cart.service.CartService;
import com.cqx.qxmall.cart.vo.Cart;
import com.cqx.qxmall.cart.vo.CartItem;
import com.cqx.qxmall.cart.vo.UserInfoTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
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
     * 获取当前购物车被选中的信息
     * @return
     */
    @ResponseBody
    @GetMapping("/currentUserCartItems")
    public List<CartItem> getCurrentUserCartItems(){

        return cartService.getUserCartItems();
    }

    /**
     * 删除某项购物车
     * @param skuId
     * @return
     */
    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam("skuId") Long skuId){
        cartService.deleteItem(skuId);
        return "redirect:http://cart.qxmall.com/cart.html";
    }


    /**
     * 购物车增减数量
     * @param skuId
     * @param num
     * @return
     */
    @GetMapping("/countItem")
    public String countItem(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num){
        cartService.changeItemCount(skuId, num);
        return "redirect:http://cart.qxmall.com/cart.html";
    }

    /**
     * 选中购物车功能
     * @param skuId
     * @param check
     * @return
     */
    @GetMapping("checkItem")
    public String checkItem(@RequestParam("skuId") Long skuId, @RequestParam("check") Integer check){
        cartService.checkItem(skuId, check);

        return "redirect:http://cart.qxmall.com/cart.html";
    }


    /**
     * 加入购物车后跳转到该页面 重复刷新购物车数据不会增加(通过id查询购物车数据)
     * @param skuId
     * @param model
     * @return
     */
    @GetMapping("/addToCartSuccess.html")
    public String addToCartSuccessPage(@RequestParam(value = "skuId",required = false) Long skuId, Model model){
        CartItem cartItem = null;
        // 然后在查一遍 购物车
        if(skuId == null){
            model.addAttribute("item", null);
        }else{
            try {
                cartItem = cartService.getCartItem(skuId);
            } catch (NumberFormatException e) {
                log.warn("恶意操作! 页面传来非法字符.");
            }
            model.addAttribute("item", cartItem);
        }

        return "success";
    }


    /**
     * 添加商品到购物车
     * RedirectAttributes: 会自动将数据添加到url后面
     */
    @GetMapping("/addToCart")
    public String addToCart(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num, RedirectAttributes redirectAttributes) throws ExecutionException, InterruptedException {

        CartItem cartItem = cartService.addToCart(skuId, num);

        redirectAttributes.addAttribute("skuId", skuId);
        // 重定向到成功页面
        return "redirect:http://cart.qxmall.com/addToCartSuccess.html";
    }


    /**
     * 浏览器有一个名为user-key的cookie, 用于标识用户身份,一个月后过期
     * 第一次使用购物车功能会创建一个user-key, 浏览器会保存该cookie,每次访问都会带上该cookie
     *
     * @return
     */
    @GetMapping("/cart.html")
    public String carListPage(Model model) throws ExecutionException, InterruptedException {
        Cart cart = cartService.getCart();
        model.addAttribute("cart", cart);
        return "cartList";
    }
}
