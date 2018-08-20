package wechat.handle;

import org.json.JSONObject;

import wechat.servlet.WeChatRouter.CParam;
import wechat.service.UserService;

public class UserHandle extends BaseHandle{
	private UserService userService ;
	public UserHandle(){
		userService =  new UserService();
	}
	public int handleMsg(CParam param) throws Exception{
		JSONObject data = getReqMessage(param);
		String op = data.getString("op");
		System.out.println("请求的参数为："+data.toString());
		JSONObject result=null;
		switch(op){
		case  "register":
			result = userService.register(data);
			break;
		case  "login":
			result = userService.login(data);
			break;
		default:
			String str = "{\"result\":\"error\",\"resultTip\":\"请求参数出错\"}";
			result = new JSONObject(str);
		}
		param.respData.append(result.toString());
		return 1;
	}
}
