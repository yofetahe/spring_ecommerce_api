package com.yhabtu.ecommerce.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "item_size_color")
public class Item_Size_Color {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long item_size_color_id;
	
	@Column
	private int initial_balance;
	
	@Column
	private int remaining_balance;
	
	@ManyToOne
	@JoinColumn(name = "fk_item_id")
	private Item item;

	@ManyToOne
	@JoinColumn(name = "fk_color_id")
	private Color color;
	
	@ManyToOne
	@JoinColumn(name = "fk_size_id")
	private Size size;
	
	@Transient
	private int quantity;
	
	@ManyToMany(mappedBy = "item_size_color")
	private Set<User> user = new HashSet<User>();

	public long getItem_size_color_id() {
		return item_size_color_id;
	}

	public void setItem_size_color_id(long item_size_color_id) {
		this.item_size_color_id = item_size_color_id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}
	
	public int getInitial_balance() {
		return initial_balance;
	}

	public void setInitial_balance(int initial_balance) {
		this.initial_balance = initial_balance;
	}

	public int getRemaining_balance() {
		return remaining_balance;
	}

	public void setRemaining_balance(int remaining_balance) {
		this.remaining_balance = remaining_balance;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
