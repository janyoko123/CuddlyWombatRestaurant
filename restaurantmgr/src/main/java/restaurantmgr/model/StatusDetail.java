package restaurantmgr.model;

import java.sql.Timestamp;
import java.time.Instant;


public class StatusDetail{
	private Timestamp waitingTime;
	private int orderId;
	// Threshold to alert if order is waiting too long in seconds
	private static long alertThreshold = 5*1000;
	
	public StatusDetail(Timestamp waitingTime, int orderId) {
		this.waitingTime = waitingTime;
		this.orderId = orderId;
	}

	public Timestamp getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(Timestamp waitingTime) {
		this.waitingTime = waitingTime;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public boolean isWaitingTooLong() {
		long epochWaitingTime = waitingTime.getTime();
			
		long epochCurrentTime = Instant.now().toEpochMilli();
		//System.out.println("Waiting since: " + epochWaitingTime + ", now: " + epochCurrentTime);
		if(epochCurrentTime - epochWaitingTime >= alertThreshold) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		long epochWaitingTime = waitingTime.getTime();
		long epochCurrentTime = Instant.now().toEpochMilli();
		long timePassed = epochCurrentTime - epochWaitingTime;
		int secondsPassed = (int) ((double) timePassed / (double) 1000.00);
		return "Order Id: " + this.orderId + ", Seconds waited: " + secondsPassed;
	}
}
