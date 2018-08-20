package wechat.util;

import java.io.IOException;
import java.util.HashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONException;
import org.json.JSONObject;


@ServerEndpoint("/websocketTest")
public class WebSocketManager {
	//keyΪsession��ID��valueΪ����
	private static final HashMap<String, Object> CONNECT = new HashMap<>();
	//keyΪsession��ID��valueΪ�û�id��������id
	private static final HashMap<String,String[]> USER_MAP = new HashMap<>();
	//keyΪroom_id,��ά�������room_id��Ӧ����������ĳ����Ա��user_id��user_name.
	private static  final HashMap<String,String[][]> ROOM_COUNT = new HashMap<>();
	
	public static HashMap<String, String[][]> getRoomCount() {
		return ROOM_COUNT;
	}

	private Session session;
	private boolean isFirst = true;
	
	@OnOpen
	public void onOpen(Session session){
		this.session = session;
		CONNECT.put(session.getId(), this);
	}

	@OnClose
	public void onClose(Session session){
		String id = session.getId();
		if(USER_MAP.containsKey(id)){
			String[] array = USER_MAP.get(id);//����session id�ҵ��û�id��room_id
			String user_id = array[0];
			String room_id = array[1];
			if(room_id != ""){
				if(ROOM_COUNT.containsKey(room_id)){
					String[][] user = ROOM_COUNT.get(room_id);
					int len = user.length;
					if(len > 1){//�����������������1������������Ƴ����û�
						int tmp_len = 0;
						for(int i = 0; i < len; i++){//����ԭ������Ϣ,��������ǰ�û�
							if(user[i][0] != array[0])
								tmp_len++;
						}
						if(tmp_len == 0){
							ROOM_COUNT.remove(room_id);
						}else{
							String[][] tmp = new String[tmp_len][2];
							int n = 0;
							for(int i = 0; i < len; i++){//����ԭ������Ϣ,��������ǰ�û�
								if(user[i][0] != array[0]){
									for(int j=0; j < 2; j++){							
										tmp[n][j] = user[i][j];
									}
									n++;
								}
								
							}
							ROOM_COUNT.remove(room_id);
							ROOM_COUNT.put(room_id, tmp);
							System.out.println(user_id + " �˳������ң�������"+room_id+"��ǰ��������Ϊ�� " + String.valueOf(tmp.length));
							JSONObject data = new JSONObject();
							try {
								//�û��뿪��֪ͨ�����û�
								data.put("user_id",user_id);
								data.put("msg","online_count");
								data.put("online_count",tmp.length);
								data.put("op","sendToRoom");
								send(data,room_id);//֪ͨ�û���ǰ��������������
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								USER_MAP.remove(id);
								CONNECT.remove(id);
								e.printStackTrace();
							}
						}	
					}else{
						ROOM_COUNT.remove(room_id);//�����ǰ�û��Ǹ����������һ���û�����ɾ��������
						System.out.println(user_id + " �˳������ң�������"+room_id+"����" );
					}
					
				}
			}
			USER_MAP.remove(id);
			CONNECT.remove(id);
			System.out.println(user_id + " ���ߣ���ǰ����������Ϊ�� " + CONNECT.size());
		}
		
		
	}
	
	@OnError
	public void onError(Session session, Throwable error){
		try{
			session.close();
		} catch(Exception e){
			System.err.println("�����WebSocket��������");
			e.printStackTrace();
			error.printStackTrace();
		}
	}
	
	@OnMessage
	public void onMessage(String msg,Session session){
		System.out.println(session.getId() + ", msg =>" + msg);
		JSONObject data;
		try {
			data = new JSONObject(msg);
			String user_id = data.getString("user_id");
			String user_name = data.getString("user_name");
			String room_id = data.getString("room_id");
			String[] array = new String[3];
			array[0] = user_id;
			array[1] = room_id;
			array[2] = user_name;
			if(!room_id.equals("")){
				if(ROOM_COUNT.containsKey(room_id)){//��������������
					String[][] user = ROOM_COUNT.get(room_id);
					int len = user.length;
					String[][] tmp = new String[len+1][2];
					
					for(int i = 0; i < len; i++){//����ԭ������Ϣ
						for(int j=0; j < 2; j++){
							//System.out.println(user[i][j]);
							tmp[i][j] = user[i][j];
						}
					}
					tmp[len][0] = user_id;
					tmp[len][1] = user_name;
					ROOM_COUNT.remove(room_id);
					ROOM_COUNT.put(room_id, tmp);
					System.out.println("�û��� "+user_name+" ���������ң� "+room_id+"����ǰ������������Ϊ�� "+ String.valueOf(len+1));
				}else{//���û��ǵ�һ������������ҵ���
					String[][] user = {{user_id,user_name}};
					ROOM_COUNT.put(room_id, user);
					System.out.println("�û��� "+user_name+" ���������ң� "+room_id+"����ǰ������������Ϊ�� 1");
				}
				
				
			}
			if(isFirst){				
				if(!isExist(user_id)){
					USER_MAP.put(session.getId(), array);
					System.out.println("�û�:" + user_name +"���ߣ�����������" + CONNECT.size());
				}else{
					String id = findIdByName(user_id);
					CONNECT.remove(id);
					CONNECT.put(id, this);
				}
				isFirst = false;
				if(!room_id.equals("")){
					String[][] users = ROOM_COUNT.get(room_id);
					int online_count = users.length;
					data.put("user_id",-1);//��������֪ͨ�����ˣ������Լ���
					data.put("msg","online_count");
					data.put("users",users);
					data.put("online_count",online_count);
					data.put("op","sendToRoom");
					send(data,room_id);//֪ͨ�û���ǰ��������������
				}
				
			}else{
				System.out.println("�û�:" + user_name +", HeartBeat!");
			}
		} catch (JSONException e) {
			System.out.println("onMessage����������Ϣʧ��");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	/*
	 * �ж��û��Ƿ�����
	 */
	public static boolean isExist(String user){
		for(String key : USER_MAP.keySet()){
			if(user.equalsIgnoreCase(USER_MAP.get(key)[0])){
				return true;
			}
		}
		return false;
	}
	
	public String findIdByName(String user_id){
		String id = "";
		for(String key : USER_MAP.keySet()){
			if(user_id.equalsIgnoreCase(USER_MAP.get(key)[0])){
				id = key;
				break;
			}
		}
		return id;
	}
	
	//�û�����������ʱ����Ӧ������������1������ϢӦ֪ͨ����û�н��������ҵ��û�
	public static void sendToAll(String msg){
		for(String key : USER_MAP.keySet()){
			WebSocketManager client = (WebSocketManager) CONNECT.get(key);
			String user_id = USER_MAP.get(key)[0];			
			synchronized (client) {
				try{
					client.session.getBasicRemote().sendText(msg);
					System.out.println("send to "+user_id+", message: "+msg);
				} catch(IOException e){
					try{
						client.session.close();
					} catch(IOException e1){
						e1.printStackTrace();
					}
				}
			}
		}
	}
	
	//���͸������������ҵ��û�����ȥ�����û�����
	public static void send(JSONObject reqData, String room_id) throws JSONException{
		boolean find = false;
		String user_id = reqData.getString("user_id");
		for(String key : USER_MAP.keySet()){
			String[] array = USER_MAP.get(key);			
			if((room_id.equalsIgnoreCase(array[1]))&&(!user_id.equalsIgnoreCase(array[0]))){
				WebSocketManager client = (WebSocketManager) CONNECT.get(key);
				synchronized (client) {
					try{
						client.session.getBasicRemote().sendText(reqData.toString());
						System.out.println("send to " + room_id + ", message: "+reqData.toString());
					} catch(IOException e){
						try{
							client.session.close();
						} catch(IOException e1){
							e1.printStackTrace();
						}
					}
				}
				find = true;
			}
		}
		if(!find){
			System.out.println("�����ң�"+room_id+" �������û�");
		}
	}
	
	//�������͸�ĳ���û�
	public static void sendToPerson(JSONObject reqData, String user_id) throws JSONException{
		boolean find = false;
		for(String key : USER_MAP.keySet()){
			if(user_id.equalsIgnoreCase(USER_MAP.get(key)[0])){
				WebSocketManager client = (WebSocketManager) CONNECT.get(key);
				synchronized (client) {
					try{
						client.session.getBasicRemote().sendText(reqData.toString());
						System.out.println("send to " + user_id + ", message: "+reqData.toString());
					} catch(IOException e){
						try{
							client.session.close();
						} catch(IOException e1){
							e1.printStackTrace();
						}
					}
				}
				find = true;
			}
		}
		if(!find){
			System.out.println("�û���"+user_id+"������");
		}
	}
		
	
}
