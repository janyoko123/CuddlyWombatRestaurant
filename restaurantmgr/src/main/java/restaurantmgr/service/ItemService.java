package restaurantmgr.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import restaurantmgr.model.Item;

public class ItemService implements ItemInterface {
	
	private static final ItemService instance = new ItemService();
	
	private ItemService() {}
	
	public static ItemService getInstance() {
        return instance;
    }
	
	public Optional<List<Item>> findItems() {
		try {
			String query = "select * from items";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			
			ResultSet rs = stmt.executeQuery();
			//System.out.println("Query executed");
			
			// List to store all items in
			List<Item> items = new ArrayList<Item>();
			
			while(rs.next()) {
				
				int _id = rs.getInt("id");
				String _name = rs.getString("name");
				float _price = rs.getFloat("price");
				
				Item item = new Item(_id,_name,_price);
				items.add(item);
			}
			//System.out.println("Found " + items.size() + " item(s) in the database");
			
			return Optional.of(items);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return Optional.empty();
		}
	}
	
	public Optional<List<Item>> findItemsDineIn() {
		try {
			String query = "select * from items where dinein = true";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			
			ResultSet rs = stmt.executeQuery();
			//System.out.println("Query executed");
			
			// List to store all items in
			List<Item> items = new ArrayList<Item>();
			
			while(rs.next()) {
				
				int _id = rs.getInt("id");
				String _name = rs.getString("name");
				float _price = rs.getFloat("price");
				
				Item item = new Item(_id,_name,_price);
				items.add(item);
			}
			//System.out.println("Found " + items.size() + " item(s) in the database");
			
			return Optional.of(items);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return Optional.empty();
		}
	}
	
	public Optional<List<Item>> findItemsTakeOut() {
		try {
			String query = "select * from items where takeout = true";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			
			ResultSet rs = stmt.executeQuery();
			//System.out.println("Query executed");
			
			// List to store all items in
			List<Item> items = new ArrayList<Item>();
			
			while(rs.next()) {
				
				int _id = rs.getInt("id");
				String _name = rs.getString("name");
				float _price = rs.getFloat("price");
				
				Item item = new Item(_id,_name,_price);
				items.add(item);
			}
			//System.out.println("Found " + items.size() + " item(s) in the database");
			
			return Optional.of(items);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return Optional.empty();
		}
	}
	
	public Optional<Item> findByItemId(int id) {
		try {
			String query = "select * from items where id = ?";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			//System.out.println("Query executed");
			if(rs.next()) {
				System.out.println("Found item!");
				
				int _id = rs.getInt("id");
				String _name = rs.getString("name");
				float _price = rs.getFloat("price");
				
				return Optional.of(new Item(_id,_name,_price));
			}
			else {
				System.out.println("Item does not exist.");
				return Optional.empty();
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return Optional.empty();
		}
	}
	
	public int addItem(Item item) {
		try {
			String query = "insert into items(name,price,takeout,dinein) values(?,?,?,?)";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, item.getItemName());
			stmt.setFloat(2, item.getPrice());
			stmt.setBoolean(3, item.isTakeout());
			stmt.setBoolean(4, item.isDinein());
			
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
	
	public boolean removeItem(int id) {
		try {
			String query = "delete from items where id = ?";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			stmt.setInt(1, id);

			stmt.execute();
			//System.out.println("Query executed");
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return false;
		}
	}
	
	public boolean updateName(int id, String name) {
		try {
			String query = "update items set name = ? where id = ?";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			stmt.setString(1, name);
			stmt.setInt(2, id);

			stmt.execute();
			//System.out.println("Query executed");
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return false;
		}
	}
	
	public boolean updatePrice(int id, float price) {
		try {
			String query = "update items set price = ? where id = ?";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			stmt.setFloat(1, price);
			stmt.setInt(2, id);

			stmt.execute();
			//System.out.println("Query executed");
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return false;
		}
	}
	
	public boolean updateDineIn(int id, boolean dinein) {
		try {
			String query = "update items set dinein = ? where id = ?";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			stmt.setBoolean(1, dinein);
			stmt.setInt(2, id);

			stmt.execute();
			//System.out.println("Query executed");
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return false;
		}
	}
	
	public boolean updateTakeOut(int id, boolean takeout) {
		try {
			String query = "update items set takeout = ? where id = ?";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			stmt.setBoolean(1, takeout);
			stmt.setInt(2, id);

			stmt.execute();
			//System.out.println("Query executed");
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return false;
		}
	}
}
