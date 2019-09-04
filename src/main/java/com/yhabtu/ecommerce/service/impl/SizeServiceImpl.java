package com.yhabtu.ecommerce.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yhabtu.ecommerce.dao.impl.SizeDaoImpl;
import com.yhabtu.ecommerce.model.Size;
import com.yhabtu.ecommerce.service.SizeService;

@Service
public class SizeServiceImpl implements SizeService {
	
	@Autowired
	private SizeDaoImpl sizeDaoImpl;

	@Override
	public long addSize(Size size) {
		
		return sizeDaoImpl.addSize(size);
	}

	@Override
	public List<Size> fetchItemSizeByItemId(int item_id) {
		
		return sizeDaoImpl.fetchItemSizeByItemId(item_id);
	}

}
