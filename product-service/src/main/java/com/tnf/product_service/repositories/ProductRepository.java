package com.tnf.product_service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tnf.product_service.entities.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
