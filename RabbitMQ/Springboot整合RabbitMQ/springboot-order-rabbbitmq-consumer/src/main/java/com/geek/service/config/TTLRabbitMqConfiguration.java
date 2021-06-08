package com.geek.service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TTLRabbitMqConfiguration
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/3 9:04
 * @Version 1.0
 **/
@Configuration
public class TTLRabbitMqConfiguration {
    //1:声明注册direct模式的交换机
    @Bean
    public DirectExchange ttlDirectExchange(){
        return new DirectExchange("ttl_direct_order_exchange");
    }
    //2:声明队列
    //  声明队列的过期时间
    @Bean
    public Queue ttlDirectSMSQueue(){
        //设置过期时间
        Map<String,Object> args = new HashMap<>();
        args.put("x-message-ttl", 5000);//时间必须是int类型
        return new Queue("ttl.direct.queue",true,false,false,args);
    }

    //3:声明队列和交换机的绑定关系
    @Bean
    public Binding directsmsBinding(){
        return BindingBuilder.bind(ttlDirectSMSQueue()).to(ttlDirectExchange()).with("ttl");
    }
}
