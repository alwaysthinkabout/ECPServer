package csAsc.ECOSS.reso.demo1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;

public class CMsgSendNewPage
{//生成一个网页数据，回送到客户端
 public int handleMsg(CParam param) throws ServletException,IOException, JSONException
 {//回送的数据格式：消息头后接HTML文件内容，以EIO的数据标志为起点，结束字符必须是"</html>", 大小写无关，要求7个字符	
  System.out.println("进入CMsgSendNewPage");
  
  HttpServletRequest msgReq;
  msgReq = param.getMsgReq();
  msgReq.setCharacterEncoding("UTF-8");

  StringBuffer reqMsgData = new StringBuffer();
  reqMsgData.append(msgReq.getParameter(param.getReqMsgDataId())); //从消息中获取消息数据体（JSON字符串)

  JSONObject msgDataObj = new JSONObject(reqMsgData.toString()); 
  
  String cName = msgDataObj.getString("cName"); //从消息中取出消息内容：cName的值
  String cAge = msgDataObj.getString("cAge"); //从消息中取出消息内容：cAge的值
     
  //String gender = msgReq.getParameter("aGender"); //获取form中的aGender属性值
  //String[] color = msgReq.getParameterValues("aColor");//获取form中的aColor属性值
  //String national = msgReq.getParameter("aCountry");////获取form中的aCountry属性值 
	   
  //准备响应数据
  
  //String cc="";
  //for(String c : color) cc += c + " "; 
  //回送的数据格式：HTML:{完整网页}
    
  param.respData.append("<!DOCTYPE html>"
          +"<HTML> <HEAD> <TITLE>返回数据</TITLE></HEAD><BODY>"
		   +"你输入的名字：" + cName + "<hr>"
		   +"你输入的年龄：" + cAge + "<hr>"
		   +"<INPUT TYPE='BUTTON' VALUE='关闭' onClick='window.close()'>" 
          +"</BODY> </HTML>");
    
  return 0;
 }
}
