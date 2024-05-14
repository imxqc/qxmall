package com.cqx.qxmall.seckill.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MyRabbitConfig {

	/**
	 * 消息转换器
	 */
	@Bean
	public MessageConverter messageConverter(){
		return new Jackson2JsonMessageConverter();
	}


}
