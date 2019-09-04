package com.yhabtu.ecommerce.dao;

import java.util.List;

import com.yhabtu.ecommerce.model.Size;

public interface SizeDao {
	
	public long addSize(Size size);

	public List<Size> fetchItemSizeByItemId(int item_id);
}
