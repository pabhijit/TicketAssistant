package com.walmart.model;

import java.util.Map;
import java.util.concurrent.Callable;

import com.walmart.database.DatabaseClass;

/**
 * @author Abhijit
 */
public class TimeoutManager implements Callable<String>{
	private int seatHoldId;
	private long waitTime;

	public TimeoutManager(int seatHoldId, long waitTime) {
		super();
		this.seatHoldId = seatHoldId;
		this.waitTime = waitTime;
	}

	/*
	 * @see java.util.concurrent.Callable#call()
	 * The method has the logic for releasing the held ticket after wait time.
	 * @throws InterruptedException
	 * @return CurrentThread name
	 */
	@Override
	public String call() throws InterruptedException {

		Thread.sleep(waitTime);
		Map<String, Integer> bookingInfo = null;
		if(DatabaseClass.getSeatHolds().get(seatHoldId).getStatus().equals(StatusEnum.HELD)){
			synchronized (DatabaseClass.getSeatHolds()) {
				bookingInfo = DatabaseClass.getSeatHolds().get(seatHoldId).getBookingInfo();

				bookingInfo.forEach((key,value) -> {
					Level level = DatabaseClass.getLevels().values()
							.stream().filter(s ->s.getLevelName().equals(key)).findFirst().get();					

					//Releasing the held seats.
					level.setAvailableSeats(level.getAvailableSeats() + value);

				});			
				//Changing the status to expired.
				DatabaseClass.getSeatHolds().get(seatHoldId).setStatus(StatusEnum.EXPIRED);
			}
		}
		return Thread.currentThread().getName();
	}

}
