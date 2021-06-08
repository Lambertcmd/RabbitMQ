package com.geek;

import com.geek.pojo.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
class GeekOrderServiceApplicationTests {

	@Test
	void contextLoads() {
	}


	@Autowired
	private OrderService orderService;

	@Test
	void orderCreated() throws Exception {
		String orderId = UUID.randomUUID().toString().replaceAll("-", "");
		Order order = new Order();
		order.setId(orderId);
		order.setOrderContent("买了一栋别墅");
		order.setUserId(666);
		order.setCreateTime(LocalDateTime.now());

		orderService.create(order);
		System.out.println("订单创建成功！");
	}

}
