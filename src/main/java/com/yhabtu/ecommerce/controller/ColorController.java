package com.yhabtu.ecommerce.controller;

import java.util.List;

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

import com.yhabtu.ecommerce.model.Color;
import com.yhabtu.ecommerce.service.ColorService;

@RestController
@RequestMapping("/api")
public class ColorController {

	@Autowired
	private ColorService colorService;
	
	@CrossOrigin(origins="http://localhost:3000")
	@PostMapping("/addColor")
	public @ResponseBody Long addColor(@RequestBody Color color) {
		
		return colorService.addColor(color);
	}
	
	@CrossOrigin(origins="http://localhost:3000")
	@GetMapping("/fetchItemColorsByItemId/{item_id}")
	public @ResponseBody List<Color> fetchItemColorsByItemId(@PathVariable("item_id") int item_id){
		
		return colorService.fetchItemColorsByItemId(item_id);		
	}
}
