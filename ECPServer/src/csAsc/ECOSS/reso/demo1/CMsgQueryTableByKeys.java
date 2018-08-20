package csAsc.ECOSS.reso.demo1;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.json.*;

import csAsc.EIO.MsgEngine.*;
import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;

public class CMsgQueryTableByKeys
{//消息处理器，从消息数据中取出查询条件，在数据库表中查询满足条件的记录并返回
 public int handleMsg(CParam param) throws IOException,JSONException
 {
  System.out.println("进入CMsgQueryTableByKeys==");
  HttpServletRequest msgReq;
  msgReq = param.getMsgReq();
  msgReq.setCharacterEncoding("UTF-8");

  StringBuffer reqMsgData = new StringBuffer();
  reqMsgData.append(msgReq.getParameter(param.getReqMsgDataId())); //从消息中获取消息数据体（JSON字符串)

  JSONObject msgDataObj = new JSONObject(reqMsgData.toString()); 
  
  //String cName = msgDataObj.getString("idName"); //从消息中取出消息内容：cName的值
     
  String tName="";
  try
  {
   tName = msgDataObj.getString("idName"); //获得信息数中的cName字段的值,该字段要与请求端协商确定
    
  } catch (JSONException e)
    {System.out.println("！！消息参数错误,不能读出");}
  
  //回应的数格式： {EIO—JSON :{.....} }
  if (tName.equals("张三")  || tName.equals("李四") || tName.equals("王五") )
  {
	  param.respData.append("{ \"HTML\":");
	  param.respData.append("[[\"" + tName +"\", \"2009年获得学士学位\"],[\"" + tName + "\", \"2011年获得硕士学位\"],[\""+tName + "\", \"2015年获得博士学位\"]]}");
	  
  }
  else param.respData.append("{\"HTML\":[\"查无此人，很抱歉\"] }");  
	 //下面是生成回送的数据，这里假定直接回送固定值  
  System.out.println("执行了：CMsgQueryTableByKeys 生成返回数据:"+ param.respData);

  return 0;  	  
 };	

}
