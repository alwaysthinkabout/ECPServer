package ECP.service;

import org.json.JSONObject;

import ECP.dao.IncubatorDao;

public class IncubatorService {
	private IncubatorDao incubatorDao;	
	
	public IncubatorService(){
		incubatorDao = new IncubatorDao();
		
	}

	public JSONObject getProjectData(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getProjectData(data);
			
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

	public JSONObject setCreateCTeamInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setCreateCTeamInfo(data);
			
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

	public JSONObject setCTeamInviteUserInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setCTeamInviteUserInfo(data);
			
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

	public JSONObject getCTeamJoinedMembersList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getCTeamJoinedMembersList(data);
			
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

	public JSONObject getCTeamWaitedUsersList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getCTeamWaitedUsersList(data);
			
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

	public JSONObject setCTeamCancelInviteUserInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setCTeamCancelInviteUserInfo(data);
			
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

	public JSONObject getCreatedUnusedCTeamInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getCreatedUnusedCTeamInfo(data);
			
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

	public JSONObject setCreateCProjInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setCreateCProjInfo(data);
			
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

	public JSONObject setCreateIcbtInfoByPerson(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setCreateIcbtInfoByPerson(data);
			
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

	public JSONObject setCreateMTeamInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setCreateMTeamInfo(data);
			
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

	public JSONObject setMTeamInviteUserInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setMTeamInviteUserInfo(data);
			
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

	public JSONObject getMTeamJoinedMembersList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getMTeamJoinedMembersList(data);
			
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

	public JSONObject getMTeamWaitedUsersList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getMTeamWaitedUsersList(data);
			
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

	public JSONObject setMTeamCancelInviteUserInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setMTeamCancelInviteUserInfo(data);
			
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

	public JSONObject getCreatedUnusedMTeamInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getCreatedUnusedMTeamInfo(data);
			
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

	public JSONObject setCreateIcbtInfoByTeam(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setCreateIcbtInfoByTeam(data);
			
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

	public JSONObject getCProjList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getCProjList(data);
			
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

	public JSONObject getCProjCheckResult(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getCProjCheckResult(data);
			
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

	public JSONObject getIncubatedCProjInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getIncubatedCProjInfo(data);
			
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

	public JSONObject getUnsettledCProjInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getUnsettledCProjInfo(data);
			
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

	public JSONObject setTodayJournal(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setTodayJournal(data);
			
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

	public JSONObject getHistoryJournalData(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getHistoryJournalData(data);
			
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

	public JSONObject getAvailableIncubatorsList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getAvailableIncubatorsList(data);
			
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

	public JSONObject getIcbtDataByClient(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getIcbtDataByClient(data);
			
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

	public JSONObject setSettleApplicationInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setSettleApplicationInfo(data);
			
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

	public JSONObject getIcbtsList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getIcbtsList(data);
			
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

	public JSONObject getCProjDataByIcbtID(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getCProjDataByIcbtID(data);
			
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

	public JSONObject getCProjApplicationData(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getCProjApplicationData(data);
			
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

	public JSONObject setCheckStatus(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setCheckStatus(data);
			
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

	public JSONObject getIncubatedProjDataByManager(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getIncubatedProjDataByManager(data);
			
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

	public JSONObject getMembersListByCTeamID(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getMembersListByCTeamID(data);
			
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

	public JSONObject getCProjMemberDetails(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getCProjMemberDetails(data);
			
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

	public JSONObject getCProjTodayJournalsData(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getCProjTodayJournalsData(data);
			
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

	public JSONObject setCProjBroadcastData(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setCProjBroadcastData(data);
			
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
			JSONObject retObject = incubatorDao.getSearchTeamRelatedUsersList(data);
			
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

	public JSONObject getHotRecruitProjsList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getHotRecruitProjsList(data);
			
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
	public JSONObject setPublishProjInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setPublishProjInfo(data);
			
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

	public JSONObject setUpdatePublishProjInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setUpdatePublishProjInfo(data);
			
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

	public JSONObject getIcbtPublishProjInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getIcbtPublishProjInfo(data);
			
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

	public JSONObject setDeletePublishProj(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setDeletePublishProj(data);
			
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

	public JSONObject getAllRecruitProjsList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getAllRecruitProjsList(data);
			
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

	public JSONObject getRecruitProjDetails(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getRecruitProjDetails(data);
			
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

	public JSONObject getUnusedCTeamInfoList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getUnusedCTeamInfoList(data);
			
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

	public JSONObject getCTeamInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getCTeamInfo(data);
			
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

	public JSONObject getCProjCheckRecord(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getCProjCheckRecord(data);
			
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

	public JSONObject getCTeamCheckRecord(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getCTeamCheckRecord(data);
			
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

	public JSONObject setSettleApplicationInfoByCTeam(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setSettleApplicationInfoByCTeam(data);
			
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

	public JSONObject getCProjApplicationDataByCTeam(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getCProjApplicationDataByCTeam(data);
			
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

	public JSONObject setCheckStatusByCTeam(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setCheckStatusByCTeam(data);
			
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

	public JSONObject getUserInvitedMsgList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getUserInvitedMsgList(data);
			
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

	public JSONObject setInviteStatusByInviteID(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setInviteStatusByInviteID(data);
			
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

	public JSONObject getUserBasicInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getUserBasicInfo(data);
			
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

	public JSONObject getCProjInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getCProjInfo(data);
			
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

	public JSONObject getPIcbtInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getPIcbtInfo(data);
			
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

	public JSONObject getUnusedMTeamInfoList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getUnusedMTeamInfoList(data);
			
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

	public JSONObject getTIcbtInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getTIcbtInfo(data);
			
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

	public JSONObject setWriteWeeklyReport(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setWriteWeeklyReport(data);
			
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

	public JSONObject getMyWeeklyReportList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getMyWeeklyReportList(data);
			
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

	public JSONObject setWriteMonthlyReport(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setWriteMonthlyReport(data);
			
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

	public JSONObject getMyMonthlyReportList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getMyMonthlyReportList(data);
			
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

	public JSONObject getTeamMonthlyReportList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getTeamMonthlyReportList(data);
			
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

	public JSONObject setWriteMeetingReport(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setWriteMeetingReport(data);
			
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

	public JSONObject getTeamMeetingReportList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getTeamMeetingReportList(data);
			
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

	public JSONObject getTeamWeeklyReportList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getTeamWeeklyReportList(data);
			
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

	public JSONObject getUserApplyJoinMsgList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.getUserApplyJoinMsgList(data);
			
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

	public JSONObject setApplyJoinStatus(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setApplyJoinStatus(data);
			
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

	public JSONObject setApplyJoinMsg(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = incubatorDao.setApplyJoinMsg(data);
			
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
