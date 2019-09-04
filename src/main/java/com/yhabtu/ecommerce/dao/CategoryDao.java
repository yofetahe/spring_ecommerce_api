package com.yhabtu.ecommerce.dao;

import java.util.List;

import com.yhabtu.ecommerce.model.Category;
import com.yhabtu.ecommerce.model.Item;

public interface CategoryDao {

	public long addCategory(Category category);

	public List<Category> getAllCategories();

}
