package com.yhabtu.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "billing_address")
public class BillingAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long billing_address_id;
	
	@Column(nullable = false)
	private String address;
		
	@Column
	private String city;
	
	@Column
	private String state;
	
	@Column
	private String coutry;
	
	@Column(nullable = false)
	private int zip_code;
	
	@ManyToOne
	@JoinColumn(name = "fk_user_id")
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getBilling_address_id() {
		return billing_address_id;
	}

	public void setBilling_address_id(long billing_address_id) {
		this.billing_address_id = billing_address_id;
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

	public String getCoutry() {
		return coutry;
	}

	public void setCoutry(String coutry) {
		this.coutry = coutry;
	}

	public int getZip_code() {
		return zip_code;
	}

	public void setZip_code(int zip_code) {
		this.zip_code = zip_code;
	}

}
