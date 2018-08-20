package wechat.handle;

import org.json.JSONObject;

import wechat.service.RoomService;
import wechat.servlet.WeChatRouter.CParam;
public class RoomHandle extends BaseHandle{
	private RoomService roomService;
	public RoomHandle(){
		roomService = new RoomService();
	}
	public int handleMsg(CParam param) throws Exception{
		JSONObject data = getReqMessage(param);
		String op = data.getString("op");
		System.out.println("请求的参数为："+data.toString());
		JSONObject result=null;
		switch(op){
		case  "createRoom":
			result = roomService.createRoom(data);
			break;
		case  "getRoomList":
			result = roomService.getRoomList(data);
			break;
		case  "getRoomInfo":
			result = roomService.getRoomList(data);
			break;
		case  "getRoom_UsersInfo":
			result = roomService.getUsersByRoom(data);
			break;
		default:
			String str = "{\"result\":\"error\",\"resultTip\":\"请求参数出错\"}";
			result = new JSONObject(str);
		}
		param.respData.append(result.toString());
		return 1;
	}
}
