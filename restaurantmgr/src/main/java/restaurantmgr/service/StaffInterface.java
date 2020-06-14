package restaurantmgr.service;

import java.util.Optional;

import restaurantmgr.model.Staff;

public interface StaffInterface {
	public Optional<Staff> findStaffByUsername(String username);
	public void addStaff(Staff staff);
}
