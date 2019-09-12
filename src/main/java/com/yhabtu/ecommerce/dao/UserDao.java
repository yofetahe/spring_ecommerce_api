package com.yhabtu.ecommerce.dao;

import java.util.List;

import com.yhabtu.ecommerce.model.BillingShippingAddress;
import com.yhabtu.ecommerce.model.CreditCardInformation;
import com.yhabtu.ecommerce.model.User;

public interface UserDao {

	public long addUser(User user);
	
	public List<User> getAllUsers();

	public User getUserByEmail(String email);

	public List<CreditCardInformation> getCreditCardInfoByUserId(int user_id);

	public List<BillingShippingAddress> getBillingAddressByUserId(int user_id);
}
