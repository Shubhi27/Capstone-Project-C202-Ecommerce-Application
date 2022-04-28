package com.shopping.style.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shopping.style.global.GlobalStaticData;
import com.shopping.style.services.CategoryService;
import com.shopping.style.services.ProductService;

@Controller
public class HomeController {

@Autowired
CategoryService catService;

@Autowired
ProductService prodService;

@GetMapping({"/","/home"})
public String home(Model model) {
	model.addAttribute("cartCount", GlobalStaticData.cart.size());
	return "index";
}

@GetMapping("/shop")
public String allProd(Model model) {
	model.addAttribute("categories",catService.getAllCategories());
	model.addAttribute("products",prodService.getAllProducts());
	model.addAttribute("cartCount", GlobalStaticData.cart.size());
	return "shop";
}
	
@GetMapping("/shop/category/{id}")
public String prodByCategory(Model model,@PathVariable int id) {
	model.addAttribute("categories",catService.getAllCategories());
	model.addAttribute("cartCount", GlobalStaticData.cart.size());
	model.addAttribute("products",prodService.getProductByCategory(id));
	return "shop";
}

@GetMapping("/shop/viewproduct/{id}")
public String viewProduct(Model model,@PathVariable long id) {
	model.addAttribute("cartCount", GlobalStaticData.cart.size());
	model.addAttribute("product",prodService.getProductById(id).get());
	return "viewProduct";
}

}
