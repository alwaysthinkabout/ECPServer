package ECP.service;

import org.json.JSONObject;

import ECP.dao.TeamDao;

public class TeamService {
	private TeamDao teamDao;	
	
	public TeamService(){
		teamDao = new TeamDao();
		
	}

	public JSONObject getAllTeamInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.getAllTeamInfo(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject getOneTeamInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.getOneTeamInfo(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject getCreateTeamInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.getCreateTeamInfo(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject getJoinTeamInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.getJoinTeamInfo(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject createTeam(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.createTeam(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject otherRecrutingInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.otherRecrutingInfo(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject myRecrutingInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.myRecrutingInfo(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject publicRecrutingInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.publicRecrutingInfo(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}
	public static void main(String[] args) throws Exception {
		System.out.println("测试TeamDao中的小函数");
		TeamService teamService = new TeamService();
		JSONObject data=new JSONObject();
	//如果用单引号的话就会把2读成字符串，值为50
		data.put("userId","1");
		JSONObject result;
//		result = teamService.getAllTeamInfo(data);
		result = teamService.getJoinTeamInfo(data);
//		System.out.println("结果是："+TeamDao.findBySchoolNo(2));
		System.out.println("结果是："+result.toString());
	}

	public JSONObject getTeamBasicInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.getTeamBasicInfo(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject getOneTeamCompletedProject(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.getOneTeamCompletedProject(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject getOneTeamGoingProject(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.getOneTeamGoingProject(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject dailyReport(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.dailyReport(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public JSONObject weeklyReport(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.weeklyReport(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public JSONObject monthlyReport(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.monthlyReport(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public JSONObject meetingReport(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.meetingReport(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public JSONObject createProj(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.createProj(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public JSONObject getSearchTeamRelatedUsersList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.getSearchTeamRelatedUsersList(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public JSONObject getTeamRecruitDetails(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.getTeamRecruitDetails(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public JSONObject projectDetail(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.projectDetail(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONObject("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public JSONObject getCTeamJoinedMembersList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.getCTeamJoinedMembersList(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public JSONObject setTaskAllocation(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.setTaskAllocation(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public JSONObject myReport(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.myReport(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public JSONObject teamReport(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.teamReport(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public JSONObject teamMeetingReport(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = teamDao.teamMeetingReport(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
}
