package com.yhabtu.ecommerce.service;

import java.util.List;

import com.yhabtu.ecommerce.model.Category;

public interface CategoryService {

	public long addCategory(Category category);
	
	public List<Category> getAllCategories();
	
}
