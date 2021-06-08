package com.geek.rabbitmq.work.lunxun;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @ClassName Work2
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/1 14:32
 * @Version 1.0
 **/
public class Work2 {
    public static void main(String[] args) {
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
            connection = connectionFactory.newConnection("消费者-work2");
            //4:通过连接获取通道Channel
            channel = connection.createChannel();
            //5:定义接收消息的回调
            final Channel finalChannel = channel;
//            finalChannel.basicQos(1);
            /**
             * @params1:队列名
             * @params2:true:自动应答(轮询模式)/false:手动应答(公平分发模式）
             */
            finalChannel.basicConsume("queue1", true, new DeliverCallback() {
                public void handle(String s, Delivery delivery) throws IOException {
                    System.out.println("work2-收到的消息是" + new String(delivery.getBody(),"UTF-8"));
                }
            }, new CancelCallback() {
                public void handle(String s) throws IOException {
                    System.out.println("work2-接收消息失败");
                }
            });
            System.out.println("work2-开始接收消息");
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
