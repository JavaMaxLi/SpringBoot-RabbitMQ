package com.lxf.springbootrabbitmq.direct;

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
        String content = "Hello RabbitMQ,I'm queue2";
        String queueName = "queue2";
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
           channel.queueDeclare(queueName,true,false,false,null);

            String exchangeName = "direct_order";
            String exchangeType = "direct";
            //定义交换机
            //@param1交换机名称
            //@param2交换机类型
            //@param3交换机是否持久化  持久化服务器重启不会丢失
    /*         channel.exchangeDeclare(exchangeName,exchangeType,true);
            //声明队列
            channel.queueDeclare("queue_order_01",true,false,false,null);
            channel.queueDeclare("queue_order_02",true,false,false,null);
            channel.queueDeclare("queue_order_03",true,false,false,null);
            //绑定队列
            channel.queueBind("queue_order_01",exchangeName,"course");
            channel.queueBind("queue_order_02",exchangeName,"user");
            channel.queueBind("queue_order_03",exchangeName,"work");
*/
            //使用默认交换机
            channel.basicPublish(exchangeName,"course",MessageProperties.PERSISTENT_TEXT_PLAIN,"hello,queue_order course".getBytes());
            channel.basicPublish(exchangeName,"user",MessageProperties.PERSISTENT_TEXT_PLAIN,"hello,queue_order user".getBytes());
      /*      channel.basicPublish(exchangeName,"work",MessageProperties.PERSISTENT_TEXT_PLAIN,"hello,queue_order work".getBytes());*/
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
