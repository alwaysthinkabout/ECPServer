package ECP.handle.platform;

import org.json.JSONObject;

import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;
import ECP.handle.CMsgBaseHandle;
import ECP.service.platform.OpeLogService;


public class OpeLogHandle extends CMsgBaseHandle {
	
	private OpeLogService opeLogService;
	
	public OpeLogHandle(){
		opeLogService = new OpeLogService();
	}
	
	@Override
	public int handleMsg(CParam param) throws Exception {
		JSONObject data=getReqMessage(param);
		System.out.println("收到的请求信息为："+data.toString());
		String op=data.getString("op");
		JSONObject result=null;
		
		switch(op){			
		case "logList":           //操作日志列表    
			result = opeLogService.opeLogList(data);
			break;
		default:
			String str = "{\"result\":\"error\",\"resultTip\":\"请求参数出错\"}";
			result = new JSONObject(str);
		}
		param.respData.append(result.toString());
		return super.handleMsg(param);
	}
	
}
