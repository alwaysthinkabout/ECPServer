package ECP.service.platform;

import org.json.JSONObject;

import ECP.dao.RoleAuditDao;
import ECP.util.helper.ExceptionHelper;

public class RoleAuditService {
	private RoleAuditDao roleAuditDao;
	
	public RoleAuditService(){
		roleAuditDao=new RoleAuditDao();
	}

	public JSONObject getRoleInfoAuditList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retData = roleAuditDao.getRoleInfoAuditList(data);
			if(retData.getInt("flag") == 0)
			{
				result.put("result", "success");
				result.put("resultTip", "保存成功");
				result.put("data", retData.getJSONArray("data"));
			}else {
				result.put("result", "fail");
				result.put("resultTip", retData.getString("tip"));
				result.put("data", "");
			}
		}catch(Exception e){
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}

	public JSONObject getRoleInfoAuditDetail(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retData = roleAuditDao.getRoleInfoAuditDetail(data);
			if(retData.getInt("flag") == 0)
			{
				result.put("result", "success");
				result.put("resultTip", "保存成功");
				result.put("data", retData.getJSONArray("data"));
			}else {
				result.put("result", "fail");
				result.put("resultTip", retData.getString("tip"));
				result.put("data", "");
			}
		}catch(Exception e){
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}

	public JSONObject getRoleInfoAudit(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retData = roleAuditDao.getRoleInfoAudit(data);
			if(retData.getInt("flag") == 0)
			{
				result.put("result", "success");
				result.put("resultTip", "审核成功");
				result.put("data", retData.getJSONArray("data"));
			}else {
				result.put("result", "fail");
				result.put("resultTip", retData.getString("tip"));
				result.put("data", "");
			}
		}catch(Exception e){
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}

	public JSONObject getRoleInfoAuditedList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retData = roleAuditDao.getRoleInfoAuditedList(data);
			if(retData.getInt("flag") == 0)
			{
				result.put("result", "success");
				result.put("resultTip", "保存成功");
				result.put("data", retData.getJSONArray("data"));
			}else {
				result.put("result", "fail");
				result.put("resultTip", retData.getString("tip"));
				result.put("data", "");
			}
		}catch(Exception e){
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}

	public JSONObject getRoleInfoAuditedDetail(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retData = roleAuditDao.getRoleInfoAuditedDetail(data);
			if(retData.getInt("flag") == 0)
			{
				result.put("result", "success");
				result.put("resultTip", "保存成功");
				result.put("data", retData.getJSONArray("data"));
			}else {
				result.put("result", "fail");
				result.put("resultTip", retData.getString("tip"));
				result.put("data", "");
			}
		}catch(Exception e){
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}

}
