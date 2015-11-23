package com.walmart.resource.bean;

import javax.ws.rs.QueryParam;

/*
 * SeatsFilter.java
 * This is a bean class in order to maintain the various fields for "find and hold seats"
 */
public class SeatsFilter {

	private @QueryParam("numSeats") int numSeats; 
	private @QueryParam("minLevel") Integer minLevel; 
	private @QueryParam("maxLevel") Integer maxLevel;
	private @QueryParam("email") String customerEmail;

	public int getNumSeats() {
		return numSeats;
	}
	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}
	public Integer getMinLevel() {
		return minLevel;
	}
	public void setMinLevel(Integer minLevel) {
		this.minLevel = minLevel;
	}
	public Integer getMaxLevel() {
		return maxLevel;
	}
	public void setMaxLevel(Integer maxLevel) {
		this.maxLevel = maxLevel;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
}
