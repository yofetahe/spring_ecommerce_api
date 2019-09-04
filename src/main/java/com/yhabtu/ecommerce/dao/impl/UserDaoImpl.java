package com.yhabtu.ecommerce.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.yhabtu.ecommerce.configuration.HibernateUtil;
import com.yhabtu.ecommerce.dao.UserDao;
import com.yhabtu.ecommerce.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Override
	public long addUser(User user) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		String user_sql = "INSERT INTO user(first_name, last_name, email, password) VALUES(:first_name, :last_name, :email, :password)";
		
		session.createNativeQuery(user_sql)
		.setParameter("first_name", user.getFirst_name())
		.setParameter("last_name", user.getLast_name())
		.setParameter("email", user.getEmail())
		.setParameter("password", user.getPassword())
		.executeUpdate();
		
		session.getTransaction().commit();
		
		return ((BigInteger)session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult()).longValue();
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByEmail(String email) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			@SuppressWarnings("unchecked")
			List<Object[]> result = session.createNativeQuery("SELECT user_id, first_name, last_name, password FROM User WHERE email = :email")
					.setParameter("email", email).getResultList();
			
			List<User> users = new ArrayList<User>();

			result.stream().forEach((record) -> {				
				User u = new User();
				u.setUser_id(((Integer)record[0]).longValue());
				u.setFirst_name((String)record[1]);
				u.setLast_name((String)record[2]);
				u.setPassword((String)record[3]);
				users.add(u);
			});

			if (users.size() > 0) {
				return users.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
