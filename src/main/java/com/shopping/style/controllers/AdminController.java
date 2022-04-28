package com.shopping.style.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shopping.style.dto.ProductDTO;
import com.shopping.style.models.Category;
import com.shopping.style.models.Product;
import com.shopping.style.services.CategoryService;
import com.shopping.style.services.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	public static String uploadDirectory=System.getProperty("user.dir")+"/src/main/resources/static/productImages";
	
	//Category endpoints
	
	@Autowired
	CategoryService service;
	
	
	@Autowired
	ProductService prodService;

	@GetMapping("")
	public String adminHome() {
		return "adminHome";
	}



	@GetMapping("/categories")
	public String adminCategories(Model model) {
		model.addAttribute("categories",service.getAllCategories());
		return "categories";
	}
	
	
	
	@GetMapping("/categories/add")
	public String adminAddCat(Model model) {
		model.addAttribute("category", new Category());
		return "categoriesAdd";
	}

	@PostMapping("/categories/add")
	public String adminAddCatPost(@ModelAttribute("category") Category category) {
		service.addCategory(category);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/categories/delete/{id}")
	public String deleteCatById(@PathVariable int id) {
		service.removeById(id);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/categories/update/{id}")
	public String update(@PathVariable int id,Model model) {
		Optional<Category> category=service.getCatById(id);
		
		if(category.isPresent()) {
			model.addAttribute("category",category.get());
			return "categoriesAdd";
		}else {
			return "404";
		}
		
	}
	
	
	//Product endpoints
	
	@GetMapping("/products")
	public String getAllProducts(Model model) {
		model.addAttribute("products", prodService.getAllProducts());
		return "products";
	}
	
	@GetMapping("/products/add")
	public String addProduct(Model model) {
		model.addAttribute("productDTO", new ProductDTO());
		model.addAttribute("categories",service.getAllCategories());
		return "productsAdd";
	}
	
	@PostMapping("/products/add")
	public String addProductPost(@ModelAttribute("productDTO")ProductDTO productDTO,@RequestParam("productImage")MultipartFile image, @RequestParam("imgName")String imageName) throws IOException{
		Product product=new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(service.getCatById(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());
		String imageUUID;
		if(!image.isEmpty()) {
			imageUUID=image.getOriginalFilename();
			Path imagePath=Paths.get(uploadDirectory,imageUUID);
			Files.write(imagePath,image.getBytes());
		}else {
			imageUUID=imageName;
		}
		product.setImageName(imageUUID);
	    prodService.addProduct(product);
		return "redirect:/admin/products";
	}
	
	@GetMapping("/product/delete/{id}")
	public String deleteProdById(@PathVariable long id) {
		prodService.removeProduct(id);
		return "redirect:/admin/products";
	}
	
	@GetMapping("/product/update/{id}")
	public String updateProd(@PathVariable long id, Model model) {
		Product product=prodService.getProductById(id).get();
		System.out.println(product);
		ProductDTO prodDto=new ProductDTO();
		prodDto.setId(product.getId());
		prodDto.setName(product.getName());
		prodDto.setCategoryId(product.getCategory().getId());
		prodDto.setPrice(product.getPrice());
		prodDto.setWeight(product.getWeight());
		prodDto.setDescription(product.getDescription());
		prodDto.setImageName(product.getImageName());
		
		model.addAttribute("categories",service.getAllCategories());
		model.addAttribute("productDTO",prodDto);
		return "productsAdd";
		
	}
	
}
