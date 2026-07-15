package com.tnf.order_service.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class Order {
	
	@Id
	private String id;
	private String productId;
	private String productName; //stored for quick reference
	private String customerEmail;
	private Integer quantity;
	private BigDecimal totalPrice;
	private String status; //PENDING, COMPLETED, FAILED, CANCELLED
	private LocalDateTime orderDate;
	
	private String paymentTransactionId; 
	private String paymentStatus;
	
	
	public String getPaymentTransactionId() {
		return paymentTransactionId;
	}


	public void setPaymentTransactionId(String paymentTransactionId) {
		this.paymentTransactionId = paymentTransactionId;
	}


	public String getPaymentStatus() {
		return paymentStatus;
	}


	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}


	public Order() {
		this.status = "PENDING";
		this.orderDate = LocalDateTime.now();
	}

	
	//not all fields
	public Order(String productId, String customerEmail, Integer quantity, String status, LocalDateTime orderDate) {
		super();
		this.productId = productId;
		this.customerEmail = customerEmail;
		this.quantity = quantity;
		this.status = "PENDING";
		this.orderDate = LocalDateTime.now();
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	@Override
	public String toString() {
		return "Order [id=" + id + ", productId=" + productId + ", productName=" + productName + ", customerEmail="
				+ customerEmail + ", quantity=" + quantity + ", totalPrice=" + totalPrice + ", status=" + status
				+ ", orderDate=" + orderDate + ", paymentTransactionId=" + paymentTransactionId + ", paymentStatus="
				+ paymentStatus + "]";
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	

	

}
