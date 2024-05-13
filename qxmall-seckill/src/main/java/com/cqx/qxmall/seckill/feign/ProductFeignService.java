package com.cqx.qxmall.seckill.feign;

import com.cqx.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("qxmall-product")
public interface ProductFeignService {

	@RequestMapping("/product/skuinfo/info/{skuId}")
	R skuInfo(@PathVariable("skuId") Long skuId);
}
