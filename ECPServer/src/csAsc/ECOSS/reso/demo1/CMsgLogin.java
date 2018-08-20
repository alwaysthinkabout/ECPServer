package csAsc.ECOSS.reso.demo1;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;
import csAsc.dbcom.CPermit;
 



public class CMsgLogin {
	
	
	public CPermit getPermit(String username, String password) throws Exception{
		
		CPermit permit=new CPermit();
		if(CPermit.isUsernameExist(username)){
			if(CPermit.isValid(username, password)){
				permit.status=1;//正常
				
			}else {
				permit.status=0;//账户或密码不对
			}
		}else {
			permit.status=3;//用户不存在
		}
		return permit;
	}
	

	public int handleMsg(CParam param) throws Exception{
		System.out.println("==进入Clogin的handleMsg");

		HttpServletRequest msgReq;
		msgReq = param.getMsgReq();
		msgReq.setCharacterEncoding("UTF-8");

		StringBuffer reqMsgData = new StringBuffer();
		reqMsgData.append(msgReq.getParameter(param.getReqMsgDataId())); //从消息中获取消息数据体（JSON字符串)

		JSONObject msgDataObj = new JSONObject(reqMsgData.toString()); 
		  
		String cName = msgDataObj.getString("MsgUserName"); //从消息中取出消息内容：cName的值
		String cPassword = msgDataObj.getString("Msgpassword"); 
		 // String cCheckCode=msgDataObj.getString("MsgcheckCode"); 
		StringBuffer retData = new StringBuffer();
		System.out.println("user:"+cName+"; password:"+cPassword);
		CPermit permit=getPermit(cName, cPassword);
		param.respData.append(permit.extInfor(msgReq, cName));
		
		System.out.println("返回的信息："+param.respData);
		return 0;
	}
	
	
	
	
	
	
	
	
//	public int handleMsg(CParam param) throws Exception{
//		System.out.println("==进入Clogin的handleMsg");
//
//		HttpServletRequest msgReq;
//		msgReq = param.getMsgReq();
//		msgReq.setCharacterEncoding("UTF-8");
//
//		StringBuffer reqMsgData = new StringBuffer();
//		reqMsgData.append(msgReq.getParameter(param.getReqMsgDataId())); //从消息中获取消息数据体（JSON字符串)
//
//		JSONObject msgDataObj = new JSONObject(reqMsgData.toString()); 
//		  
//		String cName = msgDataObj.getString("MsgUserName"); //从消息中取出消息内容：cName的值
//		String cPassword = msgDataObj.getString("Msgpassword"); 
//		 // String cCheckCode=msgDataObj.getString("MsgcheckCode"); 
//		String retData;
//		System.out.println("user:"+cName+"; password:"+cPassword);
//
//		retData="{cUsername:\"www\",cIPAddress:\"127.0.0.1\",cLoginTime:\"2015-04-23 22:50:58\",cStatus:\"1\",cUserType:\"2\",cResource:{\"ADRS00.html\":[[\"img_data_luru\",\"1\"]],\"ADRS0001.html\":[[\"database\",\"2\"],[\"tree\",\"2\"],[\"idName\",\"2\"]]}}";
//		param.respData.append(retData);
//		System.out.println("返回的信息："+param.respData);
//		return 0;
//	}
	
	
	
	
	
	
	
	
	
}
