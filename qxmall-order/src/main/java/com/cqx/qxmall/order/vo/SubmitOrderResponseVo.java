package com.cqx.qxmall.order.vo;

import com.cqx.qxmall.order.entity.OrderEntity;
import lombok.Data;

/**
 * <p>Title: SubmitOrderResponseVo</p>
 * Description：
 * date：2020/7/1 22:50
 */
@Data
public class SubmitOrderResponseVo {

	private OrderEntity orderEntity;

	/**
	 * 错误状态码： 0----成功
	 */
	private Integer code;
}
