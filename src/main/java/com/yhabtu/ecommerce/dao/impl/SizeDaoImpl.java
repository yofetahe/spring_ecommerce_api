package com.yhabtu.ecommerce.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.yhabtu.ecommerce.configuration.HibernateUtil;
import com.yhabtu.ecommerce.dao.SizeDao;
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

}
