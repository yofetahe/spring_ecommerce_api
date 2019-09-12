package com.yhabtu.ecommerce.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yhabtu.ecommerce.dao.ItemsDao;
import com.yhabtu.ecommerce.model.Item;
import com.yhabtu.ecommerce.model.User;
import com.yhabtu.ecommerce.service.ItemsService;

@Service
public class ItemsServiceImpl implements ItemsService {
	
	@Autowired
	private ItemsDao itemsDao;

	@Override
	public long addItem(Item item) {
		
		return itemsDao.addItem(item);
	}

	@Override
	public List<Item> getAllItems() {
		
		return itemsDao.getAllItems();
	}
	
	@Override
	public List<Item> getItemsByCategoryId(int category_id) {
		
		return itemsDao.getItemsByCategoryId(category_id);
	}

	@Override
	public List<Item> getItemsByCategoryTypeId(int category_id, int type_id) {
		
		return itemsDao.getItemsByCategoryTypeId(category_id, type_id);
	}

	@Override
	public boolean saveItemForLaterPurchase(int item_id, int color_id, int size_id, int user_id) {
		
		return itemsDao.saveItemForLaterPurchase(item_id, color_id, size_id, user_id);
	}

	@Override
	public int getItemRemainingBalance(int item_id, int color_id, int size_id) {
		
		return itemsDao.getItemRemainingBalance(item_id, color_id, size_id);
	}

	@Override
	public boolean checkItemForLaterPurchase(int item_id, int color_id, int size_id, int user_id) {
		
		return itemsDao.checkItemForLaterPurchase(item_id, color_id, size_id, user_id);
	}

	@Override
	public List<Item> getSavedItemsListByUserId(int user_id) {
		
		return itemsDao.getSavedItemsListByUserId(user_id);
	}

	@Override
	public boolean deleteSavedItem(int user_id, int item_size_color_id) {
		
		return itemsDao.deleteSavedItem(user_id, item_size_color_id);
	}

	@Override
	public Item addLikeByItemId(int item_id) {
		
		return itemsDao.addLikeByItemId(item_id);
	}

	@Override
	public Item addDislikeByItemId(int item_id) {
		
		return itemsDao.addDislikeByItemId(item_id);
	}
}
