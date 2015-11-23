package com.walmart.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Abhijit
 * This class has the venue level details.
 */
@XmlRootElement
public class Level {

	private int levelID;
	private String levelName;
	private int price;
	private int noOfRows;
	private  int seatsPerRow;
	private int availableSeats;   //this gets updated whenever ticket is booked

	public Level(){}

	public Level(int levelID, String levelName, int price, int noOfRows, int seatsPerRow, int availableSeats) {
		super();
		this.levelID = levelID;
		this.levelName = levelName;
		this.price = price;
		this.noOfRows = noOfRows;
		this.seatsPerRow = seatsPerRow;
		this.availableSeats = availableSeats;
	}

	//Getters and setters.
	@XmlTransient
	public int getLevelID() {
		return levelID;
	}
	public void setLevelID(int levelID) {
		this.levelID = levelID;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

	@XmlTransient
	public int getNoOfRows() {
		return noOfRows;
	}
	public void setNoOfRows(int noOfRows) {
		this.noOfRows = noOfRows;
	}

	@XmlTransient
	public int getSeatsPerRow() {
		return seatsPerRow;
	}
	public void setSeatsPerRow(int seatsPerRow) {
		this.seatsPerRow = seatsPerRow;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

}
