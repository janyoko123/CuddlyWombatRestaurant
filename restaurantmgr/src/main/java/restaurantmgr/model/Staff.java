package restaurantmgr.model;

public class Staff {
	private String name;
	private String id;
	private String password;
	private String role;
	
	public Staff(String name, String id, String password, String role) {
		this.name=name;
		this.id=id;
		this.password=password;
		this.role=role;
	}
	
	//Set properties
		private void setName(String name) {
			this.name=name;
		}
		
		private void setId(String id) {
			this.id=id;
		}
		
		private void setPassword(String password) {
			this.password=password;
		}
		
		private void setRole(String role) {
			this.role=role;
		}

	//Get properties 
		public String getName() {
			return name;
		}
		public String getId() {
			return id;
		}
		
		public String getPassword() {
			return password;
		}
		
		public String getRole() {
			return role;
		}
}
