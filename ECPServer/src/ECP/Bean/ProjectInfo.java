package ECP.Bean;

import java.sql.Timestamp;

public class ProjectInfo {
	private int projectId;
	private String projectName;
	private int projectType;
	private String projectContent;
	private String bulletin;
	private java.sql.Timestamp publishTime;
	private String protocalDoc;
	private String taskDoc;
	private String picture;
	private int teamID;
	private int IncubatorID;
	private int projectState;
	private java.sql.Timestamp startTime;
	private java.sql.Timestamp endTime;
	private String briefDesc;
	private int incubateStatus;
	
	public ProjectInfo(){
		this.projectName=null;
		this.projectType=0;
		this.projectContent=null;
		this.bulletin=null;
		this.publishTime=new Timestamp(System.currentTimeMillis());
		this.protocalDoc=null;
		this.taskDoc=null;
		this.picture=null;
		this.teamID=-1;
		this.IncubatorID=-1;
		this.projectState=-1;
		this.startTime=new Timestamp(System.currentTimeMillis());
		this.endTime=new Timestamp(System.currentTimeMillis());
		this.briefDesc=null;
		this.incubateStatus=0;
	}
	public int getProjectId(){
		return this.projectId;
	}
	public String getProjectName(){
		return this.projectName;
	}
	public void setProjectName(String projectName){
		this.projectName=projectName;
	}
	public int getProjectType(){
		return this.projectType;
	}
	public void setProjectType(int projectType){
		this.projectType=projectType;
	}
	public String getProjectContent(){
		return this.projectContent;
	}
	public void setProjectContent(String projectContent){
		this.projectContent=projectContent;
	}
	public String getBulletin(){
		return this.bulletin;
	}
	public void setBulletin(String bulletin){
		this.bulletin=bulletin;
	}
	public java.sql.Timestamp getPublishTime(){
		return this.publishTime;
	}
	public void setPublishTime(java.sql.Timestamp publishTime){
		this.publishTime=publishTime;
	}
	public String getProtocalDoc(){
		return this.protocalDoc;
	}
	public void setProtocalDoc(String protocalDoc){
		this.protocalDoc=protocalDoc;
	}
	public String getTaskDoc(){
		return this.taskDoc;
	}
	public void setTaskDoc(String taskDoc){
		this.taskDoc=taskDoc;
	}
	public String getPicture(){
		return this.picture;
	}
	public void setPicture(String picture){
		this.picture=picture;
	}
	public int getTeamID(){
		return this.teamID;
	}
	public void setTeamID(int teamID){
		this.teamID=teamID;
	}
	public int getIncubatorID(){
		return this.IncubatorID;
	}
	public void setIncubatorID(int incubatorID){
		this.IncubatorID=incubatorID;
	}
	public int getProjectState(){
		return this.projectState;
	}
	public void setProjectState(int projectState){
		this.projectState=projectState;
	}
	public java.sql.Timestamp getStartTime(){
		return this.startTime;
	}
	public void setStartTime(java.sql.Timestamp startTime){
		this.startTime=startTime;
	}
	public java.sql.Timestamp getEndTime(){
		return this.endTime;
	}
	public void setEndTime(java.sql.Timestamp endTime){
		this.endTime=endTime;
	}
	public String getBriefDesc(){
		return this.briefDesc;
	}
	public void setBriefDesc(String briefDesc){
		this.briefDesc=briefDesc;
	}
	public int getIncubateStatus(){
		return this.incubateStatus;
	}
	public void setIncubateStatus(int incubateStatus){
		this.incubateStatus=incubateStatus;
	}
}
