package restaurantmgr.model;

public class Item {
	private int itemId;
	private String itemName;
	private Float price;
	private boolean takeout;
	private boolean dinein;
	
	public Item(String name, Float price) {
		this.itemName=name;
		this.price=price;
	}
	
	public Item(int id, String name, Float price) {
		this.itemId=id;
		this.itemName=name;
		this.price=price;
	}
	
	public Item(String name, Float price, boolean takeout, boolean dinein) {
		this.itemName=name;
		this.price=price;
		this.takeout=takeout;
		this.dinein=dinein;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public boolean isTakeout() {
		return takeout;
	}

	public void setTakeout(boolean takeout) {
		this.takeout = takeout;
	}

	public boolean isDinein() {
		return dinein;
	}

	public void setDinein(boolean dinein) {
		this.dinein = dinein;
	}
	
	@Override
	public String toString() {
		return this.itemName + ",id="+this.itemId;
	}
	
}
