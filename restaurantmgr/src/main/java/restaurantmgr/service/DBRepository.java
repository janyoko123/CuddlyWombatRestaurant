package restaurantmgr.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBRepository {	
	private static final DBRepository instance = new DBRepository();
	private Connection connection;
	
	private DBRepository() {
		try {
			System.out.println("Loading Driver");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver successfully loaded!");
			
			String dbName = "mydb";
			String userName = "admin";
			String password = "admin";
			String hostname = "database-1.cc7edlmedjaq.us-west-1.rds.amazonaws.com";
			String port = "3306";
			String jdbcUrl = "jdbc:mysql://" + hostname + ":" +
					port + "/" + dbName + "?user=" + userName + "&password=" + password;
			
		    this.connection = DriverManager.getConnection(jdbcUrl);
		    System.out.println("Database connection is successful!");
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static DBRepository getInstance() {
        return instance;
    }
	
	public Connection getConnection() {
		return instance.connection;
	}
}
