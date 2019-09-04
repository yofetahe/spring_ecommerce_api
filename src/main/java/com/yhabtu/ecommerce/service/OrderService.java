package com.yhabtu.ecommerce.service;

import com.google.gson.JsonObject;

public interface OrderService {
	
	public boolean submitPurchaseOrder(String item_size_color, JsonObject shipping, JsonObject billing);

}
