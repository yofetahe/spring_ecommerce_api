package com.yhabtu.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yhabtu.ecommerce.model.User;
import com.yhabtu.ecommerce.service.UserService;

@RestController
@RequestMapping("/api")
public class UsersController {
	
	@Autowired
	private UserService userService;
	
	@CrossOrigin(origins="http://localhost:3000")
	@PostMapping(path="/addUser")
	public @ResponseBody Long addUser(@RequestBody User user){
		
		//check email address existence in db
		User u = userService.getUserByEmail(user.getEmail());
		
		long id = 0;
		
		if(u == null) {
					
			id = userService.addUser(user);
		}
		
		return id;
	}
	
	@RequestMapping(value = "/getAllUsers")
	public List<User> getAllUsers(){
				
		return userService.getAllUsers();
	}
		
	@CrossOrigin(origins="http://localhost:3000")
	@PostMapping(path="/authenticateUser")
	public @ResponseBody long authenticateUser(@RequestBody User user){
		
		return userService.authenticateUser(user);
	}
}
