package com.yhabtu.ecommerce.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.yhabtu.ecommerce.configuration.HibernateUtil;
import com.yhabtu.ecommerce.dao.CategoryTypeRelDao;
import com.yhabtu.ecommerce.model.Category_Type;

@Repository
public class CategoryTypeRelDaoImpl implements CategoryTypeRelDao {

	@Override
	public long addCategoryTypeRel(Category_Type catType) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		session.beginTransaction();
		long id = (long)session.save(catType);
		session.getTransaction().commit();
		
		return id;
	}

}
