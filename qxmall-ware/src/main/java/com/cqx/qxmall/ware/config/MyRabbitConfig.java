package com.cqx.qxmall.ware.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MyRabbitConfig {

	//需要有消费者监听才能创建交换机和队列
//	@RabbitListener(queues = "stock.delay.queue")
//	public void tt(Message msg){
//
//	}

	/**
	 * 消息转换器
	 */
	@Bean
	public MessageConverter messageConverter(){
		return new Jackson2JsonMessageConverter();
	}

	/**
	 * String name, boolean durable, boolean autoDelete, Map<String, Object> arguments
	 */
	@Bean
	public Exchange stockEventExchange(){

		return new TopicExchange("stock-event-exchange", true, false);
	}

	/**
	 * String name, boolean durable, boolean exclusive, boolean autoDelete, @Nullable Map<String, Object> arguments
	 */
	@Bean
	public Queue stockReleaseStockQueue(){
		return new Queue("stock.release.stock.queue", true, false, false);
	}

	@Bean
	public Queue stockDelayQueue(){

		Map<String, Object> args = new HashMap<>();
		// 信死了 交给 [stock-event-exchange] 交换机
		args.put("x-dead-letter-exchange","stock-event-exchange");
		// 死信路由
		args.put("x-dead-letter-routing-key","stock.release");
		args.put("x-message-ttl", 120000);

		return new Queue("stock.delay.queue", true, false, false, args);
	}

	/**
	 * 普通队列的绑定关系
	 * String destination, DestinationType destinationType, String exchange, String routingKey, @Nullable Map<String, Object> arguments
	 */
	@Bean
	public Binding stockReleaseBinding(){

		return new Binding("stock.release.stock.queue",Binding.DestinationType.QUEUE,"stock-event-exchange","stock.release.#" , null);
	}

	/**
	 * 延时队列的绑定关系
	 * String destination, DestinationType destinationType, String exchange, String routingKey, @Nullable Map<String, Object> arguments
	 */
	@Bean
	public Binding stockLockedBinding(){

		return new Binding("stock.delay.queue", Binding.DestinationType.QUEUE, "stock-event-exchange", "stock.locked", null);
	}
}
