package com.shopping.style.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shopping.style.global.GlobalStaticData;
import com.shopping.style.models.Product;
import com.shopping.style.services.ProductService;

@Controller
public class CartController {
	@Autowired
	ProductService productService;

	@GetMapping("/addToCart/{id}")
	public String addToCart(@PathVariable int id) {
		GlobalStaticData.cart.add(productService.getProductById(id).get());
		return "redirect:/shop";
	}

	@GetMapping("/cart")
	public String cart(Model model) {
		model.addAttribute("cartCount", GlobalStaticData.cart.size());
		model.addAttribute("total", GlobalStaticData.cart.stream().mapToDouble(Product::getPrice).sum());
		model.addAttribute("cart", GlobalStaticData.cart);
		return "cart";
	}
	
	@GetMapping("/cart/removeItem/{index}")
	public String remove(@PathVariable int index) {
		return "redirect:/cart";
	}
	
	@GetMapping("/checkout")
	public String checkout(Model model) {
		model.addAttribute("total", GlobalStaticData.cart.stream().mapToDouble(Product::getPrice).sum());
		return "checkout";
	}
}
