package com.shopping.style.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.style.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
