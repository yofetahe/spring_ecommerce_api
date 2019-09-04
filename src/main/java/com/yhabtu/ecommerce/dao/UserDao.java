package com.yhabtu.ecommerce.dao;

import java.util.List;

import com.yhabtu.ecommerce.model.User;

public interface UserDao {

	public long addUser(User user);
	
	public List<User> getAllUsers();

	public User getUserByEmail(String email);
}
