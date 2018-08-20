package ECP.util.db;

import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitServer extends HttpServlet{
	
	/**
	 * @author durenshi
	 * 启动服务则创建数据库连接池及文件上传目录
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	 public void init() throws ServletException {  
		//初始化连接池
		new DBConnect(); 
		System.out.println("Init Mysql Pool..");
		
		//创建文件上传到服务器的目录路径
		File uploadDir = new File("d:/ECPServerUpload");
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
	 } 
	
	@Override
	 public void destroy(){
		super.destroy();
	 }
}
