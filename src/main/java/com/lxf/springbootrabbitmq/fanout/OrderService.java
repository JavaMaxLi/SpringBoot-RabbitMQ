package com.lxf.springbootrabbitmq.fanout;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author LiXiaoFeng
 * @date 2022年01月22日 17:42
 */
@Component
public class OrderService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public String createOrder() {
        String orderId = UUID.randomUUID().toString();
        String exchangeName = "fanout_order_exchange";
        rabbitTemplate.convertAndSend(exchangeName,"",orderId);
        return orderId;
    }
}
