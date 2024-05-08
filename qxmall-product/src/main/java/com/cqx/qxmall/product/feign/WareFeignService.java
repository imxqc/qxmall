package com.cqx.qxmall.product.feign;

import com.cqx.common.to.es.SkuHasStockVo;
import com.cqx.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/4/21 22:03
 */
@FeignClient("qxmall-ware")
public interface WareFeignService {
    @PostMapping("/ware/waresku/hasStock")
    public R getSkuHasStock(@RequestBody List<Long> skuIds);
}
