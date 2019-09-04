package com.yhabtu.ecommerce.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.yhabtu.ecommerce.configuration.HibernateUtil;
import com.yhabtu.ecommerce.dao.OrderDao;
import com.yhabtu.ecommerce.model.BillingAddress;
import com.yhabtu.ecommerce.model.CreditCardInformation;
import com.yhabtu.ecommerce.model.Item_Size_Color;
import com.yhabtu.ecommerce.model.OrderDetails;
import com.yhabtu.ecommerce.model.Orders;
import com.yhabtu.ecommerce.model.ShippingAddress;
import com.yhabtu.ecommerce.model.User;

@Repository
public class OrderDaoImpl implements OrderDao {

	@Override
	public boolean submitPurchaseOrder(
			List<Item_Size_Color>itemSizeColors, 
			User user, 
			ShippingAddress shippingAddress, 
			CreditCardInformation creditCardInfo,
			double totalTransaction) {
		System.out.println("======== DAO =======");
		Session session = null;
		Transaction tx = null;
				
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			
			//############ CHECK IF THE USER EXIST OR NOT ############//
			
			
			////>>>> 1. save user -> get user_id
			String user_hql = "INSERT INTO user(first_name, last_name, email) VALUES(:first_name, :last_name, :email)";
			session.createNativeQuery(user_hql)
					.setParameter("first_name", user.getFirst_name())
					.setParameter("last_name", user.getLast_name())
					.setParameter("email", user.getEmail())
					.executeUpdate();
			Long user_id = ((BigInteger)session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult()).longValue();
			System.out.println("1 - " + user_id);
			
			
			//############ CHECK IF THE SHIPPING ADDRESS EXIST OR NOT ############//
			
			
			////>>>> 2. save shippingAddress -> get ID
			String shipping_sql = "INSERT INTO shipping_address(address, state, coutry, zip_code, fk_user_id) "
					+ "VALUES(:address, :state, :coutry, :zip_code, :user_id)";
			session.createNativeQuery(shipping_sql)
			.setParameter("address", shippingAddress.getAddress())
			.setParameter("state", shippingAddress.getState())
			.setParameter("coutry", shippingAddress.getCoutry())
			.setParameter("zip_code", shippingAddress.getZip_code())
			.setParameter("user_id", user_id)
			.executeUpdate();
			Long shipping_address_id = ((BigInteger)session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult()).longValue();
			System.out.println("2 - " + shipping_address_id);
			
			//save billingAddress -> get ID
			
			
			
			//############ CHECK IF THE CREDIT CARD INFO EXIST OR NOT ############//
			
			
			////>>>> 3. save CreditCardInformation -> get ID
			String creditCardInfo_sql = "INSERT INTO credit_card_information(card_account_number, card_holder_name, card_type, expiration_date, security_code, fk_user_id, create_date) "
					+ "VALUES(:card_account_number, :card_holder_name, :card_type, :expiration_date, :security_code, :user_id, :create_date)";
			
			//>> Secure card informations <<//
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			creditCardInfo.setCard_account_number(encoder.encode(creditCardInfo.getCard_account_number()));
			creditCardInfo.setExpiration_date(encoder.encode(creditCardInfo.getExpiration_date()));
			creditCardInfo.setSecurity_code(encoder.encode(creditCardInfo.getSecurity_code()));
			
			session.createNativeQuery(creditCardInfo_sql)
			.setParameter("card_account_number", creditCardInfo.getCard_account_number())
			.setParameter("card_holder_name", creditCardInfo.getCard_holder_name())
			.setParameter("card_type", creditCardInfo.getCard_type())
			.setParameter("expiration_date", creditCardInfo.getExpiration_date())
			.setParameter("security_code", creditCardInfo.getSecurity_code())
			.setParameter("user_id", user_id)
			.setParameter("create_date", new Date())
			.executeUpdate();
			Long credit_card_info_id = ((BigInteger)session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult()).longValue();
			System.out.println("3 - " + credit_card_info_id);
			
			
			//############ CHECK IF THERE IS ENOUGH QUANTITY OR NOT ############//
			
			
			////>>>> 4. save oder -> get ID
			String order_sql = "INSERT INTO orders(shipping_address_id, credit_card_info_id, order_status, total_transaction, user_id, order_date) "
					+ "VALUES(:shipping_address_id, :credit_card_info_id, :order_status, :total_transaction, :user_id, :order_date)";
			session.createNativeQuery(order_sql)
			.setParameter("shipping_address_id", shipping_address_id)
			.setParameter("credit_card_info_id", credit_card_info_id)
			.setParameter("order_status", "ORDERED")
			.setParameter("total_transaction", totalTransaction)
			.setParameter("user_id", user_id)
			.setParameter("order_date", new Date())
			.executeUpdate();
			Long order_id = ((BigInteger)session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult()).longValue();
			System.out.println("4 - " + order_id);
			
			////>>>> 5. save orderDetail
			for(Item_Size_Color izc: itemSizeColors) {
				
				String hql = "SELECT item_size_color_id, remaining_balance FROM Item_Size_Color "
						+ "WHERE fk_item_id = :item_id and fk_size_id = :size_id and fk_color_id = :color_id ";
				
				@SuppressWarnings("unchecked")
				List<Object[]> i = session.createNativeQuery(hql)
						.setParameter("item_id", izc.getItem().getItem_id())
						.setParameter("color_id", izc.getColor().getColor_id())
						.setParameter("size_id", izc.getSize().getSize_id())
						.getResultList();
				
				Item_Size_Color result = new Item_Size_Color();				
				i.stream().forEach((record) -> {
					result.setItem_size_color_id(((BigInteger)record[0]).longValue());
					result.setQuantity((Integer)record[1]);
				});
				System.out.println("5 - " + result.getItem_size_color_id());
				
				////>>>> Inserting Order Details
				String order_detail_sql = "INSERT INTO orders_details(item_quantity, unit_price, fk_item_size_color_id, fk_order_id, order_date) "
						+ "VALUES(:item_quantity, :unit_price, :item_size_color_id, :order_id, :order_date)";
				session.createNativeQuery(order_detail_sql)
				.setParameter("item_quantity", izc.getQuantity())
				.setParameter("unit_price", izc.getItem().getPrice())
				.setParameter("item_size_color_id", result.getItem_size_color_id())
				.setParameter("order_id", order_id)
				.setParameter("order_date", new Date())
				.executeUpdate();
				Long order_detail_id = ((BigInteger)session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult()).longValue();
				System.out.println("6 - " + order_detail_id);

				////>>>> 6. Updating Remaining_balance in Item_Size_Color
				String update_balance = "UPDATE Item_Size_Color SET remaining_balance = :remaining_balance WHERE item_size_color_id = :item_size_color_id";
				int balance = result.getQuantity() - izc.getQuantity();
				session.createNativeQuery(update_balance)
					.setParameter("remaining_balance", balance)
					.setParameter("item_size_color_id", result.getItem_size_color_id())
					.executeUpdate();		
				System.out.println(balance);
			}
			tx.commit();
			return true;
			
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}
		return false;
	}

}
