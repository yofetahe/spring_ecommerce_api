package com.yhabtu.ecommerce.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yhabtu.ecommerce.dao.impl.SalesDaoImpl;
import com.yhabtu.ecommerce.model.Orders;
import com.yhabtu.ecommerce.service.SalesService;

@Service
public class SalesServiceImpl implements SalesService {
	
	@Autowired
	private SalesDaoImpl salesDaoImpl;

	@Override
	public int addSales(Orders sales) {
		
		return salesDaoImpl.addSales(sales);
	}

}
