package ECP.service;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ECP.dao.StudentDao;




public class StudentService {
	private StudentDao studentDao;

	
	public StudentService(){
		studentDao = new StudentDao();
		
	}
	
	
	
	public JSONObject studentList(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			result.put("rows", studentDao.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	

}
