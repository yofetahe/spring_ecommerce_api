package com.yhabtu.ecommerce.dao;

import java.util.List;
import java.util.Map;

import com.yhabtu.ecommerce.model.Color;

public interface ColorDao {
	
	public long addColor(Color color);

	public List<Color> fetchItemColorsByItemId(int item_id);

	public List<Map<String, String>> fetchItemColorsByItemsList(String items_list);
}
