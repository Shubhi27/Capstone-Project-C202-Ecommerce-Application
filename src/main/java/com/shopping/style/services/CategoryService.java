package com.shopping.style.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.style.models.Category;
import com.shopping.style.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository repo;

	public List<Category> getAllCategories() {
		return repo.findAll();
	}

	public void addCategory(Category category) {
		repo.save(category);

	}
	public void removeById(int id) {
		repo.deleteById(id);
	}
	public Optional<Category> getCatById(int id){
		return repo.findById(id);
		
	}
}
