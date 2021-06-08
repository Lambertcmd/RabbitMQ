package com.geek.controller;

import com.geek.pojo.Order;
import com.geek.service.OrderMQService;
import com.geek.service.OrderMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @ClassName OrderController
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/7 20:08
 * @Version 1.0
 **/
@RestController
public class OrderController {

    @Autowired
    private OrderMQService orderMQService;
    @Autowired
    private OrderMessageService orderMessageService;

    @GetMapping("/test")
    public void test() throws Exception {
        String orderId = UUID.randomUUID().toString().replaceAll("-", "");
        Order order = new Order();
        order.setId(orderId);
        order.setOrderContent("买了一栋别墅");
        order.setUserId(666);
        order.setCreateTime(LocalDateTime.now());
        orderMessageService.saveLocalMessage(order);
        orderMQService.create(order);

        System.out.println("订单创建成功！");
    }

}