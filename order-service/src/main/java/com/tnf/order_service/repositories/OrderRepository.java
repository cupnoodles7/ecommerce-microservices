package com.tnf.order_service.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tnf.order_service.entities.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String>{
	
	//Spring Data MongoDB automatically implements these methods
	//save() findAll() findById() deleteById() count()
	
	//custom query method
	List<Order> findByCustomerEmail(String email);
	
	//Additional useful queries
	List<Order> findByStatus(String status);
	List<Order> findByProductId(String productId);
	List<Order> findByCustomerEmailAndStatus(String email, String status);
	
	
	

}
