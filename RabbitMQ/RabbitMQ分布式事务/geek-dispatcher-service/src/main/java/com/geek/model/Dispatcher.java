package com.geek.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @ClassName Dispatcher
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/7 15:41
 * @Version 1.0
 **/
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dispatcher {
    @Id
    private String id;

    private String orderId;

    private Integer status;

    private String orderContent;

    private Integer userId;

    private LocalDateTime createTime;
}