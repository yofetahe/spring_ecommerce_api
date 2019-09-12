package com.yhabtu.ecommerce.service;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

public interface OrderService {
	
	public boolean submitPurchaseOrder(String item_size_color, JsonObject shipping, JsonObject billing, JsonObject billingAddress);
	
	public boolean submitActiveUserPurchaseOrder(String item_size_color, JsonObject shipping, int cci, int billingAddressId, int user_id);
	
	public List<Map<String, String>> getOrderedItemsByUserId(int user_id);

	public boolean cancelOrderedItem(int order_id, int order_detail_id, int item_size_color_id);
}
