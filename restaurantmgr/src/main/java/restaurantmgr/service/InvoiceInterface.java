package restaurantmgr.service;

import java.util.Optional;

import restaurantmgr.model.Invoice;

public interface InvoiceInterface {
	public int addInvoice(Invoice invoice);
	public Optional<Invoice> findInvoiceByOrderId(int orderId);
}
