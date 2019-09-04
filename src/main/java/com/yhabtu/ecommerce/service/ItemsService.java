package com.yhabtu.ecommerce.service;

import java.util.List;

import com.yhabtu.ecommerce.model.Item;
import com.yhabtu.ecommerce.model.Size;

public interface ItemsService {

	public long addItem(Item item);
	
	public List<Item> getAllItems();	

	public List<Item> getItemsByCategoryId(int category_id);
	
	public List<Item> getItemsByCategoryTypeId(int category_id, int type_id);
	
	public boolean checkItemForLaterPurchase(int item_id, int color_id, int size_id, int user_id);
	
	public boolean saveItemForLaterPurchase(int item_id, int color_id, int size_id, int user_id);
	
	public int getItemRemainingBalance(int item_id, int color_id, int size_id);
	
	public List<Item> getSavedItemsListByUserId(int user_id);
	
	public boolean deleteSavedItem(int user_id, int item_size_color_id);
	
}
