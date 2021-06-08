package com.geek.service.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @ClassName FanoutSMSConsumer
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/2 11:15
 * @Version 1.0
 **/
@RabbitListener(queues = "sms.direct.queue")
@Component
public class DirectSMSConsumer {
    @RabbitHandler
    public void receiveMessage(String message){
        System.out.println("SMS direct——-接收到了订单信息是：" + message);
    }
}
