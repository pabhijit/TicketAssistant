package com.walmart.database;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.walmart.helpers.TicketConstants;
import com.walmart.model.Level;
import com.walmart.model.SeatHold;

/**
 * @author Abhijit
 * This class will emulate the database.
 */
public class DatabaseClass {

	private static Map<Integer,Level> levels = new HashMap<>();
	private static Map<Integer,SeatHold> seatHolds = new HashMap<>();
	private static AtomicInteger seatHoldIdGen = new AtomicInteger(100);
	private static AtomicInteger custIdGen = new AtomicInteger(667);
	private static AtomicInteger reserveConfirmGen = new AtomicInteger(1020);

	static{
		Level l1 = new Level( 1, TicketConstants.STR_ORCHESTRA, 100, 25, 50, 25*50 );
		Level l2 = new Level( 2, TicketConstants.STR_MAIN, 75, 20, 100, 20*100 );
		Level l3 = new Level( 3, TicketConstants.STR_BALCONY_1, 50, 15, 100, 15*100 );
		Level l4 = new Level( 4, TicketConstants.STR_BALCONY_2, 40, 15, 100, 15*100 );
		levels.put(1,l1);
		levels.put(2,l2);
		levels.put(3,l3);
		levels.put(4,l4);
	}

	public static Map<Integer, Level> getLevels() {
		return levels;
	}

	public static Map<Integer, SeatHold> getSeatHolds() {
		return seatHolds;
	}

	public static int generateHoldId() {
		return seatHoldIdGen.incrementAndGet();
	}

	public static int generateConfirmationCode() {
		return reserveConfirmGen.incrementAndGet();
	}

	public static void addSeatHolds(SeatHold seatHold) {
		seatHolds.put(seatHoldIdGen.get(), seatHold);
	}

	public static int generateCustId() {
		return custIdGen.incrementAndGet();
	}
}
