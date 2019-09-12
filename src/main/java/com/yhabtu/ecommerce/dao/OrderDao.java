package com.yhabtu.ecommerce.dao;

import java.util.List;
import java.util.Map;

import com.yhabtu.ecommerce.model.BillingShippingAddress;
import com.yhabtu.ecommerce.model.CreditCardInformation;
import com.yhabtu.ecommerce.model.Item_Size_Color;
import com.yhabtu.ecommerce.model.User;

public interface OrderDao {

	public boolean submitPurchaseOrder(List<Item_Size_Color> itemSizeColors, User user,
			BillingShippingAddress shippingAddress, CreditCardInformation billingInfo, double totalTransaction,
			BillingShippingAddress billingAddress);

	public boolean submitActiveUserPurchaseOrder(List<Item_Size_Color> itemSizeColors,
			BillingShippingAddress shippingAddress, int credit_card_info_id, int billingAddressId, int user_id,
			double totalTransaction);

	public List<Map<String, String>> getOrderedItemsByUserId(int user_id);

	public boolean cancelOrderedItem(int order_id, int order_detail_id, int item_size_color_id);
}
