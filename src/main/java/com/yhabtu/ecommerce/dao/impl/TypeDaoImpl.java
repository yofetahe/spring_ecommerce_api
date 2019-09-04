package com.yhabtu.ecommerce.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.yhabtu.ecommerce.configuration.HibernateUtil;
import com.yhabtu.ecommerce.dao.TypeDao;
import com.yhabtu.ecommerce.model.Type;

@Repository
public class TypeDaoImpl implements TypeDao {

	@Override
	public long addType(Type type) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		session.beginTransaction();
		long id = (long) session.save(type);
		session.getTransaction().commit();
		
		return id;
	}

	@Override
	public List<Type> getCategoryDetailsByCategoryId(int category_id) {
		
		String hql = "SELECT a.type_id, a.name " + 
				"FROM type a, category b, category_type c " + 
				"WHERE category_id = :category_id and a.type_id = c.fk_type_id and b.category_id = c.fk_category_id";
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			
			@SuppressWarnings("unchecked")
			List<Object[]> response = session.createSQLQuery(hql)
					.setParameter("category_id", category_id)
					.getResultList();
	
			List<Type> types = new ArrayList<Type>();
			
			response.stream().forEach((record) -> {
				Type t = new Type();
				t.setType_id(((BigInteger)record[0]).longValue());
				t.setName((String)record[1]);
				types.add(t);
			});
			
			return types;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
