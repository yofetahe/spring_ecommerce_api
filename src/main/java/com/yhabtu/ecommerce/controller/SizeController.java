package com.yhabtu.ecommerce.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.yhabtu.ecommerce.model.Size;
import com.yhabtu.ecommerce.service.SizeService;

@RestController
@RequestMapping("/api")
public class SizeController {
	
	@Autowired
	private SizeService sizeService;

	@PostMapping("/addSize")
	public @ResponseBody Long addSize(@RequestBody Size size) {
		
		return sizeService.addSize(size);
	}
	
	@GetMapping("/fetchItemSizesByItemId/{item_id}")
	public @ResponseBody List<Size> fetchItemSizeByItemId(@PathVariable("item_id") int item_id){
		
		return sizeService.fetchItemSizeByItemId(item_id);
	}
	
	@PostMapping("/fetchItemSizesByItemsList")
	public @ResponseBody List<Map<String, String>> fetchItemSizesByItemsList(@RequestBody String items){
		
		JsonObject jsonObject = new JsonParser().parse(items).getAsJsonObject();
		
		String json_ids = jsonObject.get("items").toString();		
		
		String items_list = json_ids.substring(1, json_ids.length()-1);		
		
		return sizeService.fetchItemSizesByItemsList(items_list);
	}	
}
