package ECP.handle.common;
import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.UnsupportedEncodingException;  
import java.net.Socket;  
  
public class ChatSocket extends Thread {  
    private Socket socket;  
    private String sName;  
    private BufferedReader br;
    private boolean stop;
    
    /** 
     * 构造函数 
     */  
    public ChatSocket(Socket socket, String sName) {  
        // TODO Auto-generated constructor stub  
        this.socket = socket;  
        this.sName = sName;  
        stop = false;
    }  
  
    @Override  
    public void run() {  
        // TODO Auto-generated method stub  
        // 输入  
    	try{
    		br = new BufferedReader(new InputStreamReader(  
                    socket.getInputStream(), "utf-8"));  
    		while(!stop){
    			String line = null; 
                while ((line = br.readLine()) != null) {// 接收到客户端数据  
                    System.out.println(getsName() + ":" + line);  
                }  
    		}
    	} catch (Exception e){
    		e.printStackTrace();
    	} finally{
    		try{
    			if(br!=null){
    				br.close();
    			}
    			if(socket!=null){
    				socket.close();
    			}
    		} catch(Exception e){
    			e.printStackTrace();
    		}
    	}
    }  
  
    public void out(String out) {  
        // TODO Auto-generated method stub  
        try {  
            socket.getOutputStream().write(out.getBytes("UTF-8"));  
        } catch (UnsupportedEncodingException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            if ("Socket is closed".equals(e.getMessage())) {// 对于已经关闭的连接从管理器中删除  
                System.out.println(e.getMessage());  
                ChatManager.getChatManager().remove(this);  
            }  
            // e.printStackTrace();  
        }  
    }  
  
    public String getsName() {  
        return sName;  
    }  
  
    public void setsName(String sName) {  
        this.sName = sName;  
    }  
  
    public Socket getSocket(){
    	return socket;
    }
    
    public boolean close(){
    	stop = true;
    	ChatManager.getChatManager().remove(this);
    	System.out.println("socket: "+getsName()+"关闭");
    	return true;
    }
    
}  
