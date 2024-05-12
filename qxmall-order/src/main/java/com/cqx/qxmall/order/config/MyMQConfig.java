package com.cqx.qxmall.order.config;

import com.cqx.qxmall.order.entity.OrderEntity;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/5/11 18:25
 */
@Configuration
public class MyMQConfig {


    //需要有消费者监听才能创建交换机和队列
//    @RabbitListener(queues = "order.release.order.queue")
//    public void TestListen(OrderEntity order, Message msg, Channel channel) throws IOException {
//        System.out.println("收到过期的订单,订单号:" + order.getOrderSn());
//        channel.basicAck(msg.getMessageProperties().getDeliveryTag(),false);
//    }

    /**
     * 延迟队列
     *
     * @return
     */
    @Bean
    public Queue OrderDelayQueue() {
        //设置信息过期时间 转发的死信队列以及routingKey
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "order-event-exchange");
        arguments.put("x-dead-letter-routing-key", "order.release.order");
        arguments.put("x-message-ttl", 60000);

        return new Queue("order.delay.queue", true, false, false, arguments);
    }

    /**
     * 消息过期后消息由死信交换机转发到的队列
     *
     * @return
     */
    @Bean
    public Queue OrderReleaseOrderQueue() {
        return new Queue("order.release.order.queue", true, false, false);
    }

    /**
     * 死信交换机
     *
     * @return
     */
    @Bean
    public Exchange OrderEventExchange() {
        return new TopicExchange("order-event-exchange", true, false);
    }

    /**
     * 声明延迟队列和交换机的绑定关系
     *
     * @return
     */
    @Bean
    public Binding OrderCreateBinding() {
        return new Binding("order.delay.queue", Binding.DestinationType.QUEUE,
                "order-event-exchange", "order.create.order", null);
    }

    /**
     * releaseQueue和交换机的绑定关系
     *
     * @return
     */
    @Bean
    public Binding OrderReleaseOrderBinding() {
        return new Binding("order.release.order.queue", Binding.DestinationType.QUEUE,
                "order-event-exchange", "order.release.order", null);
    }

    /**
     * 订单释放和库存释放的绑定
     * @return
     */
    @Bean
    public Binding orderReleaseOtherBinding() {
        return new Binding("stock.release.stock.queue", Binding.DestinationType.QUEUE,
                "order-event-exchange", "order.release.other.#", null);
    }


}
