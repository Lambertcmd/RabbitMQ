package com.geek.controller;

import com.geek.service.DispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DispatchController
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/7 15:55
 * @Version 1.0
 **/
@RestController
@RequestMapping("/dispatch")
public class DispatchController {

    @Autowired
    private DispatchService dispatchService;

    @GetMapping("/order")
    public String lock(String orderId) throws Exception {
        dispatchService.dispatch(orderId);//将外卖订单分配给骑手
        return "success";
    }
}
