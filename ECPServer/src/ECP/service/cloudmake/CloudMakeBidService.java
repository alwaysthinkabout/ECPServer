package ECP.service.cloudmake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ECP.dao.CloudMakeBidDao;
import ECP.dao.CloudMakeTaskDao;

public class CloudMakeBidService {
	
	private CloudMakeBidDao cloudMakeBidDao;
	private CloudMakeTaskDao cloudMakeTaskDao;
	
	public CloudMakeBidService(){
		cloudMakeBidDao = new CloudMakeBidDao();
		cloudMakeTaskDao = new CloudMakeTaskDao();
	}
	
	public JSONObject bidToMeInfo(JSONObject data){
		JSONObject result = new JSONObject();	
		result = cloudMakeBidDao.findBidToMeInfo(data);
		try {
			if (result.getString("opResult").equals("1")) {
				result.put("resultTip", "获取向我投标的信息失败");
			}
			else result.put("resultTip", "获取向我投标的信息成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject myBidInfo(JSONObject data){
		JSONObject result = new JSONObject();	
		result = cloudMakeBidDao.findMyBidInfo(data);
		try {
			if (result.getString("opResult").equals("1")) {
				result.put("resultTip", "获取我的投标信息失败");
			}
			else result.put("resultTip", "获取我的投标信息成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject bidAdd(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			int bidId = cloudMakeBidDao.insert(data);
			if (bidId > 0) {
				result.put("opResult", "0");
				result.put("bidId", bidId);
				result.put("resultTip", "提交投标申请成功");
			}
			else {
				result.put("opResult", "1");
				result.put("resultTip", "提交投标申请失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject bidDelete(JSONObject data){
		JSONObject result = new JSONObject();
		int flag = 0;
		try {
			String userString = cloudMakeBidDao.findByBidId(data).getJSONObject(0).getString("deleteuser");
			if (data.getString("user").equals("tenderUser")) {
				if (userString.equals("bidUser")) {
					flag = cloudMakeBidDao.delete(data);
				}
				else{
					flag = cloudMakeBidDao.setDeleteUser(data);
				}
			}
			else if (data.getString("user").equals("bidUser")) {
				if (userString.equals("tenderUser")) {
					flag = cloudMakeBidDao.delete(data);
				}
				else{
					flag = cloudMakeBidDao.setDeleteUser(data);
				}
			}
			if (flag > 0) {
				result.put("opResult", "0");
				result.put("resultTip", "删除投标数据成功");
			}
			else {
				result.put("opResult", "1");
				result.put("resultTip", "删除投标数据失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject confirmBid(JSONObject data){
		JSONObject result = new JSONObject();	
		JSONObject bidObject = new JSONObject();
		try {
			bidObject = cloudMakeBidDao.findByBidId(data).getJSONObject(0);
			data.put("updateStatus", "批准");
			cloudMakeBidDao.setStatus(data);
			cloudMakeTaskDao.setStatus3(bidObject);
			JSONObject inputObject = new JSONObject();
			inputObject.put("updateStatus", "拒绝");
			inputObject.put("taskId", bidObject.getString("task_id"));
			inputObject.put("bidId", data.getString("bidId"));
			cloudMakeBidDao.setStatusByTaskId(inputObject);
			result.put("opResult", "0");
			result.put("resultTip", "批准投标申请成功");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				result.put("opResult", "1");
				result.put("errorMessage", "批准投标申请失败");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		return result;
	}
	
	public JSONObject undoTask(JSONObject data){
		JSONObject result = new JSONObject();	
		JSONArray taskArray = new JSONArray();
		try {
			taskArray = cloudMakeBidDao.findByTaskId(data);
			if (taskArray.length()>0) {
				result.put("resultTip", "该任务已有用户投标，无法撤销");
				result.put("opResult", "1");
			}
			else {
				int flag = 0;
				String fatherId = cloudMakeTaskDao.findOneTask(data).getJSONObject("oneTask").getString("father_task_id");
				if(fatherId.equals("")){
					flag = cloudMakeTaskDao.delete(data);
				}
				else{
					data.put("updateStatus", "未招标");
					flag = cloudMakeTaskDao.setStatus(data);
				}
				if (flag > 0) {
					result.put("opResult", "0");
					result.put("resultTip", "撤销一个招标中的任务成功");
				}
				else{
					result.put("opResult", "1");
					result.put("resultTip", "撤销一个招标中的任务失败");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
}
