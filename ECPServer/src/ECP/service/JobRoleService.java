package ECP.service;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import ECP.dao.JobRoleDao;
import ECP.dao.UserDao;
import ECP.model.JobUserRole;
import ECP.util.helper.ExceptionHelper;

public class JobRoleService {
	private JobRoleDao jobRoleDao;
	private UserDao userDao;
	private OrganizationInfoService organizationInfoService;
	
	public JobRoleService(){
		userDao = new UserDao();
		jobRoleDao = new JobRoleDao();
		organizationInfoService = new OrganizationInfoService();
	}
	
	public JSONObject login(JSONObject data){
		JSONObject result = new JSONObject();
		try{
			String []temp = jobRoleDao.loginByRole(data).split(",");
			String uid = temp[0];
			String firstLogin = temp[1];
			data.put("user_id", uid);
			if(!uid.equals("0")){
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
				retData.put("role_ope", jobRoleDao.query(data).getJSONObject(0).getString("role_ope"));
				retData.put("user_id", uid);
				retData.put("role_id", data.getString("role_id"));
				retData.put("first_login", firstLogin);
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
	
	public JSONObject jobRoleList(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			JSONArray resultArray = jobRoleDao.query(data);
			result.put("result", "0");
			result.put("resultTip", "角色列表获取成功");
			result.put("data", resultArray);
		}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject jobUserRoleList(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			JSONArray resultArray = jobRoleDao.queryUserRole(data);
			result.put("result", "0");
			result.put("resultTip", "用户角色列表获取成功");
			result.put("data", resultArray);
		}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject jobRoleUpdate(JSONObject data){
		JSONObject result = new JSONObject();
		int rs = 0;
		try {
			JSONArray enableArray = data.getJSONArray("enable");
			JSONArray disableArray = data.getJSONArray("disable");
			List<JobUserRole> jobRoles = new ArrayList<JobUserRole>();
			for (int i = 0; i < enableArray.length(); i++) {
				JobUserRole jobUserRole = new JobUserRole();
				jobUserRole.setEnable("1");
				jobUserRole.setRoleId(String.valueOf(enableArray.get(i)));
				jobUserRole.setUserId(data.getString("user_id"));
				jobRoles.add(jobUserRole);
				jobUserRole = null;
			}
			for (int i = 0; i < disableArray.length(); i++) {
				JobUserRole jobUserRole = new JobUserRole();
				jobUserRole.setEnable("0");
				jobUserRole.setRoleId(String.valueOf(disableArray.get(i)));
				jobUserRole.setUserId(data.getString("user_id"));
				jobRoles.add(jobUserRole);
				jobUserRole = null;
			}
			rs = jobRoleDao.updateEnable(jobRoles);
			if (rs > 0) {
				//禁用角色则将密码重置为123456
				for (int i = 0; i < disableArray.length(); i++) {
					JobUserRole jobUserRole = new JobUserRole();
					jobUserRole.setRoleId(String.valueOf(disableArray.get(i)));
					jobUserRole.setUserId(data.getString("user_id"));
					jobUserRole.setPassword("123456");
					jobRoleDao.updatePasswordByEnable(jobUserRole);
					jobUserRole = null;
				}
				result.put("result", "0");
				result.put("resultTip", "角色配置成功");
				result.put("data", "null");
			}
			else{
				result.put("result", "1");
				result.put("resultTip", "角色配置失败..");
				result.put("data", "null");
			} 
		}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject userUpdatePassword(JSONObject data){
		JSONObject result = new JSONObject();
		int rs = 0;
		try {
			JobUserRole userRole = new JobUserRole();
			userRole.setPassword(data.getString("password"));
			userRole.setNewPassword(data.getString("newPassword"));
			userRole.setUserId(data.getString("user_id"));
			userRole.setRoleId(data.getString("role_id"));
			rs = jobRoleDao.updatePassword(userRole);
			userRole = null;
			if (rs > 0) {
				result.put("msg", "用户密码修改成功");
				result.put("result", "0");
				result.put("data", "");
			}
			else{
				result.put("msg", "原密码不正确");
				result.put("result", "1");
				result.put("data", "");
			}
			
		}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject roleUpdatePassword(JSONObject data){
		JSONObject result = new JSONObject();
		int rs = 0;
		try {
			JobUserRole userRole = new JobUserRole();
			userRole.setPassword(data.getString("password"));
			userRole.setUserId(data.getString("user_id"));
			userRole.setRoleId(data.getString("role_id"));
			rs = jobRoleDao.updatePasswordByEnable(userRole);
			if (rs > 0) {
				userRole.setFirstLogin("1");
				jobRoleDao.updateFirstLogin(userRole);
				userRole = null;
				result.put("msg", "用户密码修改成功");
				result.put("result", "0");
				result.put("data", "");
			}
			else{
				result.put("msg", "原密码不正确");
				result.put("result", "1");
				result.put("data", "");
			}
			
		}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
}
