package com.yhabtu.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yhabtu.ecommerce.dao.OrderDao;
import com.yhabtu.ecommerce.model.BillingShippingAddress;
import com.yhabtu.ecommerce.model.Color;
import com.yhabtu.ecommerce.model.CreditCardInformation;
import com.yhabtu.ecommerce.model.Item;
import com.yhabtu.ecommerce.model.Item_Size_Color;
import com.yhabtu.ecommerce.model.Size;
import com.yhabtu.ecommerce.model.User;
import com.yhabtu.ecommerce.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderDao orderDao;

	@Override
	public boolean submitPurchaseOrder(String item_size_color, JsonObject shipping, JsonObject billing, JsonObject billingAddress) {
		System.out.println("======== SERVICE =======");
		String iscObj = item_size_color.substring(1, item_size_color.length()-1);
		
		List<String> iscList = new ArrayList<String>();
		String temp = iscObj+",";
		int beg = 0;
			
		while(temp.length() > 0) {
			int end = temp.indexOf("}") + 1;
			iscList.add(temp.substring(beg, end));
			temp = temp.substring(end + 1);
		}
		
		List<Item_Size_Color> itemSizeColors = new ArrayList<Item_Size_Color>();
		Double totalTransaction = 0.0;
		
		for(String isc : iscList) {
			
			JsonObject jsonObject2 = new JsonParser().parse(isc).getAsJsonObject();
			
			Item_Size_Color itemSizeColor = new Item_Size_Color();
			
			Color c = new Color();
			c.setColor_id(Long.parseLong(jsonObject2.get("color_id").toString()));
			itemSizeColor.setColor(c);
			
			Item i = new Item();
			i.setItem_id(Long.parseLong(jsonObject2.get("item_id").toString()));
			i.setPrice(Double.parseDouble(jsonObject2.get("unit_price").toString()));
			itemSizeColor.setItem(i);
			
			Size s = new Size();
			s.setSize_id(Long.parseLong(jsonObject2.get("size_id").toString()));
			itemSizeColor.setSize(s);
			
			int quantity_ordered = Integer.parseInt(jsonObject2.get("quantity").toString());
			double unit_price = Double.parseDouble(jsonObject2.get("unit_price").toString());
			
			itemSizeColor.setQuantity(quantity_ordered);
			
			totalTransaction += quantity_ordered * unit_price;
						
			itemSizeColors.add(itemSizeColor);
		}	
		
		
		User user = new User();
		String fname = shipping.get("first_name").toString().trim();
		user.setFirst_name(fname.substring(1, fname.length()-1));
		String lname = shipping.get("last_name").toString().trim();
		user.setLast_name(lname.substring(1, lname.length()-1));
		String email = shipping.get("email").toString().trim();
		user.setEmail(email.substring(1, email.length()-1));
		
		BillingShippingAddress shippingAddress = new BillingShippingAddress();
		String address = shipping.get("shipping_address").toString();
		shippingAddress.setAddress(address.substring(1, address.length()-1));
		String zip = shipping.get("zip_code").toString();
		shippingAddress.setZip_code(Integer.parseInt(zip.substring(1, zip.length()-1)));
		String state = shipping.get("state").toString();
		shippingAddress.setState(state.substring(1, state.length()-1));
		String country = shipping.get("country").toString();
		shippingAddress.setCountry(country.substring(1, country.length()-1));
		
		
		BillingShippingAddress billingAddressInfo = new BillingShippingAddress();
		String b_address = billingAddress.get("shipping_address").toString();
		if(b_address.substring(1, b_address.length()-1).length() > 0) {
					
			billingAddressInfo.setAddress(b_address.substring(1, b_address.length()-1));
			String b_zip = billingAddress.get("zip_code").toString();
			billingAddressInfo.setZip_code(Integer.parseInt(b_zip.substring(1, b_zip.length()-1)));
			String b_state = billingAddress.get("state").toString();
			billingAddressInfo.setState(b_state.substring(1, b_state.length()-1));
			String b_country = billingAddress.get("country").toString();
			billingAddressInfo.setCountry(b_country.substring(1, b_country.length()-1));		
		}
		
		CreditCardInformation billingInfo = new CreditCardInformation();
		String card_holder_name = billing.get("card_holder_name").toString();
		billingInfo.setCard_holder_name(card_holder_name.substring(1, card_holder_name.length()-1));
		String card_type = billing.get("card_type").toString();
		billingInfo.setCard_type(card_type.substring(1, card_type.length()-1));
		String c_num =  billing.get("card_number").toString();
		billingInfo.setCard_account_number(c_num.substring(1, c_num.length()-1));
		String cvc = billing.get("cvc").toString();
		billingInfo.setSecurity_code(cvc.substring(1, cvc.length()-1));
		String exp_month = billing.get("expire_month").toString();
		String exp_year = billing.get("expire_year").toString();
		billingInfo.setExpiration_date(exp_month.substring(1, exp_month.length()-1) + "/" + exp_year.substring(1, exp_year.length()-1));
		
		return orderDao.submitPurchaseOrder(itemSizeColors, user, shippingAddress, billingInfo, totalTransaction, billingAddressInfo);
	}
	

	@Override
	public boolean submitActiveUserPurchaseOrder(String item_size_color, JsonObject shipping, int cci, int billingAddressId, int user_id) {

		System.out.println("======== SERVICE =======");
		String iscObj = item_size_color.substring(1, item_size_color.length()-1);
		
		List<String> iscList = new ArrayList<String>();
		String temp = iscObj+",";
		int beg = 0;
			
		while(temp.length() > 0) {
			int end = temp.indexOf("}") + 1;
			iscList.add(temp.substring(beg, end));
			temp = temp.substring(end + 1);
		}
		
		List<Item_Size_Color> itemSizeColors = new ArrayList<Item_Size_Color>();
		Double totalTransaction = 0.0;
		
		for(String isc : iscList) {
			
			JsonObject jsonObject2 = new JsonParser().parse(isc).getAsJsonObject();
			
			Item_Size_Color itemSizeColor = new Item_Size_Color();
			
			Color c = new Color();
			c.setColor_id(Long.parseLong(jsonObject2.get("color_id").toString()));
			itemSizeColor.setColor(c);
			
			Item i = new Item();
			i.setItem_id(Long.parseLong(jsonObject2.get("item_id").toString()));
			i.setPrice(Double.parseDouble(jsonObject2.get("unit_price").toString()));
			itemSizeColor.setItem(i);
			
			Size s = new Size();
			s.setSize_id(Long.parseLong(jsonObject2.get("size_id").toString()));
			itemSizeColor.setSize(s);
			
			int quantity_ordered = Integer.parseInt(jsonObject2.get("quantity").toString());
			double unit_price = Double.parseDouble(jsonObject2.get("unit_price").toString());
			
			itemSizeColor.setQuantity(quantity_ordered);
			
			totalTransaction += quantity_ordered * unit_price;
						
			itemSizeColors.add(itemSizeColor);
		}
		
		BillingShippingAddress shippingAddressInfo = new BillingShippingAddress();
		String s_address = shipping.get("shipping_address").toString();		
		if(s_address.substring(1, s_address.length()-1).length() > 0) {
					
			shippingAddressInfo.setAddress(s_address.substring(1, s_address.length()-1));
			String b_zip = shipping.get("zip_code").toString();
			shippingAddressInfo.setZip_code(Integer.parseInt(b_zip.substring(1, b_zip.length()-1)));
			String b_state = shipping.get("state").toString();
			shippingAddressInfo.setState(b_state.substring(1, b_state.length()-1));
			String b_country = shipping.get("country").toString();
			shippingAddressInfo.setCountry(b_country.substring(1, b_country.length()-1));		
		}
				
		return orderDao.submitActiveUserPurchaseOrder(itemSizeColors, shippingAddressInfo, cci, billingAddressId, user_id, totalTransaction);
	}


	@Override
	public List<Map<String, String>> getOrderedItemsByUserId(int user_id) {
		
		return orderDao.getOrderedItemsByUserId(user_id);
	}

	@Override
	public boolean cancelOrderedItem(int order_id, int order_detail_id, int item_size_color_id) {
		
		return orderDao.cancelOrderedItem(order_id, order_detail_id, item_size_color_id);
	}

}
