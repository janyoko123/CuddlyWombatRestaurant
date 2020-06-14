package restaurantmgr.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import restaurantmgr.model.StatusDetail;

public class StatusDetailService implements StatusDetailInterface {
	private ObservableList<StatusDetail> list = FXCollections.observableArrayList(new ArrayList<StatusDetail>());
	
	private static final StatusDetailService instance = new StatusDetailService();
	
	private StatusDetailService() {}
	
	public static StatusDetailService getInstance() {
        return instance;
    }
	
	public void updateList() {
		try {
			String query = "select id, date_time from orders where status != 'Complete'";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			
			ResultSet rs = stmt.executeQuery();
			//System.out.println("Query executed");		
			
			List<StatusDetail> statusList = new ArrayList<StatusDetail>();

			while(rs.next()) {
				
				int _id = rs.getInt("id");
				Timestamp _dateTime = rs.getTimestamp("date_time");
				StatusDetail sDetail = new StatusDetail(_dateTime, _id);
				if(sDetail.isWaitingTooLong()) {
					statusList.add(new StatusDetail(_dateTime, _id));
				}
				
			}
			//System.out.println("Updating status details list. Total items " + statusList.size());
			list.clear();
			list.addAll(statusList);
			
		} catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			list.clear();
		}
	}
	
	public ObservableList<StatusDetail> getList() {
		return list;
	}

}
