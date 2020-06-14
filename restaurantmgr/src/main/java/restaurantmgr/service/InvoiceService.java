package restaurantmgr.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Optional;

import restaurantmgr.model.Invoice;

public class InvoiceService implements InvoiceInterface {
	
	private static final InvoiceService instance = new InvoiceService();
	
	private InvoiceService() {}
	
	public static InvoiceService getInstance() {
        return instance;
    }
	
	public int addInvoice(Invoice invoice) {
		try {
			String query = "insert into invoices(bill, staff_id, table_id, amount_paid, order_id, date_time, payment_method) values(?, ?, ?, ?, ?, ?, ?)";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setFloat(1, invoice.getPrice());
			stmt.setString(2, invoice.getWaiterId());
			stmt.setString(3, invoice.getTableId());
			stmt.setFloat(4, invoice.getAmountPaid());
			stmt.setInt(5,  invoice.getOrderId());
			stmt.setTimestamp(6, invoice.getDate());
			stmt.setString(7, invoice.getPaymentMethod());
			
			stmt.execute();
			//System.out.println("Query executed");
			ResultSet generatedId = stmt.getGeneratedKeys();
			if(generatedId.next()) {
				return generatedId.getInt(1);
			}
			return -1;
		} catch(SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return -1;
		}
	}
	
	public Optional<Invoice> findInvoiceByOrderId(int orderId) {
		try {
			String query = "select * from invoices where order_id = ?";
			//System.out.println("Running query: " + query);
			PreparedStatement stmt = DBRepository.getInstance().getConnection().prepareStatement(query);
			stmt.setInt(1, orderId);

			
			ResultSet rs = stmt.executeQuery();
			System.out.println("Query executed");
			if(rs.next()) {
				int _id = rs.getInt("id");
				float _price = rs.getFloat("bill");
				String _waiterId = rs.getString("staff_id");
				String _tableId = rs.getString("table_id");
				float _amountPaid = rs.getFloat("amount_paid");
				Timestamp _date = rs.getTimestamp("date_time");
				int _orderId = rs.getInt("order_id");
				String _paymentMethod = rs.getString("payment_method");
				
				return Optional.of(new Invoice(_id,_price,_waiterId,_tableId,_amountPaid,_date,_orderId,_paymentMethod));
			}
			return Optional.empty();
		} catch(SQLException e) {
			e.printStackTrace();
			//System.out.println("Query has failed");
			return Optional.empty();
		}
	}
}
