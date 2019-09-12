package com.yhabtu.ecommerce.dao;

import java.util.List;
import java.util.Map;

import com.yhabtu.ecommerce.model.Size;

public interface SizeDao {
	
	public long addSize(Size size);

	public List<Size> fetchItemSizeByItemId(int item_id);	

	public List<Map<String, String>> fetchItemSizesByItemsList(String items_list);
}
