package ECP.service.platform;

import org.json.JSONArray;
import org.json.JSONObject;

import ECP.dao.UserInfoDao;
import ECP.model.UserInfo;
import ECP.util.helper.ExceptionHelper;

public class UserInfoService {
	private UserInfoDao userInfoDao;
	
	public UserInfoService(){
		userInfoDao = new UserInfoDao();
	}
	
	public JSONObject userInfoList(JSONObject data){
		JSONObject result = new JSONObject();
		try {
		    UserInfo user = new UserInfo();
			JSONArray resultArray = userInfoDao.query(user);
			user = null;
			result.put("result", "0");
			result.put("resultTip", "用户列表获取成功");
			result.put("data", resultArray);
		}catch (Exception e) {
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject userInfoDetail(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			UserInfo userInfo = new UserInfo();
		    userInfo.setUid(data.getString("id"));
			JSONArray resultArray = userInfoDao.query(userInfo);
			userInfo = null;
			JSONObject retData = new JSONObject();
			if (resultArray.length() > 0) {
				retData = resultArray.getJSONObject(0);
				retData.put("user_state", stateCodeToCN(retData.getString("user_state")));
				retData.put("user_sex", sexCodeToCN(retData.getString("user_sex")));
			}
			result.put("result", "0");
			result.put("resultTip", "用户详情获取成功");
			result.put("data", retData);
		}catch (Exception e) {
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject userInfoDelete(JSONObject data){
		JSONObject result = new JSONObject();
		int rs = 0;
		try {
			UserInfo userInfo = new UserInfo();
			userInfo.setUid(data.getString("id"));
			userInfo.setUserState(1);
			rs = userInfoDao.updateState(userInfo);
			userInfo = null;
			if (rs > 0) {
				result.put("result", "0");
				result.put("resultTip", "用户删除成功");
			}
			else{
				result.put("result", "1");
				result.put("resultTip", "用户删除失败");
			}
			
		}catch (Exception e) {
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	private String stateCodeToCN(String code){
    	switch(code){
			case "1":return "已删除";
			default:return "已启用";
		}
    }
	
	private String sexCodeToCN(String code){
    	switch(code){
			case "1":return "男";
			default:return "女";
		}
    }
	
}
