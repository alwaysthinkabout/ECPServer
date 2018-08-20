package wechat.handle;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import wechat.servlet.WeChatRouter.CParam;

public class BaseHandle {
	public JSONObject getReqMessage(CParam param) throws Exception{
		System.out.println("进入getReqMessage消息处理器");
		HttpServletRequest msgReq;
		msgReq = param.getMsgReq();
		msgReq.setCharacterEncoding("UTF-8");
		//StringBuffer reqMsgData = new StringBuffer();
		//reqMsgData.append(msgReq.getParameter("handle")); //从消息中获取消息数据体（JSON字符串)
		JSONObject msgDataObj = new JSONObject(param.getReqData()).getJSONObject("reqData");   //请求参数JSON格式
		return msgDataObj;
	}
	
}
