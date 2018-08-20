package ECP.service;

import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





import ECP.dao.JobApplyDao;
import ECP.dao.JobInfoDao;
import ECP.dao.OrganizationInfoDao;
import ECP.dao.SubscribeDao;
import ECP.service.common.CFindService;
import ECP.util.common.OfflineInfoManager;
import ECP.util.common.WebSocketManager;
import ECP.util.helper.ExceptionHelper;

public class JobInfoService {
	private JobInfoDao jobInfoDao;
	private OrganizationInfoDao organizationInfoDao;
	private JobApplyDao jobApplyDao;
	private SubscribeDao subscribeDao;
	private CFindService service;
	
	public JobInfoService(){
		jobInfoDao = new JobInfoDao();
		organizationInfoDao = new OrganizationInfoDao();
		jobApplyDao = new JobApplyDao();
		subscribeDao = new SubscribeDao();
		service = new CFindService();
	}
	
	public JSONObject jobStationList(JSONObject data){
		JSONArray jobStationArray = new JSONArray();
		jobStationArray = jobInfoDao.query(data);
		JSONArray resultArray = new JSONArray();
		HashSet<String> hs = new HashSet<String>();
		HashSet<String> hsOrgId = new HashSet<String>();
		JSONArray jobInfoIdArray = new JSONArray();
		for(int i=0;i<jobStationArray.length();i++){
			try {
				jobInfoIdArray.put(i, jobStationArray.getJSONObject(i).getString("job_info_id"));
				hs.add(jobStationArray.getJSONObject(i).getString("org_name"));
				hsOrgId.add(jobStationArray.getJSONObject(i).getString("org_id"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//获取哪些岗位是已经申请的
		try {
			data.put("ids", jobInfoIdArray);
			JSONArray isApplyArray = jobApplyDao.IsApplied(data);
			for(int i=0;i<isApplyArray.length();i++) {
				for (int j = 0; j < jobStationArray.length(); j++) {
					JSONObject object = jobStationArray.getJSONObject(j);
					if (isApplyArray.getJSONObject(i).getString("job_info_id").equals(
							object.getString("job_info_id"))) {
						object.put("is_apply", "1");
					}
				}
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			int hsIndex = 0;
			for (String str : hs ) {
				JSONArray temp = new JSONArray();
				int index =0 ;
				for(int i=0;i<jobStationArray.length();i++) {
					JSONObject object = jobStationArray.getJSONObject(i);
					if (object.getString("org_name").equals(str)) {
						temp.put(index, object);
						index++;
					}
				} 
				JSONObject tempObject = new JSONObject();
				tempObject.put("workList", temp);
				tempObject.put("stationName", str);
				tempObject.put("stationId",temp.getJSONObject(0).getString("org_id"));
				resultArray.put(hsIndex,tempObject);
				hsIndex++;
			}
			
		} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		JSONObject result = new JSONObject();
		try {
			result.put("result", "0");
			result.put("resultTip", "招聘站列表数据获取成功");
			result.put("rows", resultArray);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	//安卓端岗位详情接口
	public JSONObject jobDetatil(JSONObject data){
		JSONObject result = new JSONObject();
		if (data.has("workId")) {
			String id = "";
			try {
				id = data.getString("workId");
				System.out.println("岗位id为："+id);
				if (jobInfoDao.findById(Integer.parseInt(id)).length()>0) {
					result.put("result", "0");
					result.put("resultTip", "岗位详情获取成功");
					result.put("rows", jobInfoDao.findById(Integer.parseInt(id)));
				}
				else {
					result.put("result", "0");
					result.put("resultTip", "岗位详情获取失败，不存在该岗位..");
					result.put("rows", jobInfoDao.findById(Integer.parseInt(id)));
					return result;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = new ExceptionHelper("程序异常").getErrorResult();
			}
			
		}
		else {
			try {
				result.put("result", "1");
				result.put("resultTip", "获取岗位详情失败，缺失workId，请确认是否将workId传入..");
				result.put("rows", "");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return result;
	}
	
	public JSONObject jobStationDetail(JSONObject data){
		JSONObject result = new JSONObject();
		JSONObject wordList = new JSONObject();
		JSONArray organization = new JSONArray();
		try {
			if (data.has("stationId")) {
				organization = organizationInfoDao.findById(Integer.parseInt(data.getString("stationId")));
				wordList.put("stationId", data.getString("stationId"));
				wordList.put("stationName", organization.getJSONObject(0).getString("org_name"));
				wordList.put("registeredPlace", organization.getJSONObject(0).getString("reg_address"));
				wordList.put("registeredTime", organization.getJSONObject(0).getString("reg_time"));
				wordList.put("character", organization.getJSONObject(0).getString("org_type"));
				wordList.put("staffCount", organization.getJSONObject(0).getString("staffcount"));
				wordList.put("webSite", organization.getJSONObject(0).getString("website"));
				wordList.put("contactName", organization.getJSONObject(0).getString("legal_rep"));
				JSONArray stationArray = jobInfoDao.query(data);
				//获取哪些岗位是已经申请的
				JSONArray isApplyArray = jobApplyDao.IsApplied(data);
				for(int i=0;i < isApplyArray.length();i++) {
					for (int j = 0; j < stationArray.length(); j++) {
						JSONObject object = stationArray.getJSONObject(j);
						if (isApplyArray.getJSONObject(i).getString("job_info_id").equals(
								object.getString("job_info_id"))) {
							object.put("is_apply", "1");
						}
					}
				}
				wordList.put("workList", stationArray);
				result.put("result", "0");
				result.put("resultTip", "招聘站详情获取成功");
				result.put("rows", wordList);
			}
			else{
				result.put("result", "1");
				result.put("resultTip", "获取招聘站详情失败，缺失stationId，请确认是否将stationId传入..");
				result.put("rows", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}

	public JSONObject jobAdd(JSONObject data){
		JSONObject result = new JSONObject();
		String licenseNoString = ""; 
		String orgName = "";
		try {
			JSONObject orgObject = organizationInfoDao.findById(Integer.parseInt(data.getJSONObject("job").getString("storeId"))).getJSONObject(0);
			orgName = orgObject.getString("org_name");
			licenseNoString = orgObject.getString("license_no");
			JSONObject job = new JSONObject();
			job = data.getJSONObject("job");
			job.put("license_no", licenseNoString);
			String array[] ;//解决前台无法传递“+”至后台的问题
			if(job.has("payment_type")){
				array = job.getString("payment_type").split(" ");
				if(array.length>1)
				{
					job.put("payment_type", array[0]+"+"+array[1]);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				result.put("result", "fail");
				result.put("resultTip", "数据插入异常");
				return result;
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
		int rs = 0;
		rs = jobInfoDao.insert(data);
		if (rs > 0) {
			try {
				JSONObject params = new JSONObject();
				params.put("type_id", data.getJSONObject("job").getString("job_type_scode"));
				String keyType = jobInfoDao.findJobTypeName(params);
				params.put("key_type", keyType);
				JSONArray jobAppliersArray = subscribeDao.findJobAppliers(params);
				StringBuilder systemInfo=new StringBuilder("您好，这里是 ,根据您的订阅条件，现提醒您  "+orgName+" 的 "+data.getJSONObject("job").getString("jobName")+"岗位正在招聘，敬请关注。");
				for (int i = 0; i < jobAppliersArray.length(); i++) {
					JSONObject dataPush=new JSONObject();
					String toAccount = jobAppliersArray.getJSONObject(i).getString("userid");
					dataPush.put("toAccount", toAccount);
					dataPush.put("fromAccount", data.getJSONObject("job").getString("storeId"));
					dataPush.put("systemInfo", systemInfo);
					dataPush.put("type", "subscribe_push");
					dataPush.put("eventType", "岗位推荐");
					dataPush.put("module", "就业");
					//service.push(dataPush);
					if(WebSocketManager.isExist(toAccount)){
						//在线直接推送
						WebSocketManager.send(dataPush.toString(), toAccount);
					}else{
						//添加到离线消息中
						OfflineInfoManager.add(toAccount, dataPush);
					}
				}
				result.put("result", "success");
				result.put("resultTip", "招聘岗位添加成功");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = new ExceptionHelper("程序异常").getErrorResult();
			}
		}
		else{
			try {
				result.put("result", "fail");
				result.put("resultTip", "数据插入异常");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public JSONObject jobDelete(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			int flag = jobInfoDao.delete(Integer.parseInt(data.getString("jobId")));
			if(flag>0){		
				result.put("result", "success");
				result.put("resultTip", "删除岗位成功");
			}else{
				result.put("result", "fail");
				result.put("resultTip", "删除岗位失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject jobDetail(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			if (data.has("storeId")) {
				JSONArray stationArray = jobInfoDao.findByJobId(data.getString("jobId"));
				result.put("result", "success");
				result.put("job", stationArray.getJSONObject(0));
			}
			else{
				result.put("result", "fail");
				result.put("resultTip", "获取岗位详情失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject jobUpdate(JSONObject data){
		JSONObject result = new JSONObject();
		String licenseNoString = "";
		try {
			licenseNoString = organizationInfoDao.findById(Integer.parseInt(
					data.getJSONObject("job").getString("storeId"))).getJSONObject(0).getString("license_no");
			JSONObject job = new JSONObject();
			job = data.getJSONObject("job");
			job.put("license_no", licenseNoString);
			String array[] ;//解决前台无法传递“+”至后台的问题
			if(job.has("payment_type")){
				array = job.getString("payment_type").split(" ");
				if(array.length>1)
				{
					job.put("payment_type", array[0]+"+"+array[1]);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				result.put("result", "fail");
				result.put("resultTip", "岗位修改异常");
				return result;
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
		int rs = 0;
		rs = jobInfoDao.update(data);
		if (rs > 0) {
			try {
				result.put("result", "success");
				result.put("resultTip", "岗位修改成功");
				JSONObject params = new JSONObject();
				params.put("type_id", data.getJSONObject("job").getString("job_type_scode"));
				String keyType = jobInfoDao.findJobTypeName(params);
				params.put("key_type", keyType);
				JSONArray jobAppliersArray = subscribeDao.findJobAppliers(params);
				StringBuilder systemInfo=new StringBuilder("有您感兴趣的岗位");
				for (int i = 0; i < jobAppliersArray.length(); i++) {
					JSONObject dataPush=new JSONObject();
					dataPush.put("toAccount", jobAppliersArray.getJSONObject(i).getString("userid"));
					dataPush.put("systemInfo", systemInfo);
					service.push(dataPush);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			try {
				result.put("result", "fail");
				result.put("resultTip", "岗位修改异常");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//浏览器端岗位详情接口
	public JSONObject jobInformation(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			if (data.has("jobId")) {
				JSONArray array = new JSONArray();
				array = jobInfoDao.findByJobId(data.getString("jobId"));
				result.put("result", "0");
				result.put("resultTip", "岗位详情获取成功");
				result.put("job", array);				
				result.put("jobTypes", jobInfoDao.findJobTypeList(data));
				data.put("root_id",array.getJSONObject(0).getString("typeId") );
				result.put("jobSecondTypes", jobInfoDao.findJobTypeSecondList(data));
			}
			else{
				result.put("result", "1");
				result.put("resultTip", "岗位详情获取失败，缺失jobId，请确认是否将jobId传入..");
				result.put("rows", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject jobList(JSONObject data){
		JSONObject result = new JSONObject();
		String licenseNoString = "";
		try {
			licenseNoString = organizationInfoDao.findById(Integer.parseInt(
					data.getString("storeId"))).getJSONObject(0).getString("license_no");
			result.put("result", "success");
			result.put("resultTip", "岗位列表获取成功");
			result.put("data", jobInfoDao.findByLicenseNo(licenseNoString));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				result.put("result", "fail");
				result.put("resultTip", "岗位列表获取异常");

			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
		return result;
	}
	
	public JSONObject jobSearch(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			JSONArray workList = jobInfoDao.findByKeywordCity(data);
			//获取哪些岗位是已经申请的
			JSONArray isApplyArray = jobApplyDao.IsApplied(data);
			for(int i=0;i < isApplyArray.length();i++) {
				for (int j = 0; j < workList.length(); j++) {
					JSONObject object = workList.getJSONObject(j);
					if (isApplyArray.getJSONObject(i).getString("job_info_id").equals(
							object.getString("wordId"))) {
						object.put("is_apply", "1");
					}
				}
			}
			result.put("result", "0");
			result.put("resultTip", "岗位搜索成功");
			result.put("data", workList);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject hotJobSearch(JSONObject data){
		JSONObject result = new JSONObject();
		JSONArray hotArray = new JSONArray();
		try {
			hotArray = jobInfoDao.findHotJob(data);
			//获取哪些岗位是已经申请的
			JSONArray isApplyArray = jobApplyDao.IsApplied(data);
			for(int i=0;i<isApplyArray.length();i++) {
				for (int j = 0; j < hotArray.length(); j++) {
					JSONObject object = hotArray.getJSONObject(j);
					if (isApplyArray.getJSONObject(i).getString("job_info_id").equals(
							object.getString("workId"))) {
						object.put("is_apply", "1");
					}
				}
			}
			result.put("result", "0");
			result.put("resultTip", "请求热门招岗位列表成功");
			result.put("data", hotArray);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject sortWorkList(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			JSONArray sortList = jobInfoDao.findSortWork(data);
			//获取哪些岗位是已经申请的
			JSONArray isApplyArray = jobApplyDao.IsApplied(data);
			for(int i=0;i < isApplyArray.length();i++) {
				for (int j = 0; j < sortList.length(); j++) {
					JSONObject object = sortList.getJSONObject(j);
					if (isApplyArray.getJSONObject(i).getString("job_info_id").equals(
							object.getString("workId"))) {
						object.put("is_apply", "1");
					}
				}
			}
			result.put("result", "0");
			result.put("resultTip", "获取信息成功");
			result.put("data", sortList);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject jobTypeList(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			result.put("result", "0");
			result.put("resultTip", "获取职位分类信息成功");
			result.put("data", jobInfoDao.findJobTypeList(data));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
	
	public JSONObject jobTypeSecondList(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			result.put("result", "0");
			result.put("resultTip", "获取职位二级分类信息成功");
			result.put("data", jobInfoDao.findJobTypeSecondList(data));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			result = new ExceptionHelper("程序异常").getErrorResult();
		}
		return result;
	}
}
