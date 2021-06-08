package com.geek.service;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @ClassName OrderService
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/2 9:15
 * @Version 1.0
 **/
@Service
public class OrderService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 模拟用户下单
     * @param userid
     * @param productid
     * @param num
     */
    public void makeOrder(String userid,String productid,Integer num){
        //1:根据商品id查询库存是否充足
        //2:保存订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功：" + orderId);
        //3:通过MQ来完成消息的分发
        /**
         * @params1 交换机
         * @params2 路由key  若交换机值为""该参数为queue
         * @params3 消息内容
         */
        String exchangeName = "fanout_order_exchange";
        //fanout模式没有路由key
        String routingKey = "";
        rabbitTemplate.convertAndSend(exchangeName,routingKey,orderId);
    }

    /**
     * 模拟用户下单
     * @param userid
     * @param productid
     * @param num
     */
    public void makeOrderDirect(String userid,String productid,Integer num){
        //1:根据商品id查询库存是否充足
        //2:保存订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功：" + orderId);
        //3:通过MQ来完成消息的分发
        /**
         * @params1 交换机
         * @params2 路由key  若交换机值为""该参数为queue
         * @params3 消息内容
         */
        String exchangeName = "direct_order_exchange";

        rabbitTemplate.convertAndSend(exchangeName,"email",orderId);
        rabbitTemplate.convertAndSend(exchangeName,"duanxin",orderId);
    }

    /**
     * 模拟用户下单
     * @param userid
     * @param productid
     * @param num
     */
    public void makeOrderTopic(String userid,String productid,Integer num){
        //1:根据商品id查询库存是否充足
        //2:保存订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功：" + orderId);
        //3:通过MQ来完成消息的分发
        /**
         * @params1 交换机
         * @params2 路由key  若交换机值为""该参数为queue
         * @params3 消息内容
         */
        String exchangeName = "topic_order_exchange";
        /**
         * duanxin: #.duanxin.#
         * email:   *.email.#
         * sms:     com.#
         */
        String routingKey = "com.email.duanxin";
        rabbitTemplate.convertAndSend(exchangeName,routingKey,orderId);
    }


    /**
     * 模拟用户下单
     * @param userid
     * @param productid
     * @param num
     */
    public void makeOrderDirectTtl(String userid,String productid,Integer num){
        //1:根据商品id查询库存是否充足
        //2:保存订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功：" + orderId);
        //3:通过MQ来完成消息的分发
        /**
         * @params1 交换机
         * @params2 路由key  若交换机值为""该参数为queue
         * @params3 消息内容
         */
        String exchangeName = "ttl_direct_order_exchange";

        rabbitTemplate.convertAndSend(exchangeName,"ttl",orderId);
    }

    /**
     * 模拟用户下单
     * @param userid
     * @param productid
     * @param num
     */
    public void makeOrderDirectTtlMessage(String userid,String productid,Integer num){
        //1:根据商品id查询库存是否充足
        //2:保存订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功：" + orderId);
        //3:通过MQ来完成消息的分发
        /**
         * @params1 交换机
         * @params2 路由key  若交换机值为""该参数为queue
         * @params3 消息内容
         */
        String exchangeName = "ttl_direct_order_exchange";
        String routingKey = "ttlmessage";
        //给消息设置过期时间
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration("5000");
                message.getMessageProperties().setContentEncoding("UTF-8");
                return message;
            }
        };
        rabbitTemplate.convertAndSend(exchangeName,routingKey,orderId,messagePostProcessor);
    }


}
