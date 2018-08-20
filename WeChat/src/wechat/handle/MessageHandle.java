package wechat.handle;

import org.json.JSONObject;

import wechat.service.MessageService;
import wechat.servlet.WeChatRouter.CParam;

public class MessageHandle extends BaseHandle{
	private MessageService messageService;
	public MessageHandle(){
		messageService = new MessageService();
	}
	public int handleMsg(CParam param) throws Exception{
		JSONObject data = getReqMessage(param);
		String op = data.getString("op");
		System.out.println("请求的参数为："+data.toString());
		JSONObject result=null;
		switch(op){
		case  "sendToRoom":
			result = messageService.sendMessage(data);
			break;
		case  "sendToPerson":
			result = messageService.sendToPerson(data);
			break;
		default:
			String str = "{\"result\":\"error\",\"resultTip\":\"请求参数出错\"}";
			result = new JSONObject(str);
		}
		param.respData.append(result.toString());
		return 1;
	}
}
