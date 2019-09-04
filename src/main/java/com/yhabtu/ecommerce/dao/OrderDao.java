package com.yhabtu.ecommerce.dao;

import java.util.List;

import com.yhabtu.ecommerce.model.CreditCardInformation;
import com.yhabtu.ecommerce.model.Item_Size_Color;
import com.yhabtu.ecommerce.model.ShippingAddress;
import com.yhabtu.ecommerce.model.User;

public interface OrderDao {

	public boolean submitPurchaseOrder(
			List<Item_Size_Color>itemSizeColors, 
			User user, 
			ShippingAddress shippingAddress, 
			CreditCardInformation billingInfo,
			double totalTransaction);
}
