package com.yhabtu.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long category_id;
	
	@Column(name = "name")
	@NotEmpty(message = "Please provide the category name")
	private String name;
	
	@Column(name="picture_url")
	@NotEmpty(message = "Please provide the category picture")
	private String picture_url;
	
	@OneToMany(mappedBy = "category")
	private List<Category_Type> category_type = new ArrayList<Category_Type>();
	
	public Category() {}
	
	public Category(long category_id, String name) {
		super();
		this.category_id = category_id;
		this.name = name;
	}
		
	public long getCategory_id() {
		return category_id;
	}
	public void setCategory_id(long category_id) {
		this.category_id = category_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPicture_url() {
		return picture_url;
	}

	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}
	
}
