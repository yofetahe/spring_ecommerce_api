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
@Table(name = "size")
public class Size {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long size_id;
	
	@Column
	@NotEmpty(message = "Please provide the size name.")
	private String name;
	
	@OneToMany(mappedBy = "size")
	private List<Item_Size_Color> item_size_color = new ArrayList<Item_Size_Color>();
	
	public Size() {}
	
	public Size(long size_id, @NotEmpty(message = "Please provide the size name.") String name) {
		super();
		this.size_id = size_id;
		this.name = name;
	}
	
	public long getSize_id() {
		return size_id;
	}
	public void setSize_id(long size_id) {
		this.size_id = size_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
