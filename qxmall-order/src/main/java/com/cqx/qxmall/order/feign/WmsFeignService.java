package com.cqx.qxmall.order.feign;

import com.cqx.common.utils.R;
import com.cqx.qxmall.order.vo.WareSkuLockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>Title: WmsFeignService</p>
 * Description：
 */
@FeignClient("qxmall-ware")
public interface WmsFeignService {

	@PostMapping("/ware/waresku/hasStock")
	R getSkuHasStock(@RequestBody List<Long> SkuIds);

	@GetMapping("/ware/wareinfo/fare")
	R getFare(@RequestParam("addrId") Long addrId);

	/**
	 * 锁定库存
	 */
	@PostMapping("/ware/waresku/lock/order")
	R orderLockStock(@RequestBody WareSkuLockVo vo);


}
