package csAsc.ECOSS.reso.demo1;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.json.*;

import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;
public class CMsgVcInject
{
 public int  handleMsg(CParam param) throws ServletException,IOException, JSONException
 {
  System.out.println("进入CMsgVcInject==");
 
  HttpServletRequest msgReq;
  msgReq = param.getMsgReq();
  msgReq.setCharacterEncoding("UTF-8");
  
  StringBuffer reqMsgData = new StringBuffer();
  reqMsgData.append(msgReq.getParameter(param.getReqMsgDataId())); //从消息中获取消息数据体（JSON字符串)
  JSONObject msgDataObj = new JSONObject(reqMsgData.toString()); 
  
  String vcId = msgDataObj.getString("VcId"); //从消息中取出消息内容：vcId的值
  //String vcId = msgReq.getParameter("VcId");//从消息中取出消息内容：vcId的值
  System.out.println(param.respData);
  //下面是生成回送的数据，这里假定直接回送固定值  
  param.respData.append("{VcId:"+"\""+vcId+"\",VcItems:"+ "['中国','澳洲','越南','意大利','美国','俄罗斯','日本','韩国','加拿大']} ");
  System.out.println("执行了：CMsgVcInject 生成返回数据:"+ param.respData);
  
  //return param.respData;  
  return 0;
 }
}