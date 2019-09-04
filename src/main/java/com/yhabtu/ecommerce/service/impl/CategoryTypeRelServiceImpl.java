package com.yhabtu.ecommerce.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yhabtu.ecommerce.model.Category_Type;
import com.yhabtu.ecommerce.service.CategoryTypeRelService;

@Service
public class CategoryTypeRelServiceImpl implements CategoryTypeRelService {
	
	@Autowired
	private CategoryTypeRelService categoryTypeRelService;

	@Override
	public long addCategoryTypeRel(Category_Type catType) {
		
		return categoryTypeRelService.addCategoryTypeRel(catType);
	}

}
