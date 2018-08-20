package ECP.handle;

import org.json.JSONObject;

import ECP.service.CacStudentService;
import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;

public class CacStudentHandle extends CMsgBaseHandle{
	private CacStudentService cacStudentService;
	
	public CacStudentHandle(){
		cacStudentService = new CacStudentService();
	}	
	@Override
	public int handleMsg(CParam param) throws Exception {
		System.out.println("进入CacStudentHandle的handleMsg");
		JSONObject data=getReqMessage(param);
		//这里初步设想是根据传入参数的长度来转发各种操作。或者像以前一样增加一个op的字段。
		String op=data.getString("op");
		System.out.println("收到的请求信息为："+data.toString());
		JSONObject result=new JSONObject();
		switch(op){
		case "newStudentEntryLogin":
			result=cacStudentService.newStudentEntryLogin(data);
			break;
		case "checkInfoSuccess":
			result=cacStudentService.checkInfoSuccess(data);
			break;
		case "getStudentFee":
			result=cacStudentService.getStudentFee(data);
			break;
		case "payTuitionSuccess":
			result=cacStudentService.payTuitionSuccess(data);
			break;
		case "getStudentReportSucessInfo":
			result=cacStudentService.getStudentReportSucessInfo(data);
			break;
		case "getStudentSelectCourses":
			result=cacStudentService.getStudentSelectCourses(data);
			break;
		case "getStudentSchedule":
			result=cacStudentService.getStudentSchedule(data);
			break;
		case "studentSign":
			result=cacStudentService.studentSign(data);
			break;
		case "studentLeave":
			result=cacStudentService.studentLeave(data);
			break;
		case "studentAttendance":
			result=cacStudentService.studentAttendance(data);
			break;
		case "getClassAssignment":
			result=cacStudentService.getClassAssignment(data);
			break;
		case "getExamination":
			result=cacStudentService.getExamination(data);
			break;
		case "getExaminationResults":
			result=cacStudentService.getExaminationResults(data);
			break;
		case "getLeaveSchool":
			result=cacStudentService.getLeaveSchool(data);
			break;
		case "submintSelectedCourse":
			result=cacStudentService.submintSelectedCourse(data);
			break;
		case "getSelectedCourses":
			result=cacStudentService.getSelectedCourses(data);
			break;
		case "getStudentLeaves":
			result=cacStudentService.getStudentLeaves(data);
			break;
		}
		
		param.respData.append(result.toString());
		System.out.println("输出数据："+param.respData);
		return super.handleMsg(param);
	}
}
