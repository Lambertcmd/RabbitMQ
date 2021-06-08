package com.geek.service.topic;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @ClassName FanoutDuanxinConsumer
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/2 11:15
 * @Version 1.0
 **/
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "duanxin.topic.queue",durable = "true",autoDelete = "false"),
        exchange = @Exchange(value = "topic_order_exchange",type = ExchangeTypes.TOPIC),
        key = "#.duanxin.#"
))
@Service
public class TopicDuanxinConsumer {

    @RabbitHandler
    public void receiveMessage(String message){
        System.out.println("duanxin topic——-接收到了订单信息是：" + message);
    }
}
