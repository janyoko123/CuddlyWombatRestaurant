package restaurantmgr.model;
//grab today, this month, this year profit

public class Report {
	private Float profit;
	private Float flowIn;
	private Float flowOut;
	
	Report (Float profit, Float flowIn, Float flowOut){
		this.profit=profit;
		this.flowIn=flowIn;
		this.flowOut=flowOut;
	}

	public Float calProfit(Float flowIn, Float flowOut) {
		return flowIn-flowOut;
	}
	
	public Float getProfit() {
		return profit;
	}

	public void setProfit(Float profit) {
		this.profit = profit;
	}

	public Float getFlowIn() {
		return flowIn;
	}

	public void setFlowIn(Float flowIn) {
		this.flowIn = flowIn;
	}

	public Float getFlowOut() {
		return flowOut;
	}

	public void setFlowOut(Float flowOut) {
		this.flowOut = flowOut;
	}
	
	
}
