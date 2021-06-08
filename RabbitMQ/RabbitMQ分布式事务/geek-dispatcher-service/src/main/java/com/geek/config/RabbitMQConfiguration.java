package com.geek.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RabbitMQConfiguration
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/8 15:34
 * @Version 1.0
 **/
@Configuration
public class RabbitMQConfiguration {

    /**
     * 声明注册fanout模式的交换机，作为死信交换机
     * @return FanoutExchange
     */
    @Bean
    public FanoutExchange deadExchange() {
        return new FanoutExchange("dead_order_fanout_exchange", true, false);
    }

    // 声明死信队列
    @Bean
    public Queue deadOrderQueue() {
        return new Queue("dead.order.queue", true);
    }


    // 完成绑定关系（队列和交换机）
    @Bean
    public Binding bindingDeadOrder() {
        return BindingBuilder.bind(deadOrderQueue()).to(deadExchange());
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("order_fanout_exchange", true, false);
    }

    @Bean
    public Queue orderQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", "dead_order_fanout_exchange");
        return new Queue("order.queue", true, false, false, args);
    }

    @Bean
    public Binding bindingOrder() {
        return BindingBuilder.bind(orderQueue()).to(fanoutExchange());
    }

}