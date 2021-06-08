package com.geek;

import com.geek.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootOrderRabbitmqProducerApplicationTests {

    @Autowired
    private OrderService orderService;

    @Test
    void contextLoads() {
        orderService.makeOrder("1","1",12);
    }

    @Test
    void test() {
        orderService.makeOrderDirect("2","2",10);
    }

    @Test
    void tests() {
        orderService.makeOrderTopic("3","3",8);
    }


    @Test
    void testTtl(){
        orderService.makeOrderDirectTtl("4", "4", 6);
//        for (int i = 0; i < 12; i++) {
//
//        }

    }

    @Test
    void testTTL2(){
        orderService.makeOrderDirectTtlMessage("5", "5", 4);
    }
}
