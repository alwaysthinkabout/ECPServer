package ECP.handle.platform;

import org.json.JSONObject;

import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;
import ECP.handle.CMsgBaseHandle;
import ECP.service.platform.OrgAuditService;


public class MessageHandle extends CMsgBaseHandle {
	
	private OrgAuditService orgAuditService;
	
	public MessageHandle(){
		orgAuditService = new OrgAuditService();
	}
	
	@Override
	public int handleMsg(CParam param) throws Exception {
		JSONObject data=getReqMessage(param);
		System.out.println("收到的请求信息为："+data.toString());
		String op=data.getString("op");
		JSONObject result=null;
		
		switch(op){
		case "msgCounters":           //各种待审核信息数目   
			result = orgAuditService.getMsgCounter();
			break;
		default:
			String str = "{\"result\":\"error\",\"resultTip\":\"请求参数出错\"}";
			result = new JSONObject(str);
		}
		param.respData.append(result.toString());
		return super.handleMsg(param);
	}
	
}
