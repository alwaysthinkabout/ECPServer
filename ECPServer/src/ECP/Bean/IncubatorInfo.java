package ECP.Bean;

import java.sql.Timestamp;

import ECP.util.db.DBConnect;

public class IncubatorInfo {
	//gj,所有空的整数初始
	private int incubatorID; //数据库表自增主键，不初始化
	private int orgID;
	private String incubatorName;
	private String adminTeamName;
	private int adminTeamID;
	private String description;
	private String address;
	private String desPic;
	private java.sql.Timestamp createTime;
	private String contactPhone;
	private int incubateNum;
	private int adminID;
	private String adminDoc;
	private String password;
	private String IndustryType;
	private int IncubatorType;
	private String contactEmail;
	
	public IncubatorInfo(){
		this.orgID=-1;
		this.incubatorName=null;
		this.adminTeamName=null;
		this.adminID=-1;
		this.description=null;
		this.address=null;
		this.desPic=null;
		this.createTime=new Timestamp(System.currentTimeMillis());
		this.contactPhone=null;
		this.incubateNum=-1;
		this.adminID=-1;
		this.adminDoc=null;
		this.password=null;
		this.IndustryType=null;
		this.IncubatorType=0;
		this.contactEmail=null;		
	}
	
	public int getIncubatorID(){
		return this.incubatorID;
	}
//	public void setIncubatorID(int incubatorID){
//		this.incubatorID=incubatorID;
//	}
	public int getOrgID(){
		return this.orgID;
	}
	public void setOrgID(int orgID){
		this.orgID=orgID;
	}
	public String getIncubatorName(){
		return this.incubatorName;
	}
	public void setIncubatorName(String incubatorName){
		this.incubatorName=incubatorName;
	}
	public String getAdminTeamName(){
		return this.adminTeamName;
	}
	public void setAdminTeamName(String adminTeamName){
		this.adminTeamName=adminTeamName;
	}
	public int getAdminTeamID(){
		return this.adminTeamID;
	}
	public void setAdminTeamID(int adminTeamID){
		this.adminTeamID=adminTeamID;
	}
	public String getDescription(){
		return this.description;
	}
	public void setDescription(String description){
		this.description=description;
	}
	public String getAddress(){
		return this.address;
	}
	public void setAddress(String address){
		this.address=address;
	}
	public String getDesPic(){
		return this.desPic;
	}
	public void setDesPic(String desPic){
		this.desPic=desPic;
	}
	public java.sql.Timestamp getCreateTime(){
		return this.createTime;
	}
	public String getContactPhone(){
		return this.contactPhone;
	}
	public void setContactPhone(String contactPhone){
		this.contactPhone=contactPhone;
	}
	public int getIncubateNum(){
		return this.incubateNum;
	}
	public void setIncubateNum(int incubateNum){
		this.incubateNum=incubateNum;
	}
	public int getAminID(){
		return this.adminID;
	}
	public void setAdminID(int adminID){
		this.adminID=adminID;
	}
	public String getAdminDoc(){
		return this.adminDoc;
	}
	public void setAdminDoc(String adminDoc){
		this.adminDoc=adminDoc;
	}
	public String getPassword(){
		return this.password;
	}
	public void setPassword(String password){
		this.password=password;
	}
	public String getIndustryType(){
		return this.IndustryType;
	}
	public void setIndustryType(String industryType){
		this.IndustryType=industryType;
	}
	public int getIncubatorType(){
		return this.IncubatorType;
	}
	public void setIncubatorType(int incubatorType){
		this.IncubatorType=incubatorType;
	}
	public String getContactEmail(){
		return this.contactEmail;
	}
	public void setContactEmail(String contactEmail){
		this.contactEmail=contactEmail;
	}
	
	
	
	
}
