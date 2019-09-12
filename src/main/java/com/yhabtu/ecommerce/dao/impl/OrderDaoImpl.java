package com.yhabtu.ecommerce.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.yhabtu.ecommerce.configuration.HibernateUtil;
import com.yhabtu.ecommerce.dao.OrderDao;
import com.yhabtu.ecommerce.model.BillingShippingAddress;
import com.yhabtu.ecommerce.model.CreditCardInformation;
import com.yhabtu.ecommerce.model.Item_Size_Color;
import com.yhabtu.ecommerce.model.OrderDetails;
import com.yhabtu.ecommerce.model.Orders;
import com.yhabtu.ecommerce.model.User;

@Repository
public class OrderDaoImpl implements OrderDao {

	@Override
	public boolean submitPurchaseOrder(List<Item_Size_Color> itemSizeColors, User user,
			BillingShippingAddress shippingAddress, CreditCardInformation creditCardInfo, double totalTransaction,
			BillingShippingAddress billingAddress) {
		System.out.println("======== DAO =======");
		Session session = null;
		Transaction tx = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();

			// ############ USER - CHECK IF THE USER EXIST OR NOT ############//

			UserDaoImpl u = new UserDaoImpl();
			User userAlreadyExist = u.getUserByEmail(user.getEmail());

			Long user_id = (long) 0;

			if (userAlreadyExist != null) {

				user_id = userAlreadyExist.getUser_id();

			} else {

				//// >>>> 1. save user -> get user_id
				String user_hql = "INSERT INTO user(first_name, last_name, email) VALUES(:first_name, :last_name, :email)";
				session.createNativeQuery(user_hql).setParameter("first_name", user.getFirst_name())
						.setParameter("last_name", user.getLast_name()).setParameter("email", user.getEmail())
						.executeUpdate();
				user_id = ((BigInteger) session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult()).longValue();
			}
			System.out.println("1 - " + user_id);

			// ############ ADDRESS - CHECK IF THE SHIPPING & BILLING ADDRESS EXIST OR NOT
			// #### //

			String check_address_hql = "SELECT address_id, zip_code FROM billing_shipping_address "
					+ "WHERE address = :address and zip_code = :zip_code and fk_user_id = :user_id";

			@SuppressWarnings("unchecked")
			List<Object[]> existedAddress = session.createNativeQuery(check_address_hql)
					.setParameter("address", shippingAddress.getAddress())
					.setParameter("zip_code", shippingAddress.getZip_code()).setParameter("user_id", user_id)
					.getResultList();

			Long billing_address_id = (long) 0;
			Long shipping_address_id = (long) 0;

			if (existedAddress.size() > 0) {

				// >>> shipping-billing address already exist
				BillingShippingAddress result = new BillingShippingAddress();
				existedAddress.stream().forEach((record) -> {
					result.setAddress_id(((BigInteger) record[0]).longValue());
				});

				shipping_address_id = result.getAddress_id();
			} else {

				//// >>>> 2. save shippingAddress -> get ID
				String shipping_sql = "INSERT INTO billing_shipping_address(address, state, country, zip_code, fk_user_id) "
						+ "VALUES(:address, :state, :country, :zip_code, :user_id)";
				session.createNativeQuery(shipping_sql).setParameter("address", shippingAddress.getAddress())
						.setParameter("state", shippingAddress.getState())
						.setParameter("country", shippingAddress.getCountry())
						.setParameter("zip_code", shippingAddress.getZip_code()).setParameter("user_id", user_id)
						.executeUpdate();
				shipping_address_id = ((BigInteger) session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult())
						.longValue();
			}
			System.out.println("2 - " + shipping_address_id);

			if (billingAddress.getAddress() == null) {

				//// **** IF THE SHIPPING ADDRESS IS SAME AS BILLING ADDRESS ****////
				billing_address_id = shipping_address_id;

			} else {

				//// **** IF THE SHIPPING ADDRESS IS NOT SAME AS BILLING ADDRESS ****////
				//// >>> checking billing address if already exist or not <<<///
				String check_b_address_hql = "SELECT address_id, zip_code FROM billing_shipping_address "
						+ "WHERE address = :address and zip_code = :zip_code and fk_user_id = :user_id";

				@SuppressWarnings("unchecked")
				List<Object[]> existedBillingAddress = session.createNativeQuery(check_b_address_hql)
						.setParameter("address", billingAddress.getAddress())
						.setParameter("zip_code", billingAddress.getZip_code()).setParameter("user_id", user_id)
						.getResultList();

				if (existedBillingAddress.size() > 0) {

					// >>> shipping-billing address already exist
					BillingShippingAddress b_result = new BillingShippingAddress();
					existedBillingAddress.stream().forEach((record) -> {
						b_result.setAddress_id(((BigInteger) record[0]).longValue());
					});

					billing_address_id = b_result.getAddress_id();
				} else {

					// >>> save billingAddress <<<///
					String billing_sql = "INSERT INTO billing_shipping_address(address, state, country, zip_code, fk_user_id) "
							+ "VALUES(:address, :state, :country, :zip_code, :user_id)";
					session.createNativeQuery(billing_sql).setParameter("address", shippingAddress.getAddress())
							.setParameter("state", shippingAddress.getState())
							.setParameter("country", shippingAddress.getCountry())
							.setParameter("zip_code", shippingAddress.getZip_code()).setParameter("user_id", user_id)
							.executeUpdate();
					billing_address_id = ((BigInteger) session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult())
							.longValue();
				}
			}
			System.out.println("2 - " + billing_address_id);

			// ############ CCI - CHECK IF THE CREDIT CARD INFO EXIST OR NOT ############//

			// >> Secure card informations <<//
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			String check_CCI_hql = "SELECT credit_card_info_id, card_type, card_account_number FROM credit_card_information "
					+ "WHERE fk_user_id = :user_id";

			@SuppressWarnings("unchecked")
			List<Object[]> existedCCI = session.createNativeQuery(check_CCI_hql).setParameter("user_id", user_id)
					.getResultList();

			// credit card information already exist
			CreditCardInformation cci = new CreditCardInformation();
			existedCCI.stream().forEach(record -> {
				String cardType = (String) record[1];
				String cardNumber = (String) record[2];
				if (creditCardInfo.getCard_type().equalsIgnoreCase(cardType)
						&& encoder.matches(creditCardInfo.getCard_account_number(), cardNumber)) {
					cci.setCredit_card_info_id(((BigInteger) record[0]).longValue());
				}
			});

			Long credit_card_info_id = (long) 0;

			if (cci.getCredit_card_info_id() > 0) {

				credit_card_info_id = cci.getCredit_card_info_id();

			} else {

				//// >>>> 3. save CreditCardInformation -> get ID
				String creditCardInfo_sql = "INSERT INTO credit_card_information(card_account_number, card_holder_name, card_type, expiration_date, security_code, fk_user_id, create_date, card_last_four_digit, fk_address_id) "
						+ "VALUES(:card_account_number, :card_holder_name, :card_type, :expiration_date, :security_code, :user_id, :create_date, :card_last_four_digit, :address_id)";

				String cardNumber = creditCardInfo.getCard_account_number();
				String last_4_digit = cardNumber.substring(cardNumber.length() - 4, cardNumber.length());

				// *** encoding CCI critical information ***//
				creditCardInfo.setCard_account_number(encoder.encode(creditCardInfo.getCard_account_number()));
				creditCardInfo.setExpiration_date(encoder.encode(creditCardInfo.getExpiration_date()));
				creditCardInfo.setSecurity_code(encoder.encode(creditCardInfo.getSecurity_code()));

				session.createNativeQuery(creditCardInfo_sql)
						.setParameter("card_account_number", creditCardInfo.getCard_account_number())
						.setParameter("card_holder_name", creditCardInfo.getCard_holder_name())
						.setParameter("card_type", creditCardInfo.getCard_type())
						.setParameter("expiration_date", creditCardInfo.getExpiration_date())
						.setParameter("security_code", creditCardInfo.getSecurity_code())
						.setParameter("user_id", user_id).setParameter("create_date", new Date())
						.setParameter("card_last_four_digit", last_4_digit)
						.setParameter("address_id", billing_address_id).executeUpdate();
				credit_card_info_id = ((BigInteger) session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult())
						.longValue();
			}
			System.out.println("3 - " + credit_card_info_id);

			// ############ ITEM BALANCE - CHECK IF THERE IS ENOUGH QUANTITY OR NOT
			// ############//

			for (Item_Size_Color izc : itemSizeColors) {
				String check_item_qty_hql = "SELECT item_size_color_id, remaining_balance FROM Item_Size_Color "
						+ "WHERE fk_item_id = :item_id and fk_size_id = :size_id and fk_color_id = :color_id ";
				@SuppressWarnings("unchecked")
				List<Object[]> itemQty = session.createNativeQuery(check_item_qty_hql)
						.setParameter("item_id", izc.getItem().getItem_id())
						.setParameter("color_id", izc.getColor().getColor_id())
						.setParameter("size_id", izc.getSize().getSize_id()).getResultList();

				Item_Size_Color result = new Item_Size_Color();
				itemQty.stream().forEach((record) -> {
					result.setItem_size_color_id(((BigInteger) record[0]).longValue());
					result.setRemaining_balance((Integer) record[1]);
				});

				if (result.getRemaining_balance() < izc.getQuantity()) {
					///// ##### BLOCK ALL TRANSACTION OR REMOVE THE ITEM #####/////
					throw new Exception();
				}
			}

			//// >>>> 4. save oder -> get ID
			String order_sql = "INSERT INTO orders(fk_billing_address_id, fk_shipping_address_id, fk_credit_card_info_id, order_status, total_transaction, fk_user_id, order_date) "
					+ "VALUES(:billing_address_id, :shipping_address_id, :credit_card_info_id, :order_status, :total_transaction, :user_id, :order_date)";
			session.createNativeQuery(order_sql).setParameter("billing_address_id", billing_address_id)
					.setParameter("shipping_address_id", shipping_address_id)
					.setParameter("credit_card_info_id", credit_card_info_id).setParameter("order_status", "IN-PROCESS")
					.setParameter("total_transaction", totalTransaction).setParameter("user_id", user_id)
					.setParameter("order_date", new Date()).executeUpdate();
			Long order_id = ((BigInteger) session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult()).longValue();
			System.out.println("4 - " + order_id);

			//// >>>> 5. save orderDetail
			for (Item_Size_Color izc : itemSizeColors) {

				String hql = "SELECT item_size_color_id, remaining_balance FROM Item_Size_Color "
						+ "WHERE fk_item_id = :item_id and fk_size_id = :size_id and fk_color_id = :color_id ";

				@SuppressWarnings("unchecked")
				List<Object[]> i = session.createNativeQuery(hql).setParameter("item_id", izc.getItem().getItem_id())
						.setParameter("color_id", izc.getColor().getColor_id())
						.setParameter("size_id", izc.getSize().getSize_id()).getResultList();

				Item_Size_Color result = new Item_Size_Color();
				i.stream().forEach((record) -> {
					result.setItem_size_color_id(((BigInteger) record[0]).longValue());
					result.setRemaining_balance((Integer) record[1]);
				});
				System.out.println("5 - " + result.getItem_size_color_id());

				//// >>>> Inserting Order Details
				String order_detail_sql = "INSERT INTO orders_details(item_quantity, unit_price, fk_item_size_color_id, fk_order_id, order_date, item_status) "
						+ "VALUES(:item_quantity, :unit_price, :item_size_color_id, :order_id, :order_date, 'ACTIVE')";
				session.createNativeQuery(order_detail_sql).setParameter("item_quantity", izc.getQuantity())
						.setParameter("unit_price", izc.getItem().getPrice())
						.setParameter("item_size_color_id", result.getItem_size_color_id())
						.setParameter("order_id", order_id).setParameter("order_date", new Date()).executeUpdate();
				Long order_detail_id = ((BigInteger) session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult())
						.longValue();
				System.out.println("6 - " + order_detail_id);

				//// >>>> 6. Updating Remaining_balance in Item_Size_Color
				String update_balance = "UPDATE Item_Size_Color SET remaining_balance = :remaining_balance WHERE item_size_color_id = :item_size_color_id";
				int balance = result.getRemaining_balance() - izc.getQuantity();
				session.createNativeQuery(update_balance).setParameter("remaining_balance", balance)
						.setParameter("item_size_color_id", result.getItem_size_color_id()).executeUpdate();
				System.out.println("7 - " + balance);

				//// >>>> 7. Delete the item from saved list if it was saved item
				String delete_saved_item = "DELETE FROM saved_item WHERE fk_user_id = :user_id and fk_item_size_color_id = :item_size_color_id";
				session.createNativeQuery(delete_saved_item).setParameter("user_id", user_id)
						.setParameter("item_size_color_id", result.getItem_size_color_id()).executeUpdate();
			}

			tx.commit();
			return true;

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Map<String, String>> getOrderedItemsByUserId(int user_id) {

		List<Map<String, String>> ordered_items = new ArrayList<Map<String, String>>();

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			String billing_sql = "SELECT a.order_id, a.order_status, a.order_date, a.total_transaction, "
					+ "d.item_id, d.brand, d.description, d.name as item_name, d.picture_url, d.price, "
					+ "b.order_detail_id, b.item_quantity, b.unit_price, " + "e.size_id, e.name as size, "
					+ "f.color_id, f.name as color, " + "c.item_size_color_id "
					+ "FROM orders a, orders_details b, item_size_color c, item d, size e, color f "
					+ "WHERE fk_user_id = :user_id and a.order_id = b.fk_order_id and  "
					+ "b.fk_item_size_color_id = c.item_size_color_id and c.fk_item_id = d.item_id and "
					+ "c.fk_size_id = e.size_id and c.fk_color_id = f.color_id and a.order_status = 'IN-PROCESS' and b.item_status = 'ACTIVE'";

			@SuppressWarnings("unchecked")
			List<Object[]> result = session.createSQLQuery(billing_sql).setParameter("user_id", user_id).list();

			result.stream().forEach((record) -> {
				Map<String, String> item = new HashMap<String, String>();
				item.put("order_id", ((BigInteger) record[0]).toString());
				item.put("order_status", (String) record[1]);
				item.put("order_date", ((Date) record[2]).toString());
				item.put("total_transaction", ((Double) record[3]).toString());

				item.put("item_id", ((BigInteger) record[4]).toString());
				item.put("brand", (String) record[5]);
				item.put("description", (String) record[6]);
				item.put("item_name", (String) record[7]);
				item.put("picture_url", (String) record[8]);
				item.put("price", ((Double) record[9]).toString());

				item.put("order_detail_id", ((BigInteger) record[10]).toString());
				item.put("item_quantity", ((Integer) record[11]).toString());
				item.put("unit_price", ((Double) record[12]).toString());

				item.put("size_id", ((BigInteger) record[13]).toString());
				item.put("size", (String) record[14]);

				item.put("color_id", ((BigInteger) record[15]).toString());
				item.put("color", (String) record[16]);

				item.put("item_size_color_id", ((BigInteger) record[17]).toString());

				ordered_items.add(item);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ordered_items;
	}

	@Override
	public boolean cancelOrderedItem(int order_id, int order_detail_id, int item_size_color_id) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			Transaction tx = session.beginTransaction();

			// >>>>>> get unit_price, order_id, item_size_color_id to validate the given
			// input is correct or not <<<<<<//
			String od_sql = "SELECT unit_price, fk_order_id, fk_item_size_color_id, item_quantity "
					+ "FROM orders_details WHERE order_detail_id = :order_detail_id";

			@SuppressWarnings("unchecked")
			List<Object[]> result = session.createNativeQuery(od_sql).setParameter("order_detail_id", order_detail_id)
					.getResultList();

			OrderDetails od = new OrderDetails();

			result.stream().forEach((record) -> {
				od.setUnit_price((Double) record[0]);

				Orders o = new Orders();
				o.setOrder_id(((BigInteger) record[1]).longValue());
				od.setOrder(o);

				Item_Size_Color isc = new Item_Size_Color();
				isc.setItem_size_color_id(((BigInteger) record[2]).longValue());
				od.setItem_size_color(isc);

				od.setItem_quantity((Integer) record[3]);
			});

			/* to validate the given id's are related or not */
			if (od.getOrder().getOrder_id() != order_id
					|| od.getItem_size_color().getItem_size_color_id() != item_size_color_id) {

				return false;
			}
			System.out.println("STEP 1 >>> " + od.getUnit_price());
			// >>>>>> update order table ---> update total transaction <<<<<<//
			String order_select_sql = "SELECT order_id, total_transaction FROM orders WHERE order_id = :order_id";

			@SuppressWarnings("unchecked")
			List<Object[]> orders = session.createNativeQuery(order_select_sql).setParameter("order_id", order_id)
					.getResultList();

			Orders o = new Orders();
			orders.stream().forEach(record -> {
				o.setTotal_transaction((Double) record[1]);
			});

			String order_sql = "UPDATE orders SET total_transaction = :total_transaction WHERE order_id = :order_id";
			session.createNativeQuery(order_sql)
					.setParameter("total_transaction", o.getTotal_transaction() - od.getUnit_price())
					.setParameter("order_id", order_id).executeUpdate();
			System.out.println("STEP 2 >>> " + o.getTotal_transaction());
			// >>>>>> update item_color_size ---> update remaining balance <<<<<<//
			String item_color_size_select_sql = "SELECT item_size_color_id, remaining_balance "
					+ "FROM item_size_color WHERE item_size_color_id = :item_size_color_id";

			@SuppressWarnings("unchecked")
			List<Object[]> item_color_size = session.createNativeQuery(item_color_size_select_sql)
					.setParameter("item_size_color_id", item_size_color_id).getResultList();

			Item_Size_Color isc = new Item_Size_Color();
			item_color_size.stream().forEach(record -> {
				isc.setRemaining_balance((Integer) record[1]);
			});

			String item_color_size_update_sql = "UPDATE item_size_color SET remaining_balance = :remaining_balance "
					+ "WHERE item_size_color_id = :item_size_color_id";
			session.createNativeQuery(item_color_size_update_sql)
					.setParameter("remaining_balance", isc.getRemaining_balance() + od.getItem_quantity())
					.setParameter("item_size_color_id", item_size_color_id).executeUpdate();
			System.out.println("STEP 3 >>> " + isc.getRemaining_balance() + " | " + od.getItem_quantity());
			// >>>>>> update order_details ---> update item status <<<<<<//
			String order_detail_update_sql = "UPDATE orders_details SET item_status = 'CANCELED' "
					+ "WHERE order_detail_id = :order_detail_id";
			session.createNativeQuery(order_detail_update_sql).setParameter("order_detail_id", order_detail_id)
					.executeUpdate();
			System.out.println("STEP 4");
			tx.commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean submitActiveUserPurchaseOrder(List<Item_Size_Color> itemSizeColors,
			BillingShippingAddress shippingAddress, int credit_card_info_id, int billingAddressId, int user_id, double totalTransaction) {
		System.out.println(credit_card_info_id + " | " + user_id + " | " + totalTransaction);

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			Transaction tx = session.beginTransaction();

			// ############ ADD SHIPPING ADDRESS ############//
			
			Long shipping_address_id = (long) 0;

			if (shippingAddress.getAddress() == null) {

				//// **** IF THE SHIPPING ADDRESS IS SAME AS BILLING ADDRESS ****////
				shipping_address_id = (long) billingAddressId;

			} else {

				//// **** IF THE SHIPPING ADDRESS IS NOT SAME AS BILLING ADDRESS ****////
				//// >>> checking billing address if already exist or not <<<///
				String check_b_address_hql = "SELECT address_id, zip_code FROM billing_shipping_address "
						+ "WHERE address = :address and zip_code = :zip_code and fk_user_id = :user_id";

				@SuppressWarnings("unchecked")
				List<Object[]> existedShippingAddress = session.createNativeQuery(check_b_address_hql)
						.setParameter("address", shippingAddress.getAddress())
						.setParameter("zip_code", shippingAddress.getZip_code()).setParameter("user_id", user_id)
						.getResultList();

				if (existedShippingAddress.size() > 0) {

					// >>> shipping-billing address already exist
					BillingShippingAddress b_result = new BillingShippingAddress();
					existedShippingAddress.stream().forEach((record) -> {
						b_result.setAddress_id(((BigInteger) record[0]).longValue());
					});

					shipping_address_id = b_result.getAddress_id();
				} else {

					// >>> save shippingAddress <<<///
					String shipping_sql = "INSERT INTO billing_shipping_address(address, state, country, zip_code, fk_user_id) "
							+ "VALUES(:address, :state, :country, :zip_code, :user_id)";
					session.createNativeQuery(shipping_sql).setParameter("address", shippingAddress.getAddress())
							.setParameter("state", shippingAddress.getState())
							.setParameter("country", shippingAddress.getCountry())
							.setParameter("zip_code", shippingAddress.getZip_code()).setParameter("user_id", user_id)
							.executeUpdate();
					shipping_address_id = ((BigInteger) session.createSQLQuery("SELECT LAST_INSERT_ID()")
							.uniqueResult()).longValue();
				}
			}
			System.out.println("2 - " + shipping_address_id);

			// ############ ITEM BALANCE - CHECK IF THERE IS ENOUGH QUANTITY OR NOT ############//

			for (Item_Size_Color izc : itemSizeColors) {
				String check_item_qty_hql = "SELECT item_size_color_id, remaining_balance FROM Item_Size_Color "
						+ "WHERE fk_item_id = :item_id and fk_size_id = :size_id and fk_color_id = :color_id ";
				@SuppressWarnings("unchecked")
				List<Object[]> itemQty = session.createNativeQuery(check_item_qty_hql)
						.setParameter("item_id", izc.getItem().getItem_id())
						.setParameter("color_id", izc.getColor().getColor_id())
						.setParameter("size_id", izc.getSize().getSize_id()).getResultList();

				Item_Size_Color result = new Item_Size_Color();
				itemQty.stream().forEach((record) -> {
					result.setItem_size_color_id(((BigInteger) record[0]).longValue());
					result.setRemaining_balance((Integer) record[1]);
				});

				if (result.getRemaining_balance() < izc.getQuantity()) {
					///// ##### BLOCK ALL TRANSACTION OR REMOVE THE ITEM #####/////
					throw new Exception();
				}
			}

			// ############ SAVE ORDER AND ORDER-DETAIL ############//
			
			//// >>>> save order
			String order_sql = "INSERT INTO orders(fk_billing_address_id, fk_shipping_address_id, fk_credit_card_info_id, order_status, total_transaction, fk_user_id, order_date) "
					+ "VALUES(:billing_address_id, :shipping_address_id, :credit_card_info_id, :order_status, :total_transaction, :user_id, :order_date)";
			session.createNativeQuery(order_sql)
					.setParameter("billing_address_id", billingAddressId)
					.setParameter("shipping_address_id", shipping_address_id)
					.setParameter("credit_card_info_id", credit_card_info_id)
					.setParameter("order_status", "IN-PROCESS")
					.setParameter("total_transaction", totalTransaction)
					.setParameter("user_id", user_id)
					.setParameter("order_date", new Date())
					.executeUpdate();
			Long order_id = ((BigInteger) session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult()).longValue();
			System.out.println("4 - " + order_id);

			//// >>>> save orderDetail
			for (Item_Size_Color izc : itemSizeColors) {

				String hql = "SELECT item_size_color_id, remaining_balance FROM Item_Size_Color "
						+ "WHERE fk_item_id = :item_id and fk_size_id = :size_id and fk_color_id = :color_id ";

				@SuppressWarnings("unchecked")
				List<Object[]> i = session.createNativeQuery(hql).setParameter("item_id", izc.getItem().getItem_id())
						.setParameter("color_id", izc.getColor().getColor_id())
						.setParameter("size_id", izc.getSize().getSize_id()).getResultList();

				Item_Size_Color result = new Item_Size_Color();
				i.stream().forEach((record) -> {
					result.setItem_size_color_id(((BigInteger) record[0]).longValue());
					result.setRemaining_balance((Integer) record[1]);
				});
				System.out.println("5 - " + result.getItem_size_color_id());

				//// >>>> Inserting Order Details
				String order_detail_sql = "INSERT INTO orders_details(item_quantity, unit_price, fk_item_size_color_id, fk_order_id, order_date, item_status) "
						+ "VALUES(:item_quantity, :unit_price, :item_size_color_id, :order_id, :order_date, 'ACTIVE')";
				session.createNativeQuery(order_detail_sql).setParameter("item_quantity", izc.getQuantity())
						.setParameter("unit_price", izc.getItem().getPrice())
						.setParameter("item_size_color_id", result.getItem_size_color_id())
						.setParameter("order_id", order_id).setParameter("order_date", new Date()).executeUpdate();
				Long order_detail_id = ((BigInteger) session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult())
						.longValue();
				System.out.println("6 - " + order_detail_id);

				//// >>>> 6. Updating Remaining_balance in Item_Size_Color
				String update_balance = "UPDATE Item_Size_Color SET remaining_balance = :remaining_balance WHERE item_size_color_id = :item_size_color_id";
				int balance = result.getRemaining_balance() - izc.getQuantity();
				session.createNativeQuery(update_balance).setParameter("remaining_balance", balance)
						.setParameter("item_size_color_id", result.getItem_size_color_id()).executeUpdate();
				System.out.println("7 - " + balance);

				//// >>>> 7. Delete the item from saved list if it was saved item
				String delete_saved_item = "DELETE FROM saved_item WHERE fk_user_id = :user_id and fk_item_size_color_id = :item_size_color_id";
				session.createNativeQuery(delete_saved_item).setParameter("user_id", user_id)
						.setParameter("item_size_color_id", result.getItem_size_color_id()).executeUpdate();
			}

			tx.commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
