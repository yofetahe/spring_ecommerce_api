package com.yhabtu.ecommerce.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.yhabtu.ecommerce.configuration.HibernateUtil;
import com.yhabtu.ecommerce.dao.CategoryDao;
import com.yhabtu.ecommerce.model.Category;

@Repository
public class CategoryDaoImpl implements CategoryDao {
	
	
	@Override
	public long addCategory(Category category) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		session.beginTransaction();
		
		long id = (long)session.save(category);		
		
		session.getTransaction().commit();
		
		return id;
	}

	
	@Override
	public List<Category> getAllCategories() {
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			
			@SuppressWarnings("unchecked")
			List<Object[]> results = session.createNativeQuery("SELECT category_id, name, picture_url From Category").getResultList();

			List<Category> categories = new ArrayList<Category>();
			
			results.stream().forEach((record) -> {
				Category c = new Category();			
				c.setCategory_id(((BigInteger)record[0]).longValue());
				c.setName((String) record[1]);
				c.setPicture_url((String) record[2]);
				categories.add(c);
			});
						
			return categories;
			
        } catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
