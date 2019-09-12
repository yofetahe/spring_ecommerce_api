package com.yhabtu.ecommerce.service;

import java.util.List;

import com.yhabtu.ecommerce.model.BillingShippingAddress;
import com.yhabtu.ecommerce.model.CreditCardInformation;
import com.yhabtu.ecommerce.model.User;

public interface UserService {

	public long addUser(User user);
	
	public List<User> getAllUsers();
	
	public User getUserByEmail(String email);
	
	public long authenticateUser(User user);
	
	public List<CreditCardInformation> getCreditCardInfoByUserId(int user_id);
	
	public List<BillingShippingAddress> getBillingAddressByUserId(int user_id);
}
