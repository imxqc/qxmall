package com.cqx.qxmall.order.web;

import com.cqx.qxmall.order.service.OrderService;
import com.cqx.qxmall.order.vo.OrderConfirmVo;
import com.cqx.qxmall.order.vo.OrderSubmitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.ExecutionException;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/5/7 21:58
 */
@Controller
public class OrderWebController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/toTrade")
    public String toTrade(Model model) throws ExecutionException, InterruptedException {
        OrderConfirmVo confirmVo = orderService.confirmOrder();

        model.addAttribute("orderConfirmData", confirmVo);
        return "confirm";
    }


    /**
     * 下单功能
     * @param submitVo
     * @param model
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/submitOrder")
    public String submitOrder(OrderSubmitVo submitVo, Model model, RedirectAttributes redirectAttributes){

        return null;
    }
}
