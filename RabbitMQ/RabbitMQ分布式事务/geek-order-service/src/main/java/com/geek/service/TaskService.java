package com.geek.service;

import com.geek.pojo.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName TaskService
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/7 20:07
 * @Version 1.0
 **/
@Slf4j
@EnableScheduling
@Component
public class TaskService {

    @Autowired
    private OrderMessageService orderMessageService;

    @Autowired
    private OrderMQService orderMQService;

    @Scheduled(cron = "0 */1 * * * ?")
    public void sendMessage() {
        // 把消息为 0 的状态消息查询出来，重新投递到MQ中。
        List<OrderMessage> list = orderMessageService.findByStatus(0);
        log.info("检查未发送的消息，进行重新发送，需要重发的数量 = " + list.size());

        // 3.通过http接口发送订单消息到运单系统
        list.forEach(order -> orderMQService.sendMessage(order));
    }

}