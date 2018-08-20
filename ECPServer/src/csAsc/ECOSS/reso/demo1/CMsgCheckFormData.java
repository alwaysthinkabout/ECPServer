package csAsc.ECOSS.reso.demo1;

import java.io.IOException;

import csAsc.EIO.MsgEngine.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.json.*;

public class CMsgCheckFormData extends CEIOMsgRouter
{
 private static final long serialVersionUID = 1L;

 public int handleMsg(CParam param) throws ServletException,IOException,JSONException
 {
  System.out.println("==进入CNsgGetFormData的handleMsg");
  HttpServletRequest msgReq;
  msgReq = param.getMsgReq();
  msgReq.setCharacterEncoding("UTF-8");

  StringBuffer reqMsgData = new StringBuffer();
  reqMsgData.append(msgReq.getParameter(param.getReqMsgDataId())); //从消息中获取消息数据体（JSON字符串)

  JSONObject msgDataObj = new JSONObject(reqMsgData.toString()); 
  
  String cName = msgDataObj.getString("cName"); //从消息中取出消息内容：cName的值
  String cAge = msgDataObj.getString("cAge"); //从消息中取出消息内容：cAge的值
  
  cName +="-正确"; //处理请求端的数据
  cAge +="-正确";
	  
  String cInfo="博士学位，计算机科学专业，毕业于华南理工大学计算机学院";
  param.respData.append( "{cName:" + "\"" + cName+ "\",cAge:\"" + cAge + "\",cInfo:\"" + cInfo +"\"}");
	 
  return 0;  
 }

}
