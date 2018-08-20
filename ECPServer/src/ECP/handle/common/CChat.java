package ECP.handle.common;

import org.json.JSONObject;

import ECP.service.common.CChatService;
import ECP.handle.CMsgBaseHandle;
import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;

public class CChat extends CMsgBaseHandle{
	private CChatService cChatService;
	public CChat(){
		cChatService = new CChatService();
	}
	
	public int handleMsg(CParam param) throws Exception{
		JSONObject data=getReqMessage(param);
		String op=data.getString("op");
		JSONObject result=null;
		switch(op){
		case "connect":
			result=new JSONObject();
			result.put("msg", "success");
			break;
		
		default:
			break;
		}
		param.respData.append(result.toString());
		return super.handleMsg(param);
	}
}
