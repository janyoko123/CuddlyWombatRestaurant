package restaurantmgr.handler;

import java.util.NoSuchElementException;

import restaurantmgr.model.Staff;
import restaurantmgr.service.StaffInterface;
import restaurantmgr.service.StaffService;
import restaurantmgr.service.StatusDetailService;

public class Authentication {
	private boolean authenticated = false;
	private Staff authenticatedUser;
	StaffInterface staffService;
	
	private static final Authentication instance = new Authentication();
	
	private Authentication() {
		staffService = StaffService.getInstance();
	}
	
	public static Authentication getInstance() {
        return instance;
    }
	
	/*
	 * login(username, password)
	 * Description: This method authenticates and logs the user in. It will throw an exception is 
	 * the user is not valid in the database.
	 * Parameters:
	 * 	username - The username of the staff
	 * 	password - The password of the staff
	 */
	public void login(String username, String password) {
		try {
			// Retrieving staff record from the database
			Staff staff = staffService.findStaffByUsername(username).get();
			if(staff.getId().equalsIgnoreCase(username) && staff.getPassword().equals(password)) {
				authenticated = true;
				authenticatedUser = staff;
			}
		}
		catch (NoSuchElementException e) {
			System.out.println("Username or password is wrong");
		}
	}

	/*
	 * logout()
	 * Description: Logs the current authenticated user out.
	 */
	public void logout() { 
		authenticated = false;
		authenticatedUser = null;
	}
	
	public boolean isAuthenticated() {
		return authenticated;
	}
	
	public Staff getAuthenticatedUser() {
		return authenticatedUser;
	}
}
