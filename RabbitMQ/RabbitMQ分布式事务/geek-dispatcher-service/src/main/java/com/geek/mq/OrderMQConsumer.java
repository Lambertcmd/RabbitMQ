package com.geek.mq;

import com.fasterxml.jackson.core.type.TypeReference;
import com.geek.model.Order;
import com.geek.service.DispatchService;
import com.geek.util.JsonUtil;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

/**
 * @ClassName OrderMQConsumer
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/8 14:18
 * @Version 1.0
 **/
@Service
public class OrderMQConsumer {

    @Autowired
    private DispatchService dispatchService;

    private int count = 1;

    //解决消息重试的集中方案：
    //1.控制重发的次数
    //2.try + catch + 手动ack
    //3.try + catch + 手动ack + 死信队列 + 人工干预
    @RabbitListener(queues = {"order.queue"})
    public void messageConsumer(String orderMsg, Channel channel, CorrelationData correlationData, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        try {
            // 1.获取消息队列的消息
            System.out.println("收到MQ的消息是：" + orderMsg + "，count = " + count++);
            // 2.获取订单服务的消息
            Order order = JsonUtil.jsonToObject(orderMsg, new TypeReference<Order>() {
            });
            // 3.获取订单id
            String orderId = order.getId();
            System.out.println("订单ID = " + orderId);
            System.out.println(1 / 0);
            // 4.派单处理
            dispatchService.dispatch(orderId);
            // 5.手动 ack 告诉 MQ 消息已经正常消费
            channel.basicAck(tag, false);
            System.out.println("派单成功");
        } catch (Exception e) {
            /**
             * @params1:消息的tag
             * @params2:是否多条处理
             * @params3:是否重发，false —> 不会重发，会把消息转移到死信队列中true的话会进行死循环的重发，建议如果使用true的话，不要加try/catch否则就会造成死循环。
             *
             */
            channel.basicNack(tag, false, false);
            System.out.println("发送消息时出错，派单失败，将消息移入死信队列");
        }
    }

}