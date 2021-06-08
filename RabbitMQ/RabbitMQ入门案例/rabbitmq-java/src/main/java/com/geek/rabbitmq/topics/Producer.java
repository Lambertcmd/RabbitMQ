package com.geek.rabbitmq.topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 * @ClassName Producer
 * @Description TODO
 * @Author Lambert
 * @Date 2021/5/25 11:10
 * @Version 1.0
 **/
public class Producer {
    public static void main(String[] args) {
        //所有的中间件技术都是基于tcp/ip协议基础之上构建新型的协议规范，只不过rabbitmq遵循的是amqp
        //ip  port


        //1:创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //2:设置连接属性
        connectionFactory.setHost("10.1.53.169");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");

        Connection connection = null;
        Channel channel = null;
        try {
            //3:从连接工厂中获取连接
            connection = connectionFactory.newConnection("生产者");
            //4:通过连接获取通道Channel
            channel = connection.createChannel();
            //5:准备发送的消息内容
            String message = "Hello topic_exchange";

            //6:准备交换机
            String exchangeName = "topic_exchange";
            //7:定义路由key
            String routeKey = "com.order.user.***";
            //8:指定交换机的类型
            String type = "topic";

            //7:发送消息给中间件rabbitmq-server
            /**
             * @params1:交换机
             * @params2:队列、路由key
             * @params3:消息状态控制
             * @params4:消息的内容
             * //面试题：可以存在没有交换机的队列吗？不可以，虽然没有指定交换机但是一定会有存在一个默认的交换机
             */
            channel.basicPublish(exchangeName, routeKey, null, message.getBytes());
            System.out.println("消息发送成功");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //7:关闭连接
            if(channel != null && channel.isOpen()){
                try {
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //8:关闭通道
            if(connection != null && connection.isOpen()){
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
