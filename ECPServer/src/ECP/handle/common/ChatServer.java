package ECP.handle.common;

/*
 * 处理客户端webSocket连接与断开
 */
import java.net.InetSocketAddress;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;


public class ChatServer extends WebSocketServer{
	
	public ChatServer(InetSocketAddress address, int decoders){
		super(address, decoders);
	}
	
	public ChatServer(InetSocketAddress address){
		super(address);
	}
	
	public ChatServer(int port){
		this(new InetSocketAddress(port));
	}
	
	public void showInfo(String info){
		System.out.println(info);
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		// TODO Auto-generated method stub
//		final String ip = conn.getRemoteSocketAddress().getAddress().getHostAddress();
//		final String info = ip + "断开！，reason = " + reason;
//		showInfo(info);
//		WebSocketManager.removeByIp(ip);
		final String info = conn.hashCode() + "断开！";
		showInfo(info);
//		WebSocketManager.removeByHashcode(conn.hashCode());
	}

	@Override
	public void onError(WebSocket conn, Exception e) {
		// TODO Auto-generated method stub
		final String info = conn.getRemoteSocketAddress().getAddress().getHostAddress() + ", error => " + e;
		showInfo(info);
	}

	@Override
	public void onMessage(WebSocket conn, String msg) {
		// TODO Auto-generated method stub
		final String info = conn.hashCode() + ", msg => " + msg;
		showInfo("读取" + info); 
		if(info.indexOf("open_name:")!=-1){
			String name = info.substring(info.indexOf("open_name:")+10);
			showInfo(name+"连接到本机！(WebSocket)");
//			WebSocketManager.add(name, conn);
		}
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		// TODO Auto-generated method stub
		final String info = conn.hashCode() + "接入！";
		showInfo(info);
	}
}
