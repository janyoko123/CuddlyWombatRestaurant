package restaurantmgr.service;

import java.util.Optional;

public interface ReportInterface {
	public Optional<Float> findDailyFlowIn();
	public void addProfit(boolean insertMode, float profit);
	public Optional<Float> getTodayProfit();
	public Optional<Float> getMonthlyProfit();
	public Optional<Float> getAnnualProfit();
}
