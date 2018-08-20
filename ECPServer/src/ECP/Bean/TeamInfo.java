package ECP.Bean;

import java.sql.Timestamp;

public class TeamInfo {
	private int id;//team表自增主键。
	private int teamId;
	private String memberName;
	private int memberID;
	private int memberType;
	private String name;
	private String field;
	private java.sql.Timestamp createDate;
	private String address;
	private String logo;
	private String email;
	private String teamTel;
	private String inctroduce;
	private String abstrac;
	private int teamType;
	
	public TeamInfo(){
		this.teamId=-1;
		this.memberName=null;
		this.memberID=-1;
		this.memberType=-1;
		this.name=null;
		this.field=null;
		this.createDate=new Timestamp(System.currentTimeMillis());
		this.address=null;
		this.logo=null;
		this.email=null;
		this.teamTel=null;
		this.inctroduce=null;
		this.abstrac=null;
		this.teamType=0;
	}
	public int getID(){
		return this.id;
	}
	public int getTeamID(){
		return this.teamId;
	}
	public void setTeamID(int teamId){
		this.teamId=teamId;
	}
	public String getMemberName(){
		return this.memberName;
	}
	public void setMemberName(String memberName){
		this.memberName=memberName;
	}
	public int getMemberID(){
		return this.memberID;
	}
	public void setMemberID(int memberID){
		this.memberID=memberID;
	}
	public int getMemberType(){
		return this.memberType;
	}
	public void setMemberType(int memberType){
		this.memberType=memberType;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getField(){
		return this.field;
	}
	public void setFied(String field){
		this.field=field;
	}
	public java.sql.Timestamp getCreateDate(){
		return this.createDate;
	}
	public String getAddress(){
		return this.address;
	}
	public void setAddress(String address){
		this.address=address;
	}
	public String getLogo(){
		return this.logo;
	}
	public void setLogo(String logo){
		this.logo=logo;
	}
	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email){
		this.email=email;
	}
	public String getTeamTel(){
		return this.teamTel;
	}
	public void setTeamTel(String teamTel){
		this.teamTel=teamTel;
	}
	public String getIntroduce(){
		return this.inctroduce;
	}
	public void setIntroduce(String introduce){
		this.inctroduce=introduce;
	}
	public String getAbstract(){
		return this.abstrac;
	}
	public void setAbstract(String abstrac){
		this.abstrac=abstrac;
	}
	public int getTeamType(){
		return this.teamType;
	}
	public void setTeamType(int teamType){
		this.teamType=teamType;
	}
	
	
}
