package ECP.handle;
import org.json.JSONObject;

import ECP.service.WebChatRecordService;
import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;

public class WebChatRecordHandle extends CMsgBaseHandle{
	private WebChatRecordService webChatRecordService;
	
	public WebChatRecordHandle() {
		webChatRecordService = new WebChatRecordService();
	}
	
	@Override
	public int handleMsg(CParam param) throws Exception {
		JSONObject data=getReqMessage(param);
		System.out.println("收到的请求信息为："+data.toString());
		String op=data.getString("op");
		JSONObject result=null;
		
		switch(op){

		//浏览器端接口
		case "perUserChat_recodDetail":              //某位好友的聊天信息
			result=webChatRecordService.perUserChat_recodDetail(data);
			break;
		case "getFriendsList":              //获取好友列表
			result=webChatRecordService.getFriendsList(data);
			break;
		case "setChat_recordStatus":              //设置消息状态
			result=webChatRecordService.setChat_recordStatus(data);
			break;
		case "getOrgMsgCounters":              //获取当前企业所有未读消息
			result=webChatRecordService.getMsgCounterByOrg_id(data);
			break;
		case "getLeaveMessage":              //获取当前企业所有留言
			result=webChatRecordService.getLeaveMessage(data);
			break;
		case "leaveMessageCounter":              //获取当前企业未读留言条数
			result=webChatRecordService.getLeaveMessageCounter(data);
			break;
			
		//安卓端
		case "getContactList":              //求职端获取好友列表
			result=webChatRecordService.findOrg_friendByUser_id(data);
			break;
		case "deleteContact":              //求职端删除好友
			result=webChatRecordService.deleteOrg_friend(data);
			break;
		default:
			String str = "{\"result\":\"error\",\"resultTip\":\"请求参数出错\"}";
			result = new JSONObject(str);
		}
		param.respData.append(result.toString());
		return super.handleMsg(param);
	}
}