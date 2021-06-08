package com.geek.service.topic;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @ClassName FanoutSMSConsumer
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/2 11:15
 * @Version 1.0
 **/
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "sms.topic.queue",durable = "true",autoDelete = "false"),
        exchange = @Exchange(value = "topic_order_exchange",type = ExchangeTypes.TOPIC),
        key = "com.#"
))
@Service
public class TopicSMSConsumer {
    @RabbitHandler
    public void receiveMessage(String message){
        System.out.println("SMS topic——-接收到了订单信息是：" + message);
    }
}
