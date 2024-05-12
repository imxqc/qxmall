package com.cqx.qxmall.order.listener;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.cqx.qxmall.order.config.AlipayTemplate;
import com.cqx.qxmall.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@Slf4j
@RestController
public class OrderPayedListener {

	@Autowired
	private OrderService orderService;

	@Autowired
	private AlipayTemplate alipayTemplate;

	/**
	 * 支付宝根据AlipayTemplate中的 notify_url 的地址向项目的服务器发送请求
	 * 服务器需返回success告诉支付宝接收成功
	 * todo notify_url中的域名要改为支付宝可以访问的域名  内网穿透
	 * @param request
	 * @return
	 */
	@PostMapping("/payed/notify")
	public String handleAlipayed(HttpServletRequest request){
		Map<String, String[]> map = request.getParameterMap();

		System.out.println("支付宝已通知,数据:"+ map);

		return "success";
	}
}
