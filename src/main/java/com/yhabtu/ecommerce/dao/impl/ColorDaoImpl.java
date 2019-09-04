package com.yhabtu.ecommerce.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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

}
