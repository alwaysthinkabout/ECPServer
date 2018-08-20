package ECP.util.common;

import java.io.IOException;
import java.util.HashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/push")
public class WebSocketEndPoint {
	//key为session的ID，value为对象
	private static final HashMap<String, Object> CONNECT = new HashMap<>();
	//key为session的ID，value为用户名
	private static final HashMap<String, String> USER_MAP = new HashMap<>();
	
	private Session session;
	
	private String userName;
	
	private boolean isFirst = true;
	
	@OnOpen
	public void onOpen(Session session){
		this.session = session;
		System.out.println("session id : " + session.getId() + " 登陆~");
		CONNECT.put(session.getId(), this);
	}
	
	@OnClose
	public void onClose(Session session){
		String id = session.getId();
		String user = USER_MAP.get(id);
		USER_MAP.remove(id);
		CONNECT.remove(id);
		System.out.println(user + " 退出！当前在线人数为： " + CONNECT.size());
	}
	
	@OnMessage
	public void onMessage(String message,Session session){
		System.out.println(session.getId() + ", msg =>" + message);
		if(isFirst){
			userName =  message.substring(message.indexOf("open_name:"));
			System.out.println("用户:" + userName +"连接，在线人数：" + CONNECT.size());
			USER_MAP.put(session.getId(), userName);
			isFirst = false;
		}
	}
	
	@OnError
	public void onError(Session session, Throwable error){
		System.err.println("服务端WebSocket发生错误！");
		error.printStackTrace();
	}
	
	public static boolean isExist(String user){
		for(String key : USER_MAP.keySet()){
			if(user.equalsIgnoreCase(USER_MAP.get(key))){
				return true;
			}
		}
		return false;
	}
	
	public static void send(String msg, String user){
		boolean find = false;
		for(String key : USER_MAP.keySet()){
			if(user.equalsIgnoreCase(USER_MAP.get(key))){
				WebSocketEndPoint client = (WebSocketEndPoint) CONNECT.get(key);
				synchronized (client) {
					try{
						client.session.getBasicRemote().sendText(msg);
					} catch(IOException e){
						try{
							client.session.close();
						} catch(IOException e1){
							e1.printStackTrace();
						}
					}
				}
				find = true;
				break;
			}
		}
		if(!find){
			System.out.println("用户："+user+" 不存在/不在线");
		}
	}
	
}
