package com.shopping.style.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.style.models.Product;
import com.shopping.style.repositories.ProductRepository;

@Service
public class ProductService {
@Autowired
ProductRepository repo;

public List<Product> getAllProducts(){
	return repo.findAll();
}

public void addProduct(Product product) {
	repo.save(product);
}

public void removeProduct(long id) {
	repo.deleteById(id);
}

public Optional<Product> getProductById(long id){
	return repo.findById(id);
}

public List<Product> getProductByCategory(int id){
	return repo.findAllByCategory_id(id);
}
}
