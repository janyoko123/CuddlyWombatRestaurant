package restaurantmgr.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import restaurantmgr.model.Item;
import restaurantmgr.model.Order;
import restaurantmgr.model.OrderDetail;

public class OrderService implements OrderInterface{

	private static final OrderService instance = new OrderService();
	
	private OrderService() {}
	
	public static OrderService getInstance() {
        return instance;
    }
	
	public int addNewOrder(Order order) {
		try {
			PreparedStatement stmt;
			if(order.getPrice() != null) {
				String query = "insert into orders(table_id,price,status,date_time) values (?,?,?,?)";
				//System.out.println("Running query: " + query);
				stmt = DBRepository.getInstance().getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, order.getTableId());
				stmt.setFloat(2, order.getPrice());
				stmt.setString(3, order.getStatus());
				stmt.setTimestamp(4, order.getDateTime());
			}
			else {
				String query = "insert into orders(table_id,status,date_time) values (?,?,?)";
				//System.out.println("Running query: " + query);
				stmt = DBRepository.getInstance().getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, order.getTableId());
				stmt.setString(2, order.getStatus());
				stmt.setTimestamp(3, order.getDateTime());
			}
			
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
	
	public int addItemToOrder(int orderId, Item item, int quantity) {
		try {
			String query = "insert into order_detail(order_id,item_id,quantity) values (?,?,?)";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, orderId);
			stmt.setInt(2, item.getItemId());
			stmt.setInt(3, quantity);
			
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
	
	public boolean removeItemFromOrder(int orderDetailId) {
		try {
			String query = "delete from order_detail where id = ?";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			stmt.setInt(1, orderDetailId);
			
			stmt.execute();
			//System.out.println("Query executed");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return false;
		}
	}
	
	public boolean updateStatus(int orderId, String status) {
		try {
			String query = "update orders set status=? where id = ?";
			//System.out.println("Running query: " + query +" values("+orderId + ","+status+")");
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			stmt.setInt(2, orderId);
			stmt.setString(1, status);
			
			stmt.execute();
			//System.out.println("Query executed");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return false;
		}
	}
	
	public Optional<Order> findOrder(int orderId) {
		try {
			String query = "select * from orders where id = ?";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			stmt.setInt(1, orderId);
			
			ResultSet rs = stmt.executeQuery();
			//System.out.println("Query executed");
			if(rs.next()) {
				int _id = rs.getInt("id");
				String _tableId = rs.getString("table_id");
				float _price = rs.getFloat("price");
				String _status = rs.getString("status");
				Timestamp _dateTime = rs.getTimestamp("date_time");
				
				return Optional.of(new Order(_id,_tableId,_price,_status,_dateTime));
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
	
	public Optional<List<Order>> findOrders() {
		try {
			String query = "select * from orders";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			
			ResultSet rs = stmt.executeQuery();
			//System.out.println("Query executed");
			
			// List to store all items in
			List<Order> orders = new ArrayList<Order>();
			
			while(rs.next()) {
				
				int _id = rs.getInt("id");
				String _tableId = rs.getString("table_id");
				float _price = rs.getFloat("price");
				String _status = rs.getString("status");
				Timestamp _dateTime = rs.getTimestamp("date_time");
				
				Order order = new Order(_id,_tableId,_price,_status,_dateTime);
				orders.add(order);
			}
			//System.out.println("Found " + orders.size() + " order(s) in the database");
			
			return Optional.of(orders);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return Optional.empty();
		}
	}
	
	public Optional<List<OrderDetail>> findOrderDetailsByOrderId(int orderId) {
		try {
			String query = "select a.*, b.name from order_detail a join items b on a.item_id = b.id where order_id = ?";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			stmt.setInt(1, orderId);
			
			ResultSet rs = stmt.executeQuery();
			//System.out.println("Query executed");
			
			// List to store all items in
			List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
			
			while(rs.next()) {
				
				int _id = rs.getInt("id");
				int _orderId = rs.getInt("order_id");
				int _itemId = rs.getInt("item_id");
				int _quantity = rs.getInt("quantity");
				String _itemName = rs.getString("name");
				
				
				OrderDetail orderDetail = new OrderDetail(_id,_orderId,_itemId,_quantity);
				orderDetail.setItemName(_itemName);
				orderDetails.add(orderDetail);
			}
			//System.out.println("Found " + orderDetails.size() + " order detail(s) in the database");
			
			return Optional.of(orderDetails);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return Optional.empty();
		}
	}

}
