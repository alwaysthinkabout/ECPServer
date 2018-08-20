package ECP.util.common;

import ECP.handle.common.ChatManager;
import ECP.handle.common.ChatSocket;

public class ChatSocketUtil {
	//判断接收方是否在线
		public static boolean isOnline(String to){
			ChatSocket chatSocket = ChatManager.getChatManager().getChatSocketByName(to);
			if(chatSocket != null){
				return true;
			}else{
				return false;
			}
		}
}
