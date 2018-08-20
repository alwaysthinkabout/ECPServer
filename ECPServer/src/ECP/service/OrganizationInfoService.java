package ECP.service;

import java.util.Calendar;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ECP.dao.CertificateInfoDao;
import ECP.dao.OrgAuditDao;
import ECP.dao.OrgStateDao;
import ECP.dao.OrganizationInfoDao;
import ECP.model.OrgStateAudit;
import ECP.util.helper.ExceptionHelper;
import ECP.util.helper.FileOperate;
import ECP.util.helper.JobHelper;

public class OrganizationInfoService {
	
	private OrganizationInfoDao organizationInfoDao;
	private OrgStateDao orgStateDao;
	private CertificateInfoDao certificateInfoDao;
	private OrgAuditDao orgAuditDao;
	private OrgStateAudit orgStateAudit;
	Calendar now = Calendar.getInstance();  
	
	public OrganizationInfoService(){
		organizationInfoDao = new OrganizationInfoDao();
		orgStateDao = new OrgStateDao();
		certificateInfoDao = new CertificateInfoDao();
		orgAuditDao = new OrgAuditDao();
		orgStateAudit = new OrgStateAudit();
	}
	
	public JSONObject storeList(JSONObject data){
		JSONObject result = new JSONObject();
		JSONArray jobFairArray = organizationInfoDao.query(data);
		try {
			result.put("result", "0");
			result.put("resultTip", "招聘站列表数据获取成功");
			result.put("rows", jobFairArray);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject storeDetail(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			result.put("result", "success");
			result.put("store", organizationInfoDao.findById(Integer.parseInt(data.getString("storeId"))).getJSONObject(0));
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject storeAdd(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			boolean isExit = organizationInfoDao.IsExist(data);
			if (isExit) {
				result.put("result", "fail");
				result.put("resultTip", "新增招聘站失败，原因为招聘站名称或者营业执照号已经存在，请检查..");
			}
			else{
				int flag = organizationInfoDao.insert(data);
				String orgIdString = String.valueOf(flag);
				String user_id=data.getJSONObject("store").getString("user_id");
				data.put("user_id", user_id);
				data.put("org_id", orgIdString);
				int flag2 = organizationInfoDao.insertIntoUserOrg(data);
				if(flag>0 && flag2>0){
					String orgInfoString = setOrgInfo(data, true);
					data.put("operation_type", "新增");
					data.put("user_id", data.getString("user_id"));
					data.put("org_id", orgIdString);
					data.put("org_info", orgInfoString);
					orgAuditDao.insertOrgInfoAudit(data);
					result.put("result", "success");
					result.put("resultTip", "新增招聘站成功，等待审核");
					result.put("data", orgIdString);
				}else{
					result.put("result", "fail");
					result.put("resultTip", "新增招聘站失败");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject storeDelete(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			String orgId = data.getJSONObject("store").getString("storeId");
			boolean is = orgAuditDao.isExistOrgInfoAudit(orgId);
			if (is) {
				result.put("result", "fail");
				result.put("resultTip", "删除注册级信息失败，含有未审核的注册级信息。");
				return result;
			}
			int flag = organizationInfoDao.delete(Integer.parseInt(orgId));
			if(flag>0){		
				result.put("result", "success");
				result.put("resultTip", "删除招聘站成功");
			}else{
				result.put("result", "fail");
				result.put("resultTip", "删除招聘站失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject storeUpdate(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			String orgId = data.getJSONObject("store").getString("storeId");
			boolean is = orgAuditDao.isExistOrgInfoAudit(orgId);
			if (is) {
				result.put("result", "fail");
				result.put("resultTip", "修改注册级信息失败，含有未审核的注册级信息。");
				return result;
			}
			data.put("org_id", orgId);
			data.put("license_no", data.getJSONObject("store").getString("license_no"));
			data.put("org_name", data.getJSONObject("store").getString("storeName"));
			boolean isExit = organizationInfoDao.isExistByLicenseNO(data);
			if (isExit) {
				result.put("result", "fail");
				result.put("resultTip", "修改招聘站失败，招聘站营业执照号已经存在，请检查..");
				return result;
			}
			isExit = organizationInfoDao.isExistByName(data);
			if (isExit) {
				result.put("result", "fail");
				result.put("resultTip", "修改招聘站失败，原因为招聘站名称已经存在，请检查..");
				return result;
			}
			String orgInfoString = setOrgInfo(data,false);
			System.out.println("修改后的信息：" + orgInfoString);
			if (orgInfoString.equals("{}")) {
				result.put("result", "fail");
				result.put("resultTip", "您并未修改任何内容哦！");
				return result;
			}
			data.put("operation_type", "修改");
			data.put("user_id", data.getString("user_id"));
			data.put("org_id", orgId);
			data.put("org_info", orgInfoString);
			int flag = orgAuditDao.insertOrgInfoAudit(data);
			if(flag>0){		
				result.put("result", "success");
				result.put("resultTip", "提交注册级信息成功，等待审核");
			}else{
				result.put("result", "fail");
				result.put("resultTip", "提交注册级信息失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject orgStateAdd(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			int stateId = orgStateDao.insert(data);
			String orgStateString = "";
			orgStateString = setOrgStateInfo(data,true);
			orgStateAudit.setOperationType("新增");
			orgStateAudit.setOrgUserId(data.getString("user_id"));
			orgStateAudit.setOrgInfoId(data.getJSONObject("state").getString("org_id"));
			orgStateAudit.setOrgStateId(String.valueOf(stateId));
			orgStateAudit.setOrgStateInfo(orgStateString);
			int flag2 = orgAuditDao.insertOrgStateAudit(orgStateAudit);
			if(flag2>0){		
				result.put("result", "success");
				result.put("resultTip", "企业状况新增成功，等待审核");
			}else{
				result.put("result", "fail");
				result.put("resultTip", "企业状况新增失败");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject OrgStateDelete(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			
			String stateId = data.getString("id");
			boolean is = orgAuditDao.isExistOrgStateAudit(stateId);
			if (is) {
				result.put("result", "fail");
				result.put("resultTip", "删除企业状况信息失败，含有未审核的企业状况信息。");
				return result;
			}
			JSONArray cids = orgStateDao.findCidByStateId(data);
			String ids = "";
			for (int i = 0; i < cids.length(); i++) {
				ids += cids.getJSONObject(i).getString("cid");
				if (i != cids.length()-1){
					ids += ",";
				}
			}
			data.put("ids", ids);
			JSONArray fileArray = certificateInfoDao.findByIds(data);
			certificateInfoDao.deleteByIds(data);
			orgStateDao.delete(data.getString("id"));
			int flag = 0;
			for (int i = 0; i < fileArray.length(); i++) {
				String filePath = "d:/ECPServerUpload/OrgFile"+"/"+fileArray.getJSONObject(i).getString("cert_path");
				System.out.println(filePath);
				if(FileOperate.deleteFile(filePath)){
					flag = 1;
				}
				else {
					break;
				}
			}
			if(flag>0 || fileArray.length()<1){		
				result.put("result", "success");
				result.put("resultTip", "删除企业状况信息成功");
			}else{
				result.put("result", "fail");
				result.put("resultTip", "删除企业状况信息失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject OrgStateDetail(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			result.put("result", "success");
			result.put("data", orgStateDao.findById(data.getString("org_id")));
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject OrgStateDetailAnnual(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			JSONObject orgState = orgStateDao.findByIdAnnual(data.getString("id")).getJSONObject(0);
			//最近一条审核记录
			data.put("stateIds", orgState.getString("id"));
			JSONArray auditObject = orgAuditDao.findByStateIdRecent(data);
			if (auditObject.length() > 0) {
				orgState.put("audit_state", auditObject.getJSONObject(0).getString("audit_result"));
				if (!auditObject.getJSONObject(0).getString("audit_result").equals("通过")) {
					String stateInfo = auditObject.getJSONObject(0).getString("org_state_info");
					if (stateInfo.equals("")) {
						stateInfo = "{}";
					}
					JSONObject contentObj = new JSONObject(stateInfo);
				    Iterator<String> iterator = contentObj.keys();
				    StringBuffer contentBuffer = new StringBuffer("");
				    while(iterator.hasNext()){
				    	String key = iterator.next();
					    String value = contentObj.getString(key);
					    contentBuffer.append(JobHelper.stateInfoEnToCN(key) + " 更改为 " + value + ";\n");
				    }
				    orgState.put("org_state_info", contentBuffer);
					
				}
				orgState.put("operation_type",  auditObject.getJSONObject(0).getString("operation_type"));
				orgState.put("ope_time", auditObject.getJSONObject(0).getString("operation_time"));
				orgState.put("audit_time", auditObject.getJSONObject(0).getString("audit_time"));
				orgState.put("audit_reason", auditObject.getJSONObject(0).getString("audit_reason"));
			}
			else {
				orgState.put("audit_state", "");
			}
			result.put("result", "success");
			result.put("data", orgState);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject orgStateUpdate(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			String stateId = data.getJSONObject("state").getString("id");
			boolean is = orgAuditDao.isExistOrgStateAudit(stateId);
			if (is) {
				result.put("result", "fail");
				result.put("resultTip", "修改企业状况信息失败，含有未审核的企业状况信息。");
				return result;
			}
			String orgStateString = "";
			orgStateString = setOrgStateInfo(data,false);
			System.out.println("修改后的内容为：" + orgStateString);
			if (orgStateString.equals("{}")) {
				result.put("result", "fail");
				result.put("resultTip", "提交企业状况级信息失败，您并未修改任何内容");
				return result;
			}
			orgStateAudit.setOperationType("修改");
			orgStateAudit.setOrgUserId(data.getString("user_id"));
			orgStateAudit.setOrgInfoId(data.getJSONObject("state").getString("org_id"));
			orgStateAudit.setOrgStateId(stateId);
			orgStateAudit.setOrgStateInfo(orgStateString);
			int flag = orgAuditDao.insertOrgStateAudit(orgStateAudit);
			if(flag>0){	
				result.put("result", "success");
				result.put("resultTip", "提交企业状况信息成功，等待审核");
			}else{
				result.put("result", "fail");
				result.put("resultTip", "提交企业状况信息失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject OrgAllDetail(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			result.put("result", "success");
			JSONObject orginfo = new JSONObject();
			if("".equals(data.getString("storeId"))){
				orginfo.put("level1", organizationInfoDao.findById(0));
				orginfo.put("level2", orgStateDao.findById(data.getString("storeId")));
				orginfo.put("level3", certificateInfoDao.findByLicenseNo("0"));
			}else{
				data.put("org_id",data.getString("storeId"));
				JSONObject orgBasic = organizationInfoDao.findById(Integer.parseInt(data.getString("storeId"))).getJSONObject(0);
				//最近一条审核记录
				JSONArray auditObject = orgAuditDao.findByOrgIdRecent(data);
				if (auditObject.length() > 0) {
					orgBasic.put("audit_state", auditObject.getJSONObject(0).getString("audit_result"));
					if (!auditObject.getJSONObject(0).getString("audit_result").equals("通过")) {
						String orgInfo = auditObject.getJSONObject(0).getString("org_info");
						if (orgInfo.equals("")) {
							orgInfo = "{}";
						}
						JSONObject contentObj = new JSONObject(orgInfo);
					    Iterator<String> iterator = contentObj.keys();
					    StringBuffer contentBuffer = new StringBuffer("");
					    while(iterator.hasNext()){
					    	String key = iterator.next();
						    String value = contentObj.getString(key);
						    contentBuffer.append(JobHelper.orgInfoEnToCN(key) + " 更改为 " + value + ";\n");
					    }
						orgBasic.put("org_info", contentBuffer);
						
					}
				    orgBasic.put("operation_type",  auditObject.getJSONObject(0).getString("operation_type"));
					orgBasic.put("ope_time", auditObject.getJSONObject(0).getString("operation_time"));
					orgBasic.put("audit_time", auditObject.getJSONObject(0).getString("audit_time"));
					orgBasic.put("audit_reason", auditObject.getJSONObject(0).getString("audit_reason"));
				}
				else {
					orgBasic.put("audit_state", "");
				}
				orginfo.put("level1", orgBasic);
				JSONArray orgStateInfo = orgStateDao.findById(data.getString("storeId"));
				if (orgStateInfo.length()>0) {
					String ids = "";
					for (int i = 0; i < orgStateInfo.length(); i++) {
						ids += orgStateInfo.getJSONObject(i).getString("id");
						if(i != orgStateInfo.length()-1){
							ids += ",";
						}
					}
					data.put("stateIds", ids);
					JSONArray stateArray = orgAuditDao.findByStateIdRecent(data);
					for (int i = 0; i < stateArray.length(); i++) {
						for (int j = 0; j < orgStateInfo.length(); j++) {
							if (orgStateInfo.getJSONObject(j).getString("id").equals(
									stateArray.getJSONObject(i).getString("org_state_id"))) {
								orgStateInfo.getJSONObject(j).put("audit_state", stateArray.getJSONObject(i).getString("audit_result"));
								orgStateInfo.getJSONObject(j).put("audit_reason", stateArray.getJSONObject(i).getString("audit_reason"));
								break;
							}
						}
					}
				}
				orginfo.put("level2", orgStateInfo);
				
				orginfo.put("level3", certificateInfoDao.findByLicenseNo(data.getString("storeId")));
			}
			
			result.put("data", orginfo);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject deleteOrgCertInfo(JSONObject data){
		JSONObject result = new JSONObject();
		JSONArray fileObj = new JSONArray();
		int ret = 0;
		try {
			data.put("id", data.getString("cert_id"));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		fileObj = certificateInfoDao.findById(data);
		ret = certificateInfoDao.deleteById(data);
		try {
			if (ret>0) {
				int flag = 0;
				for (int i = 0; i < fileObj.length(); i++) {
					String filePath = "d:/ECPServerUpload/OrgFile"+"/"+fileObj.getJSONObject(i).getString("cert_path");
					System.out.println(filePath);
					if(FileOperate.deleteFile(filePath)){
						flag = 1;
					}
					else {
						break;
					}
				}
				if (flag == 1) {
					result.put("result", "0");
					result.put("resultTip", "删除企业高级注册信息成功");
				}
			}
			else {
				result.put("result", "1");
				result.put("resultTip", "删除企业高级注册信息失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	} 
	
	public JSONObject deleteLicenseFile(JSONObject data){
		JSONObject result = new JSONObject();
		JSONArray fileObj = new JSONArray();
		int ret = 0;
		System.out.println(data.toString());
		try {
			data.put("id", data.getString("cid"));
			data.put("cert_id", data.getString("cid"));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(data.toString());
		fileObj = certificateInfoDao.findById(data);
		ret = certificateInfoDao.deleteByOrgId(data);
		certificateInfoDao.deleteById(data);
		try {
			if (ret>0) {
				int flag = 0;
				for (int i = 0; i < fileObj.length(); i++) {
					String filePath = "d:/ECPServerUpload/OrgFile"+"/"+fileObj.getJSONObject(i).getString("cert_path");
					System.out.println("-------->"+filePath);
					if(FileOperate.deleteFile(filePath)){
						flag = 1;
					}
					else {
						break;
					}
				}
				if (flag == 1) {
					result.put("result", "0");
					result.put("resultTip", "删除信息成功");
				}
			}
			else {
				result.put("result", "1");
				result.put("resultTip", "删除信息失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	} 
	
	public JSONObject deleteStateFile(JSONObject data){
		JSONObject result = new JSONObject();
		JSONArray fileObj = new JSONArray();
		int ret = 0;
		try {
			data.put("id", data.getString("cid"));
			data.put("cert_id", data.getString("cid"));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		fileObj = certificateInfoDao.findById(data);
		ret = certificateInfoDao.deleteByStateId(data);
		certificateInfoDao.deleteById(data);
		try {
			if (ret>0) {
				int flag = 0;
				for (int i = 0; i < fileObj.length(); i++) {
					String filePath = "d:/ECPServerUpload/OrgFile"+"/"+fileObj.getJSONObject(i).getString("cert_path");
					System.out.println(filePath);
					if(FileOperate.deleteFile(filePath)){
						flag = 1;
					}
					else {
						break;
					}
				}
				if (flag == 1) {
					result.put("result", "0");
					result.put("resultTip", "删除信息成功");
				}
			}
			else {
				result.put("result", "1");
				result.put("resultTip", "删除信息失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	} 
	
	public JSONObject orgIntegrity(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			boolean flag = organizationInfoDao.IsExistByOrgId(data);
			if(flag){		
				int integrity = 50;
				int year = now.get(Calendar.YEAR) - 1;
				data.put("year", String.valueOf(year));
				if(orgStateDao.isExitByIdYear(data))
					integrity += 25;
				data.put("year", String.valueOf(year-1));
				if(orgStateDao.isExitByIdYear(data))
					integrity += 15;
				data.put("year", String.valueOf(year-2));
				if(orgStateDao.isExitByIdYear(data))
					integrity += 10;
				result.put("result", String.valueOf(integrity));
			}else{
				result.put("result", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject stateNotFileList(JSONObject data){
		JSONObject result = new JSONObject();
		JSONArray stateArray = new JSONArray();
		try {
			stateArray = orgStateDao.findStateNotFile(data.getString("org_id"));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			result.put("result", "0");
			result.put("rows", stateArray);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	private String setOrgInfo(JSONObject data, boolean isAdd){
		String orgInfo = "";
		try {
			JSONObject orgData = new JSONObject();
			if (!isAdd) {
				orgData = organizationInfoDao.findById(
						Integer.parseInt(data.getJSONObject("store").
								getString("storeId"))).getJSONObject(0);
			}
			JSONObject updateData = data.getJSONObject("store");
			orgInfo = compareOrgInfo(orgData, updateData);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orgInfo;
	}
	
	private String compareOrgInfo(JSONObject orgData, JSONObject updateData){
		JSONObject orgInfObject = new JSONObject();
		try {
			if (!orgData.has("org_name") || !orgData.getString("org_name").equals(updateData.getString("storeName"))) {
				orgInfObject.put("org_name", updateData.getString("storeName"));
			}
			if (!orgData.has("business_scope") || !orgData.getString("business_scope").equals(updateData.getString("coreBusiness"))) {
				orgInfObject.put("business_scope", updateData.getString("coreBusiness"));
			}
			if (!orgData.has("staffcount") || !orgData.getString("staffcount").equals(updateData.getString("staffAmount"))) {
				orgInfObject.put("staffcount", updateData.getString("staffAmount"));
			}
			if (!orgData.has("contact_phone") || !orgData.getString("contact_phone").equals(updateData.getString("contactPhone"))) {
				orgInfObject.put("contact_phone", updateData.getString("contactPhone"));
			}
			if (!orgData.has("license_no") || !orgData.getString("license_no").equals(updateData.getString("license_no"))) {
				orgInfObject.put("license_no", updateData.getString("license_no"));
			}
			if (!orgData.has("contact_email") || !orgData.getString("contact_email").equals(updateData.getString("contactEmail"))) {
				orgInfObject.put("contact_email", updateData.getString("contactEmail"));
			}
			if (!orgData.has("reg_capital") || !orgData.getString("reg_capital").equals(updateData.getString("registerFund"))) {
				orgInfObject.put("reg_capital", updateData.getString("registerFund"));
			}
			if (!orgData.has("org_type") || !orgData.getString("org_type").equals(updateData.getString("properties"))) {
				orgInfObject.put("org_type", updateData.getString("properties"));
			}
			if (!orgData.has("contact") || !orgData.getString("contact").equals(updateData.getString("contact"))) {
				orgInfObject.put("contact", updateData.getString("contact"));
			}
			if (!orgData.has("reg_time") || !orgData.has("org_name") ||!orgData.getString("reg_time").equals(updateData.getString("registerTime"))) {
				orgInfObject.put("reg_time", updateData.getString("registerTime"));
			}
			if (!orgData.has("reg_auth") || !orgData.getString("reg_auth").equals(updateData.getString("regAuth"))) {
				orgInfObject.put("reg_auth", updateData.getString("regAuth"));
			}
			if (!orgData.has("website") || !orgData.getString("website").equals(updateData.getString("officialWebsite"))) {
				orgInfObject.put("website", updateData.getString("officialWebsite"));
			}
			if (!orgData.has("legal_rep") ||!orgData.getString("legal_rep").equals(updateData.getString("registerPerson"))) {
				orgInfObject.put("legal_rep", updateData.getString("registerPerson"));
			}
			if (!orgData.has("reg_address") ||!orgData.has("org_name") ||!orgData.getString("reg_address").equals(updateData.getString("registerRegion"))) {
				orgInfObject.put("reg_address", updateData.getString("registerRegion"));
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orgInfObject.toString();
	}
	
	private String setOrgStateInfo(JSONObject data, boolean IsAdd){
		String orgInfo = "";
		try {
			JSONObject orgData = new JSONObject();
			if (!IsAdd) {
				orgData = orgStateDao.findByIdAnnual(data.getJSONObject("state").getString("id")).getJSONObject(0);
			}
			JSONObject updateData = data.getJSONObject("state");
			orgInfo = compareOrgStateInfo(orgData, updateData);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orgInfo;
	}
	
	private String compareOrgStateInfo(JSONObject orgData, JSONObject updateData){
		JSONObject orgInfObject = new JSONObject();
		try {
			if (!orgData.has("current_indebt") || !orgData.getString("current_indebt").equals(updateData.getString("current_indebt"))) {
				orgInfObject.put("current_indebt", updateData.getString("current_indebt"));
			}
			if (!orgData.has("profit") || !orgData.getString("profit").equals(updateData.getString("profit"))) {
				orgInfObject.put("profit", updateData.getString("profit"));
			}
			if (!orgData.has("taxation") || !orgData.getString("taxation").equals(updateData.getString("taxation"))) {
				orgInfObject.put("taxation", updateData.getString("taxation"));
			}
			if (!orgData.has("loan_balance") || !orgData.getString("loan_balance").equals(updateData.getString("loan_balance"))) {
				orgInfObject.put("loan_balance", updateData.getString("loan_balance"));
			}
			if (!orgData.has("total_assets") || !orgData.getString("total_assets").equals(updateData.getString("total_assets"))) {
				orgInfObject.put("total_assets", updateData.getString("total_assets"));
			}
			if (!orgData.has("net_sales") || !orgData.getString("net_sales").equals(updateData.getString("net_sales"))) {
				orgInfObject.put("net_sales", updateData.getString("net_sales"));
			}
			if (!orgData.has("techstaff_num") || !orgData.getString("techstaff_num").equals(updateData.getString("techStaff_num"))) {
				orgInfObject.put("techstaff_num", updateData.getString("techStaff_num"));
			}
			if (!orgData.has("operate_expense") || !orgData.getString("operate_expense").equals(updateData.getString("operate_expense"))) {
				orgInfObject.put("operate_expense", updateData.getString("operate_expense"));
			}
			if (!orgData.has("employee_num") || !orgData.getString("employee_num").equals(updateData.getString("employee_num"))) {
				orgInfObject.put("employee_num", updateData.getString("employee_num"));
			}
			if (!orgData.has("current_assets") || !orgData.getString("current_assets").equals(updateData.getString("current_assets"))) {
				orgInfObject.put("current_assets", updateData.getString("current_assets"));
			}
			if (!orgData.has("total_indebt") || !orgData.getString("total_indebt").equals(updateData.getString("total_indebt"))) {
				orgInfObject.put("total_indebt", updateData.getString("total_indebt"));
			}
			if (!orgData.has("rd_budget") || !orgData.getString("rd_budget").equals(updateData.getString("RD_budget"))) {
				orgInfObject.put("rd_budget", updateData.getString("RD_budget"));
			}
			if (!orgData.has("revenue") || !orgData.getString("revenue").equals(updateData.getString("revenue"))) {
				orgInfObject.put("revenue", updateData.getString("revenue"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orgInfObject.toString();
	}
	
}
