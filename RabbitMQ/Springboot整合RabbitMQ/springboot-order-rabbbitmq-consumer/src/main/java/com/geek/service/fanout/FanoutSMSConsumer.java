package com.geek.service.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @ClassName FanoutSMSConsumer
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/2 11:15
 * @Version 1.0
 **/
@RabbitListener(queues = "sms.fanout.queue")
@Service
public class FanoutSMSConsumer {
    @RabbitHandler
    public void receiveMessage(String message){
        System.out.println("SMS fanout——-接收到了订单信息是：" + message);
    }
}
