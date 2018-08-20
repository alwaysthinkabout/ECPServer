package ECP.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import ECP.dao.JobRoleDao;
import ECP.dao.UserDao;
import ECP.service.OrganizationInfoService;
import ECP.util.helper.ExceptionHelper;

public class UserService {
	private UserDao userDao;
	private OrganizationInfoService organizationInfoService;
	private JobRoleDao jobRoleDao;
	
	public UserService(){
		userDao = new UserDao();
		organizationInfoService = new OrganizationInfoService();
		jobRoleDao = new JobRoleDao();
	}
	
	//加入角色后这个接口不再维护，可删除
	public JSONObject login(JSONObject data){
		JSONObject result = new JSONObject();
		try{
			String uid = userDao.login(data);
			data.put("user_id", uid);
			if(!uid.equals("")){
				JSONObject retData = new JSONObject();
				JSONArray rdata = new JSONArray();
				rdata = userDao.findOrgId(data);
				if(rdata.length()>0){
					retData.put("org_id",rdata.getJSONObject(0).getString("org_id"));
					retData.put("org_name",rdata.getJSONObject(0).getString("org_name"));
					retData.put("nick_name",rdata.getJSONObject(0).getString("nick_name"));
					if(!rdata.getJSONObject(0).getString("org_id").equals("")){
						JSONObject obj = new JSONObject();
						obj = organizationInfoService.orgIntegrity(retData);
						retData.put("integrity", obj.getString("result"));
					}
					else{
						retData.put("integrity", "0");
					}
				}
				retData.put("user_id", uid);
				result.put("msg", "success");
				result.put("result", "0");
				result.put("data", retData);
			}
			else{
				result.put("msg", "用户名或密码不正确");
				result.put("result", "1");
				result.put("data", "");
			}
		}catch(Exception e){
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject register(JSONObject data){
		JSONObject result = new JSONObject();
		try{
			boolean isExist = userDao.IsExist(data);
			if (isExist) {
				result.put("msg", "用户注册失败，账号已经存在，请重新选择账号");
				result.put("result", "1");
				result.put("data", "");
			}
			else{
				int flag = userDao.register(data);
				if (flag > 0) {
					jobRoleDao.insertToJobUserRole(data);
					result.put("msg", "用户注册成功");
					result.put("result", "0");
					result.put("user_id", flag);
				}
				else{
					result.put("msg", "用户注册失败");
					result.put("result", "1");
					result.put("data", "");
				}
			}
		}catch(Exception e){
			result = new ExceptionHelper("程序异常").getErrorResult();
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject userIds(JSONObject data){
		JSONObject result = new JSONObject();
		try{
			List<String> resList=new ArrayList<>();
			resList = userDao.generateUserAccount();
			result.put("user_ids", resList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject updateUserInfo(JSONObject data){
		JSONObject result = new JSONObject();
		try{			
			boolean isExist = userDao.IsExist(data);
			if (isExist) {
				result.put("msg", "该邮箱已经被使用");
				result.put("result", "1");
				result.put("data", "");
				return result;
			}
			if(data.has("uid"))
				data.put("user_id", data.getString("uid"));
			int flag = userDao.updateUserInfo(data);
			if(flag > 0){
				result.put("msg", "邮箱更新成功");
				result.put("result", "0");
				result.put("data", "");
			}
			else{
				result.put("msg", "邮箱更新失败");
				result.put("result", "1");
				result.put("data", "");
			}
		}catch(Exception e){
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject updatePassword(JSONObject data){
		JSONObject result = new JSONObject();
		try{
			if(data.has("uid"))
			data.put("user_id", data.getString("uid"));
			int flag = userDao.updatePass(data);
			if(flag > 0){
				result.put("msg", "用户密码修改成功");
				result.put("result", "0");
				result.put("data", "");
			}
			else{
				result.put("msg", "原密码不正确");
				result.put("result", "1");
				result.put("data", "");
			}
		}catch(Exception e){
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject findPassword(JSONObject data){
		JSONObject result = new JSONObject();
		try{
			if( userDao.IsExist(data))
			{
				JSONArray rdata = new JSONArray();
				rdata = userDao.findPassword(data);
				if(rdata.length()>0)
				{
					result.put("msg", "用户密码设置成功");
					result.put("result", "0");
					result.put("user_id", rdata.getJSONObject(0).getString("uid"));
				}else
				{
					result.put("msg", "用户密码设置失败！");
					result.put("result", "1");
				}
			}else
			{
				result.put("msg", "邮箱不正确！");
				result.put("result", "1");
			}
		}catch(Exception e){
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}

	public JSONObject getUserInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = userDao.getUserInfo(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "success");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "fail");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public JSONObject userPasswordAlter(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = userDao.userPasswordAlter(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "success");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "fail");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public JSONObject userEmailAlter(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = userDao.userEmailAlter(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "success");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "fail");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
}
