package ECP.dao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ECP.util.common.OfflineInfoManager;
import ECP.util.common.WebSocketManager;
import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;
import ECP.util.helper.MD5;

public class UserDao extends BaseDao{
	
	private MessageDao messageDao;
   
	public UserDao(){
		messageDao = new MessageDao();
	}
	
	public String login(JSONObject data){
		conn = DBConnect.getConn();
		String uid = "";
		try {
			ps = conn.prepareStatement("select uid from user_info where uid = ? and pass = ? and user_type=?");
			ps.setString(1, data.getString("user_id"));
			ps.setString(2, MD5.EncoderByMd5(data.getString("password")));
			ps.setString(3, data.getString("user_type"));
			rs = ps.executeQuery();
			if(rs.next()){
				uid = rs.getString(1);
			}
			if (uid.equals("")) {
				ps = conn.prepareStatement("select uid from user_info where mail = ? and pass = ? and user_type=?");
				ps.setString(1, data.getString("user_id"));
				ps.setString(2, MD5.EncoderByMd5(data.getString("password")));
				ps.setString(3, data.getString("user_type"));
				rs = ps.executeQuery();
				if(rs.next()){
					uid = rs.getString(1);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		
		return uid;
	}
	
	public JSONArray findOrgId(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select ui.nick_name,oi.org_id,oi.org_name from user_org uo,organization_info oi,"+
		         " user_info ui where ui.uid=uo.user_id and oi.org_id=uo.org_id and ui.uid=?");
			ps.setString(1, data.getString("user_id"));
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	public int register(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {				
			ps = conn.prepareStatement("insert into user_info(uid,nick_name,pass,user_type,mail,user_phone) values (?,?,?,?,? ,?)");
			ps.setString(1, data.getString("user_id"));
			ps.setString(2, data.has("nick_name")?data.getString("nick_name"):"");
			ps.setString(3, data.has("password")?MD5.EncoderByMd5(data.getString("password")):MD5.EncoderByMd5("123"));
			ps.setString(4, data.has("user_type")?data.getString("user_type"):"机构");
			ps.setString(5, data.has("newEmail")?data.getString("newEmail"):"");
			ps.setString(6, data.has("phone")?data.getString("phone"):"");
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public int updateUserInfo(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {	
			StringBuffer sql = new StringBuffer("update user_info set uid = '"+data.getString("user_id")+"'");
			if (data.has("newEmail")) {
				sql.append(" , mail = '"+data.getString("newEmail")+"' ");
			}
			if (data.has("newPhone")) {
				sql.append(" , user_phone = '"+data.getString("newPhone")+"' ");
			}
			sql.append(" where uid = '"+data.getString("user_id")+"'");
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public int updatePass(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {				
			ps = conn.prepareStatement("update user_info set pass = ? where pass = ? and uid =?");
			ps.setString(1, MD5.EncoderByMd5(data.getString("newPassword")));
			ps.setString(2, MD5.EncoderByMd5(data.getString("password")));
			ps.setString(3, data.getString("user_id"));
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public JSONArray findPassword(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		JSONArray rdata = new JSONArray();
		try {				
			ps = conn.prepareStatement("update user_info set pass = ? where mail = ? and user_type = ?");
			ps.setString(1, MD5.EncoderByMd5(data.getString("password")));
			ps.setString(2, data.getString("newEmail"));
			ps.setString(3, "机构");
			result = ps.executeUpdate();
			if(result>0)
			{
				ps = conn.prepareStatement("select uid from user_info where mail=? and user_type =?");
				ps.setString(1, data.getString("newEmail"));
				ps.setString(2, "机构");
				rs = ps.executeQuery();
				rdata = CDataTransform.rsToJson(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	public boolean IsExist(JSONObject data){	
		if(data.has("user_id")){
			try {
				conn = DBConnect.getConn();
				ps = conn.prepareStatement("SELECT uid from user_info where uid = ?");
				ps.setString(1, data.getString("user_id"));
				rs = ps.executeQuery();
				if(rs.next()){
					DBConnect.close(conn,ps,rs);
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("系统错误");
				e.printStackTrace();
			} finally{
				DBConnect.close(conn,ps,rs);
			}
		}
		if(data.has("newEmail")){
			try {
				conn = DBConnect.getConn();
				ps = conn.prepareStatement("SELECT uid from user_info where mail  = ?");
				ps.setString(1, data.getString("newEmail"));
				rs = ps.executeQuery();
				if(rs.next()){
					DBConnect.close(conn,ps,rs);
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("系统错误");
				e.printStackTrace();
			} finally{
				DBConnect.close(conn,ps,rs);
			}
		}
		return false;
	}
	
	//微信端登录
	public JSONObject wechatLogin(JSONObject data) throws Exception{
		JSONObject result = new JSONObject();
		String email=data.getString("email");
		String passwd=MD5.EncoderByMd5(data.getString("password"));
		//通过用户的邮箱字段判断用户的账号类型：账号（10位数字）account，邮箱（@符分割）email，手机号（11位数字）phone
		String accType=checkUserAccountType(email);
		//这里返回的是id。
		String user_code=checkUserInfo(email,passwd,accType);
		result.put("user_id", user_code);
		//设置用户不存在时返回-2，账号密码不对时返回-1；已经登录返回-3；
		if(user_code.equals("-2")){
			result.put("result", "fail");
			result.put("msg", "用户不存在");
			return result;
		}
		if(user_code.equals("-1")){
			result.put("result", "fail");
			result.put("msg", "用户名或者密码不对");
			return result;
		}
		result.put("result", "success");
		return result;
	}
	
	
	//gj:通用登录和注册的接口。
	public JSONObject commonLogin(JSONObject data) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		JSONObject result = new JSONObject();
		String email=data.getString("email");
		String passwd=MD5.EncoderByMd5(data.getString("password"));
		//通过用户的邮箱字段判断用户的账号类型：账号（10位数字）account，邮箱（@符分割）email，手机号（11位数字）phone
		String accType=checkUserAccountType(email);
		//这里返回的是id。
		String user_code=checkUserInfo(email,passwd,accType);
		System.out.println("step1:");
		if(WebSocketManager.isExist(user_code)){
			result.put("result", "fail");
			result.put("fail_detail", 3);
			return result;
		}
		//设置用户不存在时返回-2，账号密码不对时返回-1；已经登录返回-3；
		if(user_code.equals("-2")){
			result.put("result", "fail");
			result.put("fail_detail", 1);
			return result;
		}
		if(user_code.equals("-1")){
			result.put("result", "fail");
			result.put("fail_detail", 2);
			return result;
		}
		if(user_code.equals("-3")){
			result.put("result", "fail");
			result.put("fail_detail", 3);
			return result;
		}
		data.put("account", user_code);
		JSONArray userRoles = userRole(data);
		if(data.has("role")){
			String role = data.getString("role");
			boolean contains = false;
			for(int i=0;i<userRoles.length();i++){
				if(role.equals(userRoles.getJSONObject(i).getString("role_name"))){
					contains = true;
					break;
				}
			}
			if(!contains){
				result.put("result", "fail");
				result.put("fail_detail", 6);
			}
		}
		result.put("user_roles", userRoles);
		
		JSONArray mySubscribed = new JSONArray();
		JSONObject subscribed;
		JSONArray array = messageDao.getMsgOrderCondition(user_code);
		for(int i=0;i<array.length();i++){
			subscribed = new JSONObject();
			JSONObject object = array.getJSONObject(i);
			subscribed.put("sub",object.getString("attribute"));
			String[] orders = object.getString("order_condition").split("&&");
			String order_condition = "";
			for(String order : orders){
				String[] o = order.split(":");
				String value = "";
				if(o.length>1){
					value = o[1];
				}
				switch(o[0]){
					case "title":
						order_condition += ("商品名称:" + value);
						break;
					case "price":
						order_condition += ("价格:" + value);
						break;
					case "production":
						order_condition += ("产地:" + value);
						break;
					case "attention":
						order_condition += ("关注数:" + value);
						break;
					case "score":
						order_condition += ("评分:" + value);
						break;
					default:
						break;
				}
				order_condition += " ";
			}
			subscribed.put("order_condition",order_condition);
			subscribed.put("eventType", object.getString("eventType"));
			mySubscribed.put(subscribed);
		}
		result.put("mySub", mySubscribed);
		
		//查找用户所有的相关好友和群账号。
		List<String> accounts=findUserRelatedAccounts(user_code);
		accounts.add("104");
		accounts.add("105");
		accounts.add("106");
		//查找用户相关的所有好友和群信息。
		List<Object> userinfo=findUserRelatedAccountsInfo(user_code);
		JSONObject myInfo = findUserOwnInfo(user_code);
		result.put("result", "success");
		result.put("contacts", accounts);
		result.put("myInfo", myInfo);
		result.put("userInfo", userinfo);	
		return result;
	}

	public String checkUserAccountType(String email) {
		// TODO Auto-generated method stub
		//返回账号的类型：email，account，phone
		String result=null;
		Pattern pattern = Pattern.compile("[0-9]*"); 
		Matcher isNum = pattern.matcher(email);
		if(isNum.matches()){
		    if(email.length()==10){
		    	result="account";
		    	
		    }else{
		    	if(email.length()==11){
		    		result="phone";
		    	}else{
		    		result="error";
		    	}
		    }
		}else{
			result="email";
		}
		return result;
	}

	private JSONObject findUserOwnInfo(String user_code) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject result=new JSONObject();
		JSONArray sqlArray=new JSONArray();
		conn = DBConnect.getConn();		
		try {
			ps=conn.prepareStatement("SELECT id, uid AS account,user_sex AS gender,user_phone AS phone, mail AS email FROM user_info WHERE uid='"+user_code+"'");
			rs=ps.executeQuery();			
			sqlArray=CDataTransform.rsToJsonLabel(rs);
			result = sqlArray.getJSONObject(0);
//				System.out.println("***"+sqlArray.toString());
//				System.out.println("***"+sqlArray.getJSONObject(0).toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}

	private List<Object> findUserRelatedAccountsInfo(String user_code) throws JSONException {
		// TODO Auto-generated method stub
		List<Object> result=new ArrayList<>();
		List<String> friends=findUserFriends(user_code);
		List<String> group=findUserGroup(user_code);
		for(int i=0;i<friends.size();i++){
			String user_codetmp=friends.get(i);
//			System.out.println(user_codetmp);
			JSONObject obj=findUserInfo(user_codetmp,1);
			result.add(obj);
		}
		for(int i=0;i<group.size();i++){
			String user_codetmp=group.get(i);
//			System.out.println(user_codetmp);
			JSONObject obj=findUserInfo(user_codetmp,2);
			result.add(obj);
		}
		
		JSONObject tmp=new JSONObject();
		JSONObject tmp1=new JSONObject();
		JSONObject tmp2=new JSONObject();
		JSONObject tmp3 = new JSONObject();
		tmp = findUserInfo("104",1);
		tmp.put("tag", "服务");
		tmp.put("objectType", "friendApply");
		result.add(tmp);
		tmp1 = findUserInfo("105",1);
		tmp1.put("tag", "服务");
		tmp1.put("objectType", "systemInfo");
		result.add(tmp1);
		tmp2 = findUserInfo("106",1);
		tmp2.put("tag", "服务");
		tmp2.put("objectType", "systemInfo");
		result.add(tmp2);
		tmp3 = findUserInfo(user_code,1);
		result.add(tmp3);
		return result;
	}

	private JSONObject findUserInfo(String user_codetmp, int i) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject result=new JSONObject();
		JSONArray sqlArray=new JSONArray();
		
		if (i==1) {
			try {
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT id, uid AS account,nick_name AS nickname,picture AS image,"
						+ "case user_type when '设备' then '设备' else '好友' end AS tag,"+
						"NULL AS objectType,introduce FROM user_info WHERE uid='"+user_codetmp+"'");
				rs=ps.executeQuery();
				
				sqlArray=CDataTransform.rsToJsonLabel(rs);
				result = sqlArray.getJSONObject(0);
//				System.out.println("***"+sqlArray.toString());
//				System.out.println("***"+sqlArray.getJSONObject(0).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				DBConnect.close(conn,ps,rs);
			}
		}else{
			try {
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT groupID AS account,nick_name AS nickname,image,'群组' AS tag,"+
						"NULL AS objectType,introduce FROM group_table WHERE groupID='"+user_codetmp+"'");
				rs=ps.executeQuery();
				sqlArray=CDataTransform.rsToJsonLabel(rs);
				result = sqlArray.getJSONObject(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				DBConnect.close(conn,ps,rs);
			}
		}
		
		return result;
	}

	private List<String> findUserRelatedAccounts(String user_code) {
		// TODO Auto-generated method stub
		//返回用户相关的所有好友账号，群账号。群账号具有特殊编码，以9开头。
		
		List<String> friends=findUserFriends(user_code);
		List<String> group=findUserGroup(user_code);
		friends.addAll(group);
		return friends;
	}

	private List<String> findUserGroup(String user_code) {
		// TODO Auto-generated method stub
		List<String> result=new ArrayList<>();
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT groupid FROM groupmember_table WHERE user_code='"+user_code+"'");
			rs=ps.executeQuery();
			while(rs.next()){
//				System.out.println("结果是："+rs.getString(1));
				result.add(rs.getString(1));
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}

	private List<String> findUserFriends(String user_code) {
		// TODO Auto-generated method stub
		List<String> result=new ArrayList<>();
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT friendCode FROM friend WHERE userID='"+user_code+"'");
			rs=ps.executeQuery();
//			System.out.println(rs.toString());
//			boolean flag=rs.next();
			while(rs.next()){
//				System.out.println("flag:"+flag);
				String temp=rs.getString(1);
//				System.out.println(""+temp);
				result.add(temp);
//				flag=rs.next();
				
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public List<String> findAllUserByGroupID(String groupID) {
		List<String> result=new ArrayList<>();
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT user_code FROM groupmember_table WHERE groupid='"+groupID+"'");
			rs=ps.executeQuery();
			while(rs.next()){
//				System.out.println("结果是："+rs.getString(1));
				result.add(rs.getString(1));
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public String findGroupNameByGroupID(String groupID){
		String result=null;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT nick_name FROM group_table WHERE groupID='"+groupID+"'");
			rs=ps.executeQuery();
			if(rs.next()){
				result=rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	//通过账号类型查找账号ID
	public String findAccountByEmail(String account, String type){
		String result=null;
		conn = DBConnect.getConn();
		try {
			switch(type){
				case "email":
					ps=conn.prepareStatement("SELECT uid FROM user_info WHERE mail='"+account+"'");
					rs=ps.executeQuery();
					break;
				case "account":
					ps=conn.prepareStatement("SELECT uid FROM user_info WHERE uid='"+account+"'");
					rs=ps.executeQuery();
				break;
				case "phone":
					ps=conn.prepareStatement("SELECT uid FROM user_info WHERE user_phone='"+account+"'");
					rs=ps.executeQuery();
				break;
				default:
			break;
			}
			
			if(rs.next()){
				result=rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public String findUserNickNameByUserID(String UserAccount){
		String result=null;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT nick_name FROM user_info WHERE uid='"+UserAccount+"'");
			rs=ps.executeQuery();
			if(rs.next()){
				result=rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	private String checkUserInfo(String email, String passwd, String accType) {
		// TODO Auto-generated method stub
		//验证用户的账号，密码，返回用户ID。这里设置用户不存在时返回-2，账号密码不对时返回-1；已经登录返回-3；
		String result=null;
		System.out.println("accType"+accType);
		conn = DBConnect.getConn();
		try {
			switch(accType){
				case "email":
					ps=conn.prepareStatement("SELECT uid FROM user_info WHERE mail='"+email+"' AND pass='"+passwd+"'");
					rs=ps.executeQuery();					
					break;
				case "phone":
					ps=conn.prepareStatement("SELECT uid FROM user_info WHERE user_phone='"+email+"' AND pass='"+passwd+"'");
					rs=ps.executeQuery();	
					break;
				case "account":
					ps=conn.prepareStatement("SELECT uid FROM user_info WHERE uid='"+email+"' AND pass='"+passwd+"'");
					rs=ps.executeQuery();	
					break;
			}
			if(rs.next()){
				result=rs.getString(1);
			}else{
				result="-1";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "-1";
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("测试userDao中的小函数");		
		UserDao userDao = new UserDao();
		JSONObject result=new JSONObject();
		List<String> result1=null;
		JSONObject data=new JSONObject();
		data.put("email", "191977197@qq.com");
		data.put("password", "123");
//		result=userDao.findUserInfo("2", 1);
		result=userDao.commonLogin(data);
		String email="530417599@qq.com";
		String pass="123";
//		result=userDao.findUserInfo("10000",1);
		System.out.println(""+result.toString());
//		System.out.println(""+userDao.generateUserAccount());		
	}
	
	public JSONObject sendMessage(JSONObject data) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		JSONObject result = new JSONObject();
		String uid=data.getString("account");
		if(!isFindUserAccount(uid)){
			result.put("result", "fail");
			return result;
		}
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("INSERT INTO user_info(uid,mail,nick_name,pass,user_phone) VALUES(?,?,?,?,?)");
			ps.setString(1, data.getString("account"));
			ps.setString(2, data.getString("email"));
			ps.setString(3, data.getString("nickname"));
			ps.setString(4, MD5.EncoderByMd5(data.getString("password")));
			ps.setString(5, data.getString("phone_num"));
			ps.executeUpdate();
			result.put("result", "success");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	//用户的账号是否已注册，如果已注册，则返回真。
	private boolean isFindUserAccount(String uid) {
		// TODO Auto-generated method stub
		boolean result=false;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT uid FROM user_info WHERE uid='"+uid+"'");
			rs=ps.executeQuery();
			if(rs.next()){
				result=true;
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	//用户的邮箱是否已注册，如果已注册则返回真。
	public boolean isFindUserEmail(String email) {
		// TODO Auto-generated method stub
		boolean result=false;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT uid FROM user_info WHERE mail='"+email+"'");
			rs=ps.executeQuery();
			if(rs.next()){
				result=true;
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public JSONObject findFriend(JSONObject data) throws JSONException{
		JSONObject result = new JSONObject();
		String key=data.getString("key");
		JSONArray sqlArray=new JSONArray();
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT id as userID, uid AS account,nick_name AS nickname,picture AS image,"+
						" introduce FROM user_info WHERE (uid LIKE '%"+key+"%' OR nick_name LIKE '%"+key+"%' OR mail LIKE '%"+key+"%') "
								+ "and uid not in (104,105,106)");
			rs=ps.executeQuery();
			sqlArray=CDataTransform.rsToJsonLabel(rs);
			result.put("result", "success");
			result.put("detail", sqlArray);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		return result;
	}
	
	public JSONObject findServiceInfo(String account) throws JSONException{
		JSONObject result = new JSONObject();
		JSONArray sqlArray=new JSONArray();
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT uid AS account,nick_name AS nickname,picture AS image,"+
						" introduce FROM user_info WHERE uid = "+account);
			rs=ps.executeQuery();
			sqlArray=CDataTransform.rsToJsonLabel(rs);
			result.put("result", "success");
			result.put("detail", sqlArray);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public JSONObject applyFriend(JSONObject data) throws JSONException{
		JSONObject result = new JSONObject();
		String uid=data.getString("fromAccount");	
		String toAccount = data.getString("toAccount");
		data.put("type", "apply");
		data.put("serviceInfo", findUserInfo(uid,1));
		int msgType=1;
		try {
			if(!WebSocketManager.isExist(toAccount)){
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("INSERT INTO offline_message(senderID,receiverID,msgType) VALUES(?,?,?)");
				ps.setString(1, data.getString("fromAccount"));
				ps.setString(2, toAccount);
				ps.setInt(3, msgType);
				ps.executeUpdate();
				OfflineInfoManager.add(toAccount, data);
				result.put("result", "success");
			}else{
				WebSocketManager.send(data.toString(), toAccount);
				result.put("result", "success");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	private boolean isOnLine(String uid) {
		// TODO Auto-generated method stub
		boolean result=false;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT user_state FROM user_info WHERE uid='"+uid+"'");
			rs=ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)==1){
					result=true;
				}
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}

	public JSONObject friendApplyAccept(JSONObject data) throws JSONException{
		JSONObject result = new JSONObject();
		String uid=data.getString("toAccount");
		int msgType=2;
		conn = DBConnect.getConn();
		data.put("type", "accept");
		try {
			if(!WebSocketManager.isExist(uid)){
				//如果不在线的话，向朋友关系表中插入数据，再向离线消息表中插入数据。
				ps=conn.prepareStatement("INSERT INTO friend(userID,friendCode) VALUES(?,?)");
				ps.setString(1, data.getString("fromAccount"));
				ps.setString(2, data.getString("toAccount"));				
				ps.executeUpdate();
				ps=conn.prepareStatement("INSERT INTO friend(userID,friendCode) VALUES(?,?)");
				ps.setString(1, data.getString("toAccount"));
				ps.setString(2, data.getString("fromAccount"));				
				ps.executeUpdate();
				
				ps=conn.prepareStatement("INSERT INTO offline_message(senderID,receiverID,msgType) VALUES(?,?,?)");
				ps.setString(1, data.getString("fromAccount"));
				ps.setString(2, data.getString("toAccount"));
				ps.setInt(3, msgType);
				ps.executeUpdate();
				OfflineInfoManager.add(uid, data);
				result.put("result", "success");
			}else{
				ps=conn.prepareStatement("INSERT INTO friend(userID,friendCode) VALUES(?,?)");
				ps.setString(1, data.getString("fromAccount"));
				ps.setString(2, data.getString("toAccount"));				
				ps.executeUpdate();
				ps=conn.prepareStatement("INSERT INTO friend(userID,friendCode) VALUES(?,?)");
				ps.setString(1, data.getString("toAccount"));
				ps.setString(2, data.getString("fromAccount"));				
				ps.executeUpdate();
				WebSocketManager.send(data.toString(), uid);
				result.put("result", "success");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public JSONObject getMailRegister(JSONObject data) throws Exception{
		JSONObject result = new JSONObject();
		String email = data.getString("email");
		if(isFindUserEmail(email)){
			result.put("result", "fail");
		}else{
			result.put("accounts", generateUserAccount());
			result.put("result", "success");
		}
		return result;
	}
	
	public JSONObject getRegister(JSONObject data) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		JSONObject result = new JSONObject();
		if(isFindUserEmail(data.getString("email"))){
			result.put("result", "fail");
			result.put("msg", "该邮箱/手机号已被注册");
		}else{
			String uid=data.getString("account");	//这里的问题是如何添加特殊用户。发送消息的账号和群账号。	
			conn = DBConnect.getConn();
			try {
				ps=conn.prepareStatement("INSERT INTO user_info(uid,mail,nick_name,pass,picture) VALUES(?,?,?,?,?)");
				ps.setString(1, uid);
				ps.setString(2, data.getString("email"));
				ps.setString(3, data.getString("nickname"));
				ps.setString(4, MD5.EncoderByMd5(data.getString("password")));
				ps.setString(5, "http://on3x7yjly.bkt.clouddn.com/default.png");
				ps.executeUpdate();
				ps=conn.prepareStatement("INSERT INTO user_role(uid,rid) VALUES(?,1)");
				ps.setString(1, uid);
				ps.executeUpdate();
				result.put("result", "success");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result.put("result", "fail");
				DBConnect.close(conn,ps,rs);
				return result;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				DBConnect.close(conn,ps,rs);
			}
		}
		
		return result;
	}

	//确认密码
	public JSONObject confirmPassword(JSONObject data){
		JSONObject result = new JSONObject();
		try{
			result.put("result", "fail");
			String email = data.getString("email");
			String passwd = MD5.EncoderByMd5(data.getString("password"));
			String accType= findTypeByAccount(email);
			String user_code=checkUserInfo(email,passwd, accType);
			//设置用户不存在时返回-2，账号密码不对时返回-1；已经登录返回-3；
			if(!user_code.equals("-1") && !user_code.equals("-2") && !user_code.equals("-3")){
				result.put("result", "success");
			}
		} catch (Exception e){
			e.printStackTrace();
			return result;
		}
		return result;
	}
	
	private String findTypeByAccount(String email) {
		// TODO Auto-generated method stub
		String result ="email";
		Pattern pattern = Pattern.compile("[0-9]*"); 
		Matcher isNum = pattern.matcher(email);
		if(email.length()==10&&isNum.matches()){
			result="uid";
		}else if (email.length()==11&&isNum.matches()) {
			result="phone";
		}
		return result;
	}

	public JSONObject modifyInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			result.put("result", "fail");
			String type = data.getString("type");
			String input = data.getString("input");
			String email = data.getString("email");
			if(type.equals("password")){
				JSONObject object = new JSONObject();
				object.put("email", email);
				object.put("password", input);
				if(confirmPassword(object).getString("result").equals("success")){
					result.put("fail_code", 1);
					return result;
				}
			}
			conn = DBConnect.getConn();
			switch(type){
				case "nickName":
					ps=conn.prepareStatement("update user_info set nick_name ='" + input + "' where mail='"+email+"'");
					break;
				case "gender":
					int gender = 0;
					if(input.equals("男")){
						gender = 1;
					}
					ps=conn.prepareStatement("update user_info set user_sex =" + gender + " where mail='"+email+"'");
					break;
				case "phone":
					ps=conn.prepareStatement("update user_info set user_phone ='" + input + "' where mail='"+email+"'");
					break;
				case "introduce":
					ps=conn.prepareStatement("update user_info set introduce ='" + input + "' where mail='"+email+"'");
					break;
				case "password":
					ps=conn.prepareStatement("update user_info set pass ='" + MD5.EncoderByMd5(input) + "' where mail='"+email+"'");
					break;
				default:
					break;
			}
			ps.executeUpdate();
			result.put("result", "success");
		} catch (Exception e){
			e.printStackTrace();
			DBConnect.close(conn,ps,rs);
			return result;
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public List<String> generateUserAccount(){
		//1次生成15个备选账号。
		List<String> resList=new ArrayList<>();
		Set<String> result=new HashSet<String>();
		Long tempAcc=generateOneCandidate();
		int count=0;
		while(result.size()<15){
			if(!isFindUserAccount(tempAcc.toString())){
				result.add(tempAcc.toString());
				tempAcc=generateOneCandidate();
				count=0;
			}else{
				tempAcc=generateOneCandidate();
				count=count+1;
				if(count>5){
					tempAcc=(tempAcc%100000007L)*10;
				}
				if(count==7){
					System.out.print("随机数生成算法出现问题，需要调整");
				}
			}
		}
		resList.addAll(result);
		return resList;
	}

	private Long generateOneCandidate() {
		// TODO Auto-generated method stub
		//现在是用系统时间生成账号，估计账号在几万个以内时不会出现问题。数量大的时候会比较麻烦。
		Long result=System.currentTimeMillis()%9000000000L;
		if(result>1000000000L){
			return result;
		}else{
			return result+1000000000L;
		}		
	}
	
//	用户身份查询，判断用户是否具有身份，有则返回角色id，否则返回-1.
	public int isUserHasRole(String uid,String role){
		int result=-1;
		conn = DBConnect.getConn();		
		try {
			ps=conn.prepareStatement("SELECT id FROM user_info ui JOIN user_role ur ON ui.uid=ur.uid JOIN role r ON ur.rid=r.rid "+ 
					"WHERE ui.uid='"+uid+"' AND ur.flag=0 and r.role_name='"+role+"'");
			rs=ps.executeQuery();			
			if(rs.next()){
				result=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
//查找系统中所有可用的身份。
	public JSONArray allRole(){		
		JSONArray sqlData=new JSONArray();				
		try{
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT role_name FROM role r WHERE r.status=0");
			rs=ps.executeQuery();
			sqlData= CDataTransform.rsToJsonLabel(rs);			
		}catch(Exception e){			
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		return sqlData;
	}
//查找一个用户所拥有的身份。输入用户的account（字符串，user_info表中的uid字段），返回该用户拥有的角色名称
	public JSONArray userRole(JSONObject data){		
		JSONArray sqlData=new JSONArray();				
		try{
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT role_name FROM user_info u JOIN user_role ur " +
					"ON u.uid=ur.uid JOIN role r ON ur.rid=r.rid WHERE u.uid=? and ur.flag=0 ");
			ps.setString(1, data.getString("account"));
			rs=ps.executeQuery();
			sqlData= CDataTransform.rsToJsonLabel(rs);			
		}catch(Exception e){			
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		return sqlData;
	}
//	用户身份插入，将用户申请的身份信息插入数据库。默认每个注册用户都具有普通用户身份。成功返回1，否则返回-1.
	public int insertUserRole(String uid,int roleID){
		int result=-1;
		conn = DBConnect.getConn();		
		try {
			ps=conn.prepareStatement("INSERT INTO user_audit(user_id,oper_type,oper_user_name,user_name,identity,idCard,role_type,role_limits)"+
					" SELECT u.uid,1,u.nick_name,u.nick_name,u.identity,NULL,r.rid,r.comment FROM user_info u "+
					" ,role r  WHERE u.uid=? AND r.rid=? ");
			ps.setString(1, uid);
			ps.setInt(2, roleID);
			ps.executeUpdate();			
			result=1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
/*	角色和授权及相关实现说明
 * 一个账号可以有多个角色，为了便于实现，第一个注册账号的用户（假设为A）具有最高权限，A登录后可以继续创建角色，分配相应的权限，设置密码。
 * 权限表示为允许的请求（EIO通信中的op字段值）；每个权限的含义写在permission表中。
 * 每个角色具有的权限写在role_permission表中 ，用户A分配的角色及权限会记录A的账号值。
 * 用户首次注册时，默认为普通用户，登录后补充资料，申请新的身份（身份和角色是两个概念，身份相当于模块）
 * 用户登录后服务器返回：当前身份具有的权限，好友列表，推送消息，授权账号列表等信息。
 * 用户点击授权账号，进入授权用户的操作界面，相当于二次登录，服务端返回授权账号所分配的权限。
 * 所有权限判断在前端实现，目前设想为，根据服务端的权限返回值构造一个函数，在每次点击操作前判断是否可以进行操作，返回真或假。
 * 同一账号的不同角色通过密码确定要登录的角色，或者在登录信息输入后弹出一个角色选择列表进行二次选择。
 * 
 * 目前设计的接口操作为：
 * 添加角色 ：账号A设置角色r1名称、默认密码、权限。
 * 设置角色密码：（输入 账号id，角色id）
 * 修改角色密码：账号A，超级管理员，角色r1修改密码。
 * 设置角色权限
 * 修改角色权限：账号A修改角色r1对应的权限。
 * 对账号B授权：账号A设置授权对象B，权限，时限。
 * 获取授权信息：账号B登录后获取授权的账号列表。/。返回账号A向外授权的账号列表和相关信息。
 * 服务端的权限操作判断：输入账号，授权账号，角色id，密码，操作对象，操作；判断操作能否执行。
 * */
	
//	授权信息查询，查询用户的授权信息，如果有记录则在规定时间段内具有被授权的身份，可以对授权账号的相应模块进行操作。
//	用前面判断用户是否具有某一身份的函数就可以了。
	/*添加角色，输入角色名称，默认密码，权限列表，输出成功或失败。
	 * 操作过程：新建角色，设置角色权限，设置密码，返回操作状态。
	 * */
//	增加角色，返回角色的rid
	public int addRole(String roleName,String module){
		int result=0;
		conn = DBConnect.getConn();		
		try {
			ps=conn.prepareStatement("insert into role(role_name,moduler) values(?,?)");
			ps.setString(1, roleName);
			ps.setString(2, module);			
			ps.executeUpdate();	
			rs=ps.getGeneratedKeys();
			if (rs.next()) {				
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
//	返回值为0时表示操作失败。
	public int addRoleToUser(String uid,int rid){
		int result=0;
		conn = DBConnect.getConn();		
		try {
			ps=conn.prepareStatement("insert into user_role(uid,rid) values(?,?)");
			ps.setString(1, uid);
			ps.setInt(2, rid);			
			result=ps.executeUpdate();				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	public List<Integer> findUserRoleInfo(String uid) {
		List<Integer> result=new ArrayList<Integer>();
		conn = DBConnect.getConn();		
		try {
			ps=conn.prepareStatement("select rid from user_role where uid=? and flag=0 ");
			ps.setString(1, uid);	
			rs=ps.executeQuery();
			while(rs.next()){
				result.add(rs.getInt("rid"));
			}						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
//	返回值为零时表示操作失败
	public int deleteRoleOfUser(String uid,int rid){
		int result=0;
		conn = DBConnect.getConn();		
		try {
			ps=conn.prepareStatement("update user_role set flag=1 where uid=? and rid=?");
			ps.setString(1, uid);
			ps.setInt(2, rid);			
			result=ps.executeUpdate();				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
//	返回值为0时操作失败；返回插入数据的id。
	public int setRolePermission(int rid,String permission,String module) {
		int result=0;
		conn = DBConnect.getConn();		
		try {
			ps=conn.prepareStatement("insert into role_permission(rid,subsystem,module) values(?,?,?)");
			ps.setInt(1, rid);
			ps.setString(2, permission);
			ps.setString(3, module);
			ps.executeUpdate();
			rs=ps.getGeneratedKeys();
			if (rs.next()) {				
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
//	返回值为零时操作失败
	public int deleteRolePermission(int rid,String permission,String module){
		int result=0;
		conn = DBConnect.getConn();		
		try {
			ps=conn.prepareStatement("delete from role_permission where rid=? and subsystem=? and module=? ");
			ps.setInt(1, rid);
			ps.setString(2, permission);
			ps.setString(3, module);
			result=ps.executeUpdate();				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
//	这里的表示方法貌似不大好，把所有模块的权限都返回太多了；或者规定每个角色只能对应一个模块。
	public List<String> findPermissionOfRole(int rid){
		List<String> result=new ArrayList<String>();
		conn = DBConnect.getConn();		
		try {
			ps=conn.prepareStatement("select subsystem as permission,module from role_permission where rid=?");
			ps.setInt(1, rid);	
			rs=ps.executeQuery();
			while(rs.next()){
				result.add(rs.getString("permission"));
			}						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
//	如果用户具有角色r，则返回1.
	public int isUserAuthRole(String uid,int rid){
		int result=-1;
		conn = DBConnect.getConn();		
		try {
			ps=conn.prepareStatement("select rid from user_role where rid=? and uid=? and flag=0 ");
			ps.setInt(1, rid);	
			ps.setString(2, uid);
			rs=ps.executeQuery();
			while(rs.next()){
				result=1;
			}						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
//	授权信息插入，将用户的授权信息插入数据库。需要判断用户是否已具有改身份.authID为被授权用户的account。
//	expireTime为授权的有效期，默认为从当前时间开始的30天。输入数据为小时数。
	public int insertUserAuthRole(String uid,String authID,int roleID,int expireTime){
		int result=-1;
		conn = DBConnect.getConn();		
		try {
			ps=conn.prepareStatement("insert into user_role(uid,identity,rid,exprie_date) values(?,?,?,?)");
			ps.setString(1, authID);
			ps.setString(2, uid);
			ps.setInt(3, roleID);
			ps.setInt(4,expireTime);
			ps.executeUpdate();			
			result=1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
//	授权信息删除，删除用户的授权信息，取消对用户的授权。
	public int deleteUserAuthRole(String uid,String authID,int roleID){
		int result=-1;
		conn = DBConnect.getConn();		
		try {
			ps=conn.prepareStatement("update user_role set flag=1 where uid='"+authID+"' and identity='"+uid+"' and rid="+roleID);		
			ps.executeUpdate();			
			result=1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	public List<String> userAuthList(String uid){
		List<String> result=new ArrayList<String>();
		conn = DBConnect.getConn();		
		try {
			ps=conn.prepareStatement("select identity from user_role where uid=? and flag=0");
			ps.setString(1, uid);	
			rs=ps.executeQuery();
			while(rs.next()){
				result.add(rs.getString("identity"));
			}						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	//根据邮箱查找用户uid，用户确定当前邮箱是否属于该用户
	public JSONArray findUidByEmail(JSONObject data) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {							
				ps = conn.prepareStatement("select uid from user_info where mail=? and user_type =?");
				ps.setString(1, data.getString("email"));
				ps.setString(2, "机构");
				rs = ps.executeQuery();
				rdata = CDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}

	public JSONObject getUserInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			String user_id=data.getString("user_id");
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT ui.uid AS user_id,nick_name AS user_name,r.role_name AS role_type,r.COMMENT AS role_limits"+
					" FROM user_info ui JOIN user_role ur ON ui.uid=ur.uid JOIN role r ON ur.rid=r.rid WHERE ur.flag=0 and ui.uid="+user_id);
			rs=ps.executeQuery();
			sqlData= CDataTransform.rsToJsonLabel(rs);
			tip = "信息获取成功";				
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	public JSONObject userPasswordAlter(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			String user_id=data.getString("user_id");
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("update user_info set password='"+
					MD5.EncoderByMd5(data.getString("new_password"))+"' WHERE uid="+user_id);
			ps.executeUpdate();
//			sqlData= CDataTransform.rsToJsonLabel(rs);
			tip = "信息获取成功";				
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	public JSONObject userEmailAlter(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			String user_id=data.getString("user_id");
			if(!findAccountByEmail(data.getString("new_email"), "email").isEmpty()){
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("update user_info set mail='"+
						data.getString("new_email")+"' WHERE uid="+user_id);
				ps.executeUpdate();
	//			sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";
			}else{
				flag=1;
				tip="邮箱已被使用";
			}
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
}
