package com.geek.rabbitmq.direct;

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
    private static Runnable runnable = new Runnable() {
        public void run() {
            //1:创建连接工厂
            ConnectionFactory connectionFactory = new ConnectionFactory();
            //2:设置连接属性
            connectionFactory.setHost("10.1.53.169");
            connectionFactory.setPort(5672);
            connectionFactory.setUsername("admin");
            connectionFactory.setPassword("admin");
            connectionFactory.setVirtualHost("/");

            //获取队列的名称
            final String queueName = Thread.currentThread().getName();
            Connection connection = null;
            Channel channel = null;
            try {
                //3:从连接工厂中获取连接
                connection = connectionFactory.newConnection("生产者");
                //4:通过连接获取通道Channel
                channel = connection.createChannel();

                //5:定义接受信息的回调
                Channel finalChannel = channel;
                finalChannel.basicConsume(queueName, true, new DeliverCallback() {
                    public void handle(String s, Delivery delivery) throws IOException {
                        System.out.println(delivery.getEnvelope().getDeliveryTag());
                        System.out.println(queueName + "收到的消息是:"+new String(delivery.getBody(),"UTF-8"));
                    }
                }, new CancelCallback() {
                    public void handle(String s) throws IOException {

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
    };

    public static void main(String[] args) {
        new Thread(runnable,"queue1").start();
        new Thread(runnable,"queue2").start();
        new Thread(runnable,"queue3").start();
    }
}
