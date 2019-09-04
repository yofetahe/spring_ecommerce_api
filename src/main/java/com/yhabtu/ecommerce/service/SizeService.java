package com.yhabtu.ecommerce.service;

import java.util.List;

import com.yhabtu.ecommerce.model.Size;

public interface SizeService {
	
	public long addSize(Size size);	

	public List<Size> fetchItemSizeByItemId(int item_id);
}
