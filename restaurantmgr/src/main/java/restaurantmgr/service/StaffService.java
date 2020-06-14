package restaurantmgr.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import restaurantmgr.model.Chef;
import restaurantmgr.model.Owner;
import restaurantmgr.model.Staff;
import restaurantmgr.model.Waiter;

public class StaffService implements StaffInterface {

	private static final StaffService instance = new StaffService();
	
	private StaffService() {}
	
	public static StaffService getInstance() {
        return instance;
    }
	
	//Wrap optional so not run into null 
	public Optional<Staff> findStaffByUsername(String username) {
		// Query and find staff.
		try {
			String query = "select * from staff where id = ?";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();
			//System.out.println("Query executed");
			if(rs.next()) {
				System.out.println("Found user!");
				
				String _name = rs.getString("name");
				String _username = rs.getString("id");
				String _password = rs.getString("password");
				String _role = rs.getString("role");
				
				switch(_role) {
				case "owner":
					return Optional.of(new Owner(_name,_username,_password));
				case "waiter":
					return Optional.of(new Waiter(_name,_username,_password));
				case "chef":
					return Optional.of(new Chef(_name,_username,_password));
				default:
					return Optional.of(new Staff(_name,_username,_password,_role));
				}
			}
			else {
				System.out.println("Username does not exist.");
				return Optional.empty();
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return Optional.empty();
		}
	}
	
	public void addStaff(Staff staff) {
		try {
			String query = "insert into staff values(?, ?, ?, ?)";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			stmt.setString(1, staff.getName());
			stmt.setString(2, staff.getId());
			stmt.setString(3, staff.getPassword());
			stmt.setString(4, staff.getRole());
			
			stmt.execute();
			System.out.println("User has been signed up successfully");
		} catch (SQLException e) {
			//e.printStackTrace();
			//System.out.println("Query has failed");
			System.out.println("Failed to add new staff. User may be already existing");
		}
	}
}
