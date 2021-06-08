package com.geek.service;

import com.geek.model.Dispatcher;
import com.geek.repository.DispatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @ClassName DispatchService
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/7 15:45
 * @Version 1.0
 **/
@Service
public class DispatchService {

    @Autowired
    private DispatchRepository dispatchRepository;

    public void dispatch(String orderId) throws Exception {
        Dispatcher dispatcher = new Dispatcher(UUID.randomUUID().toString().replaceAll("-", ""), orderId, 0, "买了一栋别墅", 666, LocalDateTime.now());

        Dispatcher result = dispatchRepository.save(dispatcher);
        if (result == null) {
            throw new Exception("运单创建失败！原因【操作数据库失败】");
        }
    }
}