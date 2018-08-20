package ECP.handle.platform;

import org.json.JSONObject;

import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;
import ECP.handle.CMsgBaseHandle;
import ECP.service.platform.OrgAuditService;


public class OrgAuditHandle extends CMsgBaseHandle {
	
	private OrgAuditService orgAuditService;
	
	public OrgAuditHandle(){
		orgAuditService = new OrgAuditService();
	}
	
	@Override
	public int handleMsg(CParam param) throws Exception {
		JSONObject data=getReqMessage(param);
		System.out.println("收到的请求信息为："+data.toString());
		String op=data.getString("op");
		JSONObject result=null;
		
		switch(op){
		case "auditWaitingList":           //待审核企业列表    
			result = orgAuditService.auditWaitingList(data);
			break;
		case "orgAuditWaitingList":        //某个企业待审核的所有信息    
			result = orgAuditService.orgAuditWaiting(data);
			break;
		case "orgAuditDetail":             //具体某个审核的信息   
			result = orgAuditService.auditDetail(data);
			break;
		case "orgAuditUpdate":             //审核企业资料
			result = orgAuditService.orgAuditUpdate(data);
			break;
		case "getEnterpriseInfoAuditedList":           //已审核企业列表    
			result = orgAuditService.auditedList(data);
			break;
		case "getEnterpriseInfoAuditedDetail":         //企业三个级别的信息列表获取    
			result = orgAuditService.orgAudited(data);
			break;
		case "getRegisterInfoDetail":                  //注册级已审核信息详情    
			result = orgAuditService.orgInfoAuditedDetail(data);
			break;
		case "getPrimaryInfoDetail":                   //初级已审核信息详情    
			result = orgAuditService.orgStateAuditedDetail(data);
			break;
		case "getAdvanceInfoDetail":                   //高级已审核信息详情    
			result = orgAuditService.orgCertAuditedDetail(data);
			break;
			
		case "getStoreInfoAuditList":             //审核店面资料
			result = orgAuditService.getStoreInfoAuditList(data);
			break;
		case "getStoreInfoAuditDetail":           //审核店面详情资料
			result = orgAuditService.getStoreInfoAuditDetail(data);
			break;
		case "getStoreInfoAudit":                 //审核店面信息
			result = orgAuditService.getStoreInfoAudit(data);
			break;
		case "getStoreInfoAuditedList":                 //审核店面信息
			result = orgAuditService.getStoreInfoAuditedList(data);
			break;		
		default:
			String str = "{\"result\":\"error\",\"resultTip\":\"请求参数出错\"}";
			result = new JSONObject(str);
		}
		param.respData.append(result.toString());
		return super.handleMsg(param);
	}
	
}
