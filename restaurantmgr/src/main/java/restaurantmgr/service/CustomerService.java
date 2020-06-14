package restaurantmgr.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Optional;

import restaurantmgr.model.Customer;
import restaurantmgr.model.Item;
import restaurantmgr.model.Reservation;

public class CustomerService implements CustomerInterface {
	private static final CustomerService instance = new CustomerService();
	
	private CustomerService() {}
	
	public static CustomerService getInstance() {
        return instance;
    }
	
	public int addCustomer(Customer customer) {
		try {
			String query = "insert into customers(name) values(?)";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, customer.getName());
			
			stmt.execute();
			//System.out.println("Query executed");
			ResultSet generatedId = stmt.getGeneratedKeys();
			if(generatedId.next()) {
				return generatedId.getInt(1);
			}
			return -1;
		}
		catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return -1;
		}
	}
	
	public String findCustomerName(int customerId) {
		try {
			String query = "select * from customers where id = ?";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			stmt.setInt(1, customerId);
			
			ResultSet rs = stmt.executeQuery();
			//System.out.println("Query executed");
			
			if(rs.next()) {
				
				String _name = rs.getString("name");
				
				return _name;
			}
			else {
				return "";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return "";
		}
	}
}
