package com.yhabtu.ecommerce.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.yhabtu.ecommerce.configuration.HibernateUtil;
import com.yhabtu.ecommerce.dao.ItemsDao;
import com.yhabtu.ecommerce.model.Item;
import com.yhabtu.ecommerce.model.Item_Size_Color;

@Repository
public class ItemsDaoImpl implements ItemsDao {

	@Override
	public long addItem(Item item) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		long id = (long) session.save(item);
		session.getTransaction().commit();

		return id;
	}

	@Override
	public List<Item> getAllItems() {

		return null;
	}

	@Override
	public List<Item> getItemsByCategoryId(int category_id) {
		System.out.println(">>>>>>>>>>>>>>>>>> " + category_id);
//		String hql_color_size_by_item_id = "SELECT a.item_size_color_id, a.remaining_balance, b.color_id, b.name, c.size_id, c.name " + 
//				"FROM item_size_color a, color b, size c " + 
//				"where fk_item_id = 7 and a.fk_color_id = b.color_id and a.fk_size_id = c.size_id";

		String hql_item = "SELECT distinct a.item_id, a.brand, a.description, a.dislike as dislikes, a.likes, a.name, a.picture_url, a.price " + 
				"FROM item a, category_type c, item_size_color b " + 
				"where a.fk_category_type_id = c.category_type_id and a.item_id = b.fk_item_id and c.fk_category_id = :category_id and b.remaining_balance > 0 " + 
				"order by item_id";

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			@SuppressWarnings("unchecked")
			List<Object[]> results = session.createSQLQuery(hql_item).setParameter("category_id", category_id)
					.getResultList();

			List<Item> items = new ArrayList<Item>();

			results.stream().forEach((record) -> {
				Item i = new Item();
				i.setItem_id(((BigInteger) record[0]).longValue());
				System.out.println("ID >>> "+i.getItem_id());
				i.setBrand((String) record[1]);
				i.setDescription((String) record[2]);
				i.setDislikes((Integer) record[3]);
				i.setLikes((Integer) record[4]);
				i.setName((String) record[5]);
				i.setPicture_url((String) record[6]);
				i.setPrice((Double) record[7]);				
				items.add(i);
			});
			
			return items;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Item> getItemsByCategoryTypeId(int category_id, int type_id) {

		String hql = "SELECT a.item_id, a.brand, a.description, a.dislike as dislikes, a.likes, a.name, a.picture_url, a.price "
				+ "FROM item a, category_type c "
				+ "where a.fk_category_type_id = c.category_type_id and c.fk_category_id = :category_id and c.fk_type_id = :type_id";

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			@SuppressWarnings("unchecked")
			List<Object[]> results = session.createSQLQuery(hql).setParameter("category_id", category_id)
					.setParameter("type_id", type_id).getResultList();

			List<Item> items = new ArrayList<Item>();

			results.stream().forEach((record) -> {
				Item i = new Item();
				i.setItem_id(((BigInteger) record[0]).longValue());
				i.setBrand((String) record[1]);
				i.setDescription((String) record[2]);
				i.setDislikes((Integer) record[3]);
				i.setLikes((Integer) record[4]);
				i.setName((String) record[5]);
				i.setPicture_url((String) record[6]);
				i.setPrice((Double) record[7]);
				items.add(i);
			});

			return items;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean saveItemForLaterPurchase(int item_id, int color_id, int size_id, int user_id) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			Transaction tx = session.beginTransaction();

			String hql = "SELECT item_size_color_id, fk_item_id "
					+ "FROM Item_Size_Color "
					+ "WHERE fk_item_id = :item_id and fk_color_id = :color_id and fk_size_id = :size_id";

			// get Item_Color_Size Object
			@SuppressWarnings("unchecked")
			List<Object[]> results = session.createNativeQuery(hql).setParameter("item_id", item_id)
					.setParameter("color_id", color_id).setParameter("size_id", size_id).getResultList();

			Item_Size_Color isc = new Item_Size_Color();
			results.stream().forEach((record) -> {
				isc.setItem_size_color_id(((BigInteger) record[0]).longValue());				
			});

			// saving save-item
			int value = session.createSQLQuery(
					"INSERT INTO saved_item(fk_user_id, fk_item_size_color_id) VALUES(:user_id, :item_size_color_id)")
					.setParameter("user_id", user_id).setParameter("item_size_color_id", isc.getItem_size_color_id())
					.executeUpdate();

			tx.commit();
			session.close();

			if (value > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int getItemRemainingBalance(int item_id, int color_id, int size_id) {

		String hql = "SELECT remaining_balance, item_size_color_id "
				+ "FROM Item_Size_Color "
				+ "WHERE fk_item_id = :item_id and fk_color_id = :color_id and fk_size_id = :size_id";
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			
			@SuppressWarnings("unchecked")
			List<Object[]> response = session.createNativeQuery(hql).setParameter("item_id", item_id)
					.setParameter("color_id", color_id).setParameter("size_id", size_id).getResultList();

			Item_Size_Color isc = new Item_Size_Color();

			response.stream().forEach((record) -> {
				isc.setRemaining_balance((Integer) record[0]);
			});
			
			return isc.getRemaining_balance();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean checkItemForLaterPurchase(int item_id, int color_id, int size_id, int user_id) {
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			String hql = "SELECT fk_user_id, fk_item_size_color_id "
					+ "FROM saved_item "
					+ "WHERE fk_user_id = :user_id and "
					+ "fk_item_size_color_id = (SELECT item_size_color_id, fk_item_id "
												+ "FROM Item_Size_Color "
												+ "WHERE fk_item_id = :item_id and fk_color_id = :color_id and fk_size_id = :size_id)";
						
			@SuppressWarnings("unchecked")
			List<Object[]> results = session.createSQLQuery(hql)
					.setParameter("user_id", user_id)
					.setParameter("item_id", item_id)
					.setParameter("color_id", color_id)
					.setParameter("size_id", size_id)
					.getResultList();

			if (results.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Item> getSavedItemsListByUserId(int user_id) {

		String hql_item = "SELECT c.item_id, c.brand, c.description, c.dislike as dislikes, c.likes, c.name, c.picture_url, c.price, b.item_size_color_id " + 
				"FROM saved_item a, item_size_color b, item c " + 
				"WHERE a.fk_item_size_color_id = b.item_size_color_id and b.fk_item_id = c.item_id and a.fk_user_id = :user_id";

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			@SuppressWarnings("unchecked")
			List<Object[]> results = session.createSQLQuery(hql_item).setParameter("user_id", user_id)
					.getResultList();

			List<Item> items = new ArrayList<Item>();

			results.stream().forEach((record) -> {
				Item i = new Item();
				i.setItem_id(((BigInteger) record[0]).longValue());
				i.setBrand((String) record[1]);
				i.setDescription((String) record[2]);
				i.setDislikes((Integer) record[3]);
				i.setLikes((Integer) record[4]);
				i.setName((String) record[5]);
				i.setPicture_url((String) record[6]);
				i.setPrice((Double) record[7]);
				i.setItem_size_color_id(((BigInteger) record[8]).longValue());
				items.add(i);
			});
			
			return items;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean deleteSavedItem(int user_id, int item_size_color_id) {
		
		Session session = null;
		Transaction tx = null;
		
		try{

			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			
			String hql = "DELETE FROM saved_item WHERE fk_user_id = :user_id and fk_item_size_color_id = :item_size_color_id";
			
			int result = session.createNativeQuery(hql).setParameter("user_id", user_id).setParameter("item_size_color_id", item_size_color_id).executeUpdate();
			
			tx.commit();
			
			if(result > 0)
				return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Item addLikeByItemId(int item_id) {
		
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			
			Transaction tx = session.beginTransaction();
			
			String update_hql = "UPDATE item SET likes = likes + 1 WHERE item_id = :item_id";
			
			int result = session.createNativeQuery(update_hql).setParameter("item_id", item_id).executeUpdate();
			
			Item i = new Item();
			
			if(result > 0) {
			
				String select_hql = "SELECT item_id, brand, description, dislike as dislikes, likes, name, picture_url, price "
						+ "FROM item WHERE item_id = :item_id";
				
				@SuppressWarnings("unchecked")
				List<Object[]> items = session.createNativeQuery(select_hql).setParameter("item_id", item_id).getResultList();
				
				
				items.stream().forEach(record -> {
					i.setItem_id(((BigInteger) record[0]).longValue());
					i.setBrand((String)record[1].toString());
					i.setBrand((String) record[1]);
					i.setDescription((String) record[2]);
					i.setDislikes((Integer) record[3]);
					i.setLikes((Integer) record[4]);
					i.setName((String) record[5]);
					i.setPicture_url((String) record[6]);
					i.setPrice((Double) record[7]);
				});
			}
			
			tx.commit();
			
			return i;
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Item addDislikeByItemId(int item_id) {
		
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			
			Transaction tx = session.beginTransaction();
			
			String update_hql = "UPDATE item SET dislike = dislike + 1 WHERE item_id = :item_id";
			
			int result = session.createNativeQuery(update_hql).setParameter("item_id", item_id).executeUpdate();
			
			Item i = new Item();
			
			if(result > 0) {
			
				String select_hql = "SELECT item_id, brand, description, dislike as dislikes, likes, name, picture_url, price "
						+ "FROM item WHERE item_id = :item_id";
				
				@SuppressWarnings("unchecked")
				List<Object[]> items = session.createNativeQuery(select_hql).setParameter("item_id", item_id).getResultList();
				
				
				items.stream().forEach(record -> {
					i.setItem_id(((BigInteger) record[0]).longValue());
					i.setBrand((String)record[1].toString());
					i.setBrand((String) record[1]);
					i.setDescription((String) record[2]);
					i.setDislikes((Integer) record[3]);
					i.setLikes((Integer) record[4]);
					i.setName((String) record[5]);
					i.setPicture_url((String) record[6]);
					i.setPrice((Double) record[7]);
				});
			}
			
			tx.commit();
			
			return i;
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
