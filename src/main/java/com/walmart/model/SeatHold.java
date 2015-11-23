package com.walmart.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class SeatHold {

	private int id;	
	private StatusEnum status;
	private Customer customer;
	private Map<String,Integer> bookingInfo;
	private String purchaseValue;
	private List<Link> links = new ArrayList<>();

	public SeatHold() {}

	public SeatHold(int id, StatusEnum status, Customer customer, long holdTime, Map<String, Integer> bookingInfo, String purchaseValue) {
		super();
		this.id = id;
		this.status = status;
		this.customer = customer;
		this.setBookingInfo(bookingInfo);
		this.purchaseValue = purchaseValue;
	}


	//Getters and setters.
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	@XmlTransient
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}	
	public int getSeatHoldID() {
		return id;
	}
	public void setSeatHoldID(int seatHoldID) {
		this.id = seatHoldID;
	}	
	public void addLink(String url, String rel) {
		Link link = new Link();
		link.setLink(url);
		link.setRel(rel);
		links.add(link);
	}

	public String getPurchaseValue() {
		return purchaseValue;
	}

	public void setPurchaseValue(String purchaseValue) {
		this.purchaseValue = purchaseValue;
	}

	public Map<String,Integer> getBookingInfo() {
		return bookingInfo;
	}

	public void setBookingInfo(Map<String,Integer> bookingInfo) {
		this.bookingInfo = bookingInfo;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

}
