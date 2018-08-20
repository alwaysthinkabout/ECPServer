package ECP.service;

import org.json.JSONObject;

import ECP.util.common.WebSocketManager;

public class MobileService {
	public JSONObject sendTo(JSONObject data){
		JSONObject result = new JSONObject();
		
		try{
			String to = data.getString("to");
			if(WebSocketManager.isExist(to)){
				data.put("type", data.getString("op"));
				WebSocketManager.send(data.toString(), to);
				result.put("result", "success");
			}else{
				result.put("result", "fail");
			}
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return result;
	}
}
