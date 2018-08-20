package ECP.service;

import org.json.JSONArray;
import org.json.JSONObject;

import ECP.dao.WebChatRecordDao;

public class WebChatRecordService{
	private WebChatRecordDao webChatRecordDao;
	public WebChatRecordService(){
		webChatRecordDao = new WebChatRecordDao();
	}
	//获取当前招聘端和某个求职端用户的所有聊天记录
	public JSONObject perUserChat_recodDetail(JSONObject data){
		JSONObject result = new JSONObject();
		JSONArray chat_recodArray = new JSONArray();
		try {
			chat_recodArray = webChatRecordDao.findById(data.getString("user_id"),data.getString("org_id"));
			int msgCounter = webChatRecordDao.getMsgCounterByUser_id(data);
			int flag = webChatRecordDao.updateChat_recordStatu(data);
			
			if(flag>0){
				result.put("result", "0");
				result.put("resultTip", "聊天记录获取成功");
				result.put("msgCounter", msgCounter);
				result.put("rows", chat_recodArray);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//获取当前招聘所有聊天好友
	public JSONObject getFriendsList(JSONObject data){
		JSONObject result = new JSONObject();
		JSONArray friendsList = new JSONArray();
		try {
			friendsList = webChatRecordDao.findUserByOrg_id(data.getString("org_id"));
			for(int i=0;i<friendsList.length();i++)
			{
				friendsList.getJSONObject(i).put("msgCounter", webChatRecordDao.getMsgCounterByUser_id(friendsList.getJSONObject(i)));
			}
			result.put("result", "0");
			result.put("resultTip", "好友列表获取成功");
			result.put("rows", friendsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//修改聊天记录状态
	public JSONObject setChat_recordStatus(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			int flag = webChatRecordDao.updateChat_recordStatu(data);
			if(flag>0)
			{
				result.put("result", "0");
				result.put("resultTip", "消息记录状态修改成功");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//获取当前企业的所有未读聊天记录
	public JSONObject getMsgCounterByOrg_id(JSONObject data){
		JSONObject result = new JSONObject();
		int flag = 0;
		String license_no = "";
		try {
				flag = webChatRecordDao.getMsgCounterByOrg_id(data.getString("org_id"));
				license_no = webChatRecordDao.findLisence_noByOrg_id(data.getString("org_id"));
				if(!license_no.equals(""))
				{
					flag += webChatRecordDao.getLeaveMsgCountersByOrg_id(license_no);
				}
				result.put("orgMsgCounters", flag);
				result.put("resultTip", "数据获取成功");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//根据user_id查找和该用户所有有过聊天记录的企业
	public JSONObject findOrg_friendByUser_id(JSONObject data){
		JSONObject result = new JSONObject();
		JSONArray rdata =new JSONArray();		
		try {
		    rdata = webChatRecordDao.findOrg_friendByUser_id(data.getString("userId"));
		    result.put("result", "0");
		    result.put("resultTip", "获取成功");
			result.put("data", rdata);						
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//求职端删除企业好友
	public JSONObject deleteOrg_friend(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			int flag = webChatRecordDao.deleteOrg_friend(data);
			if(flag>0){		
				result.put("result", "0");
				result.put("resultTip", "好友删除成功");
			}else{
				result.put("result", "1");
				result.put("resultTip", "好友删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//获取所有留言
	public JSONObject getLeaveMessage(JSONObject data){
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		String license_no = "";
		try {
			license_no = webChatRecordDao.findLisence_noByOrg_id(data.getString("org_id"));
			if(!license_no.equals(""))
			{
				array = webChatRecordDao.findLeaveByLicense_no(license_no);
				webChatRecordDao.updateLeaveStatu(license_no);
			}else
			{
				array = null;
			}	
			result.put("data", array);
			result.put("resultTip", "0");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//获取未读留言条数
	public JSONObject getLeaveMessageCounter(JSONObject data){
		JSONObject result = new JSONObject();
		String license_no = "";
		int leaveMessageCounter = 0;
		try {
			license_no = webChatRecordDao.findLisence_noByOrg_id(data.getString("org_id"));
			if(!license_no.equals(""))
			{
				leaveMessageCounter = webChatRecordDao.getLeaveMsgCountersByOrg_id(license_no);
			}

			result.put("leaveMessageCounter", leaveMessageCounter);
			result.put("resultTip", "未读留言条数获取成功");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}