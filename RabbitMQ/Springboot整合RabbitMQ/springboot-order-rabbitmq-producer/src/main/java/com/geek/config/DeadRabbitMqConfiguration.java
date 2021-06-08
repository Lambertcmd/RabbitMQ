package com.geek.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName DeadRabbitMqConfiguration
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/3 10:47
 * @Version 1.0
 **/
@Configuration
public class DeadRabbitMqConfiguration {
    //1:声明direct模式的死信交换机
    @Bean
    public DirectExchange deadDirectExchange(){
        return new DirectExchange("dead_direct_exchange");
    }

    //2:声明死信队列
    @Bean
    public Queue deadQueue(){
        return new Queue("dead.direct.queue",true);
    }
    //3:绑定死信队列与死信交换机
    @Bean
    public Binding deadBinding(){
        return BindingBuilder.bind(deadQueue()).to(deadDirectExchange()).with("dead");
    }
}
