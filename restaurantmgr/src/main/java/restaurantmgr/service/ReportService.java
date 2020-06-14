package restaurantmgr.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ReportService implements ReportInterface {

	private static final ReportService instance = new ReportService();
	
	private ReportService() {}
	
	public static ReportService getInstance() {
        return instance;
    }
	
	public Optional<Float> findDailyFlowIn() {
		try {
			String query = "select sum(bill) as total from invoices where date_time >= CURDATE()";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			
			ResultSet rs = stmt.executeQuery();
			//System.out.println("Query executed");
			
			if(rs.next()) {
				return Optional.of(rs.getFloat("total"));
			}
			else {
				return Optional.empty();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return Optional.empty();
		}
	}
	
	public void addProfit(boolean insertMode, float profit) {
		try {
			if(insertMode) {
				String query = "insert into profit values (CURDATE(), ?)";
				PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
				stmt.setFloat(1, profit);
				
				stmt.execute();
			}
			else {
				String query = "update profit set amount=? where date_time=CURDATE()";
				PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
				stmt.setFloat(1, profit);
				
				stmt.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
		}
	}
	
	public Optional<Float> getTodayProfit() {
		try {
			String query = "select amount from profit where date_time = CURDATE()";
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				return Optional.of(rs.getFloat("amount"));
			}
			else return Optional.empty();
		} catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return Optional.empty();
		}
	}
	
	public Optional<Float> getMonthlyProfit() {
		try {
			String query = "select sum(amount) as amount from profit where MONTH(date_time) = MONTH(CURDATE())";
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				return Optional.of(rs.getFloat("amount"));
			}
			else return Optional.empty();
		} catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return Optional.empty();
		}
	}
	
	public Optional<Float> getAnnualProfit() {
		try {
			String query = "select sum(amount) as amount from profit where YEAR(date_time) = YEAR(CURDATE())";
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				return Optional.of(rs.getFloat("amount"));
			}
			else return Optional.empty();
		} catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return Optional.empty();
		}
	}
}
