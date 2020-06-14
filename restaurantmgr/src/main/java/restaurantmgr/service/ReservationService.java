package restaurantmgr.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import restaurantmgr.model.Reservation;

public class ReservationService implements ReservationInterface {
	
	private static final ReservationService instance = new ReservationService();
	
	private ReservationService() {}
	
	public static ReservationService getInstance() {
        return instance;
    }
	
	public int addNewReservation(Reservation reservation) {
		try {
			String query = "insert into reservations(customer_id,num,table_type,date_time,active) values (?,?,?,?,?)";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, reservation.getCustomerId());
			stmt.setInt(2, reservation.getNumofPeople());
			stmt.setString(3, reservation.getTableType());
			stmt.setTimestamp(4, reservation.getDateTime());
			stmt.setBoolean(5, reservation.getActive());
			
			stmt.execute();
			//System.out.println("Query executed");
			ResultSet generatedId = stmt.getGeneratedKeys();
			if(generatedId.next()) {
				return generatedId.getInt(1);
			}
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return -1;
		}
	}
	
	public boolean cancelReservation(int reservationId) {
		try {
			String query = "update reservations set active = false where id = ?";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			stmt.setInt(1, reservationId);
			
			stmt.execute();
			//System.out.println("Query executed");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return false;
		}
	}
	
	public Optional<Reservation> lookupReservation(int reservationId) {
		try {
			String query = "select * from reservations where id = ?";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			stmt.setInt(1, reservationId);
			
			ResultSet rs = stmt.executeQuery();
			//System.out.println("Query executed");
			
			if(rs.next()) {
				
				int _id = rs.getInt("id");
				int _customerId = rs.getInt("customer_id");
				int _numofPeople = rs.getInt("num");
				String _tableType = rs.getString("table_type");
				Timestamp _date = rs.getTimestamp("date_time");
				boolean _active = rs.getBoolean("active");
				
				return Optional.of(new Reservation(_id,_customerId,_numofPeople,_tableType,_date,_active));
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
	
	public Optional<List<Reservation>> findReservations() {
		try {
			String query = "select * from reservations";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			
			ResultSet rs = stmt.executeQuery();
			//System.out.println("Query executed");
			
			// List to store all items in
			List<Reservation> reservations = new ArrayList<Reservation>();
			
			while(rs.next()) {
				
				int _id = rs.getInt("id");
				int _customerId = rs.getInt("customer_id");
				int _num = rs.getInt("num");
				String _tableType = rs.getString("table_type");
				Timestamp _dateTime = rs.getTimestamp("date_time");
				boolean _active = rs.getBoolean("active");
				
				Reservation reservation = new Reservation(_id,_customerId,_num,_tableType,_dateTime,_active);
				reservations.add(reservation);
			}
			//System.out.println("Found " + reservations.size() + " reservation(s) in the database");
			
			return Optional.of(reservations);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return Optional.empty();
		}
	}

}
