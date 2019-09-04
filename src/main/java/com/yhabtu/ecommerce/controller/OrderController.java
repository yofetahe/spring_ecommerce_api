package com.yhabtu.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yhabtu.ecommerce.service.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@CrossOrigin(origins="http://localhost:3000")
	@PostMapping("/submitPurchaseOrder")
	public @ResponseBody boolean submitPurchaseOrder(@RequestBody String request){
		System.out.println("======== CONTROLLER =======");
		
		//changing request into JSON
		JsonObject jsonObject = new JsonParser().parse(request).getAsJsonObject();
		
		//changing item_size_color string into Item_Size_Color Object
		String item_size_color = jsonObject.get("item_size_color").toString();
				
		JsonObject shipping = (JsonObject) jsonObject.get("shippingAddress");		

		JsonObject billing = (JsonObject) jsonObject.get("billingInformation");
				
		return orderService.submitPurchaseOrder(item_size_color, shipping, billing);
	}
}
