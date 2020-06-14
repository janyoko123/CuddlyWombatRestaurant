package restaurantmgr.model;

public class Owner extends Staff {

	public Owner(String name, String id, String password) {
		super(name, id, password, "owner");
	}

}
