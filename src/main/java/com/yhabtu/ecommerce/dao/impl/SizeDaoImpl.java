package com.yhabtu.ecommerce.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.yhabtu.ecommerce.configuration.HibernateUtil;
import com.yhabtu.ecommerce.dao.SizeDao;
import com.yhabtu.ecommerce.model.Item;
import com.yhabtu.ecommerce.model.Size;

@Repository
public class SizeDaoImpl implements SizeDao {

	@Override
	public long addSize(Size size) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		long id = (long) session.save(size);
		session.getTransaction().commit();

		return id;
	}

	@Override
	public List<Size> fetchItemSizeByItemId(int item_id) {

		String hql = "SELECT distinct b.size_id, b.name, a.fk_item_id FROM item_size_color a, size b "
				+ "WHERE a.fk_size_id = b.size_id and a.fk_item_id = :item_id and a.remaining_balance > 0";

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			@SuppressWarnings("unchecked")
			List<Object[]> response = session.createSQLQuery(hql).setParameter("item_id", item_id)
					.getResultList();

			List<Size> sizes = new ArrayList<Size>();

			response.stream().forEach((record) -> {
				Size s = new Size();
				s.setSize_id(((BigInteger) record[0]).longValue());
				s.setName((String) record[1]);
				sizes.add(s);
			});

			return sizes;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Map<String, String>> fetchItemSizesByItemsList(String items_list) {
		System.out.println("############# " + items_list);
		String hql = "select distinct b.size_id, b.name, a.fk_item_id " + 
				"from item_size_color a, size b " + 
				"where a.fk_size_id = b.size_id and a.fk_item_id in (" + items_list + ")";
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			@SuppressWarnings("unchecked")
			List<Object[]> response = session.createSQLQuery(hql)
					.getResultList();

			List<Map<String, String>> sizes = new ArrayList<Map<String,String>>();

			response.stream().forEach((record) -> {
				Map<String, String> s = new HashMap<String, String>();
				s.put("size_id", ((BigInteger) record[0]).toString());
				s.put("name", (String) record[1]);
				s.put("item_id", ((BigInteger) record[2]).toString());
				sizes.add(s);
			});
			System.out.println("Last");
			return sizes;
			
		} catch (Exception e) {
			System.out.println("in exception");
			e.printStackTrace();
		}
		return null;
	}

}
