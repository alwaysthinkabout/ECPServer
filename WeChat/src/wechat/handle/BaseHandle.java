package wechat.handle;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import wechat.servlet.WeChatRouter.CParam;

public class BaseHandle {
	public JSONObject getReqMessage(CParam param) throws Exception{
		System.out.println("����getReqMessage��Ϣ������");
		HttpServletRequest msgReq;
		msgReq = param.getMsgReq();
		msgReq.setCharacterEncoding("UTF-8");
		//StringBuffer reqMsgData = new StringBuffer();
		//reqMsgData.append(msgReq.getParameter("handle")); //����Ϣ�л�ȡ��Ϣ�����壨JSON�ַ���)
		JSONObject msgDataObj = new JSONObject(param.getReqData()).getJSONObject("reqData");   //�������JSON��ʽ
		return msgDataObj;
	}
	
}
