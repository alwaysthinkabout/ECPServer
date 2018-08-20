package ECP.service;

import org.json.JSONObject;

import ECP.dao.CacTeacherDao;

public class CacTeacherService {
	private CacTeacherDao cacTeacher;	
	
	public CacTeacherService(){
		cacTeacher = new CacTeacherDao();
		
	}
	public JSONObject submintTeacherSelectedCourse(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = cacTeacher.submintTeacherSelectedCourse(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject getTeacherSelectedCourses(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = cacTeacher.getTeacherSelectedCourses(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject getTeacherSchedule(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = cacTeacher.getTeacherSchedule(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject getCurrentCourses(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = cacTeacher.getCurrentCourses(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject getCurrentCoursesRoster(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = cacTeacher.getCurrentCoursesRoster(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject rollCallByStudentName(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = cacTeacher.rollCallByStudentName(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject getStudentLeaveRequest(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = cacTeacher.getStudentLeaveRequest(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject studentLeaveResponse(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = cacTeacher.studentLeaveResponse(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject arrageClassAssign(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = cacTeacher.arrageClassAssign(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject getArrageClassAssign(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = cacTeacher.getArrageClassAssign(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject getStuSubmClassAssign(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = cacTeacher.getStuSubmClassAssign(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
			
	}

	public JSONObject markingStuSubmit(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = cacTeacher.markingStuSubmit(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject getStudentExamResults(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = cacTeacher.getStudentExamResults(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject submitStudentResults(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = cacTeacher.submitStudentResults(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

}
