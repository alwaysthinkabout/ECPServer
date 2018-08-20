package ECP.util.common;

import java.util.HashMap;

import org.java_websocket.WebSocket;



public class WebSocketManagerBackup {
	private static HashMap<String, WebSocket> storage = new HashMap<>();
//	private static HashMap<String, String> ipToAccount = new HashMap<>();
	private static HashMap<Integer, String> hashcodeToAccount = new HashMap<>();
	
	
	//添加
	public static void add(String account, WebSocket webSocket){
		if(!storage.containsKey(account)){
			storage.put(account, webSocket);
			hashcodeToAccount.put(webSocket.hashCode(), account);
//			ipToAccount.put(webSocket.getRemoteSocketAddress().getAddress().getHostAddress(), account);
		}
	}
	
	
	//移除
	public static void removeByAccount(String account){
		if(storage.containsKey(account)){
//			String ip = storage.get(account).getRemoteSocketAddress().getAddress().getHostAddress();
//			if(ipToAccount.containsKey(ip))
//				ipToAccount.remove(ip);
			WebSocket webSocket = storage.get(account);
			int hashcode = webSocket.hashCode();
			webSocket.close();
			if(hashcodeToAccount.containsKey(hashcode)){
				hashcodeToAccount.remove(hashcode);
			}
			System.out.println("移除账号为："+account+" 的webSocket");
			storage.remove(account);
		}
	}
	
//	public static void removeByIp(String ip){
//		if(ipToAccount.containsKey(ip)){
//			String account = ipToAccount.get(ip);
//			ipToAccount.remove(ip);
//			System.out.println("移除ip为："+ip+" 的webSocket");
//			if(storage.containsKey(account)){
//				storage.remove(account);
//				System.out.println("移除账号为："+account+" 的webSocket");
//			}
//		}
//	}
	
	public static void removeByHashcode(int hashcode){
		if(hashcodeToAccount.containsKey(hashcode)){
			String account = hashcodeToAccount.get(hashcode);
			hashcodeToAccount.remove(hashcode);
			if(storage.containsKey(account)){
				WebSocket webSocket = storage.get(account);
				webSocket.close();
				storage.remove(account);
				System.out.println("移除账号为："+account+" 的webSocket");
			}
		}
	}
	
	//发送消息
	public static void send(String msg, String account){
		try{
			WebSocket webSocket = storage.get(account);
			if(webSocket != null){
				webSocket.send(msg);
				System.out.println("send: "+msg);
			}
		} catch(Exception e){
			e.printStackTrace();
			return;
		}
		
	}

	//判断是否存在账号对应的webSocket
	public static boolean isExist(String account){
		if(storage.containsKey(account)){
			WebSocket webSocket = storage.get(account);
			if(webSocket.isOpen()){
				return true;
			}else{
				removeByAccount(account);
				return false;
			}
		}
		return false;
	}
	
	//根据账号获取webSocket
	public static WebSocket getWebSocket(String account){
		return storage.get(account);
	}
}
