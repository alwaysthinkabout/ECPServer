package csAsc.ECOSS.reso.demo1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

import csAsc.EIO.MsgEngine.CEIOMsgRouter;
import csAsc.com.file.CTextFileReader;

public class COpenHtmlFile extends CEIOMsgRouter
{	
	private static final long serialVersionUID = 1L;

	//打开一个网页文件，回送到客户端
	 public int handleMsg(CParam param) throws ServletException,IOException, JSONException
	 {//回送的数据格式：消息头后接HTML文件内容，以EIO的数据标志为起点，结束字符必须是"</html>", 大小写无关，要求7个字符	
	  System.out.println("==进入COpenHTMLFile");
	  
	  HttpServletRequest msgReq;
	  msgReq = param.getMsgReq();
	  msgReq.setCharacterEncoding("UTF-8");

	  StringBuffer reqMsgData = new StringBuffer();
	  reqMsgData.append(msgReq.getParameter(param.getReqMsgDataId())); //从消息中获取消息数据体（JSON字符串)

	  JSONObject msgDataObj = new JSONObject(reqMsgData.toString()); 
	  
	  String cName = msgDataObj.getString("idName"); //从消息中取出消息内容：cName的值
	  String cAge = msgDataObj.getString("idAge"); //从消息中取出消息内容：cAge的值
	 
	  //String gender = msgReq.getParameter("aGender"); //获取form中的aGender属性值
	  //String[] color = msgReq.getParameterValues("aColor");//获取form中的aColor属性值
	  //String national = msgReq.getParameter("aCountry");////获取form中的aCountry属性值 
		   
	  //准备响应数据

	  String filePath =param.getProjectPath() + "\\WebContent\\elemtEI.html";
	  
	  int k =CTextFileReader.readTxtFileToStringBuffer(filePath,param.respData);
	  //retData.append("\"");
	  //String cc="";
	  //for(String c : color) cc += c + " "; 
	  //回送的数据格式：HTML:{完整网页}
	 	  
	  return 0;
	 }
}
