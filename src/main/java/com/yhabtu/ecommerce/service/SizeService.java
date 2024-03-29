package com.yhabtu.ecommerce.service;

import java.util.List;
import java.util.Map;

import com.yhabtu.ecommerce.model.Size;

public interface SizeService {
	
	public long addSize(Size size);	

	public List<Size> fetchItemSizeByItemId(int item_id);
	
	public List<Map<String, String>> fetchItemSizesByItemsList(String items_list);
}
