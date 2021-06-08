package com.geek.service.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @ClassName EmailConsumer
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/2 11:14
 * @Version 1.0
 **/
@RabbitListener(queues = "email.fanout.queue")
@Service
public class FanoutEmailConsumer {

    @RabbitHandler
    public void receiveMessage(String message){
        System.out.println("Email fanout——-接收到了订单信息是：" + message);
    }
}
