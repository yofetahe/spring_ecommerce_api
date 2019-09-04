package com.yhabtu.ecommerce.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yhabtu.ecommerce.dao.impl.CategoryDaoImpl;
import com.yhabtu.ecommerce.model.Category;
import com.yhabtu.ecommerce.model.Item;
import com.yhabtu.ecommerce.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryDaoImpl categoryDaoImpl;

	@Override
	public long addCategory(Category category) {
		
		return categoryDaoImpl.addCategory(category);
	}

	@Override
	public List<Category> getAllCategories() {
		
		return categoryDaoImpl.getAllCategories();
	}

}
