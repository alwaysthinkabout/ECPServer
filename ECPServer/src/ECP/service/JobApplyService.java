package ECP.service;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ECP.dao.CertificateInfoDao;
import ECP.dao.ContractDao;
import ECP.dao.JobApplyDao;
import ECP.dao.JobInfoDao;
import ECP.dao.JobUserAuditDao;
import ECP.dao.OrganizationInfoDao;
import ECP.dao.PerExperienceDao;
import ECP.dao.PersonInfoDao;
import ECP.model.JobUserAudit;
import ECP.util.common.OfflineInfoManager;
import ECP.util.common.WebSocketManager;
import ECP.util.helper.ExceptionHelper;

/**
 * @Description 
 * Created by durenshi on 2017-4-26
 */
public class JobApplyService {
	
	private JobApplyDao jobApplyDao;
	private PersonInfoDao personInfoDao;
	private PerExperienceDao perExperienceDao;
	private JobInfoDao jobInfoDao;
	private CertificateInfoDao certificateInfoDao;
	private OrganizationInfoDao organizationInfoDao;
	private ContractDao contractDao;
	private JobUserAuditDao jobUserAuditDao;
	
	public JobApplyService(){
		jobApplyDao = new JobApplyDao();
		personInfoDao = new PersonInfoDao();
		perExperienceDao = new PerExperienceDao();
		jobInfoDao = new JobInfoDao();
		certificateInfoDao = new CertificateInfoDao();
		organizationInfoDao = new OrganizationInfoDao();
		contractDao = new ContractDao();
		jobUserAuditDao = new JobUserAuditDao();
	}
	
	public JSONObject jobApplyList(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			String licenseNoString = organizationInfoDao.findById(Integer.parseInt(data.getString("org_id"))).getJSONObject(0).getString("license_no");
			data.put("license_no", licenseNoString);
			result.put("rows", jobApplyDao.query(data));
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject jobApplyDetail(JSONObject data){
		JSONObject result = new JSONObject();	
		JSONObject jobApply = new JSONObject();
		JSONArray certArray = new JSONArray();
		try {
			jobApply = jobApplyDao.findById(Integer.parseInt(data.getString("job_apply_id"))).getJSONObject(0);
			data.put("userId", jobApply.getString("user_id"));
			certArray = certificateInfoDao.findByUserId(data);
			for (int i = 0; i < certArray.length(); i++) {
				JSONObject cert = certArray.getJSONObject(i);
				cert.put("filename",engToCN(cert.getString("cert_name")));
			}
			jobApply.put("fileList", certArray);
			if (certArray.length() > 0) {
				jobApply.put("filesUrl", "/ECPServer/job/downloads?userId="+jobApply.getString("user_id"));
			}
			else jobApply.put("filesUrl", "");
			String contractId = "";
			String contract_modelno = "";
			JSONArray array = new JSONArray();
			array = contractDao.findByJobApplyId(data.getString("job_apply_id"));
			if (array.length()>0) {
				contractId = array.getJSONObject(0).getString("contract_id");
				contract_modelno = array.getJSONObject(0).getString("contract_modelno");
			}
			jobApply.put("contract_id", contractId);
			jobApply.put("contract_modelno", contract_modelno);
			result.put("rows", jobApply);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject applyJob(JSONObject data){
		JSONObject result = new JSONObject();
		//如果用户初级资料未审核，则不能申请岗位
		try {
			boolean is = jobUserAuditDao.isExistJobInfoAudit(data.getString("userId"));
			if (is) {
				result.put("result", "1");
				result.put("resultTip", "申请岗位失败，您的个人信息还未审核。");
				return result;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean flag = personInfoDao.isExit(data);
		int perLevel = 0;    //用户注册级别
		int orgLevel = 0;    //企业注册级别
		if (flag == false) {
			try {
				result.put("result", "1");
				result.put("resultTip", "您信息不完善，请先至少完善初级资料方可求职");
				result.put("data", "null");
				return result;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			perLevel = 1;
		}
		
		flag = perExperienceDao.isExit(data);
		if (flag) {
			perLevel = 2;
		}
		
		try {
			String str = data.getString("workList");
			JSONArray workList = new JSONArray(str);
			for (int i = 0; i < workList.length(); i++) {
				String licenseNo = jobInfoDao.findLicenseById(workList.getJSONObject(i).getString("workId"));
				if (certificateInfoDao.isExit(licenseNo) == true) {
					orgLevel = 2;
					break;
				}
			}
			data.put("workList", workList);
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (perLevel < orgLevel) {
			try {
				result.put("result", "2");
				result.put("resultTip", "申请岗位的所属企业为高级企业，请先完善高级资料");
				result.put("data", "null");
				return result;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//判断存在已提交的用户相关材料
		boolean ifHas = true;
		try {
			if(data.getString("resumeFile").equals("0")){
				result.put("result", "1");
				result.put("resultTip", "请先至少上传简历文件方可求职");
				result.put("data", "null");
				return result;
			}
			Iterator<String> iterator = data.keys();
			while(iterator.hasNext()){
				JSONObject params = new JSONObject();
				params.put("user_id", data.getString("userId"));
				String key = (String) iterator.next(); 
				if (key.contains("File")) {
					String value = data.getString(key);  
					if (value.equals("1")) {
						params.put("cert_name", key); 
					    ifHas = certificateInfoDao.ifHasFile(params);
					    if (!ifHas) {
							result.put("result", "1");
							result.put("resultTip", "申请岗位失败，未上传"+engToCN(key)+"文件");
							result.put("data", "null");
							return result;
						}
					}
				}
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//判断是否重复申请岗位
//		boolean isExist = jobApplyDao.IsExist(data);
//		if (isExist) {
//			try {
//				result.put("result", "1");
//				result.put("resultTip", "申请岗位失败，申请的岗位中含有申请过的岗位。");
//				result.put("data", "null");
//				return result;
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}
		int rs = 0;
		rs = jobApplyDao.insert(data);
		if (rs > 0) {
			try {
				String str = data.getString("workList");
				JSONArray workList = new JSONArray(str);
				for (int i = 0; i < workList.length(); i++) {
					result.put("result", "0");
					result.put("resultTip", "申请成功");
					result.put("data", "null");
					
					String licenseNo = jobInfoDao.findLicenseById(workList.getJSONObject(i).getString("workId"));
					String toAccount =  jobInfoDao.findByLicenseNo(licenseNo).getJSONObject(0).getString("org_id");
					data.put("toAccount", toAccount);
					data.put("fromAccount", data.getString("userId"));
					data.put("msg", "applysCount");
					data.put("fromName", "");
					if(WebSocketManager.isExist(toAccount)){
						//在线直接推送
						WebSocketManager.send(data.toString(), toAccount);
					}else{
						//添加到离线消息中
						OfflineInfoManager.add(toAccount, data);
					}
				}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			try {
				result.put("result", "1");
				result.put("resultTip", "数据插入异常");
				result.put("data", "null");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public JSONObject jobApplyStatusUpdate(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			if (data.getString("apply_status2").equals("已退回")) {
				String contractId = "";
				if (contractDao.findByJobApplyId(data.getString("job_apply_id")).length()>0) {
					contractId = contractDao.findByJobApplyId(data.getString("job_apply_id")).getJSONObject(0).getString("contract_id");
				}
				if (!contractId.equals("")) {
					result.put("result", "fail");
					result.put("resultTip", "退回失败，该申请含有未签订的合同");
					return result;
				}
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int ret = jobApplyDao.updateStatus(data);
		try {
			if (ret>0) {
				result.put("result", "success");
			}
			else {
				result.put("result", "fail");
				result.put("resultTip", "更新状态失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject jobHistoryApplyList(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			result.put("result", "0");
			result.put("resultTip", "获取用户历史职位申请记录成功");
			result.put("data", jobApplyDao.findByUserId(data));
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject jobValidApplyList(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			data.put("applyState", "已批准");
			result.put("result", "0");
			result.put("data", jobApplyDao.findByUserId(data));
			result.put("resultTip", "获取用户有效职位申请记录成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject jobLeaveAdd(JSONObject data){
		JSONObject result = new JSONObject();
		int rs = 0;
		rs = jobApplyDao.insertLeave(data);
		try {
			if (rs > 0) {
				result.put("result", "0");
				result.put("resultTip", "岗位留言成功");
				result.put("data", "null");
				//将留言推送给招聘端
				String toAccount = jobApplyDao.findByJobInfoId(data.getString("workId"));
				data.put("fromAccount", data.getString("userId"));
				data.put("toAccount", toAccount);
				data.put("msg", "leaveMessageCount");
				if(WebSocketManager.isExist(toAccount)){
					WebSocketManager.send(data.toString(), toAccount);
				}
			}
			else{
				result.put("result", "1");
				result.put("resultTip", "岗位留言失败，是否传入参数不对..");
				result.put("data", "null");
			} 
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject userDataLevel(JSONObject data){
		JSONObject result = new JSONObject();
		int userLever = 0;
		if (personInfoDao.isExit(data)) {
			userLever = 1;
		}
		if (perExperienceDao.isExit(data)) {
			userLever = 2;
		}
		try {
			result.put("result", "0");
			result.put("data", userLever);
			result.put("resultTip", "获取用户注册级别成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject personPrimaryInfo(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			JSONObject user = personInfoDao.findByUserId(data).getJSONObject(0);
			JSONArray auditObject = jobUserAuditDao.findByUserIdRecent(data.getString("userId"));
			if (auditObject.length() > 0) {
				user.put("audit_result", auditObject.getJSONObject(0).getString("audit_result"));
			}
			result.put("result", "0");
			result.put("data", user);
			result.put("resultTip", "获取用户初级资料成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject personPrimaryInfoUpdate(JSONObject data){
		JSONObject result = new JSONObject();
		int rs = 0;
		try {
			boolean is = jobUserAuditDao.isExistJobInfoAudit(data.getString("userId"));
			if (is) {
				result.put("result", "fail");
				result.put("resultTip", "修改用户信息失败，含有未审核的注册级信息。");
				return result;
			}
			JobUserAudit jobAudit = new JobUserAudit();
			if (personInfoDao.isExit(data)) {
				//rs = personInfoDao.update(data);
				String userInfoString = setUserInfo(data,false);
				System.out.println("修改后的信息：" + userInfoString);
				if (userInfoString.equals("{}")) {
					result.put("result", "fail");
					result.put("resultTip", "提交信息失败，您并未修改任何内容");
					return result;
				}
				jobAudit.setApplyUserId(data.getString("userId"));
				jobAudit.setOpeType("修改");
				jobAudit.setUserInfo(userInfoString);
				int flag = jobUserAuditDao.insertPerInfoAudit(jobAudit);
				if(flag>0){		
					result.put("result", "success");
					result.put("resultTip", "提交信息成功，等待审核");
				}else{
					result.put("result", "fail");
					result.put("resultTip", "提交信息失败");
					return result;
				}
			}
			else{
				String userInfoString = setUserInfo(data,true);
				System.out.println("修改后的信息：" + userInfoString);
				if (userInfoString.equals("{}")) {
					result.put("result", "fail");
					result.put("resultTip", "提交信息失败，您并未修改任何内容");
					return result;
				}
				jobAudit.setApplyUserId(data.getString("userId"));
				jobAudit.setOpeType("新增");
				jobAudit.setUserInfo(userInfoString);
				int flag = jobUserAuditDao.insertPerInfoAudit(jobAudit);
				rs = personInfoDao.insert(data);	
				if (rs>0 && flag >0) {
					result.put("result", "0");
					result.put("resultTip", "修改用户初级注册信息成功");
					result.put("data", "null");
				}
				else{
					result.put("result", "1");
					result.put("resultTip", "修改用户初级注册信息失败");
					result.put("data", "null");
				} 
			}
			
		} catch (Exception e) {
			result = new ExceptionHelper("程序异常").getErrorResult();		}
		return result;
	}
	
	public JSONObject personExperienceDetail(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			result.put("result", "0");
			result.put("data", perExperienceDao.findByExpId(data).getJSONObject(0));
			result.put("resultTip", "获取用户个人经历信息成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject personExperienceAdd(JSONObject data){
		JSONObject result = new JSONObject();
		int rs = 0;
		rs = perExperienceDao.insert(data);
		try {
			if (rs>0) {
				result.put("result", "0");
				result.put("data", "");
				result.put("resultTip", "添加用户高级注册资料成功");
			}
			else{
				result.put("result", "1");
				result.put("data", "");
				result.put("resultTip", "添加用户高级注册资料失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject personExperienceUpdate(JSONObject data){
		JSONObject result = new JSONObject();
		int rs = 0;
		rs = perExperienceDao.update(data);
		try {
			if (rs>0) {
				result.put("result", "0");
				result.put("data", "");
				result.put("resultTip", "修改用户个人经历资料成功");
			}
			else{
				result.put("result", "1");
				result.put("data", "");
				result.put("resultTip", "修改用户个人经历资料失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject personExperienceListDetail(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			result.put("result", "0");
			result.put("data", perExperienceDao.findByUserId(data));
			result.put("resultTip", "获取用户个人经历信息成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject personExperienceListDelete(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			String idString = perExperienceDao.findByUserIdExId(data).getJSONObject(0).getString("cert_id");
			int flag = perExperienceDao.delete(data);
			if (flag>0) {
				certificateInfoDao.delete(idString);
				result.put("result", "0");
				result.put("data", "");
				result.put("resultTip", "删除用户个人经历信息成功");
			}
			else{
				result.put("result", "1");
				result.put("data", "");
				result.put("resultTip", "删除用户个人经历信息失败");
			}
		} catch (Exception e) {
			result = new ExceptionHelper("程序异常").getErrorResult();
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject jobAppliersList(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			String licenseNoString = organizationInfoDao.findById(Integer.parseInt(data.getString("org_id"))).getJSONObject(0).getString("license_no");
			data.put("license_no", licenseNoString);
			result.put("data", jobApplyDao.findAppliers(data));
			result.put("result", "返回数据成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject findApplysCountById(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			int org_id = data.getInt("org_id");
			JSONArray array = jobApplyDao.findApplysCountById(org_id);
			if(array.getJSONObject(0).has("applyscount"))
			{
				result.put("applysCount", array.getJSONObject(0).getString("applyscount"));
			}else{
				result.put("applysCount", "0");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	private String engToCN(String certName){
    	switch(certName){
			case "resumeFile":return "个人简历";
			case "pictureFile":return "个人现照";
			case "educationFile":return "学历证书";
			case "degreesFile":return "学位证书";
			case "titleFile":return "职称证书";
			case "qualificationFile":return "资质证书";
			case "recommendFile":return "推荐信";
			case "proveFile":return "证明材料";
			default:return "其他文件";
		}
    }
	
	private String setUserInfo(JSONObject data, boolean isAdd){
		String userInfo = "";
		try {
			JSONObject userData = new JSONObject();
			if (!isAdd) {
				userData = personInfoDao.findByUserId(data).getJSONObject(0);
			}
			JSONObject updateData = data;
			userInfo = compareUserInfo(userData, updateData);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userInfo;
	}
	
	private String compareUserInfo(JSONObject userData, JSONObject updateData){
		JSONObject orgInfObject = new JSONObject();
		try {
			if (!userData.has("id_cardno") || !userData.getString("id_cardno").equals(updateData.getString("idCard"))) {
				orgInfObject.put("idCard", updateData.getString("idCard"));
			}
			if (!userData.has("name") || !userData.getString("name").equals(updateData.getString("name"))) {
				orgInfObject.put("name", updateData.getString("name"));
			}
			if (!userData.has("phone") || !userData.getString("phone").equals(updateData.getString("phone"))) {
				orgInfObject.put("phone", updateData.getString("phone"));
			}
			if (!userData.has("email") || !userData.getString("email").equals(updateData.getString("email"))) {
				orgInfObject.put("email", updateData.getString("email"));
			}
			if (!userData.has("sex") || !userData.getString("sex").equals(updateData.getString("sex"))) {
				orgInfObject.put("sex", updateData.getString("sex"));
			}
			if (!userData.has("birth") || !userData.getString("birth").equals(updateData.getString("birth"))) {
				orgInfObject.put("birth", updateData.getString("birth"));
			}
			if (!userData.has("degree") || !userData.getString("degree").equals(updateData.getString("education"))) {
				orgInfObject.put("education", updateData.getString("education"));
			}
			if (!userData.has("prof_title") || !userData.getString("prof_title").equals(updateData.getString("title"))) {
				orgInfObject.put("title", updateData.getString("title"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orgInfObject.toString();
	}
	
}
