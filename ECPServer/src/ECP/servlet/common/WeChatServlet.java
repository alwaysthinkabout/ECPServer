package ECP.servlet.common;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ECP.servlet.common.wechat.CheckUtil;
import ECP.servlet.common.wechat.util.weixin.CoreService;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by  on 2017/7/17.
 */
@WebServlet("/WeChatServlet")
public class WeChatServlet extends javax.servlet.http.HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //微信服务器发送回GET请求,GET请求携带参数
        //微信加密签名
        String signature=req.getParameter("signature");
        //时间戳
        String timestamp=req.getParameter("timestamp");
        //随机数
        String nonce=req.getParameter("nonce");
        //随机字符串
        String echostr=req.getParameter("echostr");

        PrintWriter out=resp.getWriter();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if(CheckUtil.checkSignature(signature,timestamp,nonce)){
            out.print(echostr);
        }
        out.close();
        out=null;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 调用核心业务类CoreService类的processRequest方法接收、处理消息，并得到处理结果；
        String respMessage = CoreService.processRequest(request);

        // 响应消息.调用response.getWriter().write()方法将消息的处理结果返回给用户
        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
    }
}
