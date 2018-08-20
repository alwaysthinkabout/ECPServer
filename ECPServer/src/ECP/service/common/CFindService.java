package ECP.service.common;

import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.mail.util.MailSSLSocketFactory;

import ECP.model.SubscribeMessageInfo;
import ECP.util.common.CommonUtil;
import ECP.util.common.ContactsManager;
import ECP.util.common.OfflineInfoManager;
import ECP.util.common.ServiceInfo;
import ECP.util.common.UserManager;
import ECP.util.common.WebSocketManager;
import ECP.dao.MarketDao;
import ECP.dao.MessageDao;
import ECP.dao.UserDao;
import ECP.dao.WebChatRecordDao;


public class CFindService {
	
//	private static Map<String, ServiceInfo> storageMap = new Hashtable<>();
	
	//群成员
	private static Map<String, List<String>> groupMembersMap = new Hashtable<>();
	
//	private static final ChatManager chatManager = ChatManager.getChatManager(); 
	
	public static String fileServer = "http://on3x7yjly.bkt.clouddn.com/";
	public static WebChatRecordDao webChatRecordDao;
	private UserDao userDao;
	private MessageDao messageDao;
	
	public CFindService(){
		List<String> members = Arrays.asList("10000","10001","10002");
		groupMembersMap.put("90000", members);
		webChatRecordDao = new WebChatRecordDao();
		userDao = new UserDao();
		messageDao = new MessageDao();
		
//		storageMap.put("104", new ServiceInfo("104","好友验证消息",fileServer+"SystemInfo.jpg","服务","friendApply",ServiceInfo.WT_JOINT,"",""));
//		storageMap.put("105", new ServiceInfo("105","系统消息",fileServer+"SystemInfo.jpg","服务","systemInfo",ServiceInfo.WT_JOINT,"",""));
//		storageMap.put("106", new ServiceInfo("106","推送消息",fileServer+"SystemInfo.jpg","服务","systemInfo",ServiceInfo.WT_JOINT,"",""));
//		storageMap.put("10000", new ServiceInfo("10000","吴广德",fileServer+"10000.jpg","好友","",ServiceInfo.WT_JOINT,"","我是吴广德"));
//		storageMap.put("10001", new ServiceInfo("10001","吴史德",fileServer+"10001.jpg","好友","",ServiceInfo.WT_JOINT,"","我是吴史德"));
//		storageMap.put("10002", new ServiceInfo("10002","吴行德",fileServer+"10002.jpg","好友","",ServiceInfo.WT_JOINT,"","我是吴行德"));
//		storageMap.put("90000", new ServiceInfo("90000","group",fileServer+"group.jpg","群组","",ServiceInfo.WT_JOINT,"","我是公共群组"));
	}
	
	public JSONObject handlePicture(JSONObject data) throws Exception{
		JSONObject result = new JSONObject();
		String encodeString = data.getString("picture");
		result.put("picture", encodeString);
		GenerateImage(encodeString);
//		CommonUtil.f_uploadVedio(data);
		return result;
	}
	public boolean GenerateImage(String imgStr){
		if(imgStr == null){
			return false;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try{
			byte[] b = decoder.decodeBuffer(imgStr);
			for(int i=0;i<b.length;i++){
				if(b[i]<0){
					b[i]+=256;
				}
			}
			String imgFilePath = "F:\\test_picture.jpg";
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	//注册
	public JSONObject register(JSONObject data) throws Exception{
		JSONObject result = new JSONObject();
		String email = data.getString("email");
		//判断邮箱是否被注册
		if(UserManager.hasUser(email)){
			result.put("result", "fail");
			return result;
		}
		String phone = data.getString("phone_num");
		String nickname = data.getString("nickname");
		String password = data.getString("password");
		UserManager.addUser(email, phone, nickname, "", password);
		result.put("result", "success");
		return result;
	}
	
	//修改密码
	public JSONObject change_password(JSONObject data) throws Exception{
		JSONObject result = new JSONObject();
		String email = data.getString("email");
		String password = data.getString("password");
		if(UserManager.changePassword(email, password)){
			result.put("result", "success");
		}else{
			result.put("result", "fail");
		}
		return result;
	}
	
	//登录，获取用户serviceInfo
	public JSONObject login(JSONObject data) throws JSONException{
		JSONObject result = new JSONObject();
		String email = data.getString("account");
		
		//判断用户是否存在
		if(!UserManager.hasUser(email)){
			result.put("result", "fail");
			result.put("fail_detail", 1);
			return result;
		}
		
		String account = UserManager.getAccount(email);
		
//		//判断用户是否已经登录
//		if(chatManager.isOnline(account)){
//			result.put("result", "fail");
//			result.put("fail_detail", 3);
//			return result;
//		}
		
		if(WebSocketManager.isExist(account)){
			result.put("result", "fail");
			result.put("fail_detail", 3);
			return result;
		}
		
		//判断密码是否正确
		String password = data.getString("password");
		if(!UserManager.checkPassword(email, password)){
			result.put("result", "fail");
			result.put("fail_detail", 2);
			return result;
		}
		
		List<String> accounts = ContactsManager.getContact(account);
		result.put("contacts", accounts);
		accounts.add(account);
		String[] str = new String[accounts.size()];
		str = accounts.toArray(str);
		JSONObject myInfo = new JSONObject();
		myInfo.put("account", account);
		myInfo.put("gender", UserManager.getGender(account));
		myInfo.put("phone", UserManager.getPhone(account));
		result.put("myInfo", myInfo);
		result.put("userInfo", getDetails(str));
		result.put("result", "success");
		return result;
	}
	
	//获取离线消息
	public JSONObject getOfflineInfo(JSONObject data) throws Exception{
		JSONObject result = new JSONObject();
		String account = data.getString("account");
		List<JSONObject> offlineInfos = OfflineInfoManager.getOfflineInfo(account);
		for(JSONObject info : offlineInfos){
			String to = info.getString("toAccount");
//			chatManager.publish(info.toString(), to);
			//
			WebSocketManager.send(info.toString(), to);
		}
		OfflineInfoManager.remove(account);
		result.put("result", "success");
		return result;
	}
	
	//搜索好友
	public JSONObject find(JSONObject data){
		JSONObject result = new JSONObject();
		String tag = "";
//		String keyword = "";
		try {
			tag = data.getString("tag");
//			keyword = data.getString("keyword");
			List<String> accounts;
			switch (tag) {
			case "全部":
				accounts = UserManager.getAllUsers();
				break;
			default:
				accounts = new ArrayList<>();
				break;
			}
			result.put("accounts", accounts);
			String[] str = new String[accounts.size()];
			str = accounts.toArray(str);
			result.put("detail", getDetails(str));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return result;
		}
		return result;
	}
	
	//消息推送
	public JSONObject push(JSONObject data) throws JSONException{
		JSONObject result = new JSONObject();
		//获取目标账号
		String toAccount = data.getString("toAccount");
		
		//推送消息类型，与前端ChatSocket里添加的推送消息类型一致
		data.put("type", "subscribe_push");
		data.put("fromAccount", "1");
		data.put("msgType1", "订阅消息");
		int flag = messageDao.setSubscribeInfoDetail(data);
		if(flag != 1){
			result.put("result", "fail");
			return result;
		}
		
		
		if(WebSocketManager.isExist(toAccount)){
			//在线直接推送
			WebSocketManager.send(data.toString(), toAccount);
		}else{
			//添加到离线消息中
			OfflineInfoManager.add(toAccount, data);
		}
		
		
		result.put("result", "success");
		return result;
	}
	//招聘端聊天记录保存在数据库
	public JSONObject webChat(JSONObject data) throws JSONException{
		JSONObject result = new JSONObject();
		//获取目标账号
		String toAccount = data.getString("toAccount");
		data.put("type", "chat");
		data.put("eventType", "消息聊天");
		data.put("module", "就业平台");
		int flag = 0;
		flag = webChatRecordDao.insert(data);
		if(!webChatRecordDao.IsExist(data))//若是第一次聊天，则入到求职端好友列表里头
		{
			webChatRecordDao.insertOrg_friend(data);
		}
		if(flag==1){
			if(WebSocketManager.isExist(toAccount)){
				//在线直接推送
				WebSocketManager.send(data.toString(), toAccount);
			}else{
				//添加到离线消息中
				OfflineInfoManager.add(toAccount, data);
			}		
			result.put("result", "success");
		}else{
			result.put("result", "fail");
		}
		return result;
	}
	//招聘端推送记录保存在数据库
	public JSONObject webPush(JSONObject data) throws JSONException{
		JSONObject result = new JSONObject();
		//获取目标账号
		String toAccount = data.getString("toAccount");
		data.put("type", "push");
		data.put("eventType", "消息通知");
		data.put("module", "就业平台");
		int flag = 0;
		flag = webChatRecordDao.insert(data);
		if(flag==1){
			if(WebSocketManager.isExist(toAccount)){
				//在线直接推送
				WebSocketManager.send(data.toString(), toAccount);
			}else{
				//添加到离线消息中
				OfflineInfoManager.add(toAccount, data);
			}		
			result.put("result", "success");
		}else{
			result.put("result", "fail");
		}
		return result;
	}
	//好友申请
	public JSONObject friendApply(JSONObject data) throws JSONException{
		JSONObject result = new JSONObject();
		String toAccount = data.getString("toAccount");
		String fromAccount = data.getString("fromAccount");
		result.put("fromAccount", fromAccount);
		data.put("type", "apply");
		result.put("result", "success");
		data.put("serviceInfo", getDetail(fromAccount));
//		if(chatManager.isOnline(toAccount)){
//			chatManager.publish(data.toString(), toAccount);
//		}else{
//			OfflineInfoManager.add(toAccount, data);
//		}
		if(WebSocketManager.isExist(toAccount)){
			WebSocketManager.send(data.toString(), toAccount);
		}else{
			OfflineInfoManager.add(toAccount, data);
		}
		return result;
	}
	
	//用户退出登录，关闭socket
	public JSONObject logout(JSONObject data) throws JSONException{
		JSONObject result = new JSONObject();
		String name = "";
		//将已经登陆的用户挤下线
		if(data.has("email")){
			String email = data.getString("email");
			String type = userDao.checkUserAccountType(email);
			name = userDao.findAccountByEmail(email,type);
			data.put("type", "logout");
			//如果其他客户端登陆，则将该登陆的客户端注销
			if(WebSocketManager.isExist(name)){
				WebSocketManager.send(data.toString(), name);
			}
		}else{
			name = data.getString("account");
		}
//		ChatSocket chatSocket = chatManager.getChatSocketByName(name);
//		if(chatSocket!=null){
//			if(chatSocket.close()){
//				result.put("result", "success");
//			}else{
//				result.put("result", "fail");
//			}
//		}else{
//			result.put("result", "success");
//		}
//		WebSocketManager.removeByAccount(name);
		result.put("result", "success");
		return result;
	}
	
	//接受好友申请
	public JSONObject friendAccept(JSONObject data) throws JSONException{
		JSONObject result = new JSONObject();
		String toAccount = data.getString("toAccount");
		String fromAccount = data.getString("fromAccount");
		result.put("fromAccount", fromAccount);
		data.put("type", "accept");
		result.put("result", "success");
//		if(chatManager.isOnline(toAccount)){
//			chatManager.publish(data.toString(), toAccount);
//		}else{
//			OfflineInfoManager.add(toAccount, data);
//		}
		if(WebSocketManager.isExist(toAccount)){
			WebSocketManager.send(data.toString(), toAccount);
		}else{
			OfflineInfoManager.add(toAccount, data);
		}
		ContactsManager.add(toAccount, fromAccount);
		ContactsManager.add(fromAccount, toAccount);
		return result;
	}
	
	//发送消息
	public JSONObject send(JSONObject data){
		JSONObject result = new JSONObject();
		try {
			String from = data.getString("fromAccount");
			String to = data.getString("toAccount");
			String user_type = webChatRecordDao.findUserTypeById(to);			
			if(user_type.equals("机构")){
				//招聘端聊天 临时
				int flag = 0;
				data.put("user_id", from);
				data.put("org_id", to);
				data.put("chat_content", data.getString("msg"));
				data.put("is_read", "未读");	
				data.put("msg_type", "from");
				data.put("name", webChatRecordDao.findUser_nameById(from));
				data.put("msgCounters", webChatRecordDao.getMsgCounterByOrg_id(to)+1);//当前招聘端所有未读信息条数
				data.put("msgCounter", webChatRecordDao.getMsgCounterByUser_id(data)+1);//当前单个求职者与当前招聘端所有未读信息
				data.put("org_name", webChatRecordDao.findOrg_nameByOrg_id(to));
				flag = webChatRecordDao.insert(data);
				if(!webChatRecordDao.IsExist(data))//若是第一次聊天，则加入到求职端好友列表里头
				{
					webChatRecordDao.insertOrg_friend(data);
				}
				if(flag==1){
					if(WebSocketManager.isExist(to)){
						WebSocketManager.send(data.toString(), to);
					}else{
						OfflineInfoManager.add(to, data);
					}
				}
			}else{
				
			//通过to账号判断是发送给个人还是群
			if(Long.parseLong(to)>=9000000000L){
				data.put("type", "group_chat");
				//添加群名标记
				data.put("group", to);
				data.put("groupName", CFindService.getNameByAccount(to));
				List<String> members = getGroupMembers(to);
				for(String other : members){
					if(!other.equals(from)){
						//发给群里其他人
						data.put("toAccount", other);
						data.put("fromName", CFindService.getNameByAccount(from));
//						if(chatManager.isOnline(other)){
//							chatManager.publish(data.toString(), other);
//						}else{
//							JSONObject msg = new JSONObject(data.toString());
//							OfflineInfoManager.add(other, msg);
//						}
						if(WebSocketManager.isExist(other)){
							WebSocketManager.send(data.toString(), other);
						}else{
							JSONObject msg = new JSONObject(data.toString());
							OfflineInfoManager.add(other, msg);
						}
					}
				}
			}else{
				data.put("type", "chat");
//				if(chatManager.isOnline(to)){
//					chatManager.publish(data.toString(), to);
//				}else{
//					OfflineInfoManager.add(to, data);
//				}
				if(WebSocketManager.isExist(to)){
					WebSocketManager.send(data.toString(), to);
				}else{
					OfflineInfoManager.add(to, data);
				}
			}
			}
			result.put("result", "success");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	//邮箱验证码  update by lgp
	public JSONObject emailVerify(JSONObject data) throws Exception{
		JSONObject result = new JSONObject();
		if(data.has("flag")&&data.getString("flag").equals("confirm"))//验证前判断该邮箱是否为注册邮箱
		{
			String uid = "";
			JSONArray rdata = userDao.findUidByEmail(data);
			if(rdata.length()>0)
			{
				uid = rdata.getJSONObject(0).getString("uid");
			}
			if(uid.equals(data.getString("user_id")))
			{
				String email = data.getString("email");
				String captcha = sendEmail(email);
				if(captcha!=null){
					result.put("identify_check", captcha);
					result.put("result", "success");
				}else{
					result.put("result", "fail");
				}
			}else
			{
				result.put("result", "notExist");
			}
		}else
		{
			String email = data.getString("email");
			String captcha = sendEmail(email);
			if(captcha!=null){
				result.put("identify_check", captcha);
				result.put("result", "success");
			}else{
				result.put("result", "fail");
			}
		}
		return result;
	}
	
	public String sendEmail(String email){
		try{
			Properties p = new Properties();
			p.setProperty("mail.transport.protocol", "smtp");
			p.setProperty("mail.host", "smtp.qq.com");
			p.setProperty("mail.smtp.auth", "true");
//			p.setProperty("mail.debug", "true");
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			p.put("mail.smtp.ssl.enable", "true");
			p.put("mail.smtp.ssl.socketFactory", sf);
			Authenticator authenticator = new Authenticator(){
				@Override
				protected PasswordAuthentication getPasswordAuthentication(){
					return new PasswordAuthentication("653234217@qq.com","hocnwhipwcitbcie");
				}
			};
			Session sendMailSession = Session.getDefaultInstance(p, authenticator);
			Message mailMessage = new MimeMessage(sendMailSession);
			
			Random r = new Random();
			StringBuffer captcha1 = new StringBuffer();
			for(int i=0;i<4;i++){
				captcha1.append(r.nextInt(9)+"");
			}
			String captcha = new String(captcha1);
			System.out.println("I'm sending......");
			Address from = new InternetAddress("653234217@qq.com");
			mailMessage.setFrom(from);
			Address to = new InternetAddress(email);
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			mailMessage.setSubject("Joining - 邮箱验证码");
			String text = "亲爱的"+ email + ",我们收到您获取邮箱验证码的请求，本次您的验证码为：" + captcha;
			mailMessage.setText(text);
			Transport.send(mailMessage);
			return captcha;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	//修改个人信息
	public JSONObject modifyInfo(JSONObject data) throws Exception{
		JSONObject result = new JSONObject();
		String account = data.getString("account");
		String type = data.getString("type");
		String input = data.getString("input");
		switch(type){
			case "nickName":
				UserManager.modifyNickName(account, input);
				break;
			case "gender":
				UserManager.modifyGender(account, input);
				break;
			case "phone":
				UserManager.modifyPhone(account, input);
				break;
			case "introduce":
				UserManager.modifyIntroduce(account, input);
				break;
			case "password":
				UserManager.modifyPassword(account, input);
				break;
			default:
				break;
		}
		result.put("result", "success");
		return result;
	}
	
	//判断密码是否正确
	public JSONObject checkPassword(JSONObject data) throws Exception{
		JSONObject result = new JSONObject();
		String account = data.getString("account");
		String password = data.getString("password");
		String email = UserManager.getEmail(account);
		if(UserManager.checkPassword(email, password)){
			result.put("result", "success");
		}else{
			result.put("result", "fail");
		}
		return result;
	}
	
	//获取订阅分类
	public JSONObject getClassify(JSONObject data) throws Exception{
		JSONObject result = new JSONObject();
		MarketDao marketDao = new MarketDao();
		JSONObject classify = marketDao.getClassifyTree(data);
		result.put("classify", classify);
		result.put("result", "success");
		return result;
	}
	
	//插入订阅条件
	public JSONObject subscribe(JSONObject data) throws Exception{
		JSONObject result = new JSONObject();
		SubscribeMessageInfo subscribeInfo = new SubscribeMessageInfo();
		String id = data.getString("id");
		subscribeInfo.setUserID(id);
		String condition = data.getString("condition");
		String type2 = data.getString("type2");
		String event = data.getString("event");
		subscribeInfo.setOrderCondtion(condition);
		subscribeInfo.setSubscribeType2(type2);
		subscribeInfo.setEventType(event);
		MessageDao messageDao = new MessageDao();
		int flag = messageDao.insert(subscribeInfo);
		if(flag != 0){
			result.put("result", "success");
		}else{
			result.put("result", "fail");
		}
		return result;
	}
	
	//获取所有身份
	public JSONObject getRoles(JSONObject data) throws Exception{
		JSONObject result = new JSONObject();
		result.put("roles", userDao.allRole());
		result.put("result", "success");
		return result;
	}
	
	//添加身份审核
	public JSONObject addRoles(JSONObject data) throws Exception{
		JSONObject result = new JSONObject();
		String account = data.getString("account");
		String roles = data.getString("roles").trim();
		String[] role = roles.split(" ");
		result.put("result", "success");
		for(String r:role){
			int rid = roleToRid(r);
			int flag = userDao.insertUserRole(account, rid);
			if(flag<0){
				result.put("result", "fail");
				break;
			}
		}
		return result;
	}
	
	public int roleToRid(String role){
		int rid=1;
		switch (role) {
		case "普通用户":rid = 1;break;
		case "认证用户":rid = 2;break;
		case "学生":rid = 3;break;
		case "教师":rid = 4;break;
		case "店面商户":rid = 5;break;
		case "招聘商户":rid = 6;break;
		case "云制造商户":rid = 7;break;
		case "孵化器商户":rid = 8;break;
		default:
			break;
		}
		return rid;
	}
	
	public JSONObject sendFile(JSONObject data) throws Exception{
		JSONObject result = new JSONObject();
		String record = data.getString("voice");
		CommonUtil.GenerateFile(record, "F:\\voice.amr");
		result.put("result", "success");
		return result;
	}
	
	public JSONObject getServiceInfo(JSONObject data) throws Exception{
		JSONObject result = new JSONObject();
		String account = data.getString("account");
		userDao.findServiceInfo(account);
		return result;
	}
	
	public static String getNameByAccount(String account){
		return UserManager.getNickName(account);
	}
	
	public static List<JSONObject> getDetails(String[] accounts) throws JSONException{
		List<JSONObject> result = new ArrayList<>();
		for(int i=0;i<accounts.length;i++){
//			ServiceInfo serviceInfo = storageMap.get(accounts[i]);
			ServiceInfo serviceInfo = UserManager.getServiceInfoByAccount(accounts[i]);
			if(serviceInfo!=null){
				result.add(serviceInfo.toJSONObject());
			}
		}
		return result;
	}
	
	//获取指定单个用户serviceInfo
	public static JSONObject getDetail(String account) throws JSONException{
//		ServiceInfo serviceInfo = storageMap.get(account);
		ServiceInfo serviceInfo = UserManager.getServiceInfoByAccount(account);
		if(serviceInfo!=null){
			return serviceInfo.toJSONObject();
		}else{
			return null;
		}
	}
	
	//通过群账号获取群成员
	public List<String> getGroupMembers(String account){
		List<String> result = groupMembersMap.get(account);		
		return result;
	}
	/*
	//判断接收方是否在线
	public boolean isOnline(String to){
		ChatSocket chatSocket = ChatManager.getChatManager().getChatSocketByName(to);
		if(chatSocket != null){
			return true;
		}else{
			return false;
		}
	}
	
	//好友
	static class ContactsManager{
        private static final Map<String,Set<String>> storage = new HashMap<>();

        public static List<String> getContact(String account){
            Set<String> contact = storage.get(account);
            List<String> result = new ArrayList<>();
            if(contact != null){
                result.addAll(contact);
            }else{
            	result.add("104");
                result.add("105");
                result.add("90000");
            }
            return result;
        }

        public static void add(String account,String friend){
            Set<String> contact = storage.get(account);
            if(contact != null){
                contact.add(friend);
            }else{
                contact = new HashSet<>();
                contact.add(friend);
                contact.add("104");
                contact.add("105");
                contact.add("90000");
                storage.put(account,contact);
            }
        }

        public static void remove(String account,String friend){
            Set<String> contact = storage.get(account);
            if(contact != null){
                contact.remove(friend);
            }
        }
    }
	*/
}
