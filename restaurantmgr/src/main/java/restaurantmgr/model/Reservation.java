package restaurantmgr.model;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import restaurantmgr.service.CustomerService;

/*
 * got full get set. 
 * */

public class Reservation {
	private int reservationId;
	private int customerId;
	private Integer numofPeople;
	private String tableType;
	private Timestamp dateTime;
	private boolean active;
	
	public Reservation(int customerId, Integer num, String tableType, Timestamp dateTime, boolean active) {
		this.customerId = customerId;
		this.numofPeople = num;
		this.tableType=tableType;
		this.dateTime = dateTime;
		this.active = active;
	}
	
	public Reservation(int id, int customerId, Integer num, String tableType, Timestamp dateTime, boolean active) {
		this.reservationId = id;
		this.customerId = customerId;
		this.numofPeople = num;
		this.tableType = tableType;
		this.dateTime = dateTime;
		this.active = active;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationID(int reservationId) {
		this.reservationId = reservationId;
	}

	public int getCustomerId() {
		return this.customerId;
	}

	public void setCustomerName(int customerId) {
		this.customerId = customerId;
	}

	public Integer getNumofPeople() {
		return numofPeople;
	}

	public void setNumofPeople(Integer numofPeople) {
		this.numofPeople = numofPeople;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}
	
	public boolean getActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		// Attempt to get customer name
		String name = CustomerService.getInstance().findCustomerName(this.customerId);
		return "Res Id:" + this.getReservationId() +  " | Name:" + name 
				+ " | Num Guests:" + numofPeople + " | Date:" + this.dateTime
				+ " | Table Type:" + tableType;
	}
}
