package com.geek.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Tables;
import org.springframework.data.relational.core.sql.In;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ClassName Order
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/7 14:56
 * @Version 1.0
 **/
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`order`")
public class Order {

    @Id
    private String id;
    private Integer userId;
    private String orderContent;
    private LocalDateTime createTime;
}