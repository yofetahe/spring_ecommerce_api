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

import com.yhabtu.ecommerce.model.Item;
import com.yhabtu.ecommerce.model.Type;
import com.yhabtu.ecommerce.service.TypeService;

@RestController
@RequestMapping("/api")
public class TypeController {
	
	@Autowired
	private TypeService typeService;

	@PostMapping("/addType")
	public @ResponseBody Long addType(@RequestBody Type type){
		
		return typeService.addType(type);
	}
	
	@GetMapping("/getCategoryDetailsByCategoryId/{category_id}")
	public @ResponseBody List<Type> getCategoryDetailsByCategoryId(@PathVariable("category_id") int category_id) {
		
		return typeService.getCategoryDetailsByCategoryId(category_id);
	}
}
