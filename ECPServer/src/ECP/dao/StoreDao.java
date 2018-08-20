package ECP.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.Statement;
import com.sun.star.lib.uno.environments.java.java_environment;

import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

public class StoreDao extends BaseDao{
	private ECP.service.common.CFindService service = new ECP.service.common.CFindService();
	private ECP.dao.MarketDao marketDao=new ECP.dao.MarketDao();
	private ECP.dao.CertificateInfoDao certificateInfoDao=new ECP.dao.CertificateInfoDao();
	public JSONObject setStoreInfo(JSONObject data) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject result=new JSONObject();
		int store_id=-1;
		String tipString=null;
		int flag = 3;
		//如果店面名称未被使用，则在表中插入数据，否则报错。
		try {			
			String store_name = data.getString("store_Name");
			//如果没有同名的店铺，则可以创建新的店铺。
			//如果有相同类型的待审核信息，就需要等审核之后才能提交。
			if(data.getString("store_id").equals("0")&&!findStoreName(store_name)){	
				conn = DBConnect.getConn();
				ps = conn.prepareStatement("insert into shop(shop_id,shop_contact_name,shop_contact_phone," +
						"shop_contact_email,shop_contact_address,shop_name,shop_busi_category,shop_start_date," +
						"shop_expire_date,shop_keyword,shop_introduction,shop_logo,shop_owner,shop_status," +
						"shop_code,del_flag,owner_attachment,shop_flag,shop_attachment,insure_money,license_num," +
						"permit_num,id_cardNO) values (null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
				if(data.has("contact_Name")){
					ps.setString(1,data.getString("contact_Name"));//联系人姓名：shop_contact_name
				}else{
					ps.setString(1,null);
				}
				if(data.has("contact_Phone")){
					ps.setString(2,data.getString("contact_Phone"));//联系人电话：shop_contact_phone
				}else{
					ps.setString(2,null);
				}
				if(data.has("shop_contact_email")){
					ps.setString(3,data.getString("shop_contact_email"));
				}else{
					ps.setString(3,null);
				}
				if(data.has("store_Address")){
					ps.setString(4,data.getString("store_Address"));//地址shop_contact_address
				}else{
					ps.setString(4, null);
				}
				if(data.has("store_Name")){
					ps.setString(5,data.getString("store_Name"));//店面名称：shop_name
				}
				if(data.has("category")){
					ps.setString(6,data.getString("category"));//经营品类：shop_busi_category
				}else{
					ps.setString(6,null);
				}
				if(data.has("shop_start_date")){
					ps.setString(7,data.getString("shop_start_date"));
				}else{
					ps.setString(7,null);
				}
				if(data.has("shop_expire_date")){
					ps.setString(8,data.getString("shop_expire_date"));
				}else{
					ps.setString(8,null);
				}
				if(data.has("keyWord")){
					ps.setString(9,data.getString("keyWord"));//店面关键词：shop_keyword
				}else{
					ps.setString(9,null);
				}
				if(data.has("introduction")){
					ps.setString(10,data.getString("introduction"));//店面简介：shop_introduction
				}else{
					ps.setString(10,null);
				}
				if(data.has("store_Logo")){
					ps.setString(11,data.getString("store_Logo"));//店面LOGO：shop_logo
				}else{
					ps.setString(11,null);
				}
				if(data.has("user_id")){
					ps.setInt(12,data.getInt("user_id"));//店铺拥有者：shop_owner
				}else{
					ps.setString(12,null);
				}
				if(data.has("shop_status")){
					ps.setString(13,data.getString("shop_status"));
				}else{
					ps.setString(13, null);
				}
				if(data.has("shop_code")){
					ps.setString(14,data.getString("shop_code"));
				}else{
					ps.setString(14, null);
				}
				if(data.has("del_flag")){
					ps.setString(15,data.getString("del_flag"));
				}else{
					ps.setString(15, null);
				}
				if(data.has("owner_attachment")){
					ps.setString(16,data.getString("owner_attachment"));
				}else{
					ps.setString(16, null);
				}
				if(data.has("shop_flag")){
					ps.setString(17,data.getString("shop_flag"));
				}else{
					ps.setString(17, null);
				}
				if(data.has("shop_attachment")){
					ps.setString(18,data.getString("shop_attachment"));
				}else{
					ps.setString(18, null);
				}
				if(data.has("insure_money")){
					ps.setString(19,data.getString("insure_money"));
				}else{
					ps.setString(19, null);
				}
				if(data.has("license_num")){
					ps.setString(20,data.getString("license_num"));
				}else{
					ps.setString(20, null);
				}
				if(data.has("permit_num")){
					ps.setString(21,data.getString("permit_num"));	
				}else{
					ps.setString(21, null);
				}
				if(data.has("id_cardNO")){
					ps.setString(22,data.getString("id_cardNO"));	
				}else{
					ps.setString(22, null);
				}
				System.out.println("sql:"+ps.toString());
				ps.executeUpdate();
				//上一个语句执行成功，下面这条语句的执行就不应该有问题。
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					flag=1;
					store_id = rs.getInt(1);
					ps=conn.prepareStatement("insert into shop_audit(shopID,infoType,infoContent,operatorID,operTime) values(" +
							"?,?,?,?,?)");
					ps.setInt(1, store_id);
					ps.setInt(2, 1);
					ps.setString(3, data.toString());
					ps.setInt(4, data.has("user_id")?data.getInt("user_id"):-1);
					java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
					ps.setTimestamp(5, timestamp);
					ps.executeUpdate();
					tipString="店铺新建成功，正在等待审核";
				}					
				
			}else if(shopInfoUnpassed(data.getInt("store_id"),1)){
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("insert into shop_audit(shopID,infoType,infoContent,operatorID,active,operTime) values(" +
						"?,?,?,?,?,?)");
				ps.setInt(1, data.getInt("store_id"));
				ps.setInt(2, 1);
				ps.setString(3, data.toString());
				ps.setInt(4, data.has("user_id")?data.getInt("user_id"):-1);
				ps.setInt(5, 1);
				java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(6, timestamp);
				ps.executeUpdate();	
				ps=conn.prepareStatement("UPDATE shop_audit SET active=2 WHERE auditStatus=2 AND shopID=? AND infoType=?");
				ps.setInt(1, data.getInt("store_id"));
				ps.setInt(2, 1);				
				ps.executeUpdate();
				tipString="店铺新建成功，正在等待审核";
			}else if(!existUnAuthedUpdateRecord(data.getInt("store_id"),1)){
				int fatherID=findfatherID(data.getInt("store_id"),1);
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("insert into shop_audit(shopID,infoType,infoContent,operatorID,operType,active,parentID,operTime" +
						") values(?,?,?,?,?,?,?,?)");
				ps.setInt(1, data.getInt("store_id"));
				ps.setInt(2, 1);
				ps.setString(3, data.toString());
				ps.setInt(4, data.has("user_id")?data.getInt("user_id"):-1);
				ps.setInt(5, 1);
				ps.setInt(6, 0);
				ps.setInt(7, fatherID);
				java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(8, timestamp);
				ps.executeUpdate();	
				//将审核未通过的信息更新为失效。				
				flag=2;
				tipString="店铺信息更新成功，正在等待审核";
			}else{				
				tipString="还有未审核的店铺信息，请勿重复提交申请";
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				tipString="sql语句执行异常";
				e.printStackTrace();
			}catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("系统系统出现非数据库异常。");
				tipString="系统出现非数据库异常";
				e.printStackTrace();
			}finally{
				DBConnect.close(conn,ps,rs);
				DBConnect.close(conn,ps1,rs1);
			}
		result.put("flag", flag);
		result.put("data", store_id);
		result.put("tip", tipString);
		return result;
	}

	private int findfatherID(int storeID, int infoType) {
		// TODO Auto-generated method stub
		int result=0;
		try{
			conn = DBConnect.getConn();
			//如果没有通过审核的新增店铺信息，则返回true。
			ps=conn.prepareStatement(" SELECT max(id) as id FROM shop_audit WHERE shopID=? " +
					"AND infoType=? and auditStatus=1 ");
			ps.setInt(1, storeID);	
			ps.setInt(2, infoType);
			rs=ps.executeQuery();
			if(rs.next()){
				result=rs.getInt("id");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;	
	}

	private boolean shopInfoUnpassed(int storeId,int infoType) {
		// TODO Auto-generated method stub
		boolean result=true;
		try{
			conn = DBConnect.getConn();
			//如果没有通过审核的新增店铺信息，则返回true。
			ps=conn.prepareStatement(" SELECT id FROM shop_audit WHERE shopID=? " +
					"AND infoType=? and operType=0 and auditStatus=1 ");
			ps.setInt(1, storeId);	
			ps.setInt(2, infoType);
			rs=ps.executeQuery();
			if(rs.next()){
				result=false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;	
	}

	private boolean existUnAuthedUpdateRecord(int storeId, int infoType) {
		// TODO Auto-generated method stub
		boolean result=false;
		try{
			conn = DBConnect.getConn();
			//查询未审核（auditStatus=0)的更新(operType=1)店铺（infoType=1)信息。
			ps=conn.prepareStatement(" SELECT id FROM shop_audit WHERE shopID=? " +
					"AND infoType=? AND operType=1 AND auditStatus!=1 ");
			ps.setInt(1, storeId);
			ps.setInt(2, infoType);
			rs=ps.executeQuery();
			if(rs.next()){
				result=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;		
	}

	private boolean findStoreName(String store_name) {
		// TODO Auto-generated method stub
		boolean result=false;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT shop_id FROM shop WHERE shop_name='"+store_name+"'");
			rs=ps.executeQuery();
			if(rs.next()){
//				System.out.println("结果是："+rs.getString(1));
				result=true;
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}

	public int setQualification(JSONObject data) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject data1=new JSONObject();
		JSONObject data2=new JSONObject();
		JSONObject data3=new JSONObject();
		String user_shop=new String();
		int identity_id=0;
		int license_id=0;
		int permit_id=0;
		int result = -1;
		int flag=0;
		int infoType=2;
		try{
			user_shop=data.getString("userId")+"_"+data.getString("store_id");
		}catch(Exception e){
			flag=1;
			e.printStackTrace();
		}
		if(!findStoreQualificationInfo(user_shop)){
		//身份证
			try{
				data1.put("cert_name", data.getString("identity_Name"));
				data1.put("cert_id", data.getString("identity_Num"));
				data1.put("cert_desc", "identity");
				data1.put("cert_path", data.getString("identity_Photo"));
				data1.put("user_id", user_shop);
				data1.put("cert_expire", data.has("identity_ExpiryDate")?data.getString("identity_ExpiryDate"):"");
				certificateInfoDao.insert(data1);
				identity_id=certificateInfoDao.findIDByUserId(user_shop, data.getString("identity_Name"));
			}catch(Exception e){
				flag=1;
				e.printStackTrace();
			}
			//营业执照
			try{
				data2.put("cert_name", data.getString("license_Name"));
				data2.put("cert_id", data.getString("license_Num"));
				data2.put("cert_desc", data.getString("license_Type"));
				data2.put("cert_path", data.getString("license_Photo"));
				data2.put("cert_address", data.getString("license_Address"));
				data2.put("cert_type", "store");
				data2.put("user_id", user_shop);
				data2.put("cert_expire", data.has("license_ExpiryDate")?data.getString("license_ExpiryDate"):"");
				certificateInfoDao.insert(data2);
				license_id=certificateInfoDao.findIDByUserId(user_shop, data.getString("license_Name"));
			}catch(Exception e){
				flag=1;
				e.printStackTrace();
			}
			//许可证
			try{
				data3.put("cert_name", data.getString("permit_Name"));
				data3.put("cert_id", data.getString("permit_Num"));
				data3.put("cert_desc", "permit");
				data3.put("cert_path", data.getString("permit_Photo"));
				data3.put("cert_address", data.getString("permit_Address"));
				data3.put("cert_type", "store");
				data3.put("user_id", user_shop);
				data3.put("cert_expire", data.has("permit_ExpiryDate")?data.getString("permit_ExpiryDate"):"");
				certificateInfoDao.insert(data3);
				permit_id=certificateInfoDao.findIDByUserId(user_shop, data.getString("permit_Name"));
			}catch(Exception e){
				flag=1;
				e.printStackTrace();
			}		
			conn = DBConnect.getConn();
			
			try{
				//将操作信息写入审核表
				try{
					ps=conn.prepareStatement("insert into shop_audit(shopID,infoType,infoContent,operatorID,active,operTime) values(" +
							"?,?,?,?,?,?)");
					ps.setInt(1, Integer.parseInt(data.getString("store_id")));
					ps.setInt(2, 2);
					ps.setString(3, data.toString());
					ps.setInt(4, data.getInt("userId"));
					ps.setInt(5, 0);
					java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
					ps.setTimestamp(6, timestamp);
					ps.executeUpdate();
					
				}catch(SQLException e){
					e.printStackTrace();
				}
				StringBuilder sql=new StringBuilder("update shop set  id_cardNO =? and license_num =? and permit_Num=?" +
						" where shop_id =?");
				ps = conn.prepareStatement("update shop set  id_cardNO =?, license_num =?, permit_Num=?" +
						" where shop_id =?");
				ps.setString(1, String.valueOf(identity_id));
				ps.setString(2, String.valueOf(license_id));
				ps.setString(3, String.valueOf(permit_id));
				ps.setInt(4, Integer.parseInt(data.getString("store_id")));
				result=ps.executeUpdate();
	//			System.out.println("user_shop:"+user_shop+"\n identity_id:"+identity_id+"\n license_id:"+license_id+
	//					"\n permit_id:"+permit_id);
				System.out.println(sql.toString());	
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				DBConnect.close(conn,ps,rs);
			}	
		}else if(shopInfoUnpassed(data.getInt("store_id"), 2)){
			try{
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("insert into shop_audit(shopID,infoType,infoContent,operatorID,active,operTime) values(" +
						"?,?,?,?,?,?)");
				ps.setInt(1, Integer.parseInt(data.getString("store_id")));
				ps.setInt(2, 2);
				ps.setString(3, data.toString());
				ps.setInt(4, data.getInt("userId"));
				ps.setInt(5, 1);
				java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(6, timestamp);
				ps.executeUpdate();
				ps=conn.prepareStatement("UPDATE shop_audit SET active=2 WHERE auditStatus=2 AND shopID=? AND infoType=?");
				ps.setInt(1, data.getInt("store_id"));
				ps.setInt(2, 2);				
				result=ps.executeUpdate();	
				//更新文件，审核
				result=result>0?0:result;
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(!existUnAuthedUpdateRecord(data.getInt("store_id"),2)){
			try {
				int fatherID=findfatherID(data.getInt("store_id"),2);
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("insert into shop_audit(shopID,infoType,infoContent,operatorID,operType,active,parentID,operTime)" +
						" values(?,?,?,?,?,?,?,?)");
				ps.setInt(1, data.getInt("store_id"));
				ps.setInt(2, 2);
				ps.setString(3, data.toString());
				ps.setInt(4, data.getInt("userId"));
				ps.setInt(5, 1);
				ps.setInt(6, 0);
				ps.setInt(7, fatherID);
				java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(8, timestamp);
				result=ps.executeUpdate();				
				//更新文件，审核
				result=result>0?0:result;
			} catch (SQLException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				DBConnect.close(conn,ps,rs);
			}
		}else{
			result=-1;
		}
		return result;
	}
	private boolean findStoreQualificationInfo(String user_shop) {
		// TODO Auto-generated method stub
		boolean result=false;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT id FROM certificate_info WHERE user_id='"+user_shop+"'");
			rs=ps.executeQuery();
			if(rs.next()){
//				System.out.println("结果是："+rs.getString(1));
				result=true;
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}

	private String decodePothoString(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public String insertCertificate(JSONObject data) {
		conn = DBConnect.getConn();
		String result = null;
		try{
			StringBuilder sql=new StringBuilder("inset into certificate_info(cert_id,cert_name," +
					" cert_expire,cert_path,cert_address,user_id,cert_type,cert_desc) Values(?,?,?,?,?,?,?,?)");
			ps = conn.prepareStatement(sql.toString());			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}

	public int setDelivery(JSONObject data) {
		// TODO Auto-generated method stub
		//插入数据之前要查询是否已经存在记录。这个貌似要写个函数。
		conn = DBConnect.getConn();
		int result = -1;
		int result1 = -1,result2=-1,result3=-1,result4=-1;
		try{
			int shop_id = data.getInt("store_id");
			System.out.println(data.getString("self_Delivery").equals("是"));
			if(data.getString("self_Delivery").equals("是")){
				result1=insertDelivery(shop_id,"self_Delivery");				
			}
			if(data.getString("ordinary_Delivery").equals("是")){
				result2=insertDelivery(shop_id,"ordinary_Delivery");
			}
			if(data.getString("sameDay_Delivery").equals("是")){
				result3=insertDelivery(shop_id,"sameDay_Delivery");
			}
			if(data.getString("timeLimit_Delivery").equals("是")){
				result4=insertDelivery(shop_id,"timeLimit_Delivery");
			}
			//这里的返回值不是太正确，现在的含义只是前面的语句执行没有报错，然后返回成功。
			result = 1;			
		}catch(Exception e){
			result = 0;
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	//下面三个函数处理物流方式插入和解码过程。
	public int insertDelivery(int shop_id,String deliveryType) {
		// TODO Auto-generated method stub
		//将数据插入物流方式表中,如果插入成功则返回1，否则-1.
//		conn = DBConnect.getConn();
		int result = 1;
		try{
			 int deliveryFlag = findDelivery(shop_id, deliveryType);
			 if(deliveryFlag == 1){
				 return result;
			 }else{
				 String module = "deliveryType";
				 int deliveryCode = findCode(module, deliveryType);
				 StringBuilder sql=new StringBuilder("insert into delivery(shop_id,delivery_type)" +
				 		" values("+shop_id+","+deliveryCode+")");
				 System.out.println("insertDelivery的输出："+sql.toString());
				 //前面的两个函数findDelivery和findCode也调用的数据库连接，并关掉了，有可能是因为都是从basedao中继承下来的，所以如果在一开始的位置建立conn
				 //时会被关掉。而在下面建立连接则没问题。
				 conn = DBConnect.getConn();
				 ps=conn.prepareStatement(sql.toString());
				 ps.executeUpdate();				
			 }
		}catch(Exception e){
			result = -1;
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	public int findDelivery(int shop_id,String deliveryType) {
		// TODO Auto-generated method stub
		//查询物流方式是否已经存在
		conn = DBConnect.getConn();
		int result = -1;
		try{
			 StringBuilder sql = new StringBuilder("select * from delivery where shop_id = "+shop_id+
					 " and delivery_type ='"+deliveryType+"'");
			 ps = conn.prepareStatement(sql.toString());
			 rs = ps.executeQuery();
			 if(rs.next()){
				 result = 1;
			 }
			 System.out.println("findDelivery的输出："+sql.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	public int findCode(String module,String code_name) {
		// TODO Auto-generated method stub
		//查询物流方式是否已经存在
		conn = DBConnect.getConn();
		int result = -1;
		try{
			 StringBuilder sql = new StringBuilder("select code from coding where code_name = '"+code_name+
					 "' and module = '"+ module + "'");
			 ps = conn.prepareStatement(sql.toString());
			 rs = ps.executeQuery();
			 System.out.println("编码的查询结果："+sql.toString());
			 if(rs.next()){
				 result = rs.getInt("code");
				 System.out.println("result:"+result);
			 }			 
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	public JSONObject setProductInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		int product_id=0;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了store_Id字段。这个字段还需要与店铺表验证是否匹配。
			if(data.has("store_id")){
				int store_id = data.getInt("store_id");				
				conn = DBConnect.getConn();					
				ps=conn.prepareStatement("insert into product(product_id,store_id,goods_Photo,goods_Category,goods_Name,goods_Price," +
						"package_Price,goods_Stock,amount) values(null,?,?,?,?,?,?,?,?) ",Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, store_id);
				ps.setString(2, data.getString("goods_Photo"));
				ps.setInt(3, data.getInt("goods_Category"));
				ps.setString(4, data.getString("goods_Name"));
				ps.setInt(5, data.getInt("goods_Price"));
				ps.setInt(6, data.getInt("package_Price"));
				ps.setInt(7, data.getInt("goods_Stock"));
				ps.setInt(8, 0);
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					flag=1;
					product_id = rs.getInt(1);
				}
				tip="数据插入成功";
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
			result.put("data", product_id);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	public JSONObject setBankInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了store_Id字段。这个字段还需要与店铺表验证是否匹配。
			int store_id = data.getInt("store_id");	
			if(!findStoreBankInfo(store_id)){							
				conn = DBConnect.getConn();					
				ps=conn.prepareStatement("insert into bank(store_id,account_Type,account_Name,account_Num,account_Bank," +
						"account_SubBank,user_id) values(?,?,?,?,?,?,?) ");
				ps.setInt(1, store_id);
				ps.setInt(2, data.getInt("account_Type"));
				ps.setString(3, data.getString("account_Name"));
				ps.setString(4, data.getString("account_Num"));
				ps.setString(5, data.getString("account_Bank"));
				ps.setString(6, data.getString("account_SubBank"));
				ps.setInt(7, data.getInt("userId"));
				ps.executeUpdate();				
				ps=conn.prepareStatement("update shop set completeFlag=1 where shop_id="+store_id);
				ps.executeUpdate();

				ps=conn.prepareStatement("insert into shop_audit(shopID,infoType,infoContent,operatorID,active,operTime) values(" +
						"?,?,?,?,?,?)");
				ps.setInt(1, store_id);
				ps.setInt(2, 3);
				ps.setString(3, data.toString());
				ps.setInt(4, data.getInt("userId"));
				ps.setInt(5, 0);
				java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(6, timestamp);
				ps.executeUpdate();				
				tip="数据插入成功";
			}else if(shopInfoUnpassed(store_id, 3)){
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("insert into shop_audit(shopID,infoType,infoContent,operatorID,active,operTime) values(" +
						"?,?,?,?,?,?)");
				ps.setInt(1, store_id);
				ps.setInt(2, 3);
				ps.setString(3, data.toString());
				ps.setInt(4, data.getInt("userId"));
				ps.setInt(5, 0);
				java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(6, timestamp);
				ps.executeUpdate();	
				ps=conn.prepareStatement("UPDATE shop_audit SET active=2 WHERE auditStatus=2 AND shopID=? AND infoType=?");
				ps.setInt(1, store_id);
				ps.setInt(2, 3);				
				ps.executeUpdate();
				tip="数据插入成功";
			}else if(!existUnAuthedUpdateRecord(store_id, 3)){
				int fatherID=findfatherID(store_id,3);
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("insert into shop_audit(shopID,infoType,infoContent,operatorID,operType,active,parentID,operTime)" +
						" values(?,?,?,?,?,?,?,?)");
				ps.setInt(1, store_id);
				ps.setInt(2, 3);
				ps.setString(3, data.toString());
				ps.setInt(4, data.getInt("userId"));
				ps.setInt(5, 1);	
				ps.setInt(6, 0);
				ps.setInt(7, fatherID);
				java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(8, timestamp);
				ps.executeUpdate();				
				tip="数据更新成功";
			}
			else{
				tip="存在未审核的支付信息";
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
	
	private boolean findStoreBankInfo(int store_id) {
		// TODO Auto-generated method stub
		boolean result=false;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT id FROM bank WHERE store_id="+store_id);
			rs=ps.executeQuery();
			if(rs.next()){
//				System.out.println("结果是："+rs.getString(1));
				result=true;
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("测试StoreDao中的小函数");
		StoreDao storeDao = new StoreDao();
		String module = "deliveryType";
		String code_name = "self_Delivery";
		JSONObject data=new JSONObject();
		String string="[{originPrice:'12:23', goodDesc:为, goodStock:12, goodProduction:广东省广州市天河区, price:12, goodCategory:教材-医学类, goodName:15}]";
		JSONArray sArray=new JSONArray(string);
		System.out.println("***"+sArray.toString());
//		data.put("goodData", string);
//		data.put("storeName", "");
		data.put("storeId", "2");
		data.put("goodId", "4");
//		data.put("goods_Photo", "广东省广州市天河区&&华南理工大学大学城校区");
		data.put("goodCategory", "校园二手-教材");
		data.put("goodName", "欧中贵");
		data.put("price", "82.0");
		data.put("goodDesc", "");
		data.put("originPrice", "451582");
		data.put("goodProduction", "广东");
//		data.put("package_Price", "451582");
		data.put("goodStock", "451582");
//		data.put("account_Type", "1");
//		data.put("account_Name", "0daf d");
//		data.put("account_Num", "欧中贵");
//		data.put("account_Bank", "451582");
//		data.put("account_SubBank", "451582");
	
		int shop_id = 1;
		JSONObject result = new JSONObject();
		List<String> result1 = new ArrayList<>();
//		result = storeDao.findCode(module,code_name);
//		result = storeDao.findDelivery(shop_id,code_name);
//		result = storeDao.insertDelivery(shop_id,code_name);
//		result = storeDao.setProductInfo(data);
//		result = storeDao.setBankInfo(data);
//		result = storeDao.setSubmitGoodsIformation(data);
//		result = storeDao.getGoodsList(data);
//		result = storeDao.setEditGoodsIformation(data);
		result = storeDao.getDelivery(data);
//		result1 = storeDao.findUserPayAttentionToPriceOff(4, 50.0);
		System.out.println("结果是："+result.toString());
	}

	public JSONObject setSubmitGoodsIformation(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		int product_id=0;
		try{
			//如果发送了userId字段。
			if(data.has("store_id")){
				int store_id = data.getInt("store_id");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
					JSONArray goodArray=new JSONArray(data.getString("goodData"));
											
					conn = DBConnect.getConn();
					for(int i=0;i<goodArray.length();i++){
						//下单时，如果商品在购物车中存在，则下单后删除购物车中记录。
//						int goodID=goodArray.getJSONObject(i).getInt("goodId");
						ps=conn.prepareStatement("INSERT INTO product(product_id,store_id,goods_Name,goods_Category,"+
								"description,goods_Price,org_price,production,submitTime,goods_Stock,goods_Photo)" +
								" VALUES(null,?,?,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
//						ps.setInt(1, (Integer) null);
						ps.setInt(1, store_id);
						ps.setString(2, goodArray.getJSONObject(i).getString("goodName"));
						ps.setString(3, goodArray.getJSONObject(i).getString("goodCategory"));
						ps.setString(4, goodArray.getJSONObject(i).getString("goodDesc"));
						ps.setDouble(5, goodArray.getJSONObject(i).getDouble("price"));
						ps.setDouble(6, goodArray.getJSONObject(i).getDouble("originPrice"));
						ps.setString(7, goodArray.getJSONObject(i).getString("goodProduction"));						
						java.sql.Timestamp subTimestamp=new Timestamp(System.currentTimeMillis());
						ps.setTimestamp(8, subTimestamp);
						ps.setInt(9, goodArray.getJSONObject(i).getInt("goodStock"));
						ps.setString(10, goodArray.getJSONObject(i).getString("goodPic"));
						ps.executeUpdate();						
						rs = ps.getGeneratedKeys();
						if (rs.next()) {
							flag=1;
							product_id = rs.getInt(1);
						}
					}
					List<String> userList=findUserPayAttentionToGoodsAdd(store_id);
					System.out.println("**"+userList);
					Iterator<String> it=userList.iterator();
					while(it.hasNext()){
					//推送内容： 亲，你收藏的xxxxx商品（商品名字）降价了，点击查看
						JSONObject dataPush=new JSONObject();
						StringBuilder systemInfo=new StringBuilder(" 亲，关注的店铺有新的商品了，点击查看");
						dataPush.put("eventType", "商品新增");
						dataPush.put("module", "集市");
						String account=findUserAcouunt(it.next());
						dataPush.put("toAccount", account);
						dataPush.put("systemInfo", systemInfo);
						service.push(dataPush);
					}
//					sqlData= CDataTransform.rsToJson(rs);
					tip="下单成功";
					
				}else{
					flag=1;
					tip="用户id不存在";
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
			result.put("data", product_id);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		return result;
	}

	private List<String> findUserPayAttentionToGoodsAdd(int store_id) {
		// TODO Auto-generated method stub
		List<String> result=new ArrayList<>();
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT user_id FROM store_attention WHERE state=2 and shop_id="+store_id);
			rs=ps.executeQuery();
			while(rs.next()){
//				System.out.println("结果是："+rs.getString(1));
				result.add(rs.getString(1));
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}

	public JSONObject getGoodsList(JSONObject data) {		
		// TODO Auto-generated method stub
				JSONObject	result=new JSONObject();
				int flag=0;
				String tip=null;
				JSONArray sqlData=new JSONArray();				
				try{
					//如果发送了userId字段。
					if(data.has("store_id")){
						int store_id = data.getInt("store_id");
						
						//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
						
							conn = DBConnect.getConn();
							ps=conn.prepareStatement("SELECT product_id AS goodId,goods_Name AS goodName,goods_Category "+
									" AS goodCategory,goods_Price AS price, goods_Stock as goodStock,production as goodProduction, "+
									" goods_Photo as goodPic FROM product WHERE store_id="+store_id);
							rs=ps.executeQuery();
							sqlData= CDataTransform.rsToJsonLabel(rs);
							tip = "信息获取成功";						
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

	public JSONObject getGoodsIformation(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();				
		try{
			//如果发送了userId字段。
			if(data.has("store_id")){
				int store_id = data.getInt("store_id");
				int good_id=data.getInt("goodId");
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("SELECT product_id AS goodId,goods_Name AS goodName,goods_Category "+
							" AS goodCategory, goods_Stock as goodStock,goods_Price AS price,org_price AS originPrice,production AS goodProduction,description AS goodDesc "+
							" FROM product WHERE store_id="+store_id+" and product_id="+good_id);
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip = "信息获取成功";						
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

	public JSONObject setEditGoodsIformation(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		int update=0;
		try{
			//如果发送了userId字段。
			if(data.has("store_id")){
				int store_id = data.getInt("store_id");
				int good_id=data.getInt("goodId");
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
//				产品降价/升价时，修改的价格变成商品的“价格”。商品的原价表示商品的进价或成本价，由前端店铺更新。
//				如果商品价格下降，触发消息推送。查找商品收藏表，过滤那些收藏价格高于商品现在价格的用户，向他们推送消息。
				Double goods_price = data.getDouble("price");
				String goods_name = data.getString("goodName");
				int goodOriginStock=findGoodOriginStock(good_id);
				int currentStock=data.getInt("goodStock");
				conn = DBConnect.getConn();
				StringBuilder sqlBuilder=new StringBuilder("update product set goods_Name='"+goods_name+"'" +
						", goods_Category='"+data.getString("goodCategory")+"', description ='"+data.getString("goodDesc")+"'" +
						", goods_Price="+goods_price+",goods_Stock="+data.getInt("goodStock")+", org_price="+data.getDouble("originPrice")+
						", Production='"+data.getString("goodProduction")+"',goods_Photo='"+data.getString("goodPic")+"' WHERE store_id="+store_id+" and product_id="+good_id);
						
				ps=conn.prepareStatement(sqlBuilder.toString());
				update=ps.executeUpdate();				
				List<String> userList=findUserPayAttentionToPriceOff(good_id,goods_price);
				System.out.println("**"+userList);
				Iterator<String> it=userList.iterator();
				while(it.hasNext()){
				//推送内容： 亲，你收藏的xxxxx商品（商品名字）降价了，点击查看
					JSONObject dataPush=new JSONObject();
					StringBuilder systemInfo=new StringBuilder(" 亲，你收藏的"+goods_name+"商品降价了，点击查看");
					dataPush.put("eventType", "商品降价");
					dataPush.put("module", "集市");
					String account=findUserAcouunt(it.next());
					dataPush.put("toAccount", account);
					dataPush.put("systemInfo", systemInfo);
					service.push(dataPush);
				}
				if(goodOriginStock==0&&currentStock>0){
					List<String> userList1=findUserPayAttentionToStock(good_id);
					Iterator<String> it1=userList1.iterator();
					while(it1.hasNext()){
						//推送内容： 亲，你收藏的xxxxx商品（商品名字）降价了，点击查看
							JSONObject dataPush=new JSONObject();
							StringBuilder systemInfo=new StringBuilder(" 亲，你收藏的"+goods_name+"商品现在有货了，点击查看");
							dataPush.put("eventType", "商品到货");
							dataPush.put("module", "集市");
							String account=findUserAcouunt(it1.next());
							dataPush.put("toAccount", account);
							dataPush.put("systemInfo", systemInfo);
							service.push(dataPush);
						}
				}
//				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";						
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
			result.put("data", update);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	private String findUserAcouunt(String next) {
		// TODO Auto-generated method stub
		String result=null;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT uid FROM user_info WHERE id="+String.valueOf(next));
			rs=ps.executeQuery();
			if(rs.next()){
//				System.out.println("结果是："+rs.getString(1));
				result=rs.getString(1);
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}

	private List<String> findUserPayAttentionToStock(int good_id) {
		// TODO Auto-generated method stub
		List<String> result=new ArrayList<>();
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT user_id FROM goods_collection WHERE (collectType=0 or collectType=1) and product_id="+good_id);
			rs=ps.executeQuery();
			while(rs.next()){
//				System.out.println("结果是："+rs.getString(1));
				result.add(rs.getString(1));
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}

	private int findGoodOriginStock(int good_id) {
		// TODO Auto-generated method stub
		int result=-1;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT goods_Stock FROM product WHERE product_id="+good_id);
			rs=ps.executeQuery();
			while(rs.next()){
//				System.out.println("结果是："+rs.getString(1));
				result = rs.getInt(1);
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}

	private List<String> findUserPayAttentionToPriceOff(int good_id,
			Double goods_price) {
		// TODO Auto-generated method stub
		List<String> result=new ArrayList<>();
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT user_id FROM goods_collection WHERE (collectType=0 or collectType=2) and product_id="+good_id+" AND goodPrice>"+goods_price);
			rs=ps.executeQuery();
			while(rs.next()){
//				System.out.println("结果是："+rs.getString(1));
				result.add(rs.getString(1));
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}

	public JSONObject setDeleteGoodsIformation(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();				
		try{
			//如果发送了userId字段。
			if(data.has("store_id")){
				int store_id = data.getInt("store_id");
				int good_id=data.getInt("goodId");
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				
				conn = DBConnect.getConn();
				StringBuilder sqlBuilder=new StringBuilder("delete from product WHERE store_id="+store_id+" and product_id="+good_id);
						
				ps=conn.prepareStatement(sqlBuilder.toString());
				ps.executeUpdate();
//				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";						
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

	public JSONObject getOrdersList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("store_id")){
				int user_id = data.getInt("store_id");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
					int orderState = data.getInt("orderState");		
					StringBuffer sql = new StringBuffer("SELECT orderId,orderState,storeId,storeName,goodId,goodDesc,price,originPrice,"+
							"goodCount,deliveryPrice,totalMoney,goodCategory,p.goods_Photo as goodPic,p.goods_Name as goodName" +
							" FROM market_order m,product p WHERE 1=1 and m.goodId=p.product_id and after_sale=0 ");
					if(orderState == -1){
						sql.append(" and storeId= "+user_id);
					}
					else{
						sql.append(" and orderState="+orderState+" AND storeId= "+user_id);
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
				if(marketDao.findUserByID(user_id)){
					//用户的这些信息都不允许为空，为空的话下面的写法就有问题。
//					int ID = data.getInt("id");					
					// 这里假设商品的编号是全局唯一的。而且每个店铺的商品编号都不同。
					int orderId=data.getInt("orderId");
					int storeId=data.getInt("store_id");
					conn = DBConnect.getConn();					
					ps=conn.prepareStatement("SELECT addressName,addressPhone,addressDetail,orderId,"+
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
		//这个动作的实现还需要斟酌一下。
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(marketDao.findUserByID(user_id)){
					//谁可以做删除订单的这个动作？貌似店铺不可以。
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

	public JSONObject setDeliveryInfo(JSONObject data) {
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
				if(marketDao.findUserByID(user_id)){
					//谁可以做删除订单的这个动作？貌似店铺不可以。
					int orderId=data.getInt("orderId");
					int storeId=data.getInt("store_id");					
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("Update market_order set deliveryCategory=?,deliveryNum=? WHERE userid="+user_id+" AND orderId="+orderId);
					ps.setString(1, data.getString("deliveryCategory"));
					ps.setString(2, data.getString("deliveryNum"));
					ps.executeUpdate();
	//					sqlData= CDataTransform.rsToJson(rs);
					tip="录入成功";
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

	public JSONObject getStoreList(JSONObject data) {
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
				if(marketDao.findUserByID(user_id)){
					//谁可以做删除订单的这个动作？貌似店铺不可以。
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("select shop_id as storeId,shop_name as storeName from shop WHERE completeFlag=1 and shop_owner="+user_id);
					
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

	public JSONObject getStoreInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");
				int storeId =data.getInt("store_id");
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(marketDao.findUserByID(user_id)){
					//谁可以做删除订单的这个动作？貌似店铺不可以。
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("SELECT shop_id AS storeId,shop_name AS storeName,shop_contact_name AS contact_Name,"+
							" shop_contact_phone AS contact_Phone,shop_busi_category AS category,"+
							" shop_contact_address AS storeAddress,shop_keyword AS keyWord,"+
							" shop_introduction AS introduction,shop_logo AS store_logo,auditFlag "+
							" FROM shop WHERE shop_id="+storeId+" AND shop_owner ="+user_id);
					
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

	public JSONObject getQualificationInfo(JSONObject data) {
		// TODO Auto-generated method stub
		return null;
	}

	public JSONObject getDistributionInfo(JSONObject data) {
		// TODO Auto-generated method stub
		return null;
	}

	public JSONObject getCollectionInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();				
		try{
			//如果发送了userId字段。
			if(data.has("store_id")){
				int store_id = data.getInt("store_id");
				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("SELECT store_id AS storeId,account_Type,account_Name,account_Num,"+
							" accountBank,account_SubBank,auditFlag FROM bank WHERE store_id="+store_id);
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip = "信息获取成功";						
			}else{
				flag=1;
				tip="没有用户id";
			}
			
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
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	public int setQualificationDoc(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject data1=new JSONObject();
		JSONObject data2=new JSONObject();
		JSONObject data3=new JSONObject();
		String user_shop=new String();
		int flag=0;
		try{
			user_shop=data.getString("userId")+data.getString("store_id");
		}catch(Exception e){
			flag=1;
			e.printStackTrace();
		}
		//身份证
		try{
			data1.put("cert_name", data.getString("identity_Name"));
			data1.put("cert_id", data.getString("identity_Num"));
			data1.put("cert_desc", "identity");
			data1.put("cert_path", data.getString("requirement_doc0"));
			data1.put("user_id", user_shop);
			certificateInfoDao.insert(data1);
		}catch(Exception e){
			flag=1;
			e.printStackTrace();
		}
		//营业执照
		try{
			data2.put("cert_name", data.getString("license_Name"));
			data2.put("cert_id", data.getString("license_Num"));
			data2.put("cert_desc", "license");
			data2.put("cert_path", data.getString("requirement_doc1"));
			data2.put("cert_address", data.getString("license_Address"));
			data2.put("cert_type", "store");
			data2.put("user_id", user_shop);
			certificateInfoDao.insert(data2);
		}catch(Exception e){
			flag=1;
			e.printStackTrace();
		}
		//许可证
		try{
			data3.put("cert_name", data.getString("permit_Name"));
			data3.put("cert_id", data.getString("permit_Num"));
			data3.put("cert_desc", "permit");
			data3.put("cert_path", data.getString("requirement_doc2"));
			data3.put("cert_address", data.getString("permit_Address"));
			data3.put("cert_type", "store");
			data3.put("user_id", user_shop);
			certificateInfoDao.insert(data3);
		}catch(Exception e){
			flag=1;
			e.printStackTrace();
		}
		return flag;
	}

	public JSONObject setOrderState(JSONObject data) {
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
				if(marketDao.findUserByID(user_id)){
					//谁可以做删除订单的这个动作？貌似店铺不可以。
					int orderId=data.getInt("orderId");
					int storeId=data.getInt("store_id");					
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("Update market_order set orderState=? WHERE orderId="+orderId);
					ps.setString(1, data.getString("orderState"));
					
					ps.executeUpdate();
	//					sqlData= CDataTransform.rsToJson(rs);
					tip="录入成功";
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

	public JSONObject getQualification(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();				
		try{
			//如果发送了userId字段。
			if(data.has("store_id")){
				String user_shop=data.getString("userId")+"_"+data.getString("store_id");
				JSONObject data4=findQualicationCertID(data.getString("store_id"));
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT * FROM ( SELECT cert_name AS identity_Name,cert_id AS identity_Num," +
						"cert_path AS identity_path,auditFlag as cert_audit FROM certificate_info WHERE user_id='"+user_shop+"' AND id="+data4.getInt("id_cardNO")+") a,"+
						"(SELECT cert_name AS license_Name,cert_id AS license_Num,cert_path AS license_path ," +
						"cert_address AS license_Address,auditFlag as license_audit,cert_desc as license_Type,cert_expire as license_ExpiryDate" +
						" FROM certificate_info WHERE user_id='"+user_shop+"' AND id="+data4.getInt("license_num")+") b,"+
						"(SELECT cert_name AS permit_Name,cert_id AS permit_Num,cert_path AS permit_path," +
						"cert_address AS permit_Address,auditFlag as permit_audit,cert_expire as permit_ExpiryDate" +
						" FROM certificate_info WHERE user_id='"+user_shop+"' AND id="+data4.getInt("permit_num")+") c");
				rs=ps.executeQuery();
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";						
			}else{
				flag=1;
				tip="没有用户id";
			}
			
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
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject getDelivery(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();				
		try{
			//如果发送了userId字段。
			if(data.has("store_id")){
				int shop_id=data.getInt("store_id");
				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				
//				conn = DBConnect.getConn();
//				ps=conn.prepareStatement("SELECT DISTINCT CASE WHEN delivery_type=1 THEN 'self_Delivery' 	WHEN delivery_type=2 THEN 'ordinary_Delivery' " +
//						" WHEN delivery_type=3 THEN 'sameDay_Delivery' 	WHEN delivery_type=4 THEN 'timelimit_Delivery' END AS Delivery_type  FROM delivery WHERE shop_id="+shop_id);
//				rs=ps.executeQuery();
				if(findDelivery(shop_id, "1")==1){
					JSONObject temp=new JSONObject();
					temp.put("self_Delivery", 1);
					sqlData.put(temp);
				}else{
					JSONObject temp=new JSONObject();
					temp.put("self_Delivery", 0);
					sqlData.put(temp);
				}
				if(findDelivery(shop_id, "2")==1){
					JSONObject temp=new JSONObject();
					temp.put("ordinary_Delivery", 1);
					sqlData.put(temp);
				}else{
					JSONObject temp=new JSONObject();
					temp.put("ordinary_Delivery", 0);
					sqlData.put(temp);
				}
				if(findDelivery(shop_id, "3")==1){
					JSONObject temp=new JSONObject();
					temp.put("sameDay_Delivery", 1);
					sqlData.put(temp);
				}else{
					JSONObject temp=new JSONObject();
					temp.put("sameDay_Delivery", 0);
					sqlData.put(temp);
				}
				if(findDelivery(shop_id, "4")==1){
					JSONObject temp=new JSONObject();
					temp.put("timelimit_Delivery", 1);
					sqlData.put(temp);
				}else{
					JSONObject temp=new JSONObject();
					temp.put("timelimit_Delivery", 0);
					sqlData.put(temp);
				}
//				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";						
			}else{
				flag=1;
				tip="没有用户id";
			}
			
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
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject getBankInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();				
		try{
			//如果发送了userId字段。
			if(data.has("store_id")){
				int userID=data.getInt("userId");
				int shopID=data.getInt("store_id");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT account_Type,account_Name,account_Num,account_Bank,account_SubBank,auditFlag from bank WHERE user_id="+userID+" AND store_id="+shopID);
				rs=ps.executeQuery();
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";						
			}else{
				flag=1;
				tip="没有用户id";
			}
			
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
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	public void updateBankInfo(JSONObject data) {
		// TODO Auto-generated method stub		
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("UPDATE bank SET account_Type=?,account_Name=?,account_num=?," +
					"account_Bank=?,account_SubBank=? WHERE store_id=?");			
			ps.setInt(1, data.getInt("account_Type"));
			ps.setString(2, data.getString("account_Name"));
			ps.setString(3, data.getString("account_Num"));
			ps.setString(4, data.getString("account_Bank"));
			ps.setString(5, data.getString("account_SubBank"));
			ps.setInt(6, data.getInt("store_id"));
			ps.executeUpdate();	
					
		} catch (SQLException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}	
		System.out.println("账户信息更新完成");	
	}

	public void updateQualificationInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject data1=new JSONObject();
		JSONObject data2=new JSONObject();
		JSONObject data3=new JSONObject();
		JSONObject data4=new JSONObject();
		String user_shop=new String();
		try{
			user_shop=data.getString("userId")+"_"+data.getString("store_id");
			data4=findQualicationCertID(data.getString("store_id"));
		}catch(Exception e){			
			e.printStackTrace();
		}
		
		try{
			data1.put("id", data4.getString("id_cardNO"));
			data1.put("cert_name", data.getString("identity_Name"));
			data1.put("cert_id", data.getString("identity_Num"));
			data1.put("cert_desc", "identity");
			data1.put("cert_path", decodePothoString(data.getString("identity_Photo")));
			data1.put("user_id", user_shop);
			data1.put("cert_expire", data.has("identity_ExpiryDate")?data.getString("identity_ExpiryDate"):"");
			certificateInfoDao.update(data1);
			
		}catch(Exception e){			
			e.printStackTrace();
		}
		//营业执照
		try{
			data2.put("id", data4.getString("license_Num"));
			data2.put("cert_name", data.getString("license_Name"));
			data2.put("cert_id", data.getString("license_Num"));
			data2.put("cert_desc", "license");
			data2.put("cert_path", data.getString("license_Photo"));
			data2.put("cert_address", data.getString("license_Address"));
			data2.put("cert_type", "store");
			data2.put("user_id", user_shop);
			data2.put("cert_expire", data.has("license_ExpiryDate")?data.getString("license_ExpiryDate"):"");
			certificateInfoDao.update(data2);			
		}catch(Exception e){			
			e.printStackTrace();
		}
		//许可证
		try{
			data3.put("id", data4.getString("permit_Num"));
			data3.put("cert_name", data.getString("permit_Name"));
			data3.put("cert_id", data.getString("permit_Num"));
			data3.put("cert_desc", "permit");
			data3.put("cert_path", data.getString("permit_Photo"));
			data3.put("cert_address", data.getString("permit_Address"));
			data3.put("cert_type", "store");
			data3.put("user_id", user_shop);
			data3.put("cert_expire", data.has("permit_ExpiryDate")?data.getString("permit_ExpiryDate"):"");
			certificateInfoDao.update(data3);			
		}catch(Exception e){			
			e.printStackTrace();
		}	
		System.out.println("资质信息更新完成");	
	}

	private JSONObject findQualicationCertID(String string) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();		
		JSONArray sqlData=new JSONArray();				
		try{	
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT id_cardNO,license_num,permit_num from shop where shop_id="+string);
			rs=ps.executeQuery();
			sqlData= CDataTransform.rsToJsonLabel(rs);
			result=sqlData.getJSONObject(0);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}

	public void updateShopInfo(JSONObject data) {
		// TODO Auto-generated method stub
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("UPDATE shop SET shop_contact_name=?,shop_contact_phone=?,shop_contact_address=?,shop_name=?,"+
					" shop_busi_category=?,shop_keyword=?,shop_introduction=?,shop_logo=?,shop_owner=? WHERE shop_id=?");			
			
			ps.setString(1,data.getString("contact_Name"));//联系人姓名：shop_contact_name			
			ps.setString(2,data.getString("contact_Phone"));//联系人电话：shop_contact_phone			
			ps.setString(3,data.getString("store_Address"));//地址shop_contact_address		
			ps.setString(4,data.getString("store_Name"));//店面名称：shop_name			
			ps.setString(5,data.getString("category"));//经营品类：shop_busi_category			
			ps.setString(6,data.getString("keyWord"));//店面关键词：shop_keyword			
			ps.setString(7,data.getString("introduction"));//店面简介：shop_introduction			
			ps.setString(8,data.getString("store_Logo"));//店面LOGO：shop_logo			
			ps.setInt(9,data.getInt("user_id"));//店铺拥有者：shop_owner	
			ps.setInt(10, data.getInt("store_id"));
			ps.executeUpdate();	
			System.out.println("店面信息更新完成");		
		} catch (SQLException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}	
	}

	

	
}
