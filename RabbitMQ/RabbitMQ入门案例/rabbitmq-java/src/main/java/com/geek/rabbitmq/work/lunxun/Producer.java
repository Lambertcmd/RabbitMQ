package com.geek.rabbitmq.work.lunxun;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Producer
 * @Description 简单模式（轮询）生产者
 * @Author Lambert
 * @Date 2021/6/1 14:20
 * @Version 1.0
 **/
public class Producer {
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
            connection = connectionFactory.newConnection("生产者");
            //4:通过连接获取通道Channel
            channel = connection.createChannel();

            //5:准备发送消息的内容
            for (int i = 1; i <= 20; i++) {
                //消息的内容
                String msg = "消息" + i;
                //6:发送消息给交换机
                /**
                 * @params1:交换机 /   当该参数为""时 params2为队列名
                 * @params2:队列、路由key
                 * @params3:消息状态控制
                 * @params4:消息的内容
                 */
                channel.basicPublish("", "queue1", null, msg.getBytes());
                System.out.println("发送消息成功：消息"+i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送消息出现异常");
        }finally {
            //6:释放连接关闭通道
            if(channel != null && channel.isOpen()){
                try {
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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
