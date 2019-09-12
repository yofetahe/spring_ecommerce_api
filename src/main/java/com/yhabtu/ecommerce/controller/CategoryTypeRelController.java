package com.yhabtu.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yhabtu.ecommerce.model.Category_Type;
import com.yhabtu.ecommerce.service.CategoryTypeRelService;

@RestController
@RequestMapping("/api")
public class CategoryTypeRelController {
	
	@Autowired
	private CategoryTypeRelService categoryTypeRelService;
	
	@PostMapping("/addCategoryTypeRel")
	public @ResponseBody Long addCategoryTypeRelation(@RequestBody Category_Type categoryType) {
		
		return categoryTypeRelService.addCategoryTypeRel(categoryType);		
	}

}
