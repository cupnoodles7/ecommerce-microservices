package com.tnf.payment_service.services;
 
import java.math.BigDecimal;
import java.util.UUID;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tnf.payment_service.entities.Payment;
import com.tnf.payment_service.repositories.PaymentRepository;

@Service
public class PaymentService {
	@Autowired
	private PaymentRepository paymentRepository ;
	public Payment processPayment(String orderId, String customerEmail, BigDecimal amount) {
		Payment payment = new Payment();
		payment.setOrderId(orderId);
		payment.setCustomerEmail(customerEmail);
		payment.setAmount(amount);
		//simulate payment gateway (random success/failure for demo)
		if (Math.random() > 0) {
			payment.setStatus("COMPLETED");
			payment.setTransactionId("TXN-" +UUID.randomUUID().toString());
		}else {
			payment.setStatus("FAILED");
		}
		return paymentRepository.save(payment);
	}
	public Payment getPaymentById(String id) {
		return paymentRepository.findById(id).orElseThrow(()->new RuntimeException("Payment not found"));
	}
 
}