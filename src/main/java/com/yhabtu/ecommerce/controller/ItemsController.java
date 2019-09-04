package com.yhabtu.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yhabtu.ecommerce.model.Color;
import com.yhabtu.ecommerce.model.Item;
import com.yhabtu.ecommerce.model.Size;
import com.yhabtu.ecommerce.model.User;
import com.yhabtu.ecommerce.service.ColorService;
import com.yhabtu.ecommerce.service.ItemsService;
import com.yhabtu.ecommerce.service.SizeService;
import com.yhabtu.ecommerce.service.UserService;

@RestController
@RequestMapping("/api")
public class ItemsController {
	
	@Autowired
	private ItemsService itemsService;
	
	@CrossOrigin(origins="http://localhost:3000")
	@PostMapping("/addItem")
	public @ResponseBody Long addItem(@RequestBody Item item) {
		
		return itemsService.addItem(item);
	}	

	@CrossOrigin(origins="http://localhost:3000")
	@GetMapping("/getItemsByCategoryId/{category_id}")
	public @ResponseBody List<Item> getItemsByCategoryId(@PathVariable("category_id") int category_id){
		
		return itemsService.getItemsByCategoryId(category_id);
	}
		
	@CrossOrigin(origins="http://localhost:3000")
	@GetMapping("/getItemsByCategoryTypeId/{category_id}/{type_id}")
	public @ResponseBody List<Item> getItemsByCategoryTypeId(@PathVariable("category_id") int category_id, @PathVariable("type_id") int type_id){
		
		return itemsService.getItemsByCategoryTypeId(category_id, type_id);
	}	

	@CrossOrigin(origins="http://localhost:3000")
	@PostMapping(path = "/saveItemForLaterPurchase/{user_id}/{item_id}/{size_id}/{color_id}")
	public @ResponseBody Boolean saveItemForLaterPurchase(
			@PathVariable int user_id,
			@PathVariable int item_id, 
			@PathVariable int color_id, 
			@PathVariable int size_id){
		
		if(itemsService.checkItemForLaterPurchase(item_id, color_id, size_id, user_id)) {
			return true;
		}
		
		return itemsService.saveItemForLaterPurchase(item_id, color_id, size_id, user_id);
	}
	
	@CrossOrigin(origins="http://localhost:3000")
	@GetMapping("/getItemRemainingBalance/{item_id}/{color_id}/{size_id}")
	public @ResponseBody Integer getItemRemainingBalance(@PathVariable int item_id, @PathVariable int color_id, @PathVariable int size_id){
		
		return itemsService.getItemRemainingBalance(item_id, color_id, size_id);
	}
	
	@CrossOrigin(origins="http://localhost:3000")
	@GetMapping("/getSavedItemsListByUserId/{user_id}")
	public @ResponseBody List<Item> getSavedItemsListByUserId(@PathVariable int user_id){
		
		return itemsService.getSavedItemsListByUserId(user_id);
	}
	
	@CrossOrigin(origins="http://localhost:3000")
	@DeleteMapping("/deleteSavedItem/{user_id}/{item_color_size_id}")
	public @ResponseBody boolean deleteSavedItem(@PathVariable int user_id, @PathVariable int item_color_size_id){
		System.out.println(user_id + " = " + item_color_size_id);
		return itemsService.deleteSavedItem(user_id, item_color_size_id);
	}
}
