package ECP.handle.platform;

import org.json.JSONObject;

import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;
import ECP.handle.CMsgBaseHandle;
import ECP.service.platform.JobUserAuditService;


public class JobHunterAuditHandle extends CMsgBaseHandle {
	
	private JobUserAuditService jobUserAuditService;
	
	public JobHunterAuditHandle(){
		jobUserAuditService = new JobUserAuditService();
	}
	
	@Override
	public int handleMsg(CParam param) throws Exception {
		JSONObject data=getReqMessage(param);
		System.out.println("收到的请求信息为："+data.toString());
		String op=data.getString("op");
		JSONObject result=null;
		
		switch(op){			
		case "getJobHunterInfoAuditList":           //待审核求职者列表    
			result = jobUserAuditService.jobHunterInfoAuditList(data);
			break;
		case "getJobHunterInfoAuditedList":         //已审核求职者列表    
			result = jobUserAuditService.jobHunterInfoAuditedList(data);
			break;
		case "getJobHunterInfoAuditDetail":         //待审核求职者详情    
			result = jobUserAuditService.jobHunterDetail(data);
			break;
		case "getJobHunterInfoAuditedDetail":       //已审核求职者详情    
			result = jobUserAuditService.jobHunterDetail(data);
			break;
		case "updateJobHunterInfoAudit":            //审核求职者资料    
			result = jobUserAuditService.personAuditUpdate(data);
			break;
		default:
			String str = "{\"result\":\"error\",\"data\":\"请求参数出错\"}";
			result = new JSONObject(str);
		}
		param.respData.append(result.toString());
		return super.handleMsg(param);
	}
	
}
