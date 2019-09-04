package com.yhabtu.ecommerce.service;

import java.util.List;

import com.yhabtu.ecommerce.model.Type;

public interface TypeService {

	public long addType(Type type);
	
	public List<Type> getCategoryDetailsByCategoryId(int category_id);
}
