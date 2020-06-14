package restaurantmgr.model;
//status should have status details (waiting time)
//order status is an enum

import java.sql.Date;
import java.sql.Timestamp;

public class Order {
	private int orderId;
	private String tableId;
	private Float price;
	private String status;
	private Timestamp dateTime;

	public Order( String tableId, Float price, String status, Timestamp dateTime) {
		this.tableId=tableId;
		this.price=price;
		this.status=status;
		this.dateTime=dateTime;
	}
	
	public Order( int orderId, String tableId, Float price, String status, Timestamp dateTime) {
		this.orderId=orderId;
		this.tableId=tableId;
		this.price=price;
		this.status=status;
		this.dateTime=dateTime;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}
	
	@Override
	public String toString() {
		return "OrderId="+this.orderId + "|Table=" + this.tableId + "|" + this.getStatus();
	}
	
}