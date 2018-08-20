package wechat.service;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import wechat.dao.RoomDao;
import wechat.dao.UserDao;
import wechat.util.WebSocketManager;

public class RoomService {
	private RoomDao roomDao;
	private UserDao userDao;
	public RoomService(){
		roomDao = new RoomDao();
		userDao = new UserDao();
	}
	
	public JSONObject createRoom(JSONObject data){
		JSONObject result = new JSONObject();
		int flag = 0;
		try{
			flag = roomDao.insert(data);
			if(flag > 0){
				result.put("result", "success");
				result.put("resultTip", "聊天室新建成功");
			}else{
				result.put("result", "fail");
				result.put("resultTip", "聊天室新建失败");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject getRoomList(JSONObject data){
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		try{
			array = roomDao.query(data);
			result.put("result", "success");
			result.put("resultTip", "聊天室列表获取成功");
			result.put("array", array);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject getUsersByRoom(JSONObject data){
		JSONObject result = new JSONObject();
		HashMap<String,String[][]> temp = WebSocketManager.getRoomCount();
		try {
			String room_id = data.getString("room_id");
			String[][] users = temp.get(room_id);
			JSONArray array = new JSONArray();
			for(int i = 0, len = users.length; i < len; i++){
				JSONArray jsonarray = userDao.getUser_infoById(users[i][0]);
				if(jsonarray.length() > 0)
					array.put(jsonarray.getJSONObject(0));
			}
			result.put("result", "success");
			result.put("array", array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
