package com.cqx.qxmall.product.web;

import com.cqx.qxmall.product.service.SkuInfoService;
import com.cqx.qxmall.product.vo.SkuItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/4/28 21:28
 */
@Controller
public class ItemController {
    @Autowired
    SkuInfoService skuInfoService;

    /**
     * 展示当前sku详情
     *
     * @param skuId
     * @return
     */
    @GetMapping("/{skuId}.html")
    public String skuItem(@PathVariable("skuId") Long skuId) {

        System.out.println("准备查询: " + skuId);
        SkuItemVo vo  = skuInfoService.item(skuId);

        return "item";
    }

}
