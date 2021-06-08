package com.geek.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @ClassName OrderMessage
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/7 19:59
 * @Version 1.0
 **/
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderMessage {
    @Id
    private String id;
    private Integer userId;
    private String orderContent;
    private LocalDateTime createTime;

    private Integer status; //消息发送成功状态，是否已被rabbitmq接收。
}