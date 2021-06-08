package com.geek.service;

import com.geek.pojo.Order;
import com.geek.pojo.OrderMessage;
import com.geek.repository.OrderMessageRepository;
import com.geek.repository.OrderRepository;
import com.geek.util.JsonUtil;
import jdk.internal.instrumentation.Logger;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * @ClassName OrderMQService
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/7 20:03
 * @Version 1.0
 **/
@Service
public class OrderMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderMessageRepository orderMessageRepository;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * 发布者确认的回调。
     * @PostConstruct 该注解用来修饰一个非静态的 void() 方法，被该注解修饰的方法会在服务器加载Servlet的时候运行，并且只会被运行一次。
     * @param correlationData 回调的相关数据。
     * @param ack             ack为真，nack为假
     * @param cause           一个可选的原因，用于nack，如果可用，否则为空。
     */
    @PostConstruct
    public void regCallback() {
        // 消息发送成功以后，给生产者的消息回执，来确保生产者的可靠性
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("cause: " + cause);
            // 如果 ack 为 true 代表消息已经收到
            String orderId = correlationData.getId();

            if (!ack) {
                System.out.println("MQ队列应答失败，orderId是：" + orderId);
                return;
            }

            try {
                Integer count = orderMessageRepository.updateStatus(1, orderId);
                if (count == 1) {
                    System.out.println("本地消息状态修改成功，消息成功投递到消息队列中...");
                }
            } catch (Exception e) {
                System.out.println("本地消息状态修改失败，出现异常：" + e.getMessage());
            }


        });
    }


    //事务：整体成功 整体失败
    @Transactional(rollbackFor = Exception.class)
    public void create(Order order) throws Exception {
        // 1.订单信息----插入订单数据库，订单数据库事务
        orderRepository.save(order);
        System.out.println("保存订单数据");
        // 2.发送订单编号到交换机
        sendMessage(order);
    }


    public void sendMessage(Order order) {
        rabbitTemplate.convertAndSend("order_fanout_exchange", "", JsonUtil.objectToJson(order), new CorrelationData(order.getId()));
    }

    public void sendMessage(OrderMessage orderMessage) {
        rabbitTemplate.convertAndSend("order_fanout_exchange", "", JsonUtil.objectToJson(orderMessage), new CorrelationData(orderMessage.getId()));
    }
}