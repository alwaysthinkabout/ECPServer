package ECP.model;

public class JobUserRole {
	private String id;
	private String userId;
	private String roleId;
	private String enable;
	private String firstLogin;
	private String password;
	private String newPassword;
	
	public JobUserRole(){
		id = "99999";
		userId = "99999";
		roleId = "1";
		enable = "0";
		firstLogin = "0";
		password = "123456";
		newPassword = "123456";
	}
	
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getRoleId() {
		return roleId;
	}
	
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public String getEnable() {
		return enable;
	}
	
	public void setEnable(String enable) {
		this.enable = enable;
	}
	
	public String getFirstLogin() {
		return firstLogin;
	}
	
	public void setFirstLogin(String firstLogin) {
		this.firstLogin = firstLogin;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
