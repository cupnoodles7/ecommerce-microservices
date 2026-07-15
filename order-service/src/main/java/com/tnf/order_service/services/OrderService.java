package com.tnf.order_service.services;
 
 
import java.math.BigDecimal;

import java.time.LocalDateTime;

import java.util.List;
 
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.web.reactive.function.client.WebClientResponseException;
 
import com.tnf.order_service.entities.Order;

import com.tnf.order_service.entities.PaymentDTO;

import com.tnf.order_service.entities.ProductDto;

import com.tnf.order_service.repositories.OrderRepository;

@Service

public class OrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

	@Autowired

	private OrderRepository orderRepository;

	@Autowired

	private WebClient webClient;


	//1. Place a new Order

	public Order placeOrder(Order order) {

		logger.info("Starting order placement for product: {}", order.getProductId());

		try {

			//Set order date if not set

			if(order.getOrderDate() == null) {

				order.setOrderDate(LocalDateTime.now());

			}

			//webservices communicate like this

			//Validate product exists, wrong way

			//String productServiceUrl = "hht:?/loaclhost:8081/api/products/" + prder.getProducts()

			//correct way: microservices communicates like this

			//CORRECT uses service discovery

			//String productServiceUrl = "http://PRODUCT-SERVICE/api/product/" + order.getProductId();

			ProductDto product = fetchProduct(order.getProductId());

			if (product == null) {

				logger.error("Product not found with ID: {}", order.getProductId());

				order.setStatus("FAILED");

				order.setTotalPrice(BigDecimal.ZERO);

				order.setProductName("Unknown Product");

				return orderRepository.save(order);

			}

			logger.info("Product found: {} - Price: INR {}", product.getName(), product.getPrice());

			//3.Calculate total price

			BigDecimal total = product.getPrice().multiply(BigDecimal.valueOf(order.getQuantity()));

			order.setTotalPrice(total);

			order.setProductName(product.getName());

			//4. Save Order first(Status = pending)

			order.setStatus("PENDING");

			Order savedOrder = orderRepository.save(order);

			logger.info("Order saved with ID: {}", savedOrder.getId());

			//5. Process Payment

			PaymentDTO payment = processPayment(savedOrder);

			//6. Update Order  based on payment result

			if(payment != null && "COMPLETED".equals(payment.getStatus())) {

				savedOrder.setStatus("COMPLETED");

				savedOrder.setPaymentTransactionId(payment.getTransactionId());

				savedOrder.setPaymentStatus(payment.getStatus());

				logger.info("Payment completed: {}", payment.getTransactionId());

			}else {

				savedOrder.setStatus("FAILED");

				savedOrder.setPaymentStatus("FAILED");

				logger.error("Payment failed for order: {}", savedOrder.getId());

			}

			return orderRepository.save(savedOrder);

		}catch(WebClientResponseException.NotFound e) {

			logger.error("Product not found (404): {}", order.getProductId());

			order.setStatus("FAILED");

			order.setTotalPrice(BigDecimal.ZERO);

			order.setProductId("Product not found");

			return orderRepository.save(order);

		}catch(Exception e) {

			logger.error("Error placing order: {}", e.getMessage(), e);

			order.setStatus("FAILED");

			order.setTotalPrice(BigDecimal.ZERO);

			order.setProductId("Service Error");

			return orderRepository.save(order);

		}

	}

	//HELPER METHODS

	private ProductDto fetchProduct(String productId) {

		try {

			String productServiceUrl = "http://PRODUCT-SERVICE/api/products/" + productId;

			logger.info("Fetching product from: {} " , productServiceUrl);

			return webClient.get().uri(productServiceUrl).retrieve().bodyToMono(ProductDto.class).block();

		}catch(WebClientResponseException.NotFound e) {

			logger.error("Product not found: {} ", productId);

			return null;

		}

	}

	private PaymentDTO processPayment(Order order) {

		try {

			String paymentServiceUrl = "http://PAYMENT-SERVICE/api/payments?orderId=" + order.getId()

			+ "&customerEmail=" + order.getCustomerEmail() + "&amount=" + order.getTotalPrice();

			logger.info("Calling Payment Service: {} " , paymentServiceUrl);

			return webClient.post().uri(paymentServiceUrl).retrieve().bodyToMono(PaymentDTO.class).block();

		}catch(WebClientResponseException e) {

			logger.error(" Payment Service error: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());

			return null;

		}catch (Exception e) {

			logger.error("Payment service call failed: {}", e.getMessage());

			return null;

		}

	}

	//2. GET all order

	public List<Order> getAllOrders(){

		List<Order> orders = orderRepository.findAll();

		if(orders.isEmpty()) {

			throw new RuntimeException("No Orders Found");

		}

		return orders;

	}

	// 3. Get order by Id

	public Order getOrderById(String id) {

		return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found with id"));

	}

}

 