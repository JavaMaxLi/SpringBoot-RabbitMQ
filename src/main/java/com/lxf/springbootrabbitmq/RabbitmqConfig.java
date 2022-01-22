package com.lxf.springbootrabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LiXiaoFeng
 * @date 2022年01月22日 17:44
 */
@Configuration
public class RabbitmqConfig {


    /**
     * 创建fanout交换机
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
       return new FanoutExchange("fanout_order_exchange",false,false);
    }

    /**
     * 创建队列
     * @return
     */
    @Bean
    public Queue smsQueue() {
        return new Queue("sms.fanout.queue",true);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue("email.fanout.queue",true);
    }

    @Bean
    public Queue dxQueue() {
        return new Queue("duanxin.fanout.queue",true);
    }

    /**
     * 绑定队列和交换机
     * @return
     */
    @Bean
    public Binding smsBinding() {
        return BindingBuilder.bind(smsQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder.bind(emailQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding dxBinding() {
        return BindingBuilder.bind(dxQueue()).to(fanoutExchange());
    }
}
