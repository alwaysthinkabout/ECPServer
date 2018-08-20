package ECP.handle;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import ECP.service.MobileService;
import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;

public class MobileHandle extends CMsgBaseHandle{

	private MobileService mobileService;
	public MobileHandle(){
		mobileService = new MobileService();
	}
	@Override
	public int handleMsg(CParam param) throws Exception {
		JSONObject data=getReqMessage(param);
//		String op=data.getString("op");
		
		JSONObject result;
		
		HttpServletRequest msgReq;
		msgReq = param.getMsgReq();
		msgReq.setCharacterEncoding("UTF-8");
		result = mobileService.sendTo(data);
//		switch(op){
//		case "getSettingInfo":
//			result = mobileService.sendTo(data);
//			break;
//		case "receiveSettingInfo":
//			
//		default:
//			result = new JSONObject();
//			break;
//		}
		param.respData.append(result.toString());
		return super.handleMsg(param);
	}
}
