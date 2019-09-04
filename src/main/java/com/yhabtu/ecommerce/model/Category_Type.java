package com.yhabtu.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="category_type")
public class Category_Type {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long category_type_id;
	
	@ManyToOne
	@JoinColumn(name = "fk_category_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "fk_type_id")
	private Type type;
	
	@OneToMany(mappedBy = "category_type")
	private List<Item> item = new ArrayList<Item>();
	
	public long getCategory_type_id() {
		return category_type_id;
	}

	public void setCategory_type_id(long category_type_id) {
		this.category_type_id = category_type_id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
}
