package ECP.handle.common;

import java.util.Vector;  

public class ChatManager {  
    private static final ChatManager chatManager = new ChatManager();  
    
    public static ChatManager getChatManager(){  
        return chatManager;  
    }  
    Vector<ChatSocket> vector = new Vector<ChatSocket>();  
    /** 
     * @Title: add 
     * @Description: TODO(向集合中增加ChatSocket) 
     * @param chatSocket    
     * @return: void    
     * @throws 
     */  
    public void add(ChatSocket chatSocket){  
        vector.add(chatSocket);  
    }  
    public void remove(ChatSocket chatSocket){  
        vector.remove(chatSocket);  
    }  
    /** 
     * @Title: publish 
     * @Description: TODO(向其他ChatSocket连接发送信息) 
     * @param chatSocket 
     * @param out    
     * @return: void    
     * @throws 
     */  
    public void publish(ChatSocket chatSocket,String out){  
        for(int i =0;i<vector.size();i++){  
            ChatSocket cs = vector.get(i);  
            if(!cs.equals(chatSocket)){//发给不是自己的其他人  
                cs.out(out+"\n");  
            }  
        }  
    }  
    
    /** 
     * @Title: publish 
     * @Description: TODO(向指定ChatSocket连接发送信息) 
     * @param chatSocket 
     * @param out   
     * @param name 
     * @return: void    
     * @throws 
     */  
    public void publish(String out,String name){  
        for(int i =0;i<vector.size();i++){  
            ChatSocket cs = vector.get(i);  
            if(cs.getsName().equals(name)){//发给不是自己的其他人  
                cs.out(out+"\n");  
            }  
        }  
    } 
    
    public void publish(ChatSocket chatSocket,Order order,String name){  
        for(int i =0;i<vector.size();i++){  
            ChatSocket cs = vector.get(i);  
            if(cs.getsName().equals(name)){//发给不是自己的其他人  
                cs.out(order.toString());  
            }  
        }  
    } 
    
    public boolean hasSocket(String name){
    	for(int i=0;i<vector.size();i++){
    		ChatSocket cs = vector.get(i);
    		if(cs.getName().equals(name)){
    			return true;
    		}
    	}
    	return false;
    }
    
    public ChatSocket getChatSocketByName(String name){
    	for(int i=0;i<vector.size();i++){
    		ChatSocket csChatSocket = vector.get(i);
    		if(csChatSocket.getsName().equals(name)){
    			return csChatSocket;
    		}
    	}
    	return null;
    }
    
    //判断是否在线
    public boolean isOnline(String to){
		ChatSocket chatSocket = ChatManager.getChatManager().getChatSocketByName(to);
		if(chatSocket != null){
			return true;
		}else{
			return false;
		}
	}
}  