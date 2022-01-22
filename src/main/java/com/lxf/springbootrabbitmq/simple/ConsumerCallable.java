package com.lxf.springbootrabbitmq.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * @author LiXiaoFeng
 * @date 2022年01月22日 15:13
 */
public class ConsumerCallable implements Callable<String> {
    String result = "";
    String queueName = "";


    public ConsumerCallable(String queueName) {
        this.queueName = queueName;
    }

    @Override
    public String call() throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("101.43.13.93");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin123");
        connectionFactory.setVirtualHost("/");
        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.basicConsume(queueName,true, new DeliverCallback() {
                @Override
                public void handle(String s, Delivery delivery) throws IOException {
                    result = new String(delivery.getBody(),"UTF-8");
                    System.out.println("消费者接收到队列"+queueName+"的消息是："+new String(delivery.getBody(),"UTF-8"));
                }
            }, new CancelCallback() {
                @Override
                public void handle(String consumerTag) throws IOException {
                    System.out.println("出现异常");
                }
            });
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(channel!=null && channel.isOpen()) {
                    channel.close();
                }
                connection.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
