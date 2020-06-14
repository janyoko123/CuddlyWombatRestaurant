package restaurantmgr.model;

public class PaymentMethod {
	private String tableID;
	private String dateTime;
	private Float bill;
	private Float change;
	
	public PaymentMethod(String id, String dateTime, Float bill, Float change) {
		this.tableID=id;
		this.dateTime=dateTime;
		this.bill=bill;
		this.change=change;
	}
	
	//getting information from other classes -> calculate bill 
	//then calculate change
}
