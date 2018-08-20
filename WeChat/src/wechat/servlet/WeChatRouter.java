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
	 * ���ڲ������ڼ�¼���������һЩ��Ϣ 
	 */
	public class CParam{

		HttpServletRequest msgReq;
		HttpServletResponse msgRes;
		public String reqMsgHandle; // ��Ϣ�е���Ϣ������������
		public String msgStatus = "0_�ɹ�";// ��ŷ�����Ϣ��״̬
		public int sysStatus = 0;// ���ϵͳ��״̬
		public StringBuffer respData;// ��ŷ��ص�����˵�����
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
		
		public int sendRespDada() throws ServletException, IOException{// ��respData�����ݷ��͵������
			this.getMsgRes().setContentType("text/html;charset=UTF-8");
			
			this.getMsgRes().setCharacterEncoding("UTF-8"); 
			PrintWriter pw = this.getMsgRes().getWriter();  
			pw.write(this.respData.toString());  
			pw.flush();
			pw.close();
			System.out.println("���͵�����˵ĵ�����=��" + this.respData);
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
		System.out.println("========������Ϣ����������=======");
		CParam param = new CParam();
		
		String reqData = readJSONString(request);//��ȡǰ�˴����������� readJSONString���л�����
		System.out.println("WeChatRouter�������յ�ǰ�����ݣ� "+reqData);
		JSONObject data;
		try {
			data = new JSONObject(reqData);
			String reqMsgandle = data.getString("handle");
			param.setMsgHandle(reqMsgandle);
			param.setReqData(reqData);
			param.setMsgReq(request);
			param.setMsgRes(response);//���汾�λỰ�������Ϣ
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*System.out.println("׼�����ó�ʼ��==" + param.getReqMsgHandle());
		try{
			MsgHandleLoader.callMsgHandle(param);// ����Ӧ��ϵͳ�ĳ�ʼ�����򣬷���ļ��ز���
		} catch (Exception e){
			System.out.println("����Ӧ�ó���ĳ�ʼ������");
			return;
		}*/
		System.out.println("׼�����õ���Ϣ������==" + param.getReqMsgHandle());
		param.respData = new StringBuffer();
		MsgHandleLoader.callMsgHandle(param);//���Ĳ�������respData��ֵ
		param.sendRespDada();//��resData���ظ�ǰ��
		
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
