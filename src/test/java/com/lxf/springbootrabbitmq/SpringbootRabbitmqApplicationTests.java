package com.lxf.springbootrabbitmq;

import com.lxf.springbootrabbitmq.fanout.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootRabbitmqApplicationTests {

    @Autowired
    OrderService orderService;

    @Test
    void contextLoads() {
        System.out.println(orderService.createOrder());
    }

}
