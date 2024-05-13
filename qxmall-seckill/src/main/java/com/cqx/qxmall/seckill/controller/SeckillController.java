package com.cqx.qxmall.seckill.controller;

import com.cqx.common.utils.R;
import com.cqx.qxmall.seckill.service.SeckillService;
import com.cqx.qxmall.seckill.to.SeckillSkuRedisTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/5/13 18:36
 */
@RestController
public class SeckillController {
    @Autowired
    private SeckillService seckillService;

    @GetMapping("/currentSeckillSkus")
    public R getCurrentSeckillSkus(){
        List<SeckillSkuRedisTo> vos = seckillService.getCurrentSeckillSkus();
        return R.ok().setData(vos);
    }

    @GetMapping("/sku/seckill/{skuId}")
    public R getSkuSeckillInfo(@PathVariable("skuId") Long skuId){
        SeckillSkuRedisTo to = seckillService.getSkuSeckillInfo(skuId);
        return R.ok().setData(to);
    }

    @GetMapping("/kill")
    public String secKill(@RequestParam("killId") String killId, @RequestParam("key") String key, @RequestParam("num") Integer num, Model model){
//        String orderSn = seckillService.kill(killId,key,num);
        // 1.判断是否登录 
//        model.addAttribute("orderSn", orderSn);
        return "success";
    }
}
