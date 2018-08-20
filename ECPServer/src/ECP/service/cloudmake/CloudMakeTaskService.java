package ECP.service.cloudmake;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import ECP.dao.CloudMakeGradeDao;
import ECP.dao.CloudMakeTaskDao;

public class CloudMakeTaskService {
	
	private CloudMakeTaskDao cloudMakeTaskDao;
	private CloudMakeGradeDao cloudMakeGradeDao;
	
	public CloudMakeTaskService(){
		cloudMakeTaskDao = new CloudMakeTaskDao();
		cloudMakeGradeDao = new CloudMakeGradeDao();
	}
	
	public JSONObject releasableSubtasks(JSONObject data){
		JSONObject result = new JSONObject();	
		result = cloudMakeTaskDao.findReleasableSubTasks(data);
		try {
			if (result.getString("opResult").equals("1")) {
				result.put("errorMessage", "获取可发布的子任务集合失败");
			}
			else result.put("resultTip", "获取可发布的子任务集合成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject tenderingTasks(JSONObject data){
		JSONObject result = new JSONObject();	
		result = cloudMakeTaskDao.findTenderingTasks(data);
		try {
			if (result.getString("opResult").equals("1")) {
				result.put("errorMessage", "获取招标中的任务集合失败");
			}
			else result.put("resultTip", "获取招标中的任务集合成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject myTasks(JSONObject data){
		JSONObject result = new JSONObject();	
		result = cloudMakeTaskDao.findMyTasks(data);
		try {
			if (result.getString("opResult").equals("1")) {
				result.put("errorMessage", "获取我接受的任务集合失败");
			}
			else result.put("resultTip", "获取我接受的任务集合成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject winBidTasks(JSONObject data){
		JSONObject result = new JSONObject();	
		result = cloudMakeTaskDao.findWinBidTasks(data);
		try {
			if (result.getString("opResult").equals("1")) {
				result.put("errorMessage", "获取已中标的任务集合失败");
			}
			else result.put("resultTip", "获取已中标的任务集合成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject composedTasks(JSONObject data){
		JSONObject result = new JSONObject();	
		result = cloudMakeTaskDao.findComposedTasks(data);
		try {
			if (result.getString("opResult").equals("1")) {
				result.put("errorMessage", "获取已合成的任务集合失败");
			}
			else result.put("resultTip", "获取已合成的任务集合成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject subTasks(JSONObject data){
		JSONObject result = new JSONObject();	
		result = cloudMakeTaskDao.findSubTasks(data);
		try {
			if (result.getString("opResult").equals("1")) {
				result.put("errorMessage", "获取某任务的子任务集合失败");
			}
			else result.put("resultTip", "获取某任务的子任务集合成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject taskDetail(JSONObject data){
		JSONObject result = new JSONObject();	
		result = cloudMakeTaskDao.findOneTask(data);
		try {
			if (result.getString("opResult").equals("1")) {
				result.put("errorMessage", "获取具体任务失败");
			}
			else result.put("resultTip", "获取具体任务成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject gradeInfo(JSONObject data){
		JSONObject result = new JSONObject();	
		result = cloudMakeGradeDao.findGradeInfo(data);
		try {
			if (result.getString("opResult").equals("1")) {
				result.put("errorMessage", "获取用户评分信息失败");
			}
			else result.put("resultTip", "获取用户评分信息成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject taskAdd(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			int taskId = cloudMakeTaskDao.insert(data);
			if (taskId > 0) {
				result.put("opResult", "0");
				result.put("taskId", taskId);
				result.put("resultTip", "发布新任务成功");
			}
			else {
				result.put("opResult", "1");
				result.put("errorMessage", "发布新任务失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject taskUpdate(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			int flag = cloudMakeTaskDao.update(data);
			if (flag > 0) {
				result.put("opResult", "0");
				result.put("resultTip", "发布子任务成功");
			}
			else {
				result.put("opResult", "1");
				result.put("errorMessage", "发布子任务失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject subTaskUpdate(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			int flag = cloudMakeTaskDao.updateSubTask(data);
			if (flag > 0) {
				result.put("opResult", "0");
				result.put("resultTip", "修改任务分解成功");
			}
			else {
				result.put("opResult", "1");
				result.put("errorMessage", "修改任务分解失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject confirmTask(JSONObject data){
		JSONObject result = new JSONObject();	
		String userIdString = "";
		String taskName = "";
		JSONObject oneTask = new JSONObject();
		int flag1 = 0;
		int flag2 = 0;
		try {
			oneTask = cloudMakeTaskDao.findOneTask(data).getJSONObject("oneTask");
			userIdString = oneTask.getString("bid_winner_user_id");
			taskName = oneTask.getString("name");
			if (oneTask.getString("is_decomposable").equals("不可分解")) {
				data.put("updateStatus", "已完成");
				flag1 = cloudMakeTaskDao.setStatus(data);
				if (flag1<1) {
					result.put("opResult", "1");
					result.put("errorMessage", "确认完成一个任务失败");
					return result;
				}
			}
			else if(oneTask.getString("is_decomposable").equals("可分解")){
				data.put("updateStatus", "已分解");
				data.put("is_confirm_subtask", "1");
				flag1 = cloudMakeTaskDao.setStatus(data);
				flag2 = cloudMakeTaskDao.setConfirmSubtask(data);
				if (flag1<1 || flag2<1) {
					result.put("opResult", "1");
					result.put("errorMessage", "确认完成一个任务失败");
					return result;
				}
			}
			data.put("userId", userIdString);
			data.put("taskName", taskName);
			flag1 = cloudMakeGradeDao.insert(data);
			if(flag1 > 0){
				result.put("opResult", "0");
				result.put("resultTip", "确认完成一个任务成功");
			}
			else{
				result.put("opResult", "1");
				result.put("errorMessage", "确认完成一个任务失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject taskDelete(JSONObject data){
		JSONObject result = new JSONObject();
		int flag = 0;
		try {
			JSONObject oneObject = cloudMakeTaskDao.findOneTask(data).getJSONObject("oneTask");
			String status = oneObject.getString("status");
			if (status.equals("未招标")) {
				flag = cloudMakeTaskDao.delete(data);
				if (flag > 0) {
					result.put("opResult", "0");
					result.put("resultTip", "删除任务数据成功");
				}
				else {
					result.put("opResult", "1");
					result.put("errorMessage", "删除任务数据失败");
				}
			}
			else if (status.equals("已合成")||status.equals("已完成")||status.equals("申诉通过")) {
				String fatherId = oneObject.getString("father_task_id");
				JSONObject oneTask = new JSONObject();
				data.put("fatherId", fatherId);
				JSONArray tempArray = cloudMakeTaskDao.findByFatherTaskId(data);
				String taskId = "";
				String status2 = "";
				String userString = "";
				if (tempArray.length() > 0) {
					oneTask = tempArray.getJSONObject(0);
					taskId = oneTask.getString("task_id");
					status2 = oneTask.getString("status");
				}
				userString = oneObject.getString("deleteuser");
				if (taskId.equals("")||status2.equals("已合成")) {
					if (data.getString("user").equals("tenderUser")) {
						if (userString.equals("biduser")) {
							flag = cloudMakeTaskDao.delete(data);
						}
						else {
							flag = cloudMakeTaskDao.setDeleteUser(data);
						}	
					}
					if (data.getString("user").equals("bidUser")) {
						if (userString.equals("tenderuser")) {
							flag = cloudMakeTaskDao.delete(data);
						}
						else {
							flag = cloudMakeTaskDao.setDeleteUser(data);
						}
					}
					if (flag > 0) {
						result.put("opResult", "0");
						result.put("resultTip", "删除任务数据成功");
					}
					else {
						result.put("opResult", "1");
						result.put("errorMessage", "删除任务数据失败");
					}
				}
				else{
					result.put("opResult", "1");
					result.put("errorMessage", "父任务未合成，无法删除");
				}
			}
			else{
				result.put("opResult", "1");
				result.put("errorMessage", "任务未完成，无法删除");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject subTaskAdd(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			int taskId = cloudMakeTaskDao.insertSubTask(data);
			if (taskId > 0) {
				result.put("opResult", "0");
				result.put("taskId", taskId);
				result.put("resultTip", "添加任务分解成功");
			}
			else {
				result.put("opResult", "1");
				result.put("errorMessage", "添加任务分解失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject submitTask(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			data.put("updateStatus", "已提交");
			int flag = cloudMakeTaskDao.setStatus2(data);
			if (flag > 0) {
				result.put("opResult", "0");
				result.put("resultTip", "提交任务成功");
			}
			else {
				result.put("opResult", "1");
				result.put("errorMessage", "提交任务失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject acceptTask(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			data.put("updateStatus", "工作中");
			int flag = cloudMakeTaskDao.setStatus2(data);
			if (flag > 0) {
				result.put("opResult", "0");
				result.put("resultTip", "接受任务成功");
			}
			else {
				result.put("opResult", "1");
				result.put("errorMessage", "接受任务失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject composeTask(JSONObject data){
		JSONObject result = new JSONObject();	
		try {
			data.put("updateStatus", "已合成");
			int flag = cloudMakeTaskDao.setStatus2(data);
			if (flag > 0) {
				result.put("opResult", "0");
				result.put("resultTip", "合成任务成功");
			}
			else {
				result.put("opResult", "1");
				result.put("errorMessage", "合成任务失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject composableTasks(JSONObject data){
		JSONObject result = new JSONObject();	
		JSONArray taskArray = new JSONArray();
		List<JSONObject> taskC = new ArrayList<JSONObject>();
		HashSet<String> taskR = new HashSet<String>();
		try {
			taskArray = cloudMakeTaskDao.findByUserId(data);
			for (int i = 0; i < taskArray.length(); i++) {
				JSONObject temp = taskArray.getJSONObject(i);
				if(temp.getString("status").equals("已完成")||temp.getString("status").equals("已合成")
						||(temp.getString("status").equals("申诉通过")&&temp.getString("is_decomposable").equals("不可分解"))){
					taskC.add(temp);
				}
			}
			for (int i = 0; i < taskC.size(); i++) {
				boolean isc = false;
				for (int j = 0; j < taskC.size(); j++) {
					if (taskC.get(i).getString("father_task_id").equals(taskC.get(j).getString("task_id"))) {
						isc = true;
						break;
					}
				}
				if (isc == false) {
					boolean is = false;
					List<JSONObject> taskD = new ArrayList<JSONObject>();
					for (int j = 0; j < taskArray.length(); j++) {
						if (taskArray.getJSONObject(j).getString("father_task_id").equals(taskC.get(i).getString("father_task_id"))) {
							taskD.add(taskArray.getJSONObject(j));
						}
					}
					for (int j = 0; j < taskD.size(); j++) {
						if (taskC.contains(taskD.get(j))) {
							is = true;
						}
						else {
							is = false;
							break;
						}
					}
					
					if (is) {
						taskR.add(taskC.get(i).getString("father_task_id"));
					}
				}
				
			}
			String taskIds = "";
			Iterator<String> it = taskR.iterator();
			while (it.hasNext()) {
			  String str = it.next();
			  taskIds+=str+",";
			}
			if (!taskIds.equals("")) {
				taskIds = taskIds.substring(0,taskIds.length()-1);
			}
			data.put("taskIds", taskIds);
			result = cloudMakeTaskDao.findComposableTasks(data);
			if (result.getString("opResult").equals("1")) {
				result.put("errorMessage", "获取可合成的任务集合失败");
			}
			else result.put("resultTip", "获取可合成的任务集合成功");
		} catch (Exception e) {
			System.out.println("获取可合成的任务集合异常");
			e.printStackTrace();
		}
		return result;
	}
	
}
