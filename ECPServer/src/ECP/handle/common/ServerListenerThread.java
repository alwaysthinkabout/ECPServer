package ECP.handle.common;
import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.net.ServerSocket;  
import java.net.Socket;  
  
  

public class ServerListenerThread extends Thread {  
    int i=1001;  
    @Override  
    public void run() {  
        // TODO Auto-generated method stub  
        super.run();  
        try {  
            ServerSocket serverSocket = new ServerSocket(12345);  
            // 循环监听连接  
            while (true) {  
                // 阻塞block  
                Socket socket = serverSocket.accept();  
                // 建立连接  
                // 在浏览器中输入：http://localhost:12345/  
                // 会弹出提示框，点击确定后断开连接  
                //JOptionPane.showMessageDialog(null, "有客户端连接到本机");  
                // 将Socket传递给新的线程,每个socket享受独立的连接  
                // new ChatSocket(socket).start();//开启chatSocket线程  
                BufferedReader br = new BufferedReader(new InputStreamReader(  
                        socket.getInputStream(), "utf-8"));  
                String line = null;  
                String name = null;  
                if ((line = br.readLine())!=null) {//接收到客户端数据  
                    if(line.indexOf("name:")!=-1){  
                        name = line.substring(line.indexOf("name:")+5);  
                        System.out.println(name+":连接到本机");  
                    }  
                }  
                //br.close();//关闭输入流  
                if(!ChatManager.getChatManager().hasSocket(name)){
                	ChatSocket chatSocket = new ChatSocket(socket, name);  
                    chatSocket.start();  
                    ChatManager.getChatManager().add(chatSocket);  
                    i++;  
                }
            }  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
}  