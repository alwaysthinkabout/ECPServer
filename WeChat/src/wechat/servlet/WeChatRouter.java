package wechat.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import wechat.util.MsgHandleLoader;


public class WeChatRouter extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * 该内部类用于记录本次请求的一些信息 
	 */
	public class CParam{

		HttpServletRequest msgReq;
		HttpServletResponse msgRes;
		public String reqMsgHandle; // 消息中的消息处理器的类名
		public String msgStatus = "0_成功";// 存放返回消息的状态
		public int sysStatus = 0;// 存放系统的状态
		public StringBuffer respData;// 存放返回到请求端的数据
		public String reqData;


		public HttpServletRequest getMsgReq(){
			return msgReq;
		}
		

		public String getReqData() {
			return reqData;
		}


		public void setReqData(String reqData) {
			this.reqData = reqData;
		}


		public void setMsgReq(HttpServletRequest msgReq){
			this.msgReq = msgReq;
		}

		public HttpServletResponse getMsgRes(){
			return msgRes;
		}

		public void setMsgRes(HttpServletResponse msgRes){
			this.msgRes = msgRes;
		}
		
		public String getReqMsgHandle(){
			return reqMsgHandle;
		}
		public void setMsgHandle(String reqMsgHandle){
			this.reqMsgHandle = reqMsgHandle;
		}
		
		public int sendRespDada() throws ServletException, IOException{// 将respData中数据发送到请求端
			this.getMsgRes().setContentType("text/html;charset=UTF-8");
			
			this.getMsgRes().setCharacterEncoding("UTF-8"); 
			PrintWriter pw = this.getMsgRes().getWriter();  
			pw.write(this.respData.toString());  
			pw.flush();
			pw.close();
			System.out.println("回送到请求端的的数据=》" + this.respData);
			return 0;
		}
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("========进入消息引擎的总入口=======");
		CParam param = new CParam();
		
		String reqData = readJSONString(request);//获取前端传过来的数据 readJSONString序列化数据
		System.out.println("WeChatRouter控制器收到前端数据： "+reqData);
		JSONObject data;
		try {
			data = new JSONObject(reqData);
			String reqMsgandle = data.getString("handle");
			param.setMsgHandle(reqMsgandle);
			param.setReqData(reqData);
			param.setMsgReq(request);
			param.setMsgRes(response);//保存本次会话的相关信息
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*System.out.println("准备调用初始化==" + param.getReqMsgHandle());
		try{
			MsgHandleLoader.callMsgHandle(param);// 调用应用系统的初始化程序，反射的加载步骤
		} catch (Exception e){
			System.out.println("调用应用程序的初始化出错");
			return;
		}*/
		System.out.println("准备调用的消息处理器==" + param.getReqMsgHandle());
		param.respData = new StringBuffer();
		MsgHandleLoader.callMsgHandle(param);//核心操作，给respData赋值
		param.sendRespDada();//将resData返回给前端
		
	}
	
	private String readJSONString(HttpServletRequest request){
	   StringBuffer json = new StringBuffer();
	   String line = null;
	   try {
		   BufferedReader reader = request.getReader();
		   while((line = reader.readLine()) != null) {
			   json.append(line);
		   }
	   }
	   catch(Exception e) {
		   System.out.println(e.toString());
	   }
	   return json.toString();
    }
}
