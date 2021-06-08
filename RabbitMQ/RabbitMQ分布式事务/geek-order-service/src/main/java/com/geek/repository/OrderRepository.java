package com.geek.repository;

import com.geek.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName OrderRepository
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/7 15:37
 * @Version 1.0
 **/
public interface OrderRepository extends JpaRepository<Order, String> {
}
