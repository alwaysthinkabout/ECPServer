package ECP.handle.common;
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
  
/*
 * 启动webSocket监听
 */
public class MyServerSocket extends HttpServlet {  
    private static final long serialVersionUID = 1L;  
 
    @Override  
    public void init() throws ServletException {  
        // TODO Auto-generated method stub  
//        new ServerListenerThread().start();// 开启线程  
//        System.out.println("开启socket服务…………………………");  
        new ChatServerListenerThread().start();
    }  
}  
