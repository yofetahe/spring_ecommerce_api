package com.yhabtu.ecommerce.service;

import java.util.List;

import com.yhabtu.ecommerce.model.Color;

public interface ColorService {
	
	public long addColor(Color color);
	
	public List<Color> fetchItemColorsByItemId(int item_id);

}
