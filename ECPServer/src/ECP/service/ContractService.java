package ECP.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ECP.dao.ContractDao;
import ECP.dao.ContractTemplateDao;
import ECP.dao.OrganizationInfoDao;
import ECP.dao.JobApplyDao;
import ECP.util.helper.ExceptionHelper;

/*
 * 合同逻辑处理层
 * durenshi
 */
public class ContractService {
	
	private ContractTemplateDao contractTemplateDao;
	private ContractDao contractDao;
	private OrganizationInfoDao organizationInfoDao;
	private JobApplyDao jobApplyDao;//用于签订合同时修改求职申请的状态
	
	
	public ContractService(){
		contractTemplateDao = new ContractTemplateDao();
		contractDao = new ContractDao();
		organizationInfoDao = new OrganizationInfoDao();
		jobApplyDao = new JobApplyDao();
	}
	
	public JSONObject contractTemplateAdd(JSONObject data){
		JSONObject result = new JSONObject();
		int rs = 0;
		rs = contractTemplateDao.insert(data);
		if (rs > 0) {
			try {
				result.put("result", "success");
				result.put("resultTip", "合同模板添加成功");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			try {
				result.put("result", "fail");
				result.put("resultTip", "合同模板添加异常");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public JSONObject contractTemplateUpdate(JSONObject data){
		JSONObject result = new JSONObject();
		int rs = 0;
		rs = contractTemplateDao.update(data);
		if (rs > 0) {
			try {
				result.put("result", "success");
				result.put("resultTip", "信息已发送给甲方，待甲方确认！");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			try {
				result.put("result", "fail");
				result.put("resultTip", "合同模板编辑异常！");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public JSONObject contractTamplateList(JSONObject data){
		JSONObject result = new JSONObject();
		JSONArray contractTemplateArray = new JSONArray();
		try {
			contractTemplateArray = contractTemplateDao.findByOrgId(data.getString("org_id"));
			result.put("result", "0");
			result.put("resultTip", "合同模板列表数据获取成功");
			result.put("rows", contractTemplateArray);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject contractTamplateDetail(JSONObject data){
		JSONObject result = new JSONObject();
		JSONArray contractTemplateArray = new JSONArray();
		try {
			contractTemplateArray = contractTemplateDao.findById(data.getString("template_no"));
			result.put("result", "0");
			result.put("resultTip", "合同模板数据获取成功");
			result.put("rows", contractTemplateArray);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject contractTamplateDelete(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			boolean is = false;
			is = contractDao.IsExist(data);
			if (is) {
				result.put("result", "fail");
				result.put("resultTip", "该模板已被引用，禁止删除");
				return result;
			}
			int flag = contractTemplateDao.delete(data.getString("template_no"));
			if(flag>0){		
				result.put("result", "success");
				result.put("resultTip", "合同模板删除成功");
			}else{
				result.put("result", "fail");
				result.put("resultTip", "删除合同模板失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject contractAdd(JSONObject data){
		JSONObject result = new JSONObject();
		int rs = 0;
		String license_no = "";
		try {
			license_no = organizationInfoDao.findById(Integer.parseInt(data.getString("org_id"))).getJSONObject(0).getString("license_no");
			data.put("license_no", license_no);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		rs = contractDao.insert(data);
		
		if (rs > 0) {
			String orgIdString = String.valueOf(rs);
			try {
				result.put("result", "success");
				result.put("resultTip", "合同添加成功");
				result.put("contract_id", orgIdString);
				//result.put("license_no", license_no);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			try {
				result.put("result", "fail");
				result.put("resultTip", "合同添加异常");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	public JSONObject contractDelete(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			int flag = contractDao.delete(data.getString("contract_id"));
			if(flag>0){		
				result.put("result", "success");
				result.put("resultTip", "删除合同成功");
			}else{
				result.put("result", "fail");
				result.put("resultTip", "删除合同失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject contractDetail(JSONObject data){
		JSONObject result = new JSONObject();
		JSONArray contractTemplateArray = new JSONArray();
		try {
			contractTemplateArray = contractDao.findById(data.getString("contract_id"));
			result.put("result", "0");
			result.put("resultTip", "合同数据获取成功");
			result.put("rows", contractTemplateArray);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject contractUpdate(JSONObject data){
		JSONObject result = new JSONObject();
		int rs = 0;
		rs = contractDao.update(data);
		if (rs > 0) {
			try {
				result.put("result", "success");
				result.put("resultTip", "信息已发送给甲方，待甲方确认！");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			try {
				result.put("result", "fail");
				result.put("resultTip", "合同模板编辑异常");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//add by lgp 求职端更新合同
	public JSONObject contractUpdate1(JSONObject data){
		JSONObject result = new JSONObject();
		int rs = 0;
		rs = contractDao.update1(data);
		if (rs > 0) {
			try {
				result.put("result", "0");
				result.put("resultTip", "信息已发送给甲方，待甲方确认！");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			try {
				result.put("result", "fail");
				result.put("resultTip", "合同模板编辑异常");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//add by lgp
	public JSONObject contractList(JSONObject data){
		JSONObject result = new JSONObject();
		JSONArray contractTemplateArray = new JSONArray();
		try {
			contractTemplateArray = contractDao.findContractListByLicense_no(data);
			result.put("result", "0");
			result.put("resultTip", "合同模板列表数据获取成功");
			result.put("rows", contractTemplateArray);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	//add by lgp
	public JSONObject getContractList(JSONObject data){
		JSONObject result = new JSONObject();
		JSONArray contractArray = new JSONArray();
		try {
			contractArray = contractDao.getContractListByUserId(data.getString("userId"));
			result.put("result", "0");
			result.put("resultTip", "合同列表数据获取成功");
			result.put("rows", contractArray);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
		

	public JSONObject setContract_status(JSONObject data){
		JSONObject result = new JSONObject();
		int rs = 0;
		try {
			if(data.has("contract_status")&&data.getString("contract_status").equals("已签订"))
			{
				data.put("apply_status2", "历史");
				jobApplyDao.updateStatus(data);
			}
			rs = contractDao.setContract_status(data);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (rs > 0) {
			try {
				result.put("result", "0");
				result.put("resultTip", "状态修改成功");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			try {
				result.put("result", "fail");
				result.put("resultTip", "状态修改异常");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
}
