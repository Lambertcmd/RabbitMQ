package com.geek.repository;

import com.geek.pojo.OrderMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName OrderMessageRepository
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/7 20:00
 * @Version 1.0
 **/
public interface OrderMessageRepository extends JpaRepository<OrderMessage, String> {
    @Transactional
    @Modifying
    @Query(value = "update order_message set status = ?1 where id = ?2", nativeQuery = true)
    Integer updateStatus(Integer status, String orderId);

    List<OrderMessage> findByStatus(Integer status);
}