package com.tnf.payment_service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tnf.payment_service.entities.Payment;

public interface PaymentRepository extends MongoRepository<Payment, String> {

}
