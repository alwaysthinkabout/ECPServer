package ECP.handle.common;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;
import ECP.handle.CMsgBaseHandle;
import ECP.service.common.CMobileControlService;

public class MobileControlHandler extends CMsgBaseHandle{
	
	private CMobileControlService mobileControlService;
	
	public MobileControlHandler(){
		mobileControlService = new CMobileControlService();
	}
	
	@Override
	public int handleMsg(CParam param) throws Exception {
		JSONObject data=getReqMessage(param);
		String op=data.getString("op");		
		JSONObject result;		
		HttpServletRequest msgReq;
		msgReq = param.getMsgReq();
		msgReq.setCharacterEncoding("UTF-8");
		
		switch(op){
		case "startTrace":
			result = mobileControlService.startTrace(data);
			break;
		case "stopTrace":
			result = mobileControlService.stopTrace(data);
			break;
		case "startGather":
			result = mobileControlService.startGather(data);
			break;
		case "stopGather":
			result = mobileControlService.stopGather(data);
			break;
		case "updateStatus":
			result = mobileControlService.updateStatus(data);
			break;
		case "updateStatusEntity":
			result = mobileControlService.updateStatusEntity(data);
			break;
		case "appListen":
			result = mobileControlService.appListen(data);
			break;
		default:
			result = new JSONObject();
			break;
		}
		param.respData.append(result.toString());
		return super.handleMsg(param);
	}
}
