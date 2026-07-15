package com.tnf.order_service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tnf.order_service.entities.Order;
import com.tnf.order_service.services.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping
	public ResponseEntity<Order> placeOrder(@RequestBody Order order){
		Order savedOrder = orderService.placeOrder(order);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
	}
	
	@GetMapping
	public ResponseEntity<List<Order>> getAllOrders() {
		List<Order> orders = orderService.getAllOrders();
		if(orders.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(orders);
	}

}
