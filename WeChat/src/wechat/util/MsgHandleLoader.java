package wechat.util;

import wechat.servlet.WeChatRouter.CParam;
import wechat.util.MsgMethodCaller;

/*
 * 利用反射加载处理器
 */
public class MsgHandleLoader {
	public static int callMsgHandle(CParam param){
		int flag = 0;
		String reqMsgHandle = param.getReqMsgHandle();
		if(reqMsgHandle == ""){
			return -1;
		}
		int k = reqMsgHandle.lastIndexOf(".");
		String className = reqMsgHandle.substring(0,k);
		String methodName = reqMsgHandle.substring(k+1);
		MsgMethodCaller mCaller= new MsgMethodCaller();
		Object[] params = {param};
		flag = (int )mCaller.loadExec(className, methodName, params);
		return flag; 
	}
}
