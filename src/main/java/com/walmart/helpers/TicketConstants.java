/**
 * 
 */
package com.walmart.helpers;

/**
 * @author Abhijit
 * 
 */
public class TicketConstants {

	public static final int INT_TIMEOUT = 30000; 
	public static final String STR_DOLLAR = "$";

	//Error messages
	public static final String STR_NOT_VALID_LEVEL = " is not a valid level id.";
	public static final String STR_INVALID_SEATS_REQ = "Invalid number of seats requested: ";
	public static final String STR_MAX_SEATS_EXCEED = "Maximum number of available seats: ";
	public static final String STR_INVALID_EMAIL_ID = "Either the id or the email is wrong.";
	public static final String STR_BOOKING_EXPIRED = "The booking has expired. Please try again.";
	public static final String STR_REQ_NOT_POSSIBLE = "The request could not be fulfilled.";
	public static final String STR_ALREADY_BOOKED = "The booking has already been made. Please make a new one.";

	//Levels
	public static final String STR_ORCHESTRA = "Orchestra";
	public static final String STR_MAIN = "Main";
	public static final String STR_BALCONY_1 = "Balcony1";
	public static final String STR_BALCONY_2 = "Balcony2";
}
