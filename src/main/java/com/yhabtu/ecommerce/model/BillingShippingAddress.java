package com.yhabtu.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "billing_shipping_address")
public class BillingShippingAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long address_id;
	
	@Column(nullable = false)
	private String address;
		
	@Column
	private String city;
	
	@Column
	private String state;
	
	@Column
	private String country;
	
	@Column(nullable = false)
	private int zip_code;
	
	@ManyToOne
	@JoinColumn(name = "fk_user_id")
	private User user;
	
	@OneToMany(mappedBy = "billingShippingAddress")
	private List<CreditCardInformation> creditCardInformation = new ArrayList<CreditCardInformation>();
	
	public long getAddress_id() {
		return address_id;
	}

	public void setAddress_id(long address_id) {
		this.address_id = address_id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getZip_code() {
		return zip_code;
	}

	public void setZip_code(int zip_code) {
		this.zip_code = zip_code;
	}

}
