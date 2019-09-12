package com.yhabtu.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yhabtu.ecommerce.model.BillingShippingAddress;
import com.yhabtu.ecommerce.model.CreditCardInformation;
import com.yhabtu.ecommerce.model.User;
import com.yhabtu.ecommerce.service.UserService;

@RestController
@RequestMapping("/api")
public class UsersController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping(path="/addUser")
	public @ResponseBody Long addUser(@RequestBody User user){
				
		return userService.addUser(user);
	}
	
	@RequestMapping(value = "/getAllUsers")
	public List<User> getAllUsers(){
				
		return userService.getAllUsers();
	}
		
	@PostMapping(path="/authenticateUser")
	public @ResponseBody long authenticateUser(@RequestBody User user){
		
		return userService.authenticateUser(user);
	}
	
	@RequestMapping(value = "/getCreditCardInfoByUserId/{user_id}")
	public List<CreditCardInformation> getCreditCardInfoByUserId(@PathVariable int user_id){
				
		return userService.getCreditCardInfoByUserId(user_id);
	}
	
	@RequestMapping(value = "/getBillingAddressByUserId/{user_id}")
	public List<BillingShippingAddress> getBillingAddressByUserId(@PathVariable int user_id){
				
		return userService.getBillingAddressByUserId(user_id);
	}
}
