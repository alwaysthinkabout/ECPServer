package ECP.service.platform;

import org.json.JSONArray;
import org.json.JSONObject;

import ECP.dao.OpeLogDao;

public class OpeLogService {
	public OpeLogDao opeLogDao;
	
	public OpeLogService(){
		opeLogDao = new OpeLogDao();
	}
	
	public JSONObject opeLogList(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			JSONArray reArray = opeLogDao.findAll();
			result.put("result", "success");
			result.put("data", reArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
