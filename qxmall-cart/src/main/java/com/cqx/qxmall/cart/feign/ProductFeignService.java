package com.cqx.qxmall.cart.feign;

import com.cqx.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <p>Title: ProductFeignService</p>
 * Description：
 * date：2020/6/28 11:29
 */
@FeignClient("qxmall-product")
public interface ProductFeignService {

	@RequestMapping("/product/skuinfo/info/{skuId}")
	R SkuInfo(@PathVariable("skuId") Long skuId);

	@GetMapping("/product/skusaleattrvalue/stringlist/{skuId}")
	List<String> getSkuSaleAttrValues(@PathVariable("skuId") Long skuId);

	@GetMapping("/product/skuinfo/{skuId}/price")
	R getPrice(@PathVariable("skuId") Long skuId);
}
