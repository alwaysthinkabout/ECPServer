package ECP.service.common;

import org.json.JSONException;
import org.json.JSONObject;

import ECP.util.common.WebSocketManager;

public class CMobileControlService {
	
	public JSONObject XXX(JSONObject data) throws JSONException{
		JSONObject result = new JSONObject();
		/*
		 * 这里对data进行处理，data就是我们前端传过来的req
		 * 然后把结果放在result里面，result是会发送回给前端的响应函数
		 */
		return result;
	}
	
	public JSONObject startTrace(JSONObject data) throws JSONException{
		JSONObject result = new JSONObject();
		//获取目标账号
		String to = data.getString("to");
		data.put("type", "startTrace");
		if(WebSocketManager.isExist(to)){
			WebSocketManager.send(data.toString(), to);
			result.put("result", "success");
		}else{
			result.put("result", "fail");
		}
		return result;
	}
	public JSONObject stopTrace(JSONObject data) throws JSONException{
		JSONObject result = new JSONObject();
		//获取目标账号
		String to = data.getString("to");
		data.put("type", "stopTrace");
		if(WebSocketManager.isExist(to)){
			WebSocketManager.send(data.toString(), to);
			result.put("result", "success");
		}else{
			result.put("result", "fail");
		}
		return result;
	}
	public JSONObject startGather(JSONObject data) throws JSONException{
		JSONObject result = new JSONObject();
		//获取目标账号
		String to = data.getString("to");
		data.put("type", "startGather");
		if(WebSocketManager.isExist(to)){
			WebSocketManager.send(data.toString(), to);
			result.put("result", "success");
		}else{
			result.put("result", "fail");
		}
		return result;
	}
	public JSONObject stopGather(JSONObject data) throws JSONException{
		JSONObject result = new JSONObject();
		//获取目标账号
		String to = data.getString("to");
		data.put("type", "stopGather");
		if(WebSocketManager.isExist(to)){
			WebSocketManager.send(data.toString(), to);
			result.put("result", "success");
		}else{
			result.put("result", "fail");
		}
		return result;
	}
	public JSONObject updateStatus(JSONObject data) throws JSONException{
		JSONObject result = new JSONObject();
		//获取目标账号
		String to = data.getString("to");
		data.put("type", "updateStatus");
		if(WebSocketManager.isExist(to)){
			WebSocketManager.send(data.toString(), to);
			result.put("result", "success");
		}else{
			result.put("result", "fail");
		}
		return result;
	}
	public JSONObject updateStatusEntity(JSONObject data) throws JSONException{
		JSONObject result = new JSONObject();
		//获取目标账号
		String to = data.getString("to");
		data.put("type", "updateStatusEntity");
		if(WebSocketManager.isExist(to)){
			WebSocketManager.send(data.toString(), to);
			result.put("result", "success");
		}else{
			result.put("result", "fail");
		}
		return result;
	}
	public JSONObject appListen(JSONObject data) throws JSONException{
		JSONObject result = new JSONObject();
		String to = data.getString("to");
		data.put("type", "subscribe_push");
		if(WebSocketManager.isExist(to)){
			WebSocketManager.send(data.toString(), to);
			result.put("result", "success");
		}else{
			result.put("result", "fail");
		}
		return result;
	}
}
