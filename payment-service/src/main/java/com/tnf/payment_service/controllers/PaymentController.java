package com.tnf.payment_service.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tnf.payment_service.entities.Payment;
import com.tnf.payment_service.services.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Payment processPayment(@RequestParam String orderId,
								  @RequestParam String customerEmail,
								  @RequestParam BigDecimal amount) {
		return paymentService.processPayment(orderId, customerEmail, amount);
	}
	
	@GetMapping("/{id}")
	public Payment getPayment(@PathVariable String id) {
		return paymentService.getPaymentById(id);
	}

}
