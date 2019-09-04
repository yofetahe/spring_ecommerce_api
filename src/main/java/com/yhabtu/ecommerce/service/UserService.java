package com.yhabtu.ecommerce.service;

import java.util.List;

import com.yhabtu.ecommerce.model.User;

public interface UserService {

	public long addUser(User user);
	
	public List<User> getAllUsers();
	
	public User getUserByEmail(String email);
	
	public long authenticateUser(User user);
}
