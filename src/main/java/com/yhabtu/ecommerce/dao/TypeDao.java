package com.yhabtu.ecommerce.dao;

import java.util.List;

import com.yhabtu.ecommerce.model.Type;

public interface TypeDao {

	public long addType(Type type);
	
	public List<Type> getCategoryDetailsByCategoryId(int category_id);
}
