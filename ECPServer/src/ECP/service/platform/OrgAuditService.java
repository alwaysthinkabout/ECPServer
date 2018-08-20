package ECP.service.platform;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import ECP.dao.JobUserAuditDao;
import ECP.dao.OpeLogDao;
import ECP.dao.OrgAuditDao;
import ECP.dao.OrgStateDao;
import ECP.dao.OrganizationInfoDao;
import ECP.dao.RoleAuditDao;
import ECP.dao.ShopAuditDao;
import ECP.model.OpeLog;
import ECP.util.helper.ExceptionHelper;
import ECP.util.helper.JobHelper;

public class OrgAuditService {
	private OrgAuditDao orgAuditDao;
	private OpeLog opeLog;
	private OpeLogDao opeLogDao;
	private ShopAuditDao shopAuditDao;
	private JobUserAuditDao jobUserAuditDao;
	private RoleAuditDao roleAuditDao;
	private OrganizationInfoDao organizationInfoDao;
	private OrgStateDao orgStateDao;
	
	public OrgAuditService() {
		orgAuditDao = new OrgAuditDao();
		opeLogDao = new OpeLogDao();
		opeLog = new OpeLog();
		shopAuditDao=new ShopAuditDao();
		jobUserAuditDao=new JobUserAuditDao();
		roleAuditDao=new RoleAuditDao();
		organizationInfoDao = new OrganizationInfoDao();
		orgStateDao = new OrgStateDao();
	}
	
	public JSONObject auditWaitingList(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			String status = "未审核";
			JSONArray retArray = orgAuditDao.findAudits(status);
			JSONArray list = new JSONArray();
			int index = 0;
			for (int i = 0; i < retArray.length(); i++) {
				if (!retArray.getJSONObject(i).getString("amount").equals("0")) {
					list.put(index, retArray.getJSONObject(i));
					index++;
				}
			}
			result.put("result", "success");
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject auditedList(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			String status = "已审核";
			JSONArray retArray = orgAuditDao.findAudits(status);
			JSONArray list = new JSONArray();
			int index = 0;
			for (int i = 0; i < retArray.length(); i++) {
				if (!retArray.getJSONObject(i).getString("amount").equals("0")) {
					list.put(index, retArray.getJSONObject(i));
					index++;
				}
			}
			result.put("result", "success");
			result.put("data", list);
		} catch (Exception e) {
			// TODO: handle exception
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject orgAuditWaiting(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			JSONObject info = new JSONObject();
			JSONObject orgInfo = new JSONObject();
			data.put("status", "未审核");
			JSONArray orgInfoAuditWaiting = orgAuditDao.findByOrgInfoAudit(data);
			if (orgInfoAuditWaiting.length() > 0) {
				orgInfo = orgInfoAuditWaiting.getJSONObject(0);
				String orgInfoString = orgInfo.getString("org_info");
				if(orgInfoString.equals("")){
					orgInfoString = "{}";
				}
				String operationType = orgInfo.getString("operation_type");
				JSONObject contentObj = new JSONObject(orgInfoString);
			    Iterator<String> iterator = contentObj.keys();
			    StringBuffer contentBuffer = new StringBuffer("");
			    while(iterator.hasNext()){
			    	String key = iterator.next();
				    String value = contentObj.getString(key);
				    if (operationType.equals("新增")) {
				    	if (!value.trim().equals("")) {
				    		contentBuffer.append(JobHelper.orgInfoEnToCN(key)+" "+operationType+"为 " + value + ";\n");
						} 	
					}
				    else contentBuffer.append(JobHelper.orgInfoEnToCN(key)+"由 "+orgInfo.getString(key)+" "+operationType+"为 " + value + ";\n");
				    orgInfo.put(key, value);
			    }
			    orgInfo.put("content", contentBuffer);
			}
			info.put("org_info", orgInfo);
			info.put("org_state", orgAuditDao.findByOrgStateAudit(data));
			info.put("org_cert", orgAuditDao.findByOrgCertAudit(data));
			result.put("result", "success");
			result.put("data", info);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject orgAudited(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			JSONObject info = new JSONObject();
			data.put("status", "已审核");
			JSONArray orgInfoAudited = orgAuditDao.findByOrgInfoAudited(data);
			info.put("org_info", orgInfoAudited);
			info.put("org_state", orgAuditDao.findByOrgStateAudit(data));
			info.put("org_cert", orgAuditDao.findByOrgCertAudit(data));
			result.put("result", "success");
			result.put("data", info);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject auditDetail(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			JSONObject info = new JSONObject();
			if (data.has("org_cert_id")) {
				info = orgAuditDao.findByOrgCertAuditId(data).getJSONObject(0);
			}
			else if (data.has("org_state_id")) {
				info = orgAuditDao.findByOrgStateAuditId(data).getJSONObject(0);
				String orgStateInfo = info.getString("org_state_info");
				if (orgStateInfo.equals("")){
					orgStateInfo = "{}";
				}
				String operationType = info.getString("operation_type");
				JSONObject contentObj = new JSONObject(orgStateInfo);
			    Iterator<String> iterator = contentObj.keys();
			    StringBuffer contentBuffer = new StringBuffer("");
			    while(iterator.hasNext()){
			    	String key = iterator.next();
				    String value = contentObj.getString(key);
				    if (operationType.equals("新增")) {
				    	if (!value.trim().equals("")) {
				    		contentBuffer.append(JobHelper.stateInfoEnToCN(key) + operationType+"为 " + value + ";\n");
						}
					}
				    else contentBuffer.append(JobHelper.stateInfoEnToCN(key) + "由 " + info.getString(key)+" "+operationType+"为 " + value + ";\n");
				    info.put(key, value);
			    }
			    info.put("content", contentBuffer);
			}
			else{
				info = orgAuditDao.findByOrgInfoAuditId(data).getJSONObject(0);
			}
			result.put("result", "success");
			result.put("data", info);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject orgInfoAuditedDetail(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			JSONObject info = new JSONObject();
			info = orgAuditDao.findByOrgInfoAuditId(data).getJSONObject(0);
			String orgInfoString = info.getString("org_info");
			if(orgInfoString.equals("")){
				orgInfoString = "{}";
			}
			String operationType = info.getString("operation_type");
			JSONObject contentObj = new JSONObject(orgInfoString);
		    Iterator<String> iterator = contentObj.keys();
		    StringBuffer contentBuffer = new StringBuffer("");
		    while(iterator.hasNext()){
		    	String key = iterator.next();
			    String value = contentObj.getString(key);
			    if (operationType.equals("新增")) {
			    	if (!value.trim().equals("")) {
			    		contentBuffer.append(JobHelper.orgInfoEnToCN(key)+" "+operationType+"为 " + value + ";\n");
					} 	
				}
			    else contentBuffer.append(JobHelper.orgInfoEnToCN(key)+"由 "+info.getString(key)+" "+operationType+"为 " + value + ";\n");
			    info.put(key, value);
		    }
		    info.put("content", contentBuffer);
			result.put("result", "success");
			result.put("data", info);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject orgStateAuditedDetail(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			JSONObject info = new JSONObject();
			info = orgAuditDao.findByOrgStateAuditId(data).getJSONObject(0);
			result.put("result", "success");
			result.put("data", info);
		} catch (Exception e) {
			result = new ExceptionHelper("程序异常").getErrorResult();
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject orgCertAuditedDetail(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			JSONObject info = new JSONObject();
			info = orgAuditDao.findByOrgCertAuditId(data).getJSONObject(0);
			result.put("result", "success");
			result.put("data", info);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject orgAuditUpdate(JSONObject data){
		JSONObject result = new JSONObject();
		int flag = 0;
		try {
			if (data.getString("info_type").equals("org_info")) {
				JSONObject org = orgAuditDao.findByOrgInfoAuditId(data.getJSONObject("data")).getJSONObject(0);
				JSONObject contentObj = new JSONObject(org.getString("org_info"));
			    Iterator<String> iterator = contentObj.keys();
			    StringBuffer contentBuffer = new StringBuffer("");
			    while(iterator.hasNext()){
			    	String key = iterator.next();
				    String value = contentObj.getString(key);
				    org.put(key, value);
			    }
			    if (org.getString("operation_type").equals("修改") && 
			    		data.getJSONObject("data").getString("audit_result").equals("通过")) {
//			    	boolean is = false;
//			    	//审核通过了一个信息  然后审核另一个重名企业信息时，设置不通过
//			    	if (org.has("org_name")) {
//						JSONObject param = new JSONObject();
//						param.put("org_name", org.getString("org_name"));
//						param.put("org_id", data.getString("org_info_id"));
//						is = organizationInfoDao.IsExistAndAuditedSuccess(data);
//						if(is = true){
//							data.getJSONObject("data").put("audit_result", "不通过");
//							orgAuditDao.updateOrgInfoAudit(data);
//							result.put("result", "1");
//							result.put("data", "企业审核资料成功。由于已经存在还有该企业名的企业资料，该企业资料已经设置不通过");
//							return result;
//						}
//					}
//			    	if(is == false)
			    	orgAuditDao.updateOrgInfoAudit(data);
			    	organizationInfoDao.updateAfterAudit(org);
				}
				flag = orgAuditDao.updateOrgInfoAudit(data);
				opeLog.setContent("审核企业注册级资料");
			}
			else if (data.getString("info_type").equals("org_state")) {
				JSONObject org = orgAuditDao.findByOrgStateAuditId(data.getJSONObject("data")).getJSONObject(0);
				JSONObject contentObj = new JSONObject(org.getString("org_state_info"));
			    Iterator<String> iterator = contentObj.keys();
			    StringBuffer contentBuffer = new StringBuffer("");
			    while(iterator.hasNext()){
			    	String key = iterator.next();
				    String value = contentObj.getString(key);
				    org.put(key, value);
			    }
			    if (org.getString("operation_type").equals("修改") &&
			    		data.getJSONObject("data").getString("audit_result").equals("通过")) {
			    	orgStateDao.updateAfterAudit(org);
			    }
				flag = orgAuditDao.updateOrgStateAudit(data);
				opeLog.setContent("审核企业企业年度报表资料");
			}
			else if (data.getString("info_type").equals("org_cert")) {
				flag = orgAuditDao.updateOrgCertAudit(data);
				opeLog.setContent("审核企业高级信息资料");
			}
			else{
				result.put("result", "0");
				result.put("data", "传入参数有误，审核资料失败");
				return result;
			}
			if (flag > 0) {
				opeLog.setUserId(data.getString("user_id"));
				opeLog.setType("企业资料");
				//opeLogDao.insert(opeLog);
				result.put("result", "1");
				result.put("data", "企业审核资料成功");
			}
			else{
				result.put("result", "0");
				result.put("data", "企业审核资料失败");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject getStoreInfoAuditList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retData = shopAuditDao.getStoreInfoAuditList(data);
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
		}
		return result;
	}

	public JSONObject getStoreInfoAuditDetail(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retData = shopAuditDao.getStoreInfoAuditDetail(data);
			if(retData.getInt("flag") == 0)
			{
				result.put("result", "success");
				result.put("resultTip", "保存成功");
				result.put("data", retData.getJSONObject("data"));
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

	public JSONObject getStoreInfoAudit(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retData = shopAuditDao.getStoreInfoAudit(data);
			if(retData.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", "审核成功！");
				result.put("data", retData.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retData.getString("tip"));
				result.put("data", "");
			}
		}catch(Exception e){
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	//获取当前所有未审核的信息数目
	public JSONObject getMsgCounter(){
		JSONObject result = new JSONObject();
		int orgMsgcount = 0;
		int jobHunterMsgCount=0;
		int storeMsgCount=0;
		int roleMsgCount=0;
		try {
			orgMsgcount = orgAuditDao.getOrgMsgCounter();
			jobHunterMsgCount=jobUserAuditDao.getJobHunterMsgCounter();
			storeMsgCount=shopAuditDao.getStoreMsgCounter();
			roleMsgCount=roleAuditDao.getRoleMsgCounter();
			result.put("orgMsgCounter", orgMsgcount);
			result.put("jobHunterMsgCount", jobHunterMsgCount);
			result.put("storeMsgCount", storeMsgCount);
			result.put("roleMsgCount", roleMsgCount);
			result.put("resultTip", "数据获取成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public JSONObject getStoreInfoAuditedList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retData = shopAuditDao.getStoreInfoAuditedList(data);
			if(retData.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", "审核成功！");
				result.put("data", retData.getJSONArray("data"));
			}else {
				result.put("result", "1");
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
