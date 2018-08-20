package ECP.handle.common;

public class ChatServerListenerThread extends Thread{
	@Override
	public void run(){
		try{
			ChatServer chatServer = new ChatServer(9999);
			chatServer.start();
			chatServer.showInfo("服务端链接已开启，等待客户端连接，端口号： " + chatServer.getPort());
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
