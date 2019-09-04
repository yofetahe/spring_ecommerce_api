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
@Table(name = "type")
public class Type {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long type_id;
	
	@Column
	@NotEmpty(message = "Please provide item type")
	private String name;
	
	@OneToMany(mappedBy = "type")
	private List<Category_Type> category_type = new ArrayList<Category_Type>();
	
	public Type() {}
	
	public Type(long type_id, @NotEmpty(message = "Please provide item type") String name) {
		super();
		this.type_id = type_id;
		this.name = name;
	}
	
	public long getType_id() {
		return type_id;
	}
	public void setType_id(long type_id) {
		this.type_id = type_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
