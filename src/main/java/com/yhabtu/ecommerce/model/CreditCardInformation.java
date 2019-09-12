package com.yhabtu.ecommerce.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "credit_card_information")
public class CreditCardInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long credit_card_info_id;
	
	@Column(nullable = false)
	private String card_holder_name;
	
	@Column(nullable = false)
	private String card_type;
	
	@Column(nullable = false)
	private String card_account_number;
	
	@Column
	private String card_last_four_digit;
	
	@Column(nullable = false)
	private String expiration_date;
	
	@Column(nullable = false)
	private String security_code;
	
	@Column(nullable = false)
	private Date create_date;
	
	@ManyToOne
	@JoinColumn(name = "fk_user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "fk_address_id")
	private BillingShippingAddress billingShippingAddress;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@PrePersist
	protected void onCreate() {
		this.create_date = new Date();
	}

	public long getCredit_card_info_id() {
		return credit_card_info_id;
	}

	public void setCredit_card_info_id(long credit_card_info_id) {
		this.credit_card_info_id = credit_card_info_id;
	}

	public String getCard_holder_name() {
		return card_holder_name;
	}

	public void setCard_holder_name(String card_holder_name) {
		this.card_holder_name = card_holder_name;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public String getCard_account_number() {
		return card_account_number;
	}

	public void setCard_account_number(String card_account_number) {
		this.card_account_number = card_account_number;
	}

	public String getExpiration_date() {
		return expiration_date;
	}

	public void setExpiration_date(String expiration_date) {
		this.expiration_date = expiration_date;
	}

	public String getSecurity_code() {
		return security_code;
	}

	public void setSecurity_code(String security_code) {
		this.security_code = security_code;
	}

	public String getCard_last_four_digit() {
		return card_last_four_digit;
	}

	public void setCard_last_four_digit(String card_last_four_digit) {
		this.card_last_four_digit = card_last_four_digit;
	}

	public BillingShippingAddress getBillingShippingAddress() {
		return billingShippingAddress;
	}

	public void setBillingShippingAddress(BillingShippingAddress billingShippingAddress) {
		this.billingShippingAddress = billingShippingAddress;
	}			
}
