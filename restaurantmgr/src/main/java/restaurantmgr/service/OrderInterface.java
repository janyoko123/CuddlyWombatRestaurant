package restaurantmgr.service;

import java.util.List;
import java.util.Optional;

import restaurantmgr.model.Item;
import restaurantmgr.model.Order;
import restaurantmgr.model.OrderDetail;

public interface OrderInterface {
	public int addNewOrder(Order order);
	public int addItemToOrder(int orderId, Item item, int quantity);
	public boolean removeItemFromOrder(int orderDetailId);
	public boolean updateStatus(int orderId, String status);
	public Optional<Order> findOrder(int orderId) ;
	public Optional<List<Order>> findOrders();
	public Optional<List<OrderDetail>> findOrderDetailsByOrderId(int orderId);
}
