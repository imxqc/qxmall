package com.cqx.qxmall.order.web;

import com.alipay.api.AlipayApiException;
import com.cqx.qxmall.order.config.AlipayTemplate;
import com.cqx.qxmall.order.service.OrderService;
import com.cqx.qxmall.order.vo.PayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class PayWebController {

	@Autowired
	private AlipayTemplate alipayTemplate;

	@Autowired
	private OrderService orderService;

	/**
	 * 告诉浏览器我们会返回一个html页面
	 */
	@ResponseBody
	@GetMapping(value = "/payOrder", produces = "text/html")
	public String payOrder(@RequestParam("orderSn") String orderSn) throws AlipayApiException {

		//获取支付订单数据
		PayVo payVo = orderService.getOrderPay(orderSn);

		//向沙箱放入支付数据
		String pay = alipayTemplate.pay(payVo);
		System.out.println(pay);

		return pay;
	}
}
