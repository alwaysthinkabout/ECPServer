package ECP.handle;

import org.json.JSONObject;

import ECP.service.CacTeacherService;
import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;

public class CacTeacherHandle  extends CMsgBaseHandle{
	private CacTeacherService cacTeacherService;
	
	public CacTeacherHandle(){
		cacTeacherService = new CacTeacherService();
	}	
	public int handleMsg(CParam param) throws Exception {
		System.out.println("进入CacTeacherHandle的handleMsg");
		JSONObject data=getReqMessage(param);
		//这里初步设想是根据传入参数的长度来转发各种操作。或者像以前一样增加一个op的字段。
		String op=data.getString("op");
		System.out.println("收到的请求信息为："+data.toString());
		JSONObject result=new JSONObject();
		switch(op){
		case "submintTeacherSelectedCourse":
			result=cacTeacherService.submintTeacherSelectedCourse(data);
			break;
		case "getTeacherSelectedCourses":
			result=cacTeacherService.getTeacherSelectedCourses(data);
			break;
		case "getTeacherSchedule":
			result=cacTeacherService.getTeacherSchedule(data);
			break;
		case "getCurrentCourses":
			result=cacTeacherService.getCurrentCourses(data);
			break;
		case "getCurrentCoursesRoster":
			result=cacTeacherService.getCurrentCoursesRoster(data);
			break;
		case "rollCallByStudentName":
			result=cacTeacherService.rollCallByStudentName(data);
			break;
		case "getStudentLeaveRequest":
			result=cacTeacherService.getStudentLeaveRequest(data);
			break;
		case "studentLeaveResponse":
			result=cacTeacherService.studentLeaveResponse(data);
			break;
		case "arrageClassAssign":
			result=cacTeacherService.arrageClassAssign(data);
			break;
		case "getArrageClassAssign":
			result=cacTeacherService.getArrageClassAssign(data);
			break;
		case "getStuSubmClassAssign":
			result=cacTeacherService.getStuSubmClassAssign(data);
			break;
		case "markingStuSubmit":
			result=cacTeacherService.markingStuSubmit(data);
			break;
		case "getStudentExamResults":
			result=cacTeacherService.getStudentExamResults(data);
			break;
		case "submitStudentResults":
			result=cacTeacherService.submitStudentResults(data);
			break;
		}
	
		param.respData.append(result.toString());
//		System.out.println("输出数据："+param.respData);
		return super.handleMsg(param);
	}

}
