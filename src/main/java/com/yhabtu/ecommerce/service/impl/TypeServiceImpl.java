package com.yhabtu.ecommerce.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yhabtu.ecommerce.dao.impl.TypeDaoImpl;
import com.yhabtu.ecommerce.model.Type;
import com.yhabtu.ecommerce.service.TypeService;

@Service
public class TypeServiceImpl implements TypeService {
	
	@Autowired
	private TypeDaoImpl typeDaoImpl;

	@Override
	public long addType(Type type) {
		
		return typeDaoImpl.addType(type);
	}

	@Override
	public List<Type> getCategoryDetailsByCategoryId(int category_id) {
		
		return typeDaoImpl.getCategoryDetailsByCategoryId(category_id);
	}

}
