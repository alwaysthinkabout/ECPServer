package csAsc.ECOSS.reso.demo1;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;
import csAsc.com.file.XMLParser;

public class CMsgGetTree 
{
	//public static ArrayList<Node> elemList = new ArrayList<Node>();
	public int handleMsg(CParam param)
			throws ServletException, IOException, SQLException, Exception 
	{
		System.out.println("进入CMsgGetTree的handleMsgHttp");
		
		HttpServletRequest msgReq;
		msgReq = param.getMsgReq();
		msgReq.setCharacterEncoding("UTF-8");
		
		StringBuffer reqMsgData = new StringBuffer();
		reqMsgData.append(msgReq.getParameter(param.getReqMsgDataId())); //从消息中获取消息数据体（JSON字符串)
		JSONObject msgDataObj = new JSONObject(reqMsgData.toString()); 
		  
		String treeName = msgDataObj.getString("TreeName"); //从消息中取出消息内容：TreeName的值
        //这里可以根据treeName选择打开不同的树XML文件,此略
		
	/****下面这些方法都不可正确获得路径
		System.out.println("1="+System.getProperty("place.xml"));
		System.out.println("2="+new File("place.xml").getAbsolutePath());
		System.out.println("3="+CMsgGetTree.class.getResource("")); 
		System.out.println("4="+ClassLoader.getSystemResource(""));
		System.out.println("5="+msgReq.getSession().getServletContext().getRealPath("/place.xml"));
    */
		
		/** 下面为采用XML读取动态目录树的数据 ***/
				
		//String filePath=smsgReq.getSession().getServletContext().getRealPath("/place.xml");
		
		
		String projectPath = param.getProjectPath();
		
		param.respData.append(XMLParser.getData(projectPath+"\\WebContent\\rsc\\place.xml"));
		System.out.println("返回的数据内容："+param.respData); 
				
		
		return 0;

		
		
		
		

		/** 下面为采用数据库读取动态目录树的数据 ***/
		/**************************************************/
		/****** ********************************************/
		/**
		 String sqlStr; 
		 ResultSet recs; 
		 sqlStr = "select area_id, area_name, parent_id from website_area"; 
		 recs = CQuery.getRecsBySql(sqlStr); 
		 retData = CTransform.RsToArrayStr(recs);
		 System.out.println("返回的数据内容："+retData); 
		 return retData;*/
		
	}
}
