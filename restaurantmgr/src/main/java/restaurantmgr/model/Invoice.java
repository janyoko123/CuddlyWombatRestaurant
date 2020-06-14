package restaurantmgr.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Invoice {
	private int id;
	private float price;
	private int orderId;
	private String waiterId;
	private String tableId;
	private float amountPaid;
	private Timestamp date;
	private String paymentMethod;
	
	public Invoice(float price, String waiterId, String tableId, int orderId) {
		this.price = price;
		this.waiterId = waiterId;
		this.tableId = tableId;
		this.orderId = orderId;
	}
	
	public Invoice(float price, String waiterId, String tableId, float amountPaid, Timestamp date, int orderId, String paymentMethod) {
		this.price = price;
		this.waiterId = waiterId;
		this.tableId = tableId;
		this.amountPaid = amountPaid;
		this.date = date;
		this.orderId = orderId;
		this.paymentMethod = paymentMethod;
	}
	
	public Invoice(int id, float price, String waiterId, String tableId, float amountPaid, Timestamp date, int orderId, String paymentMethod) {
		this.id = id;
		this.price = price;
		this.waiterId = waiterId;
		this.tableId = tableId;
		this.amountPaid = amountPaid;
		this.date = date;
		this.orderId = orderId;
		this.paymentMethod = paymentMethod;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getWaiterId() {
		return waiterId;
	}
	public void setWaiterId(String waiterId) {
		this.waiterId = waiterId;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public float getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(float amountPaid) {
		this.amountPaid = amountPaid;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	@Override
	public String toString() {
		return "Invoice ID: " +this.id + " | Waiter ID: " + this.waiterId + " | Table: "
			+ this.tableId + " | Order ID:" + this.orderId + " | Price: " + this.price 
			+ " | Paid: " + this.amountPaid + " | Payment Method: " + this.paymentMethod;
	}
}
