package com.yhabtu.ecommerce.service;

import java.util.List;
import java.util.Map;

import com.yhabtu.ecommerce.model.Color;

public interface ColorService {
	
	public long addColor(Color color);
	
	public List<Color> fetchItemColorsByItemId(int item_id);

	public List<Map<String, String>> fetchItemColorsByItemsList(String items_list);
}
