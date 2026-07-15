package com.tnf.product_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tnf.product_service.entities.Product;
import com.tnf.product_service.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public Product createProduct(Product product) {
		return productRepository.save(product);
	}
	
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	public Product getProductById(String id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Prodcut not found"));
	}
	

}
