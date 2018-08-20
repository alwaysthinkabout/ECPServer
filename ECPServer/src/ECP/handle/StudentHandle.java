package ECP.handle;

import org.json.JSONObject;

import ECP.service.StudentService;
import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;

/**
 * @Description 
 * Created by durenshi on 2017-4-8
 */
public class StudentHandle extends CMsgBaseHandle {
	private StudentService studentService;
	
	public StudentHandle(){
		studentService = new StudentService();
	}
	
	@Override
	public int handleMsg(CParam param) throws Exception {
		JSONObject data=getReqMessage(param);
		String op=data.getString("op");
		JSONObject result=null;
		
		switch(op){
//		case "studentGet":
//			result=studentService.studentGet(data);
//			break;
//		case "studentAdd":
//			result=studentService.studentAdd(data);
//			break;
//		case "studentUpdate":
//			result=studentService.studentUpdate(data);
//			break;
//		case "studentDelete":
//			result=studentService.studentDelete(data);
//			break;
		case "studentList":
			result=studentService.studentList(data);
			break;
//		case "studentQuery":
//			result=studentService.studentQuery(data);
//			break;
		}
		param.respData.append(result.toString());
		return super.handleMsg(param);
	}
}
