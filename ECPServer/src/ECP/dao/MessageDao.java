package ECP.dao;

import java.sql.SQLException;
import java.sql.Timestamp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ECP.model.SubscribeMessageInfo;
import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

public class MessageDao extends BaseDao{
	MarketDao marketDao=new MarketDao();
//订阅消息要实现的方法：
//1，将数据插入订阅消息表insertSubscribeMessage()
//2，遍历订阅消息表，查询相应数据，将数据插入相应的数据表（商品关注表，店铺关注表），调用相应模块下的方法。subsribeMessage()
//3,需要一个解析订阅表达式的函数。title：“”&&price：“”-“”&&production：“”&&storeID：“”
	public int insert(SubscribeMessageInfo subscribeMessageInfo){
		conn = DBConnect.getConn();
		int result = 0;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("INSERT INTO subscribe(id,userID,subscribe_type1,subscribe_type2," +
					"key_word,sendID,send_time,keep_time,content,order_condition,send_state,eventType) VALUES (null,?,?,?,?,?,?,?,?,?,?,?)");
			
			ps.setString(1, subscribeMessageInfo.getUserID().toString());			
			ps.setString(2, subscribeMessageInfo.getSubscribeType1().toString());			
			ps.setString(3, subscribeMessageInfo.getSubscribeType2().toString());			
			ps.setString(4, subscribeMessageInfo.getKeyWord());
			ps.setString(5, subscribeMessageInfo.getSendID());			
			ps.setTimestamp(6, subscribeMessageInfo.getSendTime());			
			ps.setLong(7, subscribeMessageInfo.getKeepTime());			
			ps.setString(8, subscribeMessageInfo.getContent());			
			ps.setString(9, subscribeMessageInfo.getOrderCondition().toString());
			ps.setShort(10, subscribeMessageInfo.getSendState());	
			ps.setString(11, subscribeMessageInfo.getEventType());
			result = ps.executeUpdate();
			conn.commit();
			process(subscribeMessageInfo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
				System.out.println("SQL执行错误，事务回滚!");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统错误");
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;	
	}
	
	public void process(SubscribeMessageInfo subscribeInfo) throws JSONException{
		JSONArray resultIDset=getResultSetBySubscribeCondition(subscribeInfo);
//		System.out.println("resultID:"+resultIDset.toString());
		String op=subscribeInfo.getEventType();
		int userID=getIDFromUID(subscribeInfo.getUserID());
		switch(op){
		case "商品新增":
			processGoodsAdds(resultIDset,userID);
			break;
		case "商品到货":
			processGoodsRefill(resultIDset,userID);
			break;
		case "商品降价":
			processGoodsPriceOff(resultIDset,userID);
			break;
		}
//		System.out.println("调用订阅处理函数完毕");
	}
	
	private void processGoodsPriceOff(JSONArray resultIDset, int userID) throws JSONException {
		// TODO Auto-generated method stub
		//将数据加入商品关注表中，并在降价时发送通知。
//		System.out.println("结果集数量"+resultIDset.length());
		JSONObject data=new JSONObject();
		for(int i=0;i<resultIDset.length();i++){
			data.put("userId", userID);
			data.put("storeId", "0");
			data.put("goodId", resultIDset.getJSONObject(i).getInt("goodId"));
			data.put("goodPrice",resultIDset.getJSONObject(i).getDouble("goodPrice"));
			data.put("collectType","2");
//			System.out.println(data.toString());
			marketDao.addFavoriteGood(data);
		}
		System.out.println("降价信息关注成功");
	}
	
	private void processGoodsRefill(JSONArray resultIDset, int userID) throws JSONException {
		// TODO Auto-generated method stub
		//将数据加入商品关注表中，并在到货时发送通知。
		JSONObject data=new JSONObject();
		for(int i=0;i<resultIDset.length();i++){
			data.put("userId", userID);
			data.put("storeId", "0");
			data.put("goodId", resultIDset.getJSONObject(i).getInt("goodId"));
			data.put("goodPrice",resultIDset.getJSONObject(i).getDouble("goodPrice"));
			data.put("collectType","1");
			marketDao.addFavoriteGood(data);
		}
	}
	
	private void processGoodsAdds(JSONArray resultIDset, int userID) throws JSONException {
		// TODO Auto-generated method stub
		//将数据加入店铺关注表中，并在商品新增时发送通知。
		JSONObject data=new JSONObject();
		for(int i=0;i<resultIDset.length();i++){
			data.put("userId", userID);
			data.put("storeId",resultIDset.getJSONObject(i).getInt("storeId"));
			data.put("state", 2);			
			marketDao.addAttentionStore(data);
		}
	}
	
	private int getIDFromUID(String userID) {
		// TODO Auto-generated method stub
		//通过用户的UID查询用户的id。
		int result=0;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT id FROM user_info WHERE uid='"+userID+"'");
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
		}
		DBConnect.close(conn,ps,rs);
		return result;		
	}
	
	private String getModuleType(SubscribeMessageInfo subscribeInfo) {
		// TODO Auto-generated method stub
		//获取事件类型。
		String[] category=subscribeInfo.getSubscribeType2().split("-");
		String result=category[0];
		return result;
	}
	
	private JSONArray getResultSetBySubscribeCondition(
			SubscribeMessageInfo subscribeInfo) {
		// TODO Auto-generated method stub
		JSONArray result=new JSONArray();
		String[] condition=subscribeInfo.getOrderCondition().split("&&");
		String op=getModuleType(subscribeInfo);
		System.out.println("op:"+op);
		String priceLow=new String();
		String priceHigh=new String();
		String title=new String();
		String production=new String();
		switch (op) {
		case "作品":
			
			if(condition.length>=1 && !condition[0].split(":")[1].equals(" ")){
				title=condition[0].split(":")[1];
			}
			if(condition.length>=2 && !condition[1].split(":")[1].equals(" ")){
				priceLow=condition[1].split(":")[1].split("-")[0];
				priceHigh=condition[1].split(":")[1].split("-")[1];
			}
			if(condition.length>=3){
				production=condition[2].split(":")[1];
			}
			String[] category=subscribeInfo.getSubscribeType2().split("-");
			String category1=category[2];
//			System.out.println(title+"*"+priceHigh+"*"+priceLow+"*"+production+"*"+category1);
//			System.out.println("priceHigh.equals('')"+priceHigh.equals(""));
//			System.out.println("priceHigh.isEmpty()"+priceHigh.isEmpty());
			result=SqlResultOfGoods(title,priceLow,priceHigh,production,category1);
			break;
		case "店铺":
			break;
		
		case "服务":
			break;
		default:
			break;
		}
		return result;
	}
	
	@SuppressWarnings("null")
	private JSONArray SqlResultOfGoods(String title, String priceLow,
			String priceHigh, String production, String category) {
		// TODO Auto-generated method stub
		JSONArray result=new JSONArray();
		conn = DBConnect.getConn();
		try {
			StringBuilder sqlBuilder=new StringBuilder("SELECT store_id as storeId,product_id AS goodId,goods_Price AS goodPrice FROM product "+
					" WHERE goods_Category like '%"+category+"%'");
			System.out.println(priceLow!=null||!priceLow.equals(" "));
			if(!priceLow.equals("")||!priceLow.isEmpty()){
				sqlBuilder.append(" AND goods_Price >"+priceLow);
			}
			if(!priceHigh.equals("")||!priceHigh.isEmpty()){
				sqlBuilder.append(" AND goods_Price <"+priceHigh);
			}
			if(!production.equals(" ")){
				sqlBuilder.append(" AND  production LIKE '%"+production+"%'");
			}
			if(!title.equals(" ")){
				sqlBuilder.append(" AND goods_Name LIKE '%"+title+"%'");
			}
			System.out.println(sqlBuilder.toString());
			ps=conn.prepareStatement(sqlBuilder.toString());			
			rs=ps.executeQuery();
			result=CDataTransform.rsToJsonLabel(rs);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//如果这里关闭连接，在后面查询里会报错。
//		DBConnect.close(conn,ps,rs);
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("测试消息订阅相关的小函数");
		SubscribeMessageInfo subscribeInfo=new SubscribeMessageInfo();
		subscribeInfo.setUserID("1234");
		subscribeInfo.setOrderCondtion("title:手机");
		subscribeInfo.setSubscribeType2("作品-校园二手-笔记本");
		subscribeInfo.setEventType("商品降价");
		MessageDao messageDao=new MessageDao();
		String priceHigh=null;
//		messageDao.process(subscribeInfo);
		String[] condition=subscribeInfo.getOrderCondition().split("&&");
		System.out.println(condition.length);
		String[] category=subscribeInfo.getSubscribeType2().split("-");
		String category1=category[2];
//		messageDao.insert(subscribeInfo);
		String[] title=condition[0].split(":");
		System.out.println(title[1]);
//		System.out.println(category1);
//		System.out.println("priceHigh:"+priceHigh.length());
//		System.out.println("priceHigh.isEmpty()"+priceHigh.isEmpty());
//		System.out.println("priceHigh**:"+(priceHigh==null));
//		System.out.println("priceHigh.equals('')"+priceHigh.equals(""));
//		System.out.println("priceHigh.equals('空格'):"+priceHigh.equals(" "));
	}
//	将推送的信息插入信息详情表；
//	sender 发送者 fromAccount
//	receiver 接受者 toAccount
//	content 内容 systemInfo
//	msgType1 消息类型  
//	msgType2 消息所属模块 module
//	keepTime 消息报存时间 
//	sendTime 发送时间 
	public int setSubscribeInfoDetail(JSONObject data){
		int result=0;
		conn = DBConnect.getConn();		
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("INSERT INTO subscribe_detail VALUES (null,?,?,?,?,?,?,?,?)");
			ps.setString(1, data.has("fromAccount")?data.getString("fromAccount"):null);
			ps.setString(2, data.has("toAccount")?data.getString("toAccount"):null);
			ps.setString(3, data.has("systemInfo")?data.getString("systemInfo"):null);
			ps.setString(4, data.has("msgType1")?data.getString("msgType1"):null);
			ps.setString(5, data.has("module")?data.getString("module"):null);
			java.sql.Timestamp subTime=new Timestamp(System.currentTimeMillis());
			Long temp=10776000000L;
			java.sql.Timestamp keepTime= new Timestamp(System.currentTimeMillis()+temp);
			ps.setTimestamp(6, keepTime);
			ps.setTimestamp(7, subTime);
			ps.setString(8, data.has("eventType")?data.getString("eventType"):null);
			ps.executeUpdate();
			conn.commit();
			result=1;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
//	获取订阅条件,传入数据：userAccount,返回用户的账号和订阅条件（order_condition),所属模块(module),事件名称（eventType),属性（attribute）
//	示例值：订阅条件：title: &&price:1-50&&production: 所属模块： 店铺-校园二手-电脑；事件名称：商品降价；
	public JSONArray getMsgOrderCondition(String userID) throws JSONException{
		JSONArray result =new JSONArray();
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT userID ,order_condition,subscribe_type2 as attribute," +
					"eventType FROM subscribe WHERE userID='"+userID+"'");
			rs=ps.executeQuery();
			result=CDataTransform.rsToJsonLabel(rs);		
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
	
//	获取订阅消息内容.传入数据：userAccount
//	返回数据：内容（content），消息所属模块（module），发送时间（sendTime），事件名称（eventType）
	public JSONArray getMsgOrderContent(String userID) throws JSONException{
		JSONArray result =new JSONArray();		
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT content,msgType1 as module,sendTime,eventType FROM subscribe_detail WHERE receiver='"+userID+"'");
			rs=ps.executeQuery();
			result=CDataTransform.rsToJsonLabel(rs);		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		
		return result;
	}
	
//	取消订阅.成功返回1，否则返回-1。输入用户uid 和订阅信息（对应subscribe表中的ID）的编号
//	从对应的关注表中删除数据。
//	从订阅表中删除相关记录。（已发送的推送消息不删除）
	public int cancilMsgOrder(JSONObject data) throws JSONException{
		int result=-1;
		try{
			int msgOrderID=data.getInt("msgOrderID");
			SubscribeMessageInfo scribeInfo=cancilMsgOrderProcess(msgOrderID,data.getString("userAccount"));
			JSONArray resultIDset=getResultSetBySubscribeCondition(scribeInfo);
			System.out.println(resultIDset.toString());
			String op=scribeInfo.getEventType();
			int userID=getIDFromUID(scribeInfo.getUserID());
			switch(op){
			case "商品新增":
				processCancilGoodsAdds(resultIDset,userID);
				break;
			case "商品到货":
				processCancilGoodsRefill(resultIDset,userID);
				break;
			case "商品降价":
				processCancilGoodsPriceOff(resultIDset,userID);
				break;
			}
			delMsgOrderInfo(msgOrderID);
			result=1;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
private void delMsgOrderInfo(int msgOrderID) {
		// TODO Auto-generated method stub
		
	}
private void processCancilGoodsPriceOff(JSONArray resultIDset, int userID) {
		// TODO Auto-generated method stub
		
	}
private void processCancilGoodsRefill(JSONArray resultIDset, int userID) {
		// TODO Auto-generated method stub
		
	}
private void processCancilGoodsAdds(JSONArray resultIDset, int userID) {
		// TODO Auto-generated method stub
		
	}
//	获取商品的属性
// 根据表id初始化subscribeMessageInfo对象。	
private SubscribeMessageInfo cancilMsgOrderProcess(int int1, String string) {
	// TODO Auto-generated method stub
	return null;
}
}
