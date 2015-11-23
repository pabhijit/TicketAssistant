package com.walmart.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.FutureTask;

import com.walmart.database.DatabaseClass;
import com.walmart.exception.DataNotFoundException;
import com.walmart.exception.InvalidRequestException;
import com.walmart.helpers.TicketConstants;
import com.walmart.model.Customer;
import com.walmart.model.ExecutorInstance;
import com.walmart.model.Level;
import com.walmart.model.SeatHold;
import com.walmart.model.StatusEnum;
import com.walmart.model.TimeoutManager;
import com.walmart.service.TicketService;

/**
 * @author Abhijit
 * This class provides the implementation for TicketService.java.
 */
public class TicketServiceImpl implements TicketService{

	@Override
	public List<Level> numSeatsAvailable(Optional<Integer> venueLevel){

		List<Level> levels = new ArrayList<>();
		if(venueLevel.isPresent()) {
			Optional<Level> level = DatabaseClass.getLevels().values()
					.stream().filter(s ->s.getLevelID()==venueLevel.get()).findFirst();
			if(!level.isPresent()){
				throw new DataNotFoundException(venueLevel.get() + TicketConstants.STR_NOT_VALID_LEVEL);
			}else {
				levels.add(level.get());
			}
		}else {
			//Send details of each level.
			levels.addAll(DatabaseClass.getLevels().values());
		}	
		return levels;	
	}

	@Override
	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel,
			String customerEmail) {

		int minimumLevel = 1, maximumLevel = 4; 

		//Throw an exception if the number of available seats is less than requested.
		int availableSeats = DatabaseClass.getLevels().values().stream().mapToInt(s -> s.getAvailableSeats()).sum();		
		if(numSeats <= 0) {
			throw new InvalidRequestException( TicketConstants.STR_INVALID_SEATS_REQ + numSeats );
		}	
		if(numSeats > availableSeats) {
			throw new InvalidRequestException( TicketConstants.STR_MAX_SEATS_EXCEED + availableSeats );
		}

		//Else find, hold the seats and return details.
		if(minLevel.isPresent()) {
			minimumLevel = minLevel.get();
		}
		if(minLevel.isPresent()) {
			maximumLevel = maxLevel.get();
		}
		Map<Integer, Integer> bookingDetails = findSeats(numSeats, minimumLevel, maximumLevel); 
		
		if(bookingDetails == null) {
			throw new InvalidRequestException( TicketConstants.STR_REQ_NOT_POSSIBLE );
		}
		
		SeatHold seatHold = holdSeats(bookingDetails,customerEmail);

		DatabaseClass.addSeatHolds(seatHold);

		checkforHoldTimeOut(seatHold, TicketConstants.INT_TIMEOUT);  

		return seatHold;
	}

	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {

		SeatHold seatHold = DatabaseClass.getSeatHolds().get(seatHoldId); 

		if(seatHold == null || !seatHold.getCustomer().getEmail().equals(customerEmail)){
			throw new InvalidRequestException(TicketConstants.STR_INVALID_EMAIL_ID);
		}
		if(seatHold.getStatus().equals(StatusEnum.HELD)){
			seatHold.setStatus(StatusEnum.BOOKED);
		} else if(seatHold.getStatus().equals(StatusEnum.EXPIRED)){
			throw new InvalidRequestException(TicketConstants.STR_BOOKING_EXPIRED);
		} else {
			throw new InvalidRequestException(TicketConstants.STR_ALREADY_BOOKED);
		}

		return String.valueOf(DatabaseClass.generateConfirmationCode());
	}

	/**
	 * The method scans from the maximum level to the minimum level till it finds the 
	 * requested number of seats(best seats)
	 *
	 *@param numSeats the number of seats to find and hold
	 * @param minLevel the minimum venue level
	 * @param maxLevel the maximum venue level 
	 * @return a map containing the venue level id and the corresponding number of seats.
	 */
	private Map<Integer, Integer> findSeats(int numSeats, int minimumLevel, int maximumLevel) {

		Map<Integer, Integer> rv = new HashMap<>();

		while (maximumLevel >= minimumLevel && numSeats > 0) {
			Level level = DatabaseClass.getLevels().get(maximumLevel);
			int availableSeats = level.getAvailableSeats();
			if(availableSeats > numSeats){
				level.setAvailableSeats(availableSeats - numSeats);
				rv.put(level.getLevelID(), numSeats);
				numSeats = 0;
			} else {
				level.setAvailableSeats(0);
				rv.put(level.getLevelID(), availableSeats);
				numSeats = numSeats - availableSeats;				
			}
			maximumLevel--;
		}
		if(numSeats != 0){
			rv = null;
		}
		return rv;
	}

	/**
	 * The method frames the final response to be sent back to the client.
	 *
	 * @param map with the level and number of seats
	 * @param customer email 
	 * @return SeatHold object
	 */

	private SeatHold holdSeats(Map<Integer, Integer> temp, String customerEmail) {

		int totalPrice =0;
		Map<String, Integer> bookingInfo = new HashMap<>();

		for(Map.Entry<Integer, Integer> entry : temp.entrySet())
		{
			totalPrice += DatabaseClass.getLevels().get(entry.getKey()).getPrice()*entry.getValue();
			bookingInfo.put(DatabaseClass.getLevels().get(entry.getKey()).getLevelName(), entry.getValue());
		}
		Customer newCustomer = new Customer(DatabaseClass.generateCustId(),customerEmail);

		SeatHold rv = new SeatHold(DatabaseClass.generateHoldId(), StatusEnum.HELD, newCustomer, 
				System.currentTimeMillis() + 60000, bookingInfo, TicketConstants.STR_DOLLAR + totalPrice);
		return rv;
	}

	/**
	 * The method checks the status of the seat hold id after timeout interval and changes the
	 * status to expired
	 *
	 * @param SeatHold object
	 * @param timeout interval
	 * @return void
	 */

	private void checkforHoldTimeOut(SeatHold seatHold, long waitTime) {

		FutureTask<String> task = new FutureTask<>(new TimeoutManager(seatHold.getSeatHoldID(), waitTime));
		ExecutorInstance.getInstance().executor.execute(task);
	}

}
