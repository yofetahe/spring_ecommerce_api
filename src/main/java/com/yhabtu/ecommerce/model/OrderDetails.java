package com.yhabtu.ecommerce.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name="orders_details")
public class OrderDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long order_detail_id;
	
	@Column
	private int item_quantity;
	
	@Column
	private double unit_price;
	
	@OneToOne
	@JoinColumn(name = "fk_item_size_color_id")
	private Item_Size_Color item_size_color;
	
	@Column
	private Date order_date;
	
	@ManyToOne
	@JoinColumn(name = "fk_order_id")
	private Orders order;
		
	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	@PrePersist
	protected void onCreate() {
		this.order_date = new Date();
	}

	public long getOrder_detail_id() {
		return order_detail_id;
	}

	public void setOrder_detail_id(long order_detail_id) {
		this.order_detail_id = order_detail_id;
	}

	public int getItem_quantity() {
		return item_quantity;
	}

	public void setItem_quantity(int item_quantity) {
		this.item_quantity = item_quantity;
	}

	public double getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}

	public Item_Size_Color getItem_size_color() {
		return item_size_color;
	}

	public void setItem_size_color(Item_Size_Color item_size_color) {
		this.item_size_color = item_size_color;
	}

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}	
	
}
