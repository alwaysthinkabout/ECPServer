package ECP.util.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserManager {
	private static final Map<String, UserInfo> storage = new HashMap<>();
	private static final Map<String, String> accountToEmail = new HashMap<>();
	private static String fileServer = "http://on3x7yjly.bkt.clouddn.com/";
	private static int i = 10000;
	
	static{
		String email = "104";
		String account = "";
		storage.put(email, new UserInfo(email,"123","123", "", new ServiceInfo("104","好友验证消息",fileServer+"SystemInfo.jpg","服务","friendApply",ServiceInfo.WT_JOINT,"","")));
		accountToEmail.put("104", email);
		
		email = "105";
		storage.put(email, new UserInfo(email,"123","123", "", new ServiceInfo("105","系统消息",fileServer+"SystemInfo.jpg","服务","systemInfo",ServiceInfo.WT_JOINT,"","")));
		accountToEmail.put("105", email);
		
		email = "106";
		storage.put(email, new UserInfo(email,"123","123", "", new ServiceInfo("106","推送消息",fileServer+"SystemInfo.jpg","服务","systemInfo",ServiceInfo.WT_JOINT,"","")));
		accountToEmail.put("106", email);
		
		email = "90000";
		storage.put(email, new UserInfo(email,"123","123", "", new ServiceInfo("90000","group",fileServer+"group.jpg","群组","",ServiceInfo.WT_JOINT,"","我是公共群组")));
		accountToEmail.put("90000", email);
		
		email = "530417599@qq.com";
		account = String.valueOf(i++);
		storage.put(email, new UserInfo(email,"123","123", "男", new ServiceInfo(account,"吴广德",fileServer+"10000.jpg","好友","",ServiceInfo.WT_JOINT,"","我是吴广德")));
		accountToEmail.put(account, email);
		
		email = "1153769995@qq.com";
		account = String.valueOf(i++);
		storage.put(email, new UserInfo(email,"123","123", "男", new ServiceInfo(account,"周胡健",fileServer+"10001.jpg","好友","",ServiceInfo.WT_JOINT,"","我是周胡健")));
		accountToEmail.put(account, email);
		
		email = "1194510813@qq.com";
		account = String.valueOf(i++);
		storage.put(email, new UserInfo(email,"123","123", "男",new ServiceInfo(account,"欧康贵",fileServer+"10002.jpg","好友","",ServiceInfo.WT_JOINT,"","我是欧康贵")));
		accountToEmail.put(account, email);
		
		email = "191977197@qq.com";
		account = String.valueOf(i++);
		storage.put(email, new UserInfo(email,"123","123", "男",new ServiceInfo(account,"郭靖",fileServer+"10002.jpg","好友","",ServiceInfo.WT_JOINT,"","我是郭靖")));
		accountToEmail.put(account, email);
		
		email = "446008457@qq.com";
		account = String.valueOf(i++);
		storage.put(email, new UserInfo(email,"123","123", "男",new ServiceInfo(account,"杜任仕",fileServer+"10002.jpg","好友","",ServiceInfo.WT_JOINT,"","我是杜任仕")));
		accountToEmail.put(account, email);
		
		email = "188168100@qq.com";
		account = String.valueOf(i++);
		storage.put(email, new UserInfo(email,"123","123", "男",new ServiceInfo(account,"彭瀛",fileServer+"10002.jpg","好友","",ServiceInfo.WT_JOINT,"","我是彭瀛")));
		accountToEmail.put(account, email);
		
		email = "810556163@qq.com";
		account = String.valueOf(i++);
		storage.put(email, new UserInfo(email,"123","123", "女", new ServiceInfo(account,"邹敏艳",fileServer+"10002.jpg","好友","",ServiceInfo.WT_JOINT,"","我是邹敏艳")));
		accountToEmail.put(account, email);
		
		email = "761780979@qq.com";
		account = String.valueOf(i++);
		storage.put(email, new UserInfo(email,"123","123", "男",new ServiceInfo(account,"彭雄冠",fileServer+"10002.jpg","好友","",ServiceInfo.WT_JOINT,"","我是彭雄冠")));
		accountToEmail.put(account, email);
		
		email = "734114646@qq.com";
		account = String.valueOf(i++);
		storage.put(email, new UserInfo(email,"123","123", "男",new ServiceInfo(account,"李万军",fileServer+"10002.jpg","好友","",ServiceInfo.WT_JOINT,"","我是李万军")));
		accountToEmail.put(account, email);
		
		email = "1965258618@qq.com";
		account = String.valueOf(i++);
		storage.put(email, new UserInfo(email,"123","123", "男",new ServiceInfo(account,"刘广鹏",fileServer+"10002.jpg","好友","",ServiceInfo.WT_JOINT,"","我是刘广鹏")));
		accountToEmail.put(account, email);
		
		email = "751197582@qq.com";
		account = String.valueOf(i++);
		storage.put(email, new UserInfo(email,"123","123", "男",new ServiceInfo(account,"彭伟华",fileServer+"10002.jpg","好友","",ServiceInfo.WT_JOINT,"","我是彭伟华")));
		accountToEmail.put(account, email);
		
		email = "545690162@qq.com";
		account = String.valueOf(i++);
		storage.put(email, new UserInfo(email,"123","123", "男",new ServiceInfo(account,"彭曦",fileServer+"10002.jpg","好友","",ServiceInfo.WT_JOINT,"","我是彭曦")));
		accountToEmail.put(account, email);
		
		email = "522124083@qq.com";
		account = String.valueOf(i++);
		storage.put(email, new UserInfo(email,"123","123", "男",new ServiceInfo(account,"刘良洁",fileServer+"10002.jpg","好友","",ServiceInfo.WT_JOINT,"","我是刘良洁")));
		accountToEmail.put(account, email);
	}
	
	//添加用户
	public static void addUser(String email, String phone, String nickname, String gender, String password){
		String account = String.valueOf(i++);
		ServiceInfo serviceInfo = new ServiceInfo(account, nickname, fileServer+"default.png", "好友", "", ServiceInfo.WT_JOINT, "", "");
		UserInfo userInfo = new UserInfo(email, phone, password, gender, serviceInfo);
		storage.put(email, userInfo);
		accountToEmail.put(account, email);
	}
	
	//通过账号获取昵称
	public static String getNickName(String account){
		return storage.get(accountToEmail.get(account)).getServiceInfo().getNickname();
	}
	
	//通过账号获取性别
	public static String getGender(String account){
		return storage.get(accountToEmail.get(account)).getGender();
	}
	
	//通过账号获取电话
	public static String getPhone(String account){
		return storage.get(accountToEmail.get(account)).getPhone();
	}
	
	//通过账号获取email
	public static String getEmail(String account){
		return accountToEmail.get(account);
	}
	
	//根据邮箱修改密码
	public static boolean changePassword(String email, String password){
		if(storage.containsKey(email)){
			storage.get(email).setPassword(password);
			return true;
		}else{
			return false;
		}
	}
	
	//判断密码是否正确
	public static boolean checkPassword(String email, String password){
		String password_check = storage.get(email).getPassword();
		if(password.equals(password_check)){
			return true;
		}else{
			return false;
		}
	}
	
	//判断用户是否存在
	public static boolean hasUser(String email){
		return storage.containsKey(email);
	}
	
	//根据邮箱获取账号
	public static String getAccount(String email){
		return storage.get(email).getServiceInfo().getAccount();
	}
	
	//根据账号获取用户信息
	public static ServiceInfo getServiceInfoByAccount(String account){
		return storage.get(accountToEmail.get(account)).getServiceInfo();
	}
	
	//根据账号修改昵称
	public static void modifyNickName(String account, String nickName){
		UserInfo userInfo = storage.get(accountToEmail.get(account));
		ServiceInfo serviceInfo = userInfo.getServiceInfo();
		serviceInfo.setNickname(nickName);
	}
	
	//根据账号修改昵称
	public static void modifyGender(String account, String gender){
		storage.get(accountToEmail.get(account)).setGender(gender);
	}
	
	//根据账号修改昵称
	public static void modifyPhone(String account, String phone){
		storage.get(accountToEmail.get(account)).setPhone(phone);
	}
	
	//根据账号修改昵称
	public static void modifyIntroduce(String account, String introduce){
		storage.get(accountToEmail.get(account)).getServiceInfo().setIntroduce(introduce);
	}
	
	//根据账号修改昵称
	public static void modifyPassword(String account, String password){
		storage.get(accountToEmail.get(account)).setPassword(password);
	}
	
	public static List<String> getAllUsers(){
		List<String> users = new ArrayList<>();
		users.addAll(accountToEmail.keySet());
		return users;
	}

}
