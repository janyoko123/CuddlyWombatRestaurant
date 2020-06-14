package restaurantmgr.service;

import java.util.List;
import java.util.Optional;

import restaurantmgr.model.Item;

public interface ItemInterface {
	public Optional<List<Item>> findItems();
	public Optional<List<Item>> findItemsDineIn();
	public Optional<List<Item>> findItemsTakeOut();
	public Optional<Item> findByItemId(int id);
	public int addItem(Item item);
	public boolean removeItem(int id);
	public boolean updateName(int id, String name);
	public boolean updatePrice(int id, float price);
	public boolean updateDineIn(int id, boolean dinein);
	public boolean updateTakeOut(int id, boolean takeout);
}
