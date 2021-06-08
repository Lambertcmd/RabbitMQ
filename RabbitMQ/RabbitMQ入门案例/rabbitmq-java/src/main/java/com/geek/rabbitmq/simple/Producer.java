package com.geek.rabbitmq.simple;

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
            String queueName = "queue1";
            /**
             * @params1 队列的名字
             * @params2 是否要持久化durable=false,所谓消息是否存盘,如果false是非持久化 true是持久化
             *          非持久化会存盘吗，会存盘，但是随着重启服务器会丢失。持久化时重启服务器队列仍存在
             * @params3 排他性，是否独占独立
             * @params4 是否自动删除，随着最后一个消费者消息消费完毕以后是否把队列删除
             * @params5 携带附属参数
             */
            channel.queueDeclare(queueName, false, false, false, null);
            //5:准备消息内容
            String message = "Hello RabbitMQ";
            //6:发送消息给队列queue
            /**
             * @params1:交换机
             * @params2:队列、路由key
             * @params3:消息状态控制
             * @params4:消息的内容
             * //面试题：可以存在没有交换机的队列吗？不可以，虽然没有指定交换机但是一定会有存在一个默认的交换机
             */
            channel.basicPublish("", queueName, null, message.getBytes());
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
