package com.yhabtu.ecommerce.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.yhabtu.ecommerce.configuration.HibernateUtil;
import com.yhabtu.ecommerce.dao.ColorDao;
import com.yhabtu.ecommerce.model.Color;

@Repository
public class ColorDaoImpl implements ColorDao {

	@Override
	public long addColor(Color color) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		long id = (long) session.save(color);
		session.getTransaction().commit();

		return id;
	}

	@Override
	public List<Color> fetchItemColorsByItemId(int item_id) {

		String hql = "SELECT distinct b.color_id, b.name, b.color_number, a.fk_item_id "
				+ "FROM item_size_color a, color b "
				+ "WHERE a.fk_color_id = b.color_id and a.fk_item_id = :item_id and a.remaining_balance > 0";
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			
			@SuppressWarnings("unchecked")
			List<Object[]> results = session.createNativeQuery(hql)
					.setParameter("item_id", item_id).getResultList();

			List<Color> colors = new ArrayList<Color>();

			results.stream().forEach((record) -> {
				Color c = new Color();
				c.setColor_id(((BigInteger) record[0]).longValue());
				c.setName((String) record[1]);
				c.setColor_number((String) record[2]);
				colors.add(c);
			});

			return colors;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Map<String, String>> fetchItemColorsByItemsList(String items_list) {
		
		String hql = "select distinct b.color_id, b.name, a.fk_item_id " + 
				"from item_size_color a, color b " + 
				"where a.fk_color_id = b.color_id and a.fk_item_id in (" + items_list + ")";
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			@SuppressWarnings("unchecked")
			List<Object[]> response = session.createSQLQuery(hql)
					.getResultList();

			List<Map<String, String>> sizes = new ArrayList<Map<String,String>>();

			response.stream().forEach((record) -> {
				Map<String, String> s = new HashMap<String, String>();
				s.put("color_id", ((BigInteger) record[0]).toString());
				s.put("name", (String) record[1]);
				s.put("item_id", ((BigInteger) record[2]).toString());
				sizes.add(s);
			});
			System.out.println("Last");
			return sizes;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
