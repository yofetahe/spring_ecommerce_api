package com.yhabtu.ecommerce.model;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name="orders")
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long order_id;
	
	@Column(nullable = true)
	private long fk_user_id;
	
	@Column(nullable = true)
	private long fk_credit_card_info_id;
	
	@Column(nullable = true)
	private long fk_shipping_address_id;
	
	@Column(nullable = true)
	private long fk_billing_address_id;
	
	@Column(nullable = false)
	private double total_transaction;
	
	@Column
	private Date order_date;
	
	@Column
	private String order_status;
	
	@OneToMany(mappedBy = "order")
	private List<OrderDetails> orderDetails = new ArrayList<>();
		
	@PrePersist
	protected void onCreate() {
		this.order_date = new Date();
	}

	public long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}

	
	public double getTotal_transaction() {
		return total_transaction;
	}

	public void setTotal_transaction(double total_transaction) {
		this.total_transaction = total_transaction;
	}

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public long getFk_user_id() {
		return fk_user_id;
	}

	public void setFk_user_id(long fk_user_id) {
		this.fk_user_id = fk_user_id;
	}

	public long getFk_credit_card_info_id() {
		return fk_credit_card_info_id;
	}

	public void setFk_credit_card_info_id(long fk_credit_card_info_id) {
		this.fk_credit_card_info_id = fk_credit_card_info_id;
	}

	public long getFk_shipping_address_id() {
		return fk_shipping_address_id;
	}

	public void setFk_shipping_address_id(long fk_shipping_address_id) {
		this.fk_shipping_address_id = fk_shipping_address_id;
	}

	public long getFk_billing_address_id() {
		return fk_billing_address_id;
	}

	public void setFk_billing_address_id(long fk_billing_address_id) {
		this.fk_billing_address_id = fk_billing_address_id;
	}
	
}
