package ECP.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.org.apache.bcel.internal.generic.NEW;

import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

public class MarketDao extends BaseDao{
//返回一个JSON格式的字符串，三个关键字：flag：表明操作是否成功；tip：表明失败原因；data：表明返回的数据。
	SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public JSONObject getDeliveryAddress(JSONObject data) {
		// TODO Auto-generated method stub		
		
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");
				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("select id,name,phone,delivery_address as address, isDefault " +
							"from deliveryaddress where user_id="+user_id);
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip = "信息获取成功";
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
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

	public JSONObject getUserData(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");
				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。					
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("select id as userId,user_name as userAccount,nick_name as userName," +
							"user_sex as userSex,user_phone as userPhone,mail as userEmail from user_info where id="+user_id);
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="用户查询成功";
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
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

	public JSONObject updateDeliveryAddress(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("id")){
				int id = data.getInt("id");
				String name = data.getString("name");
//				int phone = Integer.parseInt(data.getString("phone").trim());
				String phoneStr = data.getString("phone");
				Long phone = Long.parseLong(phoneStr);
				String address = data.getString("address");
				int isDefault = data.getInt("isDefault");
				int user_id = data.getInt("userId");
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(isDefault==1){
					unDefaultUserAddressByID(user_id);
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("update deliveryaddress set name ='"+name+"',phone="+phone+"," +
							"delivery_address='"+address+",isDefault="+isDefault+" where id="+id);
					ps.executeUpdate();
					tip="数据更新成功";
				}else{
//					StringBuilder sql = new StringBuilder("update deliveryaddress set name ='"+name+"',phone='"+phone+"'," +
//							"delivery_address='"+address+"',isDefault="+isDefault+" where id="+id);
//					System.out.println("sql:"+sql.toString());
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("update deliveryaddress set name ='"+name+"',phone='"+phone+"'," +
							"delivery_address='"+address+"',isDefault="+isDefault+" where id="+id);
					ps.executeUpdate();
					
					tip="数据更新成功";
				}
//				sqlData= CDataTransform.rsToJson(rs);
				
			}else{
				flag=1;
				tip="传入参数错误";
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

	public JSONObject addDeliveryAddress(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");
				int isDefault = data.getInt("isDefault");
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					if(isDefault==1){
						unDefaultUserAddressByID(user_id);
						conn = DBConnect.getConn();						
						ps=conn.prepareStatement("insert into deliveryaddress(user_id,name,phone,delivery_address,isDefault) values(?,?,?,?,?) ");
						ps.setInt(1, user_id);
						ps.setString(2, data.getString("name"));
						ps.setLong(3, data.getLong("phone"));
						ps.setString(4, data.getString("address"));
						ps.setInt(5, data.getInt("isDefault"));
						ps.executeUpdate();
						tip="数据插入成功";
					}else{
						conn = DBConnect.getConn();
						ps=conn.prepareStatement("insert into deliveryaddress(user_id,name,phone,delivery_address,isDefault) values(?,?,?,?,?) ");
						ps.setInt(1, user_id);
						ps.setString(2, data.getString("name"));
						ps.setLong(3, data.getLong("phone"));
						ps.setString(4, data.getString("address"));
						ps.setInt(5, data.getInt("isDefault"));
						ps.executeUpdate();
						tip="数据插入成功";
					}
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
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

	public JSONObject delDeliveryAddress(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("id")){
				int id = data.getInt("id");
				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("delete from deliveryaddress where id="+id);
				ps.executeUpdate();
				tip="数据删除成功";
//				sqlData= CDataTransform.rsToJson(rs);
			
			}else{
				flag=1;
				tip="传入参数名称错误";
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

	public JSONObject updateDefAddress(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("newDefaultAddressId")&&data.has("userId")){
//				int oldid = data.getInt("oldDefaultAddressId");
				int newid = data.getInt("newDefaultAddressId");
				int user_id = data.getInt("userId");
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				unDefaultUserAddressByID(user_id);
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("update deliveryaddress set isDefault = 1 where id="+newid);
				ps.executeUpdate();
//				ps=conn.prepareStatement("update deliveryaddress set isDefault = 0 where id="+oldid);
//				ps.executeUpdate();
//				sqlData= CDataTransform.rsToJson(rs);	
				tip="默认地址更新成功";
			}else{
				flag=1;
				tip="参数数量错误";
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

	public JSONObject updateUserData(JSONObject data) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
					String nick_name = data.getString("userName");
					int user_sex = data.getInt("userSex");
					Long user_phone = data.getLong("userPhone");
					String mail = data.getString("userEmail");
					conn = DBConnect.getConn();
					StringBuilder sqlBuilder=new StringBuilder("update user_info set nick_name='"+nick_name+"',user_sex="+user_sex +
							",user_phone="+user_phone+",mail='"+mail+"' where id="+user_id);
					System.out.println(sqlBuilder.toString());
					ps=conn.prepareStatement(sqlBuilder.toString());					
					ps.executeUpdate();
//					sqlData= CDataTransform.rsToJson(rs);
					tip="信息更新成功";
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		result.put("flag", flag);
		result.put("tip", tip);
		result.put("data", sqlData);		
		
		return result;
	}
	public boolean findUserByID(int user_id){
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("select id from user_info where id="+user_id);
			rs=ps.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		return false;		
	}
	public void unDefaultUserAddressByID(int user_id){
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("update deliveryaddress set isDefault=0 where user_id="+user_id);
			ps.executeUpdate();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}		
	}
	public JSONObject getGoodData(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			
		//用户的这些信息都不允许为空，为空的话下面的写法就有问题。					
		conn = DBConnect.getConn();
//		StringBuilder sql=new StringBuilder("SELECT product_id AS id,goods_name as goodsName ,a.store_id AS shopId," +
//				"b.shop_name AS shopName,description ,goods_price AS price,org_price AS originalPrice,production,amount " +
//				"FROM product a,shop b WHERE a.store_id=b.shop_id");
//		System.out.println("sql:"+sql.toString());
		ps=conn.prepareStatement("SELECT product_id AS id,goods_name as goodsName ,a.store_id AS shopId," +
				"b.shop_name AS shopName,description ,goods_price AS price,org_price AS originalPrice,production,amount " +
				"FROM product a,shop b WHERE a.store_id=b.shop_id");
		rs=ps.executeQuery();
		sqlData= CDataTransform.rsToJsonLabel(rs);
		tip="查询成功";
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	public JSONObject getGoodComment(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			
		//用户的这些信息都不允许为空，为空的话下面的写法就有问题。	
			if(data.has("goodId")&&data.has("storeId")){
				int product_id=data.getInt("goodId");
				int store_id=data.getInt("storeId");
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT cmt_id AS id,u.nick_name,cmt_time,cmt_content AS content,cmt_type "+
						" FROM product_comment pc,user_info u WHERE pc.user_id=u.id and product_id="+product_id+" AND store_id="+store_id);
				rs=ps.executeQuery();
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip="查询成功";
			}else{
				flag=1;
				tip="参数错误";
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
	public JSONObject getDefaultAddress(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			
		//用户的这些信息都不允许为空，为空的话下面的写法就有问题。	如果没有设置默认地址，则返回用户的第一条地址。
			if(data.has("userId")){				
				int user_id=data.getInt("userId");	
				if(hasDefaultAddress(user_id)){
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("SELECT id,name,phone,delivery_address AS " +
							"address,isDefault FROM deliveryaddress WHERE isDefault=1 and user_id="+user_id);
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="查询成功";
				}else{
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("SELECT id,name,phone,delivery_address AS " +
							"address,isDefault FROM deliveryaddress WHERE user_id="+user_id+" limit 1");
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="查询成功";
				}
			}else{
				flag=1;
				tip="参数错误";
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
	public boolean hasDefaultAddress(int user_id){
		boolean flag = false;
		try{
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT id,name,phone,delivery_address AS " +
					"address,isDefault FROM deliveryaddress WHERE isDefault=1 and user_id="+user_id);
			rs=ps.executeQuery();
			if(rs.next()){
				flag=true;
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return flag;
	}
	public static void main(String[] args) throws Exception {
		System.out.println("测试MarketDao中的小函数");
		SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		MarketDao marketDao = new MarketDao();
		String module = "deliveryType";
		String code_name = "self_Delivery";
		int user_id = 1;
		JSONObject data=new JSONObject();
		JSONObject data1=new JSONObject();
//{"op":"updateDeliveryAddress","isDefault":0,
//		"address":"广东省广州市天河区&&华南理工大学大学城校区","phone":18819451582,"name":"欧中贵","id":10,"userId":1}
		data.put("userId",1);
		String orderString="[{'addressName':'1','addressPhone':'1','addressDetail':'1','storeId':'1'," +
				"'storeName':'','goodId':'1','goodDesc':'','price':'1','originPrice':'1','goodCount':'1','totalMoney':'1'," +
				"'deliveryType':'1','deliveryPrice':'1','extraMessage':'','orderState':'0'}," +
				"{'addressName':'1','addressPhone':'1','addressDetail':'1','storeId':'1'," +
				"'storeName':'','goodId':'1','goodDesc':'','price':'1','originPrice':'1','goodCount':'1','totalMoney':'1'," +
				"'deliveryType':'1','deliveryPrice':'1','extraMessage':'','orderState':'0'}]";
//		String orderString="['34','35']";
		JSONArray oArray=new JSONArray(orderString);
		data.put("orderData", oArray);
//		data.put("userAccount", "ads");
//		data.put("userName", "test1");
//		data.put("userSex", "1");
//		data.put("userPhone", "1234565");
//		data.put("oldDefaultAddressId", "1");
//		data.put("newDefaultAddressId", "2");
//		data.put("id", "10");
//		data.put("address", "广东省广州市天河区&&华南理工大学大学城校区");
//		data.put("isDefault", "0");
//		data.put("name", "欧中贵");
//		data.put("phone", "18819451582");
		data.put("keyword", "店");
		JSONObject result=new JSONObject();
		String string="['1,2','3,4','5,6']";
//		result = new JSONObject(string);
		JSONArray arr = new JSONArray(string);
		System.out.println(arr.toString());
//		result = marketDao.setOrderPay(data);
//		result = marketDao.submitOrders(data);
//		result = marketDao.getGoodInfo(data);
		result = marketDao.getThemeSearch(data);
//		result = marketDao.getStoreGoodListNew(data);
//		result = marketDao.getClassifyTree(data);
//		result = marketDao.getUserData(data);
//		result = marketDao.updateUserData(data);
//		result = marketDao.getDeliveryAddress(data);
//		result = marketDao.updateDefAddress(data);
//		result = marketDao.delDeliveryAddress(data);
//		result = marketDao.addDeliveryAddress(data);
//		result = marketDao.updateDeliveryAddress(data);
		String now=sdf.format(System.currentTimeMillis());
		long nowDate = sdf.parse(now).getTime()-8*3600*24*1000;
		java.sql.Timestamp strDate=new java.sql.Timestamp(nowDate);
//		System.out.println(oArray.toString());
//		for(int i=0;i<oArray.length();i++){
//			System.out.println(oArray.get(i));
//		}
		System.out.println(marketDao.findMaxID());
		
		System.out.println("结果是："+result.toString());
		
	}
	// 这个是返回里classify_tree表中的所有数据，其实要按照使用的模块做一下筛选才对。
	public JSONObject getClassifyTree(JSONObject data) throws SQLException, JSONException {
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		conn = DBConnect.getConn();
		ps=conn.prepareStatement("SELECT DISTINCT root FROM classify_tree ");
		rs=ps.executeQuery();
//		sqlData= CDataTransform.rsToJsonLabel(rs);
//		String[] obj1=new String[5];
		//这里其实没用到集合，准备用集合去重的功能直接在数据库中做掉了。
		Set<String> oSet = new HashSet<>();
//处理第一级列表
		while(rs.next()){
			oSet.add(rs.getObject(1).toString());
//			System.out.println(rs.getObject(1).toString());
		}
		result.put("type1",oSet.toArray());
		//处理第二级列表
		ps=conn.prepareStatement("SELECT DISTINCT root,col2 FROM classify_tree ");
		rs=ps.executeQuery();
		JSONObject obj = new JSONObject();
		Set<String> oSet1 = new HashSet<>();
		Iterator<String> it = oSet.iterator();
		JSONArray tmpArray= new JSONArray();
		while(it.hasNext()){
			obj.put(it.next(),tmpArray);
		}
		while(rs.next()){
			JSONArray str=new JSONArray(obj.getString(rs.getObject(1).toString()));
			str.put(rs.getObject(2).toString());
			obj.put(rs.getObject(1).toString(),	str);
			oSet1.add(rs.getObject(2).toString());
//			System.out.println("***:"+obj.toString());
		}
		result.put("type2", obj);
		//处理第三级列表;目前只支持到三级里列表。每处理一级列表就要做一次数据库查询。
		ps=conn.prepareStatement("SELECT DISTINCT col2,col3 FROM classify_tree ");
		rs=ps.executeQuery();
		JSONObject obj1 = new JSONObject();			
		Iterator<String> it1 = oSet1.iterator();
		while(it1.hasNext()){
			obj1.put(it1.next(),tmpArray);
		}
		while(rs.next()){
			JSONArray str=new JSONArray(obj1.getString(rs.getObject(1).toString()));
			str.put(rs.getObject(2).toString());
			obj1.put(rs.getObject(1).toString(),str);			
//			System.out.println("***:"+obj1.toString());
		}
		result.put("type3", obj1);
		DBConnect.close(conn,ps,rs);	
		return result;
	}
	//将3列的表转成树形结构的json格式。格式为{type1；[作品，服务],type2:{作品：[程序，设计],服务:[...]},type3:{程序:[...],设计:[...]...}}
	//考虑通过拼接字符串来构造。	

	public JSONObject addAttentionStore(JSONObject data) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
					int storeID = data.getInt("storeId");
					int state =data.has("state")?data.getInt("state"):1;
					if(notAttentionStore(user_id,storeID)){						
						conn = DBConnect.getConn();
						ps=conn.prepareStatement("INSERT INTO store_attention(shop_id,user_id,state) VALUES(?,?,?)");
						ps.setInt(1, storeID);
						ps.setInt(2, user_id);
						ps.setInt(3, state);
						ps.executeUpdate();
						ps=conn.prepareStatement("UPDATE shop SET shop_attention=shop_attention +1 WHERE shop_id="+storeID);
						ps.executeUpdate();						
	//					sqlData= CDataTransform.rsToJson(rs);
						tip="关注成功";
					}else{
						tip="已关注";
					}
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		result.put("flag", flag);
		result.put("tip", tip);
		result.put("data", sqlData);		
		
		return result;
	}

	private boolean notAttentionStore(int user_id, int storeID) {
		// TODO Auto-generated method stub
		boolean result=true;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("select * from store_attention where shop_id="+storeID+" and user_id="+user_id);
			rs=ps.executeQuery();
			if(rs.next()){
				result=false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
//		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject cannelAttentionStore(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
					int storeID = data.getInt("storeId");
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("DELETE FROM store_attention WHERE shop_id="+storeID+" AND user_id="+user_id);
					ps.executeUpdate();
					ps=conn.prepareStatement("UPDATE shop SET shop_attention=shop_attention -1 WHERE shop_id="+storeID);
					ps.executeUpdate();
//					sqlData= CDataTransform.rsToJson(rs);
					tip="取消关注成功";
					
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		return result;
	}

	public JSONObject getAttentionStoreList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。					
					conn = DBConnect.getConn();
					
					ps=conn.prepareStatement("SELECT s.shop_id AS storeId,shop_name AS storeName, " +
							"shop_score AS storeScore FROM store_attention sa JOIN shop s ON sa.shop_id=s.shop_id WHERE sa.user_id="+user_id);
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="获取成功";
					
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		return result;
	}

	public JSONObject getIsAttentionStore(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try {
			int user_id=data.getInt("userId");
			int shop_id=data.getInt("storeId");
			if(notAttentionStore(user_id, shop_id)){
				flag=1;
				tip="还未关注该店铺";
			}else{
				tip="已关注";
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}			
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return result;
	}

	public JSONObject addFavoriteGood(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
					int storeID = data.getInt("storeId");
					int goodID=data.getInt("goodId");
					
					if(notCollectThatGood(user_id,goodID)){
						conn = DBConnect.getConn();					
						ps=conn.prepareStatement("INSERT INTO goods_collection(user_id,shop_id,product_id,goodPrice,submitTime,collectType) VALUES(?,?,?,?,?,?)");
						ps.setInt(1, user_id);
						ps.setInt(2, storeID);
						ps.setInt(3, goodID);
						ps.setDouble(4, data.getDouble("goodPrice"));
						java.sql.Timestamp subtime=new Timestamp(System.currentTimeMillis());
						ps.setTimestamp(5, subtime);
						ps.setInt(6, data.has("collectType")?data.getInt("collectType"):0);
						ps.executeUpdate();
//						sqlData= CDataTransform.rsToJsonLabel(rs);
						tip="收藏成功";
					}
					
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		return result;
	}

	private boolean notCollectThatGood(int user_id, int goodID) {
		// TODO Auto-generated method stub
		boolean result=true;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("select * from goods_collection where product_id="+goodID+" and user_id="+user_id);
			rs=ps.executeQuery();
			if(rs.next()){
				result=false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
//		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject cannelFavoriteGood(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
					int ID = data.getInt("id");					
					// 这里假设商品的编号是全局唯一的。而且每个店铺的商品编号都不同。
					conn = DBConnect.getConn();					
					ps=conn.prepareStatement("DELETE FROM goods_collection WHERE id="+ID+" AND user_id="+user_id);
					ps.executeUpdate();
//						sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="收藏成功";
					
					
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		return result;
	}

	public JSONObject getFavoriteGoodList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
									
					// 这里假设商品的编号是全局唯一的。而且每个店铺的商品编号都不同。
					conn = DBConnect.getConn();					
					ps=conn.prepareStatement("SELECT"+
							"  gc.id,"+
							"  gc.shop_id     AS storeId,"+
							"  shop.shop_name AS storeName,"+
							"  gc.product_id  AS goodId,"+
							"  p.description  AS goodDesc,"+
							"  p.goods_state  AS goodState,"+
							"  gc.goodPrice   AS oldPrice,"+
							"  p.goods_Category as goodCategory,"+
							"  p.goods_Price  AS newPrice,"+
							"  p.goods_Name as goodName,"+
							"  p.goods_Photo as goodPic"+
							" FROM goods_collection gc"+
							"  JOIN shop"+
							"    ON gc.shop_id = shop.shop_id"+
							"  JOIN product p"+
							"    ON gc.product_id = p.product_id"+
							" WHERE gc.user_id = "+user_id);
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="收藏成功";			
					
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		return result;
	}

	public JSONObject getIsFavoriteGood(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try {
			int user_id=data.getInt("userId");
			int goodID=data.getInt("goodId");
			if(notCollectThatGood(user_id, goodID)){
				flag=1;
				tip="还未收藏该商品";
			}else{
				tip="已收藏";
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}			
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return result;
	}

	public JSONObject addGoodToCart(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
					int goodID = data.getInt("goodId");
					int storeID= data.getInt("storeId");
					if(notAddToCartYet(user_id,goodID)){						
						conn = DBConnect.getConn();
						ps=conn.prepareStatement("INSERT INTO shop_cart(shop_id,user_id,product_id) VALUES(?,?,?)");
						ps.setInt(1, storeID);
						ps.setInt(2, user_id);
						ps.setInt(3, goodID);
//						ps.setInt(4, data.getInt("goodNum"));
						ps.executeUpdate();
	//					sqlData= CDataTransform.rsToJson(rs);
						tip="添加成功";
					}else{
						conn = DBConnect.getConn();
						int goodNum=data.getInt("goodNum");
						ps=conn.prepareStatement("update shop_cart set goodNum=goodNum+("+goodNum+") where product_id=? and user_id=?");
//						ps.setInt(1, data.getInt("goodNum"));
						ps.setInt(1, goodID);
						ps.setInt(2, user_id);						
						ps.executeUpdate();
						tip="更新成功";
					}
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		return result;
	}

	private boolean notAddToCartYet(int user_id, int goodID) {
		// TODO Auto-generated method stub
		boolean result=true;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("select * from shop_cart where product_id="+goodID+" and user_id="+user_id);
			rs=ps.executeQuery();
			if(rs.next()){
				result=false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		//如果这里关闭连接，在后面查询里会报错。
//		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject cannelGoodInCart(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
					int ID = data.getInt("id");					
					// 这里假设商品的编号是全局唯一的。而且每个店铺的商品编号都不同。
					conn = DBConnect.getConn();					
					ps=conn.prepareStatement("DELETE FROM shop_cart WHERE id="+ID+" AND user_id="+user_id);
					ps.executeUpdate();
//						sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="删除成功";
					
					
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		return result;
	}

	public JSONObject getCartGoodList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
//					int ID = data.getInt("id");					
					// 这里假设商品的编号是全局唯一的。而且每个店铺的商品编号都不同。
					conn = DBConnect.getConn();					
					ps=conn.prepareStatement("SELECT"+
							"  gc.id,"+
							"  gc.shop_id     AS storeId,"+
							"  shop.shop_name AS storeName,"+
							"  gc.product_id  AS goodId,"+
							"  gc.goodNum,"+
							"  p.description  AS goodDesc,"+							
							"  p.goods_Price  AS price,"+
							"  p.goods_Category as goodCategory,"+
							"  p.goods_Stock as goodStock,"+
							"  p.org_price as originPrice,"+
							"  p.goods_Name as goodName,"+
							"  p.goods_Photo as goodPic"+
							" FROM shop_cart gc"+
							"  JOIN shop"+
							"    ON gc.shop_id = shop.shop_id"+
							"  JOIN product p"+
							"    ON gc.product_id = p.product_id"+
							" WHERE gc.user_id = "+user_id);
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="获取成功";			
					
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		return result;
	}

	public JSONObject getHotGoodList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
						
					// 这里的商品推荐列表假设是手工维护的，有可能出现不一致的情况。
			conn = DBConnect.getConn();					
//			ps=conn.prepareStatement("SELECT"+
//					"  gc.shop_id     AS storeId,"+
//					"  shop.shop_name AS storeName,"+
//					"  gc.product_id  AS goodId,"+
//					"  p.goods_Name as goodName,"+
//					"  p.description  AS goodDesc,"+							
//					"  p.goods_Price  AS price,"+
//					"  p.org_price as originPrice,"+
//					"  p.production as goodProduction,"+
//					"  p.amount as goodAmount"+
//					" FROM goods_recmd gc"+
//					"  JOIN shop"+
//					"    ON gc.shop_id = shop.shop_id"+
//					"  JOIN product p"+
//					"    ON gc.product_id = p.product_id"
//					);
			ps = conn.prepareStatement("SELECT p.store_id as storeId,shop_name as storeName,product_id as goodId,p.goods_Name as goodName,p.description  AS goodDesc,					" +
					"p.goods_Price  AS price,p.org_price as originPrice,p.production as goodProduction,p.amount as goodAmount " +
					",p.goods_Category as goodCategory,p.goods_Stock as goodStock,p.goods_Photo as goodPic" +
					" from product p inner JOIN shop ON p.store_id = shop.shop_id");
			rs=ps.executeQuery();
			sqlData= CDataTransform.rsToJsonLabel(rs);
			tip="获取成功";
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		return result;
	}

	public JSONObject getGoodInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			int storeID=data.getInt("storeId");
			int goodID=data.getInt("goodId");
					// 这里的商品推荐列表假设是手工维护的，有可能出现不一致的情况。
			conn = DBConnect.getConn();					
			ps=conn.prepareStatement("SELECT"+
					"  p.store_id     AS storeId,"+
					"  s.shop_name AS storeName,"+
					"  p.product_id  AS goodId,"+
					"  p.goods_Name as goodName,"+
					"  p.description  AS goodDesc,"+							
					"  p.goods_Price  AS price,"+
					"  p.org_price as originPrice,"+
					"  p.production as goodProduction,"+
					"  p.amount as goodAmount,"+
					"  p.goods_Photo as goodPic,"+
					"  p.goods_Category as goodCategory,p.goods_Stock as goodStock"+
					" FROM product p"+
					" join shop s "+
					"    ON p.store_id = s.shop_id"+
					" where p.product_id="+goodID +" and p.store_id="+storeID 
					);
			rs=ps.executeQuery();
			sqlData= CDataTransform.rsToJsonLabel(rs);
			tip="获取成功";
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		return result;
	}

	public JSONObject getStoreInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			int storeID=data.getInt("storeId");
//			int goodID=data.getInt("goodId");
					// 这里的商品推荐列表假设是手工维护的，有可能出现不一致的情况。
			conn = DBConnect.getConn();					
			ps=conn.prepareStatement("SELECT shop_id AS storeId,shop_name AS storeName,"+
					"shop_score AS storeScore,shop_attention AS storeAttentions"+
					" FROM shop WHERE shop_id="+storeID 
					);
			rs=ps.executeQuery();
			sqlData= CDataTransform.rsToJsonLabel(rs);
			tip="获取成功";
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		return result;
	}

	public JSONObject getStoreGoodListAll(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			int storeID=data.getInt("storeId");
//			int goodID=data.getInt("goodId");
					// 这里的商品推荐列表假设是手工维护的，有可能出现不一致的情况。
			conn = DBConnect.getConn();					
			ps=conn.prepareStatement("SELECT  "+
					"  gc.store_id     AS storeId,"+
					"  s.shop_name AS storeName,"+
					"  gc.product_id  AS goodId,"+
					"  gc.description  AS goodDesc,"+
					"  gc.goods_state  AS goodState, "+
					"  gc.goods_Price  AS price,"+
					"  gc.goods_Category as goodCategory,"+
					"  gc.goods_Stock as goodStock,"+
					"  gc.org_price as originPrice,"+
					"  gc.goods_Name as goodName,"+
					"  gc.goods_Photo as goodPic,"+
					"  gc.production as goodProduction,"+
					"  gc.amount as goodAmount"+
					" FROM product gc"+
					"  JOIN shop s"+
					"    ON gc.store_id = s.shop_id  "+
					" WHERE gc.store_id = "+storeID 
					);
			rs=ps.executeQuery();
			sqlData= CDataTransform.rsToJsonLabel(rs);
			tip="获取成功";
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		return result;
	}

	public JSONObject getStoreGoodListNew(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			int storeID=data.getInt("storeId");
//			int goodID=data.getInt("goodId");
					// 这里的商品推荐列表假设是手工维护的，有可能出现不一致的情况。
			String now=sdf.format(System.currentTimeMillis());
			//把时间转换成数字还要乘以1000
			long nowDate = sdf.parse(now).getTime()-8*3600*24*1000;
			java.sql.Timestamp strDate=new java.sql.Timestamp(nowDate);
			System.out.println(strDate.toString());
			conn = DBConnect.getConn();					
			ps=conn.prepareStatement("SELECT  "+
					"  gc.store_id     AS storeId,"+
					"  s.shop_name AS storeName,"+
					"  gc.product_id  AS goodId,"+
					"  gc.description  AS goodDesc,"+
					"  gc.goods_state  AS goodState, "+
					"  gc.goods_Price  AS price,"+
					"  gc.goods_Category as goodCategory,"+
					"  gc.goods_Stock as goodStock,"+
					"  gc.org_price as originPrice,"+
					"  gc.goods_Name as goodName,"+
					"  gc.goods_Photo as goodPic,"+
					"  gc.production as goodProduction,"+
					"  gc.amount as goodAmount"+
					" FROM product gc"+
					"  JOIN shop s"+
					"    ON gc.store_id = s.shop_id  "+
					" WHERE gc.store_id = "+storeID+
					" and gc.submitTime>= '"+strDate.toString()+"'"
					);
			rs=ps.executeQuery();
			sqlData= CDataTransform.rsToJsonLabel(rs);
			tip="获取成功";
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		return result;
	}

	public JSONObject getKeywordSearchList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//目前只能检索单个词，无法处理字符串。
			String keyword=data.getString("keyword");
//			
			String now=sdf.format(System.currentTimeMillis());
			String storeID=data.getString("storeId");
			if(storeID.equals("")||storeID.equals("empty")){
				conn = DBConnect.getConn();					
				ps=conn.prepareStatement("SELECT  "+
						"  gc.store_id     AS storeId,"+
						"  s.shop_name AS storeName,"+
						"  gc.product_id  AS goodId,"+
						"  gc.description  AS goodDesc,"+
						"  gc.goods_state  AS goodState, "+
						"  gc.goods_Price  AS price,"+
						"  gc.goods_Category as goodCategory,"+
						"  gc.goods_Stock as goodStock,"+
						"  gc.org_price as originPrice,"+
						"  gc.production as goodProduction,"+
						"  gc.amount as goodAmount"+
						" FROM product gc"+
						"  JOIN shop s"+
						"    ON gc.store_id = s.shop_id  "+
						" WHERE gc.description LIKE '%"+keyword+"%'" +
						" or gc.goods_Category like '%"+keyword+"%'" +
						" ORDER BY s.shop_score DESC "
						);
			}else{
				conn = DBConnect.getConn();					
				ps=conn.prepareStatement("SELECT  "+
						"  gc.store_id     AS storeId,"+
						"  s.shop_name AS storeName,"+
						"  gc.product_id  AS goodId,"+
						"  gc.description  AS goodDesc,"+
						"  gc.goods_state  AS goodState, "+
						"  gc.goods_Price  AS price,"+
						"  gc.goods_Category as goodCategory,"+
						"  gc.goods_Stock as goodStock,"+
						"  gc.org_price as originPrice,"+
						"  gc.production as goodProduction,"+
						"  gc.amount as goodAmount"+
						" FROM product gc"+
						"  JOIN shop s"+
						"    ON gc.store_id = s.shop_id  "+
						" WHERE (gc.description LIKE '%"+keyword+"%'" +
						" or gc.goods_Category like '%"+keyword+"%') and gc.store_id="+Integer.parseInt(storeID) +
						" ORDER BY s.shop_score DESC "
						);
			}
			rs=ps.executeQuery();
			sqlData= CDataTransform.rsToJsonLabel(rs);
			tip="获取成功";
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		return result;
	}

	public JSONObject getThemeSearch(JSONObject data) {
		// TODO Auto-generated method stub
		// 主题搜索还没有好的解决办法。
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//目前只能检索单个词，无法处理字符串。
			String keyword=data.getString("keyword");
			int type=data.getInt("type");//1,store;0,good
			if(type==1){
				conn = DBConnect.getConn();		//名称，类别，关键词，介绍			
				ps=conn.prepareStatement("SELECT shop_keyword as keyword FROM shop s WHERE shop_keyword LIKE '%"+keyword+"%'"+
						" UNION ALL SELECT shop_name FROM shop s WHERE shop_name LIKE '%"+keyword+"%'"+
						" UNION ALL SELECT shop_introduction FROM shop s WHERE shop_introduction LIKE '%"+keyword+"%'"+
						" UNION ALL SELECT shop_busi_category FROM shop s WHERE shop_busi_category LIKE '%"+keyword+"%'"
						);
				rs=ps.executeQuery();			
			}else{
				conn = DBConnect.getConn();		//名称，类别，介绍			
				ps=conn.prepareStatement("SELECT description as keyword FROM product WHERE description LIKE '%"+keyword+"%'"+
						" UNION ALL SELECT goods_Name FROM product WHERE goods_Name LIKE  '%"+keyword+"%'"+
						" UNION ALL SELECT goods_Category FROM product WHERE goods_Category LIKE  '%"+keyword+"%'"
						);
				rs=ps.executeQuery();
			}
			sqlData=CDataTransform.rsToJsonOneColumn(rs);
			System.out.println("sqlData:"+sqlData.toString());
			tip="获取成功";
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}						
		return result;
	}

	public JSONObject submitOrders(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		Set<String> oSet1 = new HashSet<>();	
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
					JSONArray orderArray=data.getJSONArray("orderData");
					int ID=findMaxID();	
									
					
					for(int i=0;i<orderArray.length();i++){
						//下单时，如果商品在购物车中存在，则下单后删除购物车中记录。
						conn = DBConnect.getConn();
						int goodID=orderArray.getJSONObject(i).getInt("goodId");
						ps=conn.prepareStatement("INSERT INTO market_order(userID,addressName,addressPhone,addressDetail,"+
								"storeId,storeName,goodId,goodDesc,price,originPrice,goodCount,totalMoney,"+
								"deliveryType,deliveryPrice,extraMessage,orderState,submitTime,payTime,after_sale,goodCategory,orderCode)" +
								" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0,?,?)");
						ps.setInt(1, user_id);
						ps.setString(2, orderArray.getJSONObject(i).getString("addressName"));
						ps.setString(3, orderArray.getJSONObject(i).getString("addressPhone"));
						ps.setString(4, orderArray.getJSONObject(i).getString("addressDetail"));
						ps.setInt(5, orderArray.getJSONObject(i).getInt("storeId"));
						ps.setString(6, orderArray.getJSONObject(i).getString("storeName"));
						ps.setInt(7, orderArray.getJSONObject(i).getInt("goodId"));
						ps.setString(8, orderArray.getJSONObject(i).getString("goodDesc"));
						ps.setDouble(9, orderArray.getJSONObject(i).getDouble("price"));
						ps.setDouble(10, orderArray.getJSONObject(i).getDouble("originPrice"));
						ps.setInt(11, orderArray.getJSONObject(i).getInt("goodCount"));
						ps.setDouble(12, orderArray.getJSONObject(i).getDouble("totalMoney"));
						ps.setString(13, orderArray.getJSONObject(i).getString("deliveryType"));
						ps.setInt(14, orderArray.getJSONObject(i).getInt("deliveryPrice"));
						ps.setString(15, orderArray.getJSONObject(i).getString("extraMessage"));
						int orderState=orderArray.getJSONObject(i).getInt("orderState");
						java.sql.Timestamp submTimestamp=new Timestamp(System.currentTimeMillis());
						ps.setInt(16, orderState);
						//如果已支付，则支付时间同提交时间相同呢，否则支付时间为空。
						if(orderState==1){						
							ps.setTimestamp(17, submTimestamp);
							ps.setTimestamp(18, submTimestamp);
						}else{
							ps.setTimestamp(17, submTimestamp);
							ps.setTimestamp(18, null);
						}
						ps.setString(19, orderArray.getJSONObject(i).getString("goodCategory"));
						ps.setString(20, Long.toString(System.currentTimeMillis()));
						ps.executeUpdate();
						//如果在购物车中有记录，则删除
						//提交订单后还需要把商品库存减1.
						if(!notAddToCartYet(user_id, goodID)){
							conn = DBConnect.getConn();
							ps=conn.prepareStatement("delete from shop_cart where product_id="+goodID+" and user_id="+user_id);
							ps.executeUpdate();
						}
						ID=ID+1;
						oSet1.add(String.valueOf(ID));
					}
					
//					sqlData= CDataTransform.rsToJson(rs);
					tip="下单成功";
					
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			
			String oString= oSet1.toString();
//			System.out.println("***:"+oString);
			sqlData = new JSONArray(oString);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
		return result;
	}

	private int findMaxID() {
		// TODO Auto-generated method stub
		int result=0;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("select max(orderId) from market_order");
			rs=ps.executeQuery();
			if(rs.next()){
				result=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		//如果这里关闭连接，在后面查询里会报错。
//		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject getOrdersList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
					int orderState = data.getInt("orderState");		
					StringBuffer sql = new StringBuffer("SELECT orderId,orderState,storeId,storeName,goodId,goodDesc,price,originPrice,"+
							" goodCount,deliveryPrice,totalMoney,goodCategory,p.goods_Name AS goodName,p.goods_Photo AS goodPic,orderCode as orderNUM"+
							" FROM market_order mo JOIN product p ON mo.goodId=p.product_id WHERE 1=1 AND after_sale=0");
					if(orderState == -1){
						sql.append(" and userID= "+user_id);
					}
					else{
						sql.append(" and orderState="+orderState+" AND userID= "+user_id);
					}
					// 这里假设商品的编号是全局唯一的。而且每个店铺的商品编号都不同。
					conn = DBConnect.getConn();					
					ps=conn.prepareStatement(sql.toString());
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="获取成功";			
					
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		return result;
	}

	public JSONObject getOrderInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
//					int ID = data.getInt("id");					
					// 这里假设商品的编号是全局唯一的。而且每个店铺的商品编号都不同。
					int orderId=data.getInt("orderId");
					int storeId=data.getInt("storeId");
					conn = DBConnect.getConn();					
					ps=conn.prepareStatement("SELECT addressName,addressPhone,addressDetail,orderId ,"+
							"submitTime AS orderCreateTime,storeChatAccount,shop_contact_phone,orderCode as orderNUM "+
							" FROM market_order mo JOIN shop ON mo.storeId=shop_id"+
							" WHERE mo.after_sale=0 and mo.userID="+user_id+" AND mo.orderId="+orderId+" AND mo.storeId="+storeId);
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="获取成功";			
					
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		return result;
	}

	public JSONObject setDelOrder(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
					int orderId=data.getInt("orderId");
										
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("DELETE FROM market_order WHERE userid="+user_id+" AND orderId="+orderId);
					
					ps.executeUpdate();
	//					sqlData= CDataTransform.rsToJson(rs);
					tip="订单删除成功";
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		return result;
	}

	public JSONObject addGoodComment(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
					int goodID = data.getInt("goodId");
					int storeID= data.getInt("storeId");
					int orderID= data.getInt("orderId");
					if(orderState(orderID)==3){
						conn = DBConnect.getConn();
						ps=conn.prepareStatement("INSERT INTO product_comment(store_id,user_id,product_id,orderId,cmt_content,cmt_time,cmt_type)"+
								" VALUES(?,?,?,?,?,?,?)");
						ps.setInt(1, storeID);
						ps.setInt(2, user_id);
						ps.setInt(3, goodID);
						ps.setInt(4, data.getInt("orderId"));	
						ps.setString(5, data.getString("content"));
						java.sql.Timestamp subTimestamp=new Timestamp(System.currentTimeMillis());
						ps.setTimestamp(6, subTimestamp);
						ps.setInt(7, data.getInt("cmt_type"));
						ps.executeUpdate();
						ps=conn.prepareStatement("UPDATE market_order SET orderState=4 WHERE orderId="+orderID);					
						ps.executeUpdate();
					}else{
						tip="还未确认收货";
					}
//					sqlData= CDataTransform.rsToJson(rs);
					tip="评论成功";
					
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		return result;
	}

	public JSONObject setOrderPay(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
					JSONArray orderIDarr = data.getJSONArray("orderId");
					System.out.println(orderIDarr.length());
					java.sql.Timestamp sTimestamp=new Timestamp(System.currentTimeMillis());
					
					for(int i=0;i<orderIDarr.length();i++){
						int orderID=Integer.parseInt(orderIDarr.get(i).toString());
//						System.out.println(orderID);
						if(orderState(orderID)==0){
							conn = DBConnect.getConn();
							StringBuilder sql1=new StringBuilder("UPDATE market_order SET orderState=1,payTime=? WHERE orderId="+orderID);
							System.out.println(sql1.toString());
							ps=conn.prepareStatement(sql1.toString());
							ps.setTimestamp(1, sTimestamp);
							ps.executeUpdate();
							Double money=orderMoney(orderID);
							conn = DBConnect.getConn();
							ps=conn.prepareStatement("update user_wallet set walletMoney=walletMoney-"+money+" where userId="+user_id);							
							ps.executeUpdate();
							int goodCount=findGoodCountByOrder(orderID);
							int goodId=findGoodIdByOrder(orderID);
							int goods_stock=findGoodStockByGoodID(goodId);
							if(goodCount>0 && goodCount<=goods_stock){
								conn = DBConnect.getConn();
								ps=conn.prepareStatement("update product set amount=amount+"+goodCount+
										",goods_Stock=goods_Stock-"+goodCount+" where product_id="+goodId);							
								ps.executeUpdate();
							}else{
								flag = 1;
								tip  = "库存不足";
								break;
							}
						}
					}
//					sqlData= CDataTransform.rsToJson(rs);
					tip="支付成功";
					
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		return result;
	}

	private int findGoodStockByGoodID(int goodId) {
		// TODO Auto-generated method stub
		int result=0;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("select goods_Stock from product where product_id="+goodId);
			rs=ps.executeQuery();
			if(rs.next()){
				result=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		//如果这里关闭连接，在后面查询里会报错。
//		DBConnect.close(conn,ps,rs);
		return result;
	}

	private int findGoodIdByOrder(int orderID) {
		// TODO Auto-generated method stub
		int result=0;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("select goodId from market_order where orderId="+orderID);
			rs=ps.executeQuery();
			if(rs.next()){
				result=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		//如果这里关闭连接，在后面查询里会报错。
//		DBConnect.close(conn,ps,rs);
		return result;
	}

	private int findGoodCountByOrder(int orderID) {
		// TODO Auto-generated method stub
		int result=-1;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("select goodCount from market_order where orderId="+orderID);
			rs=ps.executeQuery();
			if(rs.next()){
				result=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		//如果这里关闭连接，在后面查询里会报错。
//		DBConnect.close(conn,ps,rs);
		return result;
	}

	private Double orderMoney(int orderID) {
		// TODO Auto-generated method stub
		Double result=0.0;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("select totalMoney from market_order where orderId="+orderID);
			rs=ps.executeQuery();
			if(rs.next()){
				result=rs.getDouble(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		//如果这里关闭连接，在后面查询里会报错。
//		DBConnect.close(conn,ps,rs);
		return result;
	}

	private int orderState(int orderID) {
		// TODO Auto-generated method stub
		int result=0;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("select orderState from market_order where orderId="+orderID);
			rs=ps.executeQuery();
			if(rs.next()){
				result=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		//如果这里关闭连接，在后面查询里会报错。
//		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject setOrderReceipt(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
					int orderID = data.getInt("orderId");										
					
					if(orderState(orderID)==2){
						conn = DBConnect.getConn();
						ps=conn.prepareStatement("UPDATE market_order SET orderState=3 WHERE orderId="+orderID);					
						ps.executeUpdate();
					}
//					sqlData= CDataTransform.rsToJson(rs);
					tip="评论成功";
					
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
		return result;
	}

	public JSONObject requestDrawback(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
					int orderID = data.getInt("orderId");
										
					conn = DBConnect.getConn();
					if(orderState(orderID)>=1){
						ps=conn.prepareStatement("INSERT INTO after_sale(userId,orderId,storeId,storeName,"+
								"goodId,goodDesc,totalMoney,deliveryPrice,drawbackType,"+
								"drawbackState,drawbackReason,drawbackExtraMsg,submitTime,goodCategory) VALUES( "+
								"?,?,?,?,?,?,?,?,?,?,?,?,?,?)");	
						ps.setInt(1, user_id);
						ps.setInt(2, orderID);
						ps.setInt(3, data.getInt("storeId"));
						ps.setString(4, data.getString("storeName"));
						ps.setInt(5, data.getInt("goodId"));
						ps.setString(6, data.getString("goodDesc"));
						ps.setDouble(7, data.getDouble("totalMoney"));
						ps.setDouble(8, data.getDouble("deliveryPrice"));
						ps.setInt(9, data.getInt("drawbackType"));
						ps.setInt(10, data.getInt("drawbackState"));
						ps.setString(11, data.getString("drawbackReason"));
						ps.setString(12, data.getString("drawbackExtraMsg"));
						java.sql.Timestamp subTimestamp=new Timestamp(System.currentTimeMillis());
						ps.setTimestamp(13, subTimestamp);
						ps.setString(14, data.getString("goodCategory"));
						ps.executeUpdate();
						//这里处理售后的流程是在订单售后后直接删除，更合理的做法是把订单设置一个标志位，把正常的订单显示在一个地方，不正常的订单显示在另外一个地方，而不是删除数据。
						ps=conn.prepareStatement("update market_order set after_sale=1 where orderId="+orderID);
						ps.executeUpdate();
					}
//					sqlData= CDataTransform.rsToJson(rs);
					tip="评论成功";
					
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	public JSONObject getDrawbackList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
					
										
					conn = DBConnect.getConn();
				
					ps=conn.prepareStatement("select id,userId,storeId,storeName,"+
							"goodId,goodDesc,totalMoney,deliveryPrice,drawbackType,"+
							"drawbackState,drawbackReason,drawbackExtraMsg,goodCategory from after_sale where userId="+user_id);	
					
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJson(rs);
					tip="获取成功";
					
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
		return result;
	}

	public JSONObject delDrawbackRecord(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
					int id=data.getInt("id");
										
					conn = DBConnect.getConn();				
					ps=conn.prepareStatement("delete from after_sale where userId="+user_id+" and Id="+id);	
					
					ps.executeUpdate();
//					sqlData= CDataTransform.rsToJson(rs);
					tip="获取成功";
					
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
		return result;
	}

	public JSONObject getUserWallet(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
					
										
					conn = DBConnect.getConn();
				
					ps=conn.prepareStatement("select walletMoney from user_wallet where userId="+user_id);	
					
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="获取成功";
					
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
		return result;
	}

	public JSONObject walletCharge(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
					Double money = data.getDouble("money");
					if(userHasWallet(user_id)){					
						conn = DBConnect.getConn();				
						ps=conn.prepareStatement("update user_wallet set walletMoney=walletMoney+("+money+") where userId="+user_id);							
						ps.executeUpdate();						
					}else{
						conn = DBConnect.getConn();				
						ps=conn.prepareStatement("insert into user_wallet(userId,walletMoney) values(?,?)");
						ps.setInt(1, user_id);
						ps.setDouble(2, money);
						ps.executeUpdate();	
					}
					tip="充值成功";
					
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
		return result;
	}

	private boolean userHasWallet(int user_id) throws SQLException {
		// TODO Auto-generated method stub
		boolean result=false;
		conn = DBConnect.getConn();		
		ps=conn.prepareStatement("select walletMoney from user_wallet where userId="+user_id);			
		rs=ps.executeQuery();
		if(rs.next()){
			result=true;
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject getCardData(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){	//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("SELECT id,user_name AS cardOwner,account_num AS cardNum," +
							"account_Bank AS cardBank,cardType,phone AS cardPhone "+
							" FROM bank WHERE TYPE=1 AND user_id="+user_id);
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="获取成功";
					
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		return result;
	}

	public JSONObject setAddCard(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。

					conn = DBConnect.getConn();
				
					ps=conn.prepareStatement("INSERT INTO bank(user_id,user_name,account_num,account_Bank,cardType,phone,TYPE) VALUES(?,?,?,?,?,?,1)");	
					ps.setInt(1, user_id);
					ps.setString(2, data.getString("cardOwner"));
					ps.setString(3, data.getString("cardNum"));
					ps.setString(4, data.getString("cardBank"));
					ps.setInt(5, data.getInt("cardType"));
					ps.setString(6, data.getString("cardPhone"));
					ps.executeUpdate();
//					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="添加成功";
					
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return result;
	}

	public JSONObject setUpdateCard(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
					
										
					conn = DBConnect.getConn();
				
					ps=conn.prepareStatement("UPDATE bank SET user_name=?,account_num=?,account_Bank=?,cardType=?,phone=? "+
							" WHERE user_id=? AND id=? AND TYPE=1");	
					ps.setString(1, data.getString("cardOwner"));
					ps.setString(2, data.getString("cardNum"));
					ps.setString(3, data.getString("cardBank"));
					ps.setInt(4, data.getInt("cardType"));
					ps.setString(5, data.getString("cardPhone"));
					ps.setInt(6, user_id);
					ps.setInt(7, data.getInt("id"));
					ps.executeUpdate();
//					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="修改成功";
					
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return result;
	}

	public JSONObject setDelCard(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。	
					int id =data.getInt("id");
					conn = DBConnect.getConn();
				
					ps=conn.prepareStatement("delete from bank where id="+id);	
					
					ps.executeUpdate();
//					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="删除成功";				
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return result;
	}

	public JSONObject getAdData(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。							
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT imageURL,contentURL AS webURL FROM ad_message WHERE TYPE=1 AND isExist=1");	
			rs=ps.executeQuery();
			sqlData= CDataTransform.rsToJsonLabel(rs);
			tip="获取成功";
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public JSONObject getHeadline(JSONObject data) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
			JSONObject	result=new JSONObject();
			int flag=0;
			String tip=null;
			JSONArray sqlData=new JSONArray();
			try{
				//如果发送了userId字段。							
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT title AS content,contentURL AS webURL FROM ad_message WHERE TYPE=2 AND isExist=1");	
				rs=ps.executeQuery();
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip="获取成功";
			}catch(Exception e){
				flag=1;
				tip="系统运行错误";
				e.printStackTrace();
			}finally{
				DBConnect.close(conn,ps,rs);
			}
			try {
				result.put("flag", flag);
				result.put("tip", tip);
				result.put("data", sqlData);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
	}

	public JSONObject getStoreSearch(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		Set<String> oSet = new HashSet<>();
		try{
			//目前只能检索单个词，无法处理字符串。
			String keyword=data.getString("key");
//			int goodID=data.getInt("goodId");
					// 这里的商品推荐列表假设是手工维护的，有可能出现不一致的情况。
			String now=sdf.format(System.currentTimeMillis());
			//把时间转换成数字还要乘以1000			
			conn = DBConnect.getConn();					
			ps=conn.prepareStatement("SELECT shop_id as storeId,shop_name as storeName" +
					",shop_contact_address as storeAddress FROM shop s WHERE shop_busi_category LIKE '%"+keyword+"%' " +
					" or shop_Category LIKE '%"+keyword+"%'"+
					" ORDER BY s.shop_score DESC "
					);
			rs=ps.executeQuery();			
			//处理第一级列表
			sqlData= CDataTransform.rsToJsonLabel(rs);			
			tip="获取成功";
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public JSONObject getStoreSearchList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		Set<String> oSet = new HashSet<>();
		try{
			//目前只能检索单个词，无法处理字符串。
			String keyword=data.getString("keyword");
//			int goodID=data.getInt("goodId");
					// 这里的商品推荐列表假设是手工维护的，有可能出现不一致的情况。
			String now=sdf.format(System.currentTimeMillis());
			//把时间转换成数字还要乘以1000			
			conn = DBConnect.getConn();					
			ps=conn.prepareStatement("SELECT shop_id as storeId,shop_name as storeName" +
					",shop_keyword as keyword,shop_introduction as introduction,shop_contact_address as storeAddress" +
					" FROM shop s WHERE shop_name LIKE '%"+keyword+"%' " +
					" or shop_keyword LIKE '%"+keyword+"%' or shop_introduction like '%"+keyword+"%'"+
					" ORDER BY s.shop_score DESC "
					);
			rs=ps.executeQuery();			
			//处理第一级列表
			sqlData= CDataTransform.rsToJsonLabel(rs);			
			tip="获取成功";
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		try {
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
}
