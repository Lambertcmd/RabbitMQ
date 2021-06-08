package com.geek.service;

import com.geek.pojo.Order;
import com.geek.pojo.OrderMessage;
import com.geek.repository.OrderMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName OrderMessageService
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/7 20:01
 * @Version 1.0
 **/
@Service
public class OrderMessageService {
    @Autowired
    private OrderMessageRepository orderMessageRepository;

    //将订单数据保存到订单消息冗余表中。消息状态默认为0，未消费
    public void saveLocalMessage(Order order) {
        OrderMessage orderMessage = new OrderMessage(order.getId(), order.getUserId(), order.getOrderContent(), order.getCreateTime(), 0);
        orderMessageRepository.save(orderMessage);
    }

    public List<OrderMessage> findByStatus(Integer status){
        return orderMessageRepository.findByStatus(status);
    }
}