package com.yhabtu.ecommerce.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.yhabtu.ecommerce.configuration.HibernateUtil;
import com.yhabtu.ecommerce.dao.UserDao;
import com.yhabtu.ecommerce.model.BillingShippingAddress;
import com.yhabtu.ecommerce.model.CreditCardInformation;
import com.yhabtu.ecommerce.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Override
	public long addUser(User user) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		User existedUser = getUserByEmail(user.getEmail());
		
		if(existedUser != null && existedUser.getPassword() != null) {
			return existedUser.getUser_id();
		}
		
		if(existedUser != null && existedUser.getPassword() == null) {
			
			String user_update_sql = "UPDATE user SET password = :password WHERE user_id = :user_id";
			session.createNativeQuery(user_update_sql)
				.setParameter("password", user.getPassword())
				.setParameter("user_id", existedUser.getUser_id())
				.executeUpdate();
			
			session.getTransaction().commit();
			
			return existedUser.getUser_id();
		}
		
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
				u.setUser_id(((BigInteger)record[0]).longValue());
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

	@Override
	public List<CreditCardInformation> getCreditCardInfoByUserId(int user_id) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		List<CreditCardInformation> cc_info = new ArrayList<CreditCardInformation>();

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

//			String cci_sql = "SELECT credit_card_info_id, card_last_four_digit, card_holder_name, card_type, expiration_date, security_code "
//					+ "FROM credit_card_information WHERE fk_user_id = :user_id";
			
			String cci_sql = "SELECT a.credit_card_info_id, a.card_last_four_digit, a.card_holder_name, a.card_type, a.expiration_date, a.security_code, " +
									"b.address_id, b.address, b.state, b.country, b.zip_code " + 
							"FROM credit_card_information a, billing_shipping_address b " + 
							"WHERE a.fk_address_id = b.address_id and a.fk_user_id = :user_id";
			
			@SuppressWarnings("unchecked")
			List<Object[]> result = session.createNativeQuery(cci_sql)
					.setParameter("user_id", user_id).getResultList();
			
			result.stream().forEach((record) -> {				
				CreditCardInformation cci = new CreditCardInformation();
				cci.setCredit_card_info_id(((BigInteger)record[0]).longValue());				
				cci.setCard_last_four_digit((String)record[1]);				
				cci.setCard_holder_name((String)record[2]);
				cci.setCard_type((String)record[3]);
				cci.setExpiration_date((String)record[4]);
				cci.setSecurity_code((String)record[5]);
				
				BillingShippingAddress bsa = new BillingShippingAddress();
				bsa.setAddress_id(((BigInteger)record[6]).longValue());	
				bsa.setAddress((String)record[7]);
				bsa.setState((String)record[8]);				
				bsa.setCountry((String)record[9]);
				bsa.setZip_code((Integer)record[10]);
				
				cci.setBillingShippingAddress(bsa);
				cc_info.add(cci);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cc_info;
	}

	@Override
	public List<BillingShippingAddress> getBillingAddressByUserId(int user_id) {

		List<BillingShippingAddress> billing_info = new ArrayList<BillingShippingAddress>();

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			String billing_sql = "SELECT address_id, address, city, state, country, zip_code "
					+ "FROM billing_shipping_address WHERE fk_user_id = :user_id";
			
			@SuppressWarnings("unchecked")
			List<Object[]> result = session.createNativeQuery(billing_sql)
					.setParameter("user_id", user_id).getResultList();
			
			result.stream().forEach((record) -> {				
				BillingShippingAddress billing = new BillingShippingAddress();
				billing.setAddress_id(((BigInteger)record[0]).longValue());
				billing.setAddress((String)record[1]);
				billing.setCity((String)record[2]);
				billing.setState((String)record[3]);
				billing.setCountry((String)record[4]);
				billing.setZip_code((Integer)record[5]);
				billing_info.add(billing);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		return billing_info;
	}

}
