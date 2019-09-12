package com.yhabtu.ecommerce.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yhabtu.ecommerce.dao.impl.ColorDaoImpl;
import com.yhabtu.ecommerce.model.Color;
import com.yhabtu.ecommerce.service.ColorService;

@Service
public class ColorServiceImpl implements ColorService {
	
	@Autowired
	private ColorDaoImpl colorDaoImpl;

	@Override
	public long addColor(Color color) {

		return colorDaoImpl.addColor(color);
	}

	@Override
	public List<Color> fetchItemColorsByItemId(int item_id) {
		
		return colorDaoImpl.fetchItemColorsByItemId(item_id);
	}

	@Override
	public List<Map<String, String>> fetchItemColorsByItemsList(String items_list) {
		
		return colorDaoImpl.fetchItemColorsByItemsList(items_list);
	}

}
