package csAsc.EIO.MsgEngine;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.net.URI;
import java.net.URLDecoder;

public class CEIOMsgRouter extends HttpServlet
{// 基于Servlet的消息引擎
	private static final long serialVersionUID = 1L;
	// 下面的两个量可以作为常量或者从外部文件读入
	
	ServletConfig config;
	String file;
	public class CParam
	{
		String reqMsgTypeId; // 消息中的消息种类的Key标识符
		String reqMsgNameId; // 消息中的消息名称的key标识符
		String reqVeNameId; // 消息中的Ve名称的键的标识符

		String reqMsgDataId = "EIOVEDATA"; // 客户端请求消息中的消息数据标识符（键）
		String resMsgStatusId = "EIOSTATUS"; // 消息处理器回应消息中的消息状态标识符（键）
		String projectPath;

		String reqVeName;
		String reqMsgType; // 消息中的消息种类的Key标识符
		String reqMsgName; // 消息中的消息名称的key标识符
		HttpServletRequest msgReq;
		HttpServletResponse msgRes;
		String configFile;
		
		
		
		public Object[] userObj;
		public String reqMsgHandle; // 消息中的消息处理器的类名
		public CEioConfig eioConfigObj;// 用户的EIO配置文件的XML对象
		public String msgStatus = "0_成功";// 存放返回消息的状态
		public int sysStatus = 0;// 存放系统的状态
		public StringBuffer respData;// 存放返回到请求端的数据

		public String getReqMsgTypeId()
		{
			return reqMsgTypeId;
		}

		public void setReqMsgTypeId(String reqMsgTypeId)
		{
			this.reqMsgTypeId = reqMsgTypeId;
		}

		public String getReqMsgNameId()
		{
			return reqMsgNameId;
		}

		public void setReqMsgNameId(String reqMsgNameId)
		{
			this.reqMsgNameId = reqMsgNameId;
		}

		public String getReqVeNameId()
		{
			return reqVeNameId;
		}

		public void setReqVeNameId(String reqVeNameId)
		{
			this.reqVeNameId = reqVeNameId;
		}

		public String getReqVeName()
		{
			return reqVeName;
		}

		public void setReqVeName(String reqVeName)
		{
			this.reqVeName = reqVeName;
		}

		public String getReqMsgDataId()
		{
			return reqMsgDataId;
		}

		public void setReqMsgDataId(String reqMsgDataId)
		{
			this.reqMsgDataId = reqMsgDataId;
		}

		public String getReqMsgType()
		{
			return reqMsgType;
		}

		public void setReqMsgType(String reqMsgType)
		{
			this.reqMsgType = reqMsgType;
		}

		public String getReqMsgName()
		{
			return reqMsgName;
		}

		public void setReqMsgName(String reqMsgName)
		{
			this.reqMsgName = reqMsgName;
		}

		public String getProjectPath()
		{
			return projectPath;
		}

		public void setProjectPath(String projectPath)
		{
			this.projectPath = projectPath;
		}

		public HttpServletRequest getMsgReq()
		{
			return msgReq;
		}

		public void setMsgReq(HttpServletRequest msgReq)
		{
			this.msgReq = msgReq;
		}

		public HttpServletResponse getMsgRes()
		{
			return msgRes;
		}

		public void setMsgRes(HttpServletResponse msgRes)
		{
			this.msgRes = msgRes;
		}

		public String getConfigFile()
		{
			return configFile;
		}

		public void setConfigFile(String configFile)
		{
			this.configFile = configFile;
		}

		public String getMsgHandle()
		{
			return reqMsgHandle;
		}

		public void setMsgHandle(String reqMsgHandle)
		{
			this.reqMsgHandle = reqMsgHandle;
		}

		public String getRetMsgHead(String reqVeName, String reqMsgType,
				String reqMsgName)
		{
			return "\"" + this.getReqVeNameId() + "\":\""
					+ this.getReqVeName() + "\",\"" + this.getReqMsgTypeId()
					+ "\":\"" + this.getReqMsgType() + "\",\""
					+ this.getReqMsgNameId() + "\":\"" + this.getReqMsgName()
					+ "\"";
		}
		public int sendRespDada() throws ServletException, IOException
		{// 将respData中数据发送到请求端
			// 准备响应流		
	 		
			//EIO本来的代码
			/*param.getMsgRes().setContentType("text/html;charset=GB2312");
			PrintStream out = new PrintStream(param.getMsgRes().getOutputStream());
			out.println(param.respData);*/
			
			//fastcard项目中为了实现utf-8的代码
			//param.getMsgRes().setContentType("text/html;charset=UTF-8");
			
			//解决reponse响应中文乱码问题  add by durenshi 下面两行效果一样
			//this.getMsgRes().setHeader("Content-type", "text/html;charset=UTF-8");
			this.getMsgRes().setContentType("text/html;charset=UTF-8");
			
			this.getMsgRes().setCharacterEncoding("UTF-8"); 
			PrintWriter pw = this.getMsgRes().getWriter();  
			pw.write(this.respData.toString());  
			
			System.out.println("回送到请求端的的数据=》" + this.respData);
			return 0;
		}
	}

	public void init(ServletConfig config) throws ServletException
	{// 初始化程序，在Servlet生命周期内仅执行一次
		System.out.println("");
		System.out.println("!!!!!!=====进入消息引擎的初始化程序====!!!");
		super.init(config);// 重写该方法，应该首先调用父类的init方法
		this.config = config; 
		this.file = this.getClass().getClassLoader().getResource("EIO.xml").getFile();
		System.out.println("!!!!!!=====初始化完成====!!!");

	}

	// 消息处理人口service重定义
	/*
	 * edit by weiwenjie
	 * 多个请求会共用param导致出错，所以在service方法上加了synchronized
	 */
	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
	{
		System.out.println("========进入消息引擎的总入口=======");
		CParam param = new CParam();
		param.setProjectPath(config.getInitParameter("ProjectPath"));// 获取用户文件的绝对路径（即WebContent的绝对路径）
		String appConfigFile = config.getInitParameter("AppConfigFile");// 获取该应用程序对应的配置文件名称
		param.setConfigFile(appConfigFile);// 获取VE应用的配置文件的路径

		// param.eioConfigObj = new
		// CEioConfig(param.getProjectPath()+"\\WebContent\\"+appConfigFile);
		//URI file =new URI((this.getClass().getClassLoader().getResource("EIO.xml").getFile()).toString())
		try {
			file = URLDecoder.decode(file, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		System.out.println("!!!!!!=====进入service1====!!!");
		param.eioConfigObj = new CEioConfig(file);
		try
		{
			// System.out.println(param.eioConfigObj.getXml2Json("消息包2"));
			param.setReqMsgTypeId(param.eioConfigObj
					.getInitParameter("VEReqMsgTypeId"));// 获取VE消息的种类的标识符
			param.setReqMsgNameId(param.eioConfigObj
					.getInitParameter("VEReqMsgNameId"));// 获取VE消息的名称的标识符
			param.setReqVeNameId(param.eioConfigObj
					.getInitParameter("VeNameId"));// 获取VE消息的名称的标识符
			param.setMsgHandle(param.eioConfigObj
					.getInitParameter("IndividualizedInit"));// 获取应用程序的初始化程序类与方法名

		} catch (Exception e)
		{
			System.out.println("读配置文件错误");
			e.printStackTrace();
		}
		System.out.println("!!!!!!=====进入service2====!!!");
		param.userObj = new Object[6];// 这里规定最多6个用户对象引用

		System.out.println("准备调用初始化==" + param.getMsgHandle());
		try
		{
			callMsgHandle(param);// 调用应用系统的初始化程序
		} catch (Exception e)
		{
			System.out.println("调用应用程序的初始化出错");
			return;
		}
		
		
		
		
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		
		param.setReqVeName(req.getParameter(param.getReqVeNameId())); // 从消息中获取名称的值
		param.setReqMsgType(req.getParameter(param.getReqMsgTypeId())); // 从消息中获取消息种类的值ֵ
		param.setReqMsgName(req.getParameter(param.getReqMsgNameId())); // 从消息中获取消息名称种类的值
		param.setMsgHandle(req.getParameter("MsgHandle"));// 设置VE消息处理器种类的标识符
		param.setMsgReq(req);
		param.setMsgRes(res);

		// ServletConfig config = getServletConfig();//获取ServletConfig对象
		// param.setMsgHandle(config.getInitParameter(param.getReqMsgName()));//获取VE消息的种类的标识符

		if (param.getReqMsgType() == null || param.getReqMsgName() == null)
		{
			System.out.println("消息头类型或者名称为空:" + param.getReqMsgType() + " "
					+ param.getReqMsgName() + "  " + param.getReqMsgTypeId()
					+ " " + param.getReqMsgNameId());
			return;
		}

		if (param.getMsgHandle() == null)
		{
			System.out.println("信息名称对应的消息处理器映射未发现，请检查配置文件:"
					+ param.getReqMsgType() + " " + param.getReqMsgName());
			return;
		}

		String msgHandle = param.getMsgHandle();
		System.out.println("准备调用的消息处理器==" + msgHandle + "  消息名称="
				+ param.getReqMsgName());

		String retMsgHead = param.getRetMsgHead(param.getReqVeName(),
				param.getReqMsgType(), param.getReqMsgName());
		// StringBuffer respData=new
		// StringBuffer("{"+retMsghead+",\""+param.getReqMsgDataId()+"\":");

		// param.respData = new
		// StringBuffer("{"+retMsgHead+",\""+param.getReqMsgDataId()+"\":");//每个消息对应一个新的retData
		param.respData = new StringBuffer();
		int msgPS = 0;
		int k = msgHandle.lastIndexOf(".");
		switch (msgHandle.substring(k + 1, msgHandle.length()))
		// 根据消息dying的不同处理器进行处理
		{
		case "handleMsg":
			msgPS = callMsgHandle(param);
			retMsgHead = "{"
					+ param.getRetMsgHead(param.getReqVeName(),
							param.getReqMsgType(), param.getReqMsgName())
					+ ",\"" + param.resMsgStatusId + "\":\"" + param.msgStatus
					+ "\",\"" + param.getReqMsgDataId() + "\":";
			param.respData.insert(0, retMsgHead);
			param.respData.append("}");
			param.sendRespDada();
			break;
		case "handleMsgDirect": // 消息处理器直接通过HttpServletResponse回应
			callMsgHandle(param);

			break;
		default:
			System.out.println("消息配置文件错误，没有找到要执行的消息处理器 ");

		}

	}// service()

	public int callMsgHandle(CParam param) throws ServletException, IOException
	{// 调用名为msgHandleId中内容的方法,用反射机制调用相应的方法
		// 返回处理结果的字符串数据；
		return CMsgLoader.callMsgHandle(param);

	}

	

}
