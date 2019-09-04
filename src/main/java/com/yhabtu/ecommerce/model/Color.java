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
@Table(name = "color")
public class Color {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long color_id;
	
	@Column
	@NotEmpty(message = "Please provide color name")
	private String name;
	
	@Column
	@NotEmpty(message = "Please provide color hexa-decimal number")
	private String color_number;
	
	@OneToMany(mappedBy = "color")
	private List<Item_Size_Color> item_size_color = new ArrayList<Item_Size_Color>();
	
	public Color() {}
	
	public Color(long color_id, @NotEmpty(message = "Please provide color name") String name,
			@NotEmpty(message = "Please provide color hexa-decimal number") String color_number) {
		super();
		this.color_id = color_id;
		this.name = name;
		this.color_number = color_number;
	}
	
	public long getColor_id() {
		return color_id;
	}
	public void setColor_id(long color_id) {
		this.color_id = color_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor_number() {
		return color_number;
	}
	public void setColor_number(String color_number) {
		this.color_number = color_number;
	}
	
}
