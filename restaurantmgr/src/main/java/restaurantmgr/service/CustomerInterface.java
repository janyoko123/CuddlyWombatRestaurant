package restaurantmgr.service;

import restaurantmgr.model.Customer;

public interface CustomerInterface {
	public int addCustomer(Customer customer);
	public String findCustomerName(int customerId);
}
