package com.cqx.qxmall.ware.feign;

import com.cqx.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>Title: MemberFeignService</p>
 * Description：
 */
@FeignClient("qxmall-member")
public interface MemberFeignService {

	@RequestMapping("/member/memberreceiveaddress/info/{id}")
	R addrInfo(@PathVariable("id") Long id);
}
