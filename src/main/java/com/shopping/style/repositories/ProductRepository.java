package com.shopping.style.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.style.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
List<Product> findAllByCategory_id(int id);
}
