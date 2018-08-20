package wechat.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import wechat.dao.UserDao;

public class UserService {
	private UserDao userDao;
	public UserService(){
		userDao = new UserDao();
	}
	
	public JSONObject register(JSONObject data){
		JSONObject result = new JSONObject();
		if(userDao.IsExist(data)){
			try {
				result.put("result", "fail");
				result.put("resultTip", "�û����ѱ���ʹ��");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}		
		int flag = userDao.insert(data);
			try {
				if(flag >= 0){
					result.put("result", "success");
					result.put("user_id", flag);
					result.put("resultTip", "�û�ע��ɹ�");
				}else{
					result.put("result", "fail");
					result.put("resultTip", "ע��ʧ�ܣ����Ժ�����");
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return result;
	}
	
	public JSONObject login(JSONObject data){
		JSONObject result = new JSONObject();
		JSONArray array = userDao.login(data);
		JSONObject obj = new JSONObject();
		try {
			if(array.length() > 0){
				obj = array.getJSONObject(0);//��ȡJSONObject����
				result.put("result", "success");
				result.put("user_info", obj);
				result.put("resultTip", "��¼�ɹ�");
			}else{
				result.put("result", "fail");
				result.put("resultTip", "��¼ʧ��");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
