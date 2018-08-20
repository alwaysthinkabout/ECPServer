package ECP.service.platform;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import ECP.dao.JobUserAuditDao;
import ECP.dao.OpeLogDao;
import ECP.dao.PersonInfoDao;
import ECP.model.JobUserAudit;
import ECP.model.OpeLog;
import ECP.util.helper.ExceptionHelper;
import ECP.util.helper.JobHelper;

public class JobUserAuditService {
	private JobUserAuditDao jobUserAuditDao;
	private OpeLog opeLog;
	private OpeLogDao opeLogDao;
	private JobUserAudit jobUserAudit;
	private PersonInfoDao personInfoDao;
	
	public JobUserAuditService() {
		jobUserAuditDao = new JobUserAuditDao();
		opeLogDao = new OpeLogDao();
		opeLog = new OpeLog();
		jobUserAudit = new JobUserAudit();
		personInfoDao = new PersonInfoDao();
	}
	
	public JSONObject jobHunterInfoAuditList(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			JobUserAudit user = new JobUserAudit();
			user.setAuditStatus("未审核");
			JSONArray retArray = jobUserAuditDao.query(user);
			result.put("result", "success");
			result.put("data", retArray);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject jobHunterInfoAuditedList(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			JobUserAudit user = new JobUserAudit();
			user.setAuditStatus("已审核");
			JSONArray retArray = jobUserAuditDao.query(user);
			result.put("result", "success");
			result.put("data", retArray);
		} catch (Exception e) {
			result = new ExceptionHelper("程序异常").getErrorResult();		}
		return result;
	}
	
	public JSONObject jobHunterDetail(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			JobUserAudit jobUserAudit = new JobUserAudit();
			jobUserAudit.setAuditId(data.getString("audit_id"));
			JSONObject retObj = jobUserAuditDao.findById(jobUserAudit).getJSONObject(0);
			data.put("userId", retObj.getString("apply_user_id"));
			JSONObject ret = new JSONObject();
			JSONObject perObject = personInfoDao.findByUserId2(data).getJSONObject(0);
			String perInfoString = retObj.getString("user_info");
			if(perInfoString.equals("")){
				perInfoString = "{}";
			}
			JSONObject contentObj = new JSONObject(perInfoString);
		    Iterator<String> iterator = contentObj.keys();
		    StringBuffer contentBuffer = new StringBuffer("");
		    while(iterator.hasNext()){
		    	String key = iterator.next();
			    String value = contentObj.getString(key);
			    if (retObj.getString("ope_type").equals("新增")) {
			    	if (!value.trim().equals("")) {
			    		contentBuffer.append(JobHelper.personInfoEnToCN(key) + "新增为 " + value + ";\n");
					}
				}
			    else contentBuffer.append(JobHelper.personInfoEnToCN(key)+"由 "+perObject.getString(key)+" 更改为 " + value + ";\n");
			    perObject.put(key, value);
		    }
		    perObject.put("content", contentBuffer);
			ret.put("jobHunterInfo", perObject);
			ret.put("auditInfo", retObj);
			result.put("result", "success");
			result.put("data", ret);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject personAuditUpdate(JSONObject data){
		JSONObject result = new JSONObject();
		int flag = 0;
		try {
			data = data.getJSONObject("data");
			jobUserAudit.setAuditId(data.getString("audit_id"));
			jobUserAudit.setAuditReason(data.getString("audit_reason"));
			jobUserAudit.setAuditResult(data.getString("audit_result"));
			jobUserAudit.setAuditStatus("已审核");
			jobUserAudit.setAuditUserId(data.getString("user_id"));
			JSONObject user = jobUserAuditDao.findPerInfoById(jobUserAudit).getJSONObject(0);
			JSONObject contentObj = new JSONObject(user.getString("user_info"));
		    Iterator<String> iterator = contentObj.keys();
		    StringBuffer contentBuffer = new StringBuffer("");
		    while(iterator.hasNext()){
		    	String key = iterator.next();
			    String value = contentObj.getString(key);
			    user.put(key, value);
		    }
		    if (user.getString("ope_type").equals("修改") && 
		    		data.getString("audit_result").equals("通过")) {
		    	user.put("userId", user.getString("user_code"));
		    	personInfoDao.update(user);
			}
		    if (user.getString("ope_type").equals("新增")) {
		    	if (data.getString("audit_result").equals("不通过")) {
				}
			}			
			flag = jobUserAuditDao.updatePerInfoAudit(jobUserAudit);
			if (flag > 0) {
				opeLog.setUserId(data.getString("user_id"));
				opeLog.setType("求职者资料");
//				opeLogDao.insert(opeLog);
				result.put("result", "1");
				result.put("data", "求职者审核资料成功");
			}
			else{
				result.put("result", "0");
				result.put("data", "求职者审核资料失败");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}

}
