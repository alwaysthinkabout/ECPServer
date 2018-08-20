package wechat.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import wechat.util.WebSocketManager;
import wechat.dao.UserDao;

public class MessageService {
	private UserDao userDao;
	public MessageService(){
		userDao = new UserDao();
	}
	public JSONObject sendMessage(JSONObject data){
		JSONObject result = new JSONObject();
		
		try {
			String user_id = data.getString("user_id");
			JSONArray array = userDao.getUser_infoById(user_id);
			data.put("picture", array.getJSONObject(0).getString("picture"));
			String room_id = data.getString("room_id");
			WebSocketManager.send(data, room_id);
			result.put("result", "success");
			result.put("resultTip", "��Ϣ���ͳɹ�");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject sendToPerson(JSONObject data){
		JSONObject result = new JSONObject();		
		try {
			
			String user_id = data.getString("toUser_id");
			if(!WebSocketManager.isExist(user_id)){
				result.put("result", "success");
				result.put("resultTip", "���û�������");
			}else{
				WebSocketManager.sendToPerson(data, user_id);
				result.put("result", "success");
				result.put("resultTip", "��Ϣ���ͳɹ�");
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
}
