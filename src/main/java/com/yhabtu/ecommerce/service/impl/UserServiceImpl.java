package com.yhabtu.ecommerce.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.yhabtu.ecommerce.dao.impl.UserDaoImpl;
import com.yhabtu.ecommerce.model.User;
import com.yhabtu.ecommerce.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDaoImpl userDaoImpl;
	
	@Override
	public long addUser(User user) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		
		return userDaoImpl.addUser(user);
	}

	@Override
	public List<User> getAllUsers() {
		
		return userDaoImpl.getAllUsers();
	}

	@Override
	public User getUserByEmail(String email) {
		
		return userDaoImpl.getUserByEmail(email);
	}

	@Override
	public long authenticateUser(User user) {
		
		long check = 0;
		
		User u = getUserByEmail(user.getEmail());
				
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if(u != null && encoder.matches(user.getPassword(), u.getPassword())) {
			
			return u.getUser_id();
		}
		return check;
	}

}
