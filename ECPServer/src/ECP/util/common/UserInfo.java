package ECP.util.common;


public class UserInfo {
	private String email;
	private String phone;
	private String password;
	private String gender;
	private ServiceInfo serviceInfo;
	
	public UserInfo(String email, String phone, String password, String gender, ServiceInfo serviceInfo){
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.gender = gender;
		this.setServiceInfo(serviceInfo);
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender(){
		return gender;
	}
	
	public void setGender(String gender){
		this.gender = gender;
	}
	
	public ServiceInfo getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(ServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}
	
}
