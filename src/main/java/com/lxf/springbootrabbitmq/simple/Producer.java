package com.lxf.springbootrabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置服务器ip,端口号,账号，密码
        connectionFactory.setHost("101.43.13.93");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin123");
        connectionFactory.setVirtualHost("/");
        String content = "Hello RabbitMQ,I am Learn";
        String queueName = "queue1";
        Connection connection = null;
        Channel channel = null;
        try{
            //获得连接
            connection = connectionFactory.newConnection();
            //创建channel
            channel = connection.createChannel();
            //声明队列
            // @param1 队列名称
            // @param2 是否持久化
            // @param3 排他性
            // @param4 是否自动删除，最后一个消费者消费完是否删除队列
            // @param5 附加参数
            channel.queueDeclare(queueName,false,false,false,null);

            channel.basicPublish("",queueName,MessageProperties.PERSISTENT_TEXT_PLAIN,content.getBytes());
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (channel!=null && channel.isOpen()) {
                    channel.close();
                }
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
