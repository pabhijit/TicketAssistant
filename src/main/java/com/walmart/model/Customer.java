package com.walmart.model;

/*
 * Customer.java
 * This maintains the customer information.
 */
public class Customer {

	private int id;
	private String email;

	public Customer(){}

	public Customer(int id, String email) {
		super();
		this.id = id;
		this.email = email;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


}
