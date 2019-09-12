package com.yhabtu.ecommerce.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.yhabtu.ecommerce.service.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@PostMapping("/submitPurchaseOrder")
	public @ResponseBody boolean submitPurchaseOrder(@RequestBody String request){
				
		//changing request into JSON
		JsonObject jsonObject = new JsonParser().parse(request).getAsJsonObject();
		
		//changing item_size_color string into Item_Size_Color Object
		String item_size_color = jsonObject.get("item_size_color").toString();
				
		JsonObject shipping = (JsonObject) jsonObject.get("shippingAddress");		

		JsonObject billing = (JsonObject) jsonObject.get("billingInformation");
		
		JsonObject billingAddress = (JsonObject) jsonObject.get("billingAddress");
				
		return orderService.submitPurchaseOrder(item_size_color, shipping, billing, billingAddress);
	}
	
	@PostMapping("/submitActiveUserPurchaseOrder")
	public @ResponseBody boolean submitActiveUserPurchaseOrder(@RequestBody String request){
				
		//changing request into JSON
		JsonObject jsonObject = new JsonParser().parse(request).getAsJsonObject();

		//changing item_size_color string into Item_Size_Color Object
		String item_size_color = jsonObject.get("item_size_color").toString();
				
		JsonObject shipping = (JsonObject) jsonObject.get("shippingAddress");		
		
		int credit_card_info_id = Integer.parseInt(jsonObject.get("creditCardInfoId").toString());
		
		int billingAddressId = Integer.parseInt(jsonObject.get("billingAddressId").toString());
		
		String u_id = jsonObject.get("user_id").toString();
		int user_id = Integer.parseInt(u_id.substring(1, u_id.length()-1));
				
		return orderService.submitActiveUserPurchaseOrder(item_size_color, shipping, credit_card_info_id, billingAddressId, user_id);
	}
	
	@GetMapping(value = "/getOrderedItemsByUserId/{user_id}")
	public List<Map<String, String>> getOrderedItemsByUserId(@PathVariable int user_id){
		
		return orderService.getOrderedItemsByUserId(user_id);
	}
	
	@PostMapping(value = "/cancelOrderedItem/{order_id}/{order_detail_id}/{item_size_color_id}")
	public boolean cancelOrderedItem(
			@PathVariable int order_id, 
			@PathVariable int order_detail_id, 
			@PathVariable int item_size_color_id){
		
		return orderService.cancelOrderedItem(order_id, order_detail_id, item_size_color_id);
	}
}
