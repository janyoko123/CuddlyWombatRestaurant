package restaurantmgr.service;

import javafx.collections.ObservableList;
import restaurantmgr.model.StatusDetail;

public interface StatusDetailInterface {
	public void updateList();
	public ObservableList<StatusDetail> getList();
}
