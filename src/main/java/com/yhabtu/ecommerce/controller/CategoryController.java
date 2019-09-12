package com.yhabtu.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yhabtu.ecommerce.model.Category;
import com.yhabtu.ecommerce.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/addCategory")
	public @ResponseBody Long addCategory(@RequestBody Category category){
		
		return categoryService.addCategory(category);
	}
		
	@GetMapping("/getAllCategories")
	public @ResponseBody List<Category> getAllCategories(){
		
		return categoryService.getAllCategories();
	}

}
