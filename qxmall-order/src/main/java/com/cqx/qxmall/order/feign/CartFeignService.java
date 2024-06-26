package com.cqx.qxmall.order.feign;

import com.cqx.qxmall.order.vo.OrderItemVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * <p>Title: CartFeignService</p>
 * Description：
 * date：2020/6/30 18:08
 */
@FeignClient("qxmall-cart")
public interface CartFeignService {

	@GetMapping("/currentUserCartItems")
	List<OrderItemVo> getCurrentUserCartItems();

}
