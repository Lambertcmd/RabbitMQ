package com.geek.repository;


import com.geek.model.Dispatcher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName DispatcherRepository
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/7 15:43
 * @Version 1.0
 **/
public interface DispatchRepository extends JpaRepository<Dispatcher, String> {
}
