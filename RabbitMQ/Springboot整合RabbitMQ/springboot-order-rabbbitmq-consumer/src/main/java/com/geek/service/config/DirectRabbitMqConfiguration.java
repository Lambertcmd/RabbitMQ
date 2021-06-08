package com.geek.service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName DirectRabbitMqConfiguration
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/2 14:04
 * @Version 1.0
 **/
@Configuration
public class DirectRabbitMqConfiguration {
    //1:声明注册direct模式的交换机
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("direct_order_exchange");
    }

    //2:声明队列
    @Bean
    public Queue directDuanxinQueue(){
        return new Queue("duanxin.direct.queue",true);
    }
    @Bean
    public Queue directEmailQueue(){
        return new Queue("email.direct.queue",true);
    }
    @Bean
    public Queue directSmsQueue(){
        return new Queue("sms.direct.queue",true);
    }


    //3:绑定交换和队列的关系
    @Bean
    public Binding directDuanxinBinding(){
        return BindingBuilder.bind(directDuanxinQueue()).to(directExchange()).with("duanxin");
    }

    @Bean
    public Binding directEmailBinding(){
        return BindingBuilder.bind(directEmailQueue()).to(directExchange()).with("email");
    }

    @Bean
    public Binding directSmsBinding(){
        return BindingBuilder.bind(directSmsQueue()).to(directExchange()).with("sms");
    }
}
