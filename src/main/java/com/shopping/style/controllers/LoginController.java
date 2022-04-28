package com.shopping.style.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.shopping.style.global.GlobalStaticData;
import com.shopping.style.models.Role;
import com.shopping.style.models.User;
import com.shopping.style.repositories.RoleRepository;
import com.shopping.style.repositories.UserRepository;

@Controller
public class LoginController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	UserRepository userRepo;

	@Autowired
	RoleRepository roleRepo;

	@GetMapping("/login")
	public String login() {
		GlobalStaticData.cart.clear();
		
		return "login";
	}
	
//	@PostMapping("/login")
//	public String loginPost() {
//		return "login";
//	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping("/register")
	public String saveUser(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException {
		String password = user.getPassword();

		user.setPassword(bCryptPasswordEncoder.encode(password));
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleRepo.findById(2).get());
		user.setRoles(roles);
		userRepo.save(user);

		request.login(user.getEmail(), password);
		return "redirect:/";

	}
}
