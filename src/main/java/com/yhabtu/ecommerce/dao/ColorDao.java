package com.yhabtu.ecommerce.dao;

import java.util.List;

import com.yhabtu.ecommerce.model.Color;

public interface ColorDao {
	
	public long addColor(Color color);

	public List<Color> fetchItemColorsByItemId(int item_id);
}
