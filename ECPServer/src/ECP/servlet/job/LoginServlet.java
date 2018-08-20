package ECP.servlet.job;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servlet/LoginServlet")   //使用webservlet注解，无需在web.xml上配置
public class LoginServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
    {
       doPost(request, response);
    }
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String piccode=(String) request.getSession().getAttribute("piccode");
        String checkCode=request.getParameter("checkCode");  //取值
        checkCode=checkCode.toUpperCase();  //把字符全部转换为大写的（此语句可以用于验证码不区分大小写）
        response.setContentType("text/html;charset=gbk");//解决乱码问题
        PrintWriter out=response.getWriter();
        if(checkCode.equals(piccode))
        {
            out.println("true");
        }
        else
        {
            out.println("false");
        }
        out.flush();//将流刷新
        out.close();//将流关闭
	}
}