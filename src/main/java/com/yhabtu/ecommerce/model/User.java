package com.yhabtu.ecommerce.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="user_id")
	private Long user_id;
	
	@Column
	@NotEmpty(message = "Please provide first name")
	@Size(min = 2, message = "First name must have greater or equal to 5")
	private String first_name;
	
	@Column
	@NotEmpty(message = "Please provide last name")
	@Size(min = 2, message = "Last name must have greater or equal to 5")
	private String last_name;
	
	@Column
	@NotEmpty(message = "Please provide the email address")
	@Email(message = "Please provide the valid email address")
	private String email;
		
	@Column(nullable = true)
	private String password;
	
	@OneToMany(mappedBy = "user")
	private List<CreditCardInformation> creditCardInfo = new ArrayList<CreditCardInformation>();
	
	@OneToMany(mappedBy = "user")
	private List<BillingShippingAddress> billingAddress = new ArrayList<BillingShippingAddress>();
	
	@OneToMany(mappedBy = "user")
	private List<BillingShippingAddress> shippingAddress = new ArrayList<BillingShippingAddress>();
	
	@ManyToMany
	@JoinTable(name="Saved_Item", 
		joinColumns = { @JoinColumn(name="fk_user_id") }, 
		inverseJoinColumns = { @JoinColumn(name="fk_item_size_color_id") })
	private Set<Item_Size_Color> item_size_color = new HashSet<Item_Size_Color>();
	
	public Set<Item_Size_Color> getItem_size_color() {
		return item_size_color;
	}

	public void setItem_size_color(Set<Item_Size_Color> item_size_color) {
		this.item_size_color = item_size_color;
	}

	public User() {
		super();		
	}
	
	public User(long userid, String email, String first_name, String last_name, String password) {
		super();
		this.user_id = userid;
		this.email = email;
		this.first_name = first_name;
		this.last_name = last_name;
		this.password = password;
	}
	
	public long getUser_id() {		
		return user_id;
	}
	public void setUser_id(long userid) {
		this.user_id = userid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	
}
