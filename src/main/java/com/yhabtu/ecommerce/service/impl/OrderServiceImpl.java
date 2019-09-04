package com.yhabtu.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yhabtu.ecommerce.dao.OrderDao;
import com.yhabtu.ecommerce.model.Color;
import com.yhabtu.ecommerce.model.CreditCardInformation;
import com.yhabtu.ecommerce.model.Item;
import com.yhabtu.ecommerce.model.Item_Size_Color;
import com.yhabtu.ecommerce.model.ShippingAddress;
import com.yhabtu.ecommerce.model.Size;
import com.yhabtu.ecommerce.model.User;
import com.yhabtu.ecommerce.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderDao orderDao;

	@Override
	public boolean submitPurchaseOrder(String item_size_color, JsonObject shipping, JsonObject billing) {
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
		String fname = shipping.get("first_name").toString();
		user.setFirst_name(fname.substring(1, fname.length()-1));
		String lname = shipping.get("last_name").toString();
		user.setLast_name(lname.substring(1, lname.length()-1));
		String email = shipping.get("email").toString();
		user.setEmail(email.substring(0, email.length()-1));
		user.setPassword("defaultPassword");
		
		ShippingAddress shippingAddress = new ShippingAddress();
		String address = shipping.get("shipping_address").toString();
		shippingAddress.setAddress(address.substring(1, address.length()-1));
		String zip = shipping.get("zip_code").toString();
		shippingAddress.setZip_code(Integer.parseInt(zip.substring(1, zip.length()-1)));
		String state = shipping.get("state").toString();
		shippingAddress.setState(state.substring(1, state.length()-1));
		String country = shipping.get("country").toString();
		shippingAddress.setCoutry(country.substring(1, country.length()-1));
				
		CreditCardInformation billingInfo = new CreditCardInformation();
		String card_holder_name = billing.get("card_holder_name").toString();
		billingInfo.setCard_holder_name(card_holder_name);
		String card_type = billing.get("card_type").toString();
		billingInfo.setCard_type(card_type.substring(1, card_type.length()-1));
		String c_num =  billing.get("card_number").toString();
		billingInfo.setCard_account_number(c_num.substring(1, c_num.length()-1));
		String cvc = billing.get("cvc").toString();
		billingInfo.setSecurity_code(cvc.substring(1, cvc.length()-1));
		String exp_month = billing.get("expire_month").toString();
		String exp_year = billing.get("expire_year").toString();
		billingInfo.setExpiration_date(exp_month.substring(1, exp_month.length()-1) + "/" + exp_year.substring(1, exp_year.length()-1));
		
		return orderDao.submitPurchaseOrder(itemSizeColors, user, shippingAddress, billingInfo, totalTransaction);
	}

}
