package com.cqx.qxmall.order.to;

import com.cqx.qxmall.order.entity.OrderEntity;
import com.cqx.qxmall.order.entity.OrderItemEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>Title: OrderCreateTo</p>
 * Description：
 * date：2020/7/1 23:51
 */
@Data
public class OrderCreateTo {

	private OrderEntity order;

	private List<OrderItemEntity> orderItems;

	/**
	 * 订单计算的应付价格
	 */
	private BigDecimal payPrice;

	/**
	 * 运费
	 */
	private BigDecimal fare;
}
