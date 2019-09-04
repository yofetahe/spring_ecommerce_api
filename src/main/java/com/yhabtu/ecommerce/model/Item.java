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
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "item")
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long item_id;
	
	@Column
	@NotEmpty(message = "Please provide the item name.")
	private String name;
	
	@Column
	@NotEmpty(message = "Please provide the item brand")
	private String brand;
	
	@Column
	@NotEmpty(message = "Please provide the item description")
	private String description;

	@Column
	@NotEmpty(message = "Please provide the picture url")
	private String picture_url;
	
	@Column(name = "likes")
	private Integer likes = 0;
	
	@Column(name = "dislike")
	private Integer dislikes = 0;
	
	@Column
	private double price;
	
	@Transient
	private long item_size_color_id;
	
	@ManyToOne
	@JoinColumn(name = "fk_category_type_id")
	private Category_Type category_type;
	
	@OneToMany(mappedBy = "item")
	private List<Item_Size_Color> item_size_color = new ArrayList<Item_Size_Color>();
		
	public Item() {}
	
	public Item(long item_id, @NotEmpty(message = "Please provide the item name.") String name,
			@NotEmpty(message = "Please provide the item brand") String brand,
			@NotEmpty(message = "Please provide the item description") String description,
			@NotEmpty(message = "Please provide the picture url") String picture_url, int likes, int dislikes) {
		super();
		this.item_id = item_id;
		this.name = name;
		this.brand = brand;
		this.description = description;
		this.picture_url = picture_url;
		this.likes = likes;
		this.dislikes = dislikes;
	}
	
		
	public Category_Type getCategory_type() {
		return category_type;
	}

	public void setCategory_type(Category_Type category_type) {
		this.category_type = category_type;
	}

	public long getItem_id() {
		return item_id;
	}
	public void setItem_id(long item_id) {
		this.item_id = item_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicture_url() {
		return picture_url;
	}
	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public int getDislikes() {
		return dislikes;
	}
	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public long getItem_size_color_id() {
		return item_size_color_id;
	}
	public void setItem_size_color_id(long item_size_color_id) {
		this.item_size_color_id = item_size_color_id;
	}
}
