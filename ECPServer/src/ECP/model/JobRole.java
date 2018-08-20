package ECP.model;

public class JobRole {
	private String id;
	private String roleName;
	private String roleOpe;
	
	public String getRoleOpe() {
		return roleOpe;
	}

	public void setRoleOpe(String roleOpe) {
		this.roleOpe = roleOpe;
	}

	public JobRole(){
		roleName = "";
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
