package ECP.model;

import java.sql.Timestamp;

public class UserInfo {
	private String id;
	private String uid;
	private String user_name;
	private String nick_name;
	private String user_type;
	private String pass;
	private int valid_day;
	private int auth_right;
	private String identity;
	private String mail;
	private String theme;
	private java.sql.Timestamp created;
	private java.sql.Timestamp access;
	private java.sql.Timestamp login;
	private int status;
	private String timezone;
	private String language;
	private String picture;
	private String pass_ques;
	private String backup_mail;
	private int user_sex;
	private String user_phone;
	private String introduce;
	private int user_state;
	
	public UserInfo() {
		// TODO Auto-generated constructor stub
		this.id = "-1";
		this.uid = "-1";
		this.user_name=null;
		this.nick_name=null;
		this.user_type="个人";
		this.pass=null;
		this.valid_day=-1;
		this.auth_right=-1;
		this.identity=null;
		this.mail=null;
		this.theme=null;
		this.created=new Timestamp(System.currentTimeMillis());
		this.access=new Timestamp(System.currentTimeMillis());
		this.login=new Timestamp(System.currentTimeMillis());
		this.status=-1;
		this.timezone=null;
		this.language=null;
		this.picture=null;
		this.pass_ques=null;
		this.backup_mail=null;
		this.user_sex=-1;
		this.user_phone=null;
		this.introduce=null;
		this.user_state= -1;
	}
	
	public String getID(){
		return this.id;
	}
	public void setID(String id){
		this.id = id;
	}
	public String getUid(){
		return this.uid;
	}
	public void setUid(String uid){
		this.uid=uid;
	}
	public String getUserName(){
		return this.user_name;
	}
	public void setUserName(String userName){
		this.user_name=userName;
	}
	public String getNickName(){
		return this.nick_name;
	}
	public void setNickName(String nickName){
		this.nick_name=nickName;
	}
	public String getUserType(){
		return this.user_type;
	}
	public void setUserType(String userType){
		this.user_type=userType;
	}
	public String getPass(){
		return this.pass;
	}
	public void setPass(String pass){
		this.pass=pass;
	}
	public int getValidDay(){
		return this.valid_day;
	}
	public void setValidDay(int validDay){
		this.valid_day=validDay;
	}
	public int getAuthRight(){
		return this.auth_right;
	}
	public void setAuthRight(int authRight){
		this.auth_right=authRight;
	}
	public String getIdentity(){
		return this.identity;
	}
	public void setIdentity(String identity){
		this.identity=identity;
	}
	public String getMail(){
		return this.mail;
	}
	public void setMail(String mail){
		this.mail=mail;
	}
	public String getTheme(){
		return this.theme;
	}
	public void setTheme(String theme){
		this.theme=theme;
	}
	public java.sql.Timestamp getCreated(){
		return this.created;
	}
	public void setCreated(java.sql.Timestamp created){
		this.created=created;
	}
	public java.sql.Timestamp getAccess(){
		return this.access;
	}
	public void setAccess(java.sql.Timestamp access){
		this.access=access;
	}
	public java.sql.Timestamp getLogin(){
		return this.login;
	}
	public void setLogin(java.sql.Timestamp login){
		this.login=login;
	}
	public int getStatus(){
		return this.status;
	}
	public void setStatus(int status){
		this.status=status;
	}
	public String getTimeZone(){
		return this.timezone;
	}
	public void setTimeZone(String timeZone){
		this.timezone=timeZone;
	}
	public String getLanguage(){
		return this.language;
	}
	public void setLanguage(String language){
		this.language=language;
	}
	public String getPicture(){
		return this.picture;
	}
	public void setPicture(String picture){
		this.picture=picture;
	}
	public String getPassQues(){
		return this.pass_ques;
	}
	public void setPassQues(String passQues){
		this.pass_ques=passQues;
	}
	public String getBackupMail(){
		return this.backup_mail;
	}
	public void setBackupMail(String backupMail){
		this.backup_mail=backupMail;
	}
	public int getUserSex(){
		return this.user_sex;
	}
	public void setUserSex(int userSex){
		this.user_sex=userSex;
	}
	public String getUserPhone(){
		return this.user_phone;
	}
	public void setUserPhone(String userPhone){
		this.user_phone=userPhone;
	}
	public String getIntroduce(){
		return this.introduce;
	}
	public void setIntroduce(String introduce){
		this.introduce=introduce;
	}
	public int getUserState(){
		return this.user_state;
	}
	public void setUserState(int userState){
		this.user_state = userState;
	}
	
}
