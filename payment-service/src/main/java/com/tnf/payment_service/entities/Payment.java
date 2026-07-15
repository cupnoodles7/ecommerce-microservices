package com.tnf.payment_service.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "payments")
public class Payment {
	
	@Id
	private String id;
	private String orderId;
	private String customerEmail;
	private BigDecimal amount; 
	private String status; //COMPLETED, FAILED
	private String transactionId;
	private LocalDateTime paymentDate;
	
	public Payment() {
		this.paymentDate = LocalDateTime.now();
	}

	public Payment(String id, String orderId, String customerEmail, BigDecimal amount, String status,
			String transactionId, LocalDateTime paymentDate) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.customerEmail = customerEmail;
		this.amount = amount;
		this.status = status;
		this.transactionId = transactionId;
		this.paymentDate = paymentDate;
	}
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", orderId=" + orderId + ", customerEmail=" + customerEmail + ", amount=" + amount
				+ ", status=" + status + ", transactionId=" + transactionId + ", paymentDate=" + paymentDate + "]";
	}
	
}
