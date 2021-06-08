package com.geek.rabbitmq.simple;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @ClassName Consumer
 * @Description TODO
 * @Author Lambert
 * @Date 2021/5/25 11:10
 * @Version 1.0
 **/
public class Consumer {
    public static void main(String[] args) {
        //所有的中间件技术都是基于tcp/ip协议基础之上构建新型的协议规范，只不过rabbitmq遵循的是amqp
        //ip  port


        //1:创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("10.1.53.169");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");

        Connection connection = null;
        Channel channel = null;
        try {
            //2:创建连接Connection
            connection = connectionFactory.newConnection("生产者");
            //3:通过连接获取通道Channel
            channel = connection.createChannel();
            //4:通过通道创建交换机，声明队列，绑定关系，路由key，发送消息和接收消息
            channel.basicConsume("queue1", true, new DeliverCallback() {
                public void handle(String s, Delivery message) throws IOException {
                    System.out.println("收到消息是：" + new String(message.getBody(), "UTF-8"));
                }
            }, new CancelCallback() {
                public void handle(String s) throws IOException {
                    System.out.println("接收消息失败");
                }
            });
            System.out.println("开始接收消息");
            System.in.read();
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
