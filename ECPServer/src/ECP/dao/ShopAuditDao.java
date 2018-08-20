package ECP.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;

import javax.persistence.criteria.CriteriaBuilder.Case;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.org.apache.bcel.internal.generic.NEW;

import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

public class ShopAuditDao extends BaseDao {
	private StoreDao storeDao=new StoreDao();
	
	public JSONObject getStoreInfoAuditList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();	
		//获取待审核列表前要先判断信息提交是否完整。首次提交的信息要3条才算完整。
		try{			
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT sa.id AS audit_id,s.shop_name AS store_name,s.shop_contact_name AS contact_name,  "+
					" CASE WHEN sa.operType=0 THEN '新增店铺信息' WHEN sa.operType=1 THEN '修改店铺信息' END AS oper_type,  "+
					" sa.operTime AS oper_time,ui.nick_name AS oper_user_name FROM (SELECT id,shopID,operatorID,operType,operTime "+ 
					" FROM shop_audit WHERE ((infoType=1 AND operType=0) OR operType=1) AND auditStatus=0 ) sa,shop s,user_info ui WHERE "+ 
					" sa.shopID=s.shop_id AND sa.operatorID=ui.id ");
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

	public JSONObject getStoreInfoAuditDetail(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip,auditContent=null;
		JSONObject sqlData=new JSONObject();
		JSONObject baseInfo,storeInfo,qualificationInfo,collectionInfo=null;
		try{			
			int applyID=data.getInt("audit_id");
			if(!isAudited(applyID)){
				auditContent=getStoreAuditContent(applyID);
				storeInfo=getStoreRelatedInfo(applyID,1,0);
				qualificationInfo=getStoreRelatedInfo(applyID,2,0);
				collectionInfo=getStoreRelatedInfo(applyID,3,0);
				baseInfo=getStoreAuditBaseInfo(applyID);
			}else{
				auditContent=getStoreAuditContent(applyID);
				storeInfo=getStoreRelatedInfo(applyID,1,1);
				qualificationInfo=getStoreRelatedInfo(applyID,2,1);
				collectionInfo=getStoreRelatedInfo(applyID,3,1);
				baseInfo=getStoreAuditBaseInfo(applyID);
			}
			sqlData.put("baseInfo", baseInfo);
			sqlData.put("audit_content", auditContent);
			sqlData.put("storeInfo", storeInfo);
			sqlData.put("qualificationInfo", qualificationInfo);
			sqlData.put("collectionInfo", collectionInfo);
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

	private boolean isAudited(int applyID) throws SQLException {
		// TODO Auto-generated method stub
		boolean result=false;
		conn = DBConnect.getConn();
		ps=conn.prepareStatement("select 1 from shop_audit where auditStatus!=0 and id="+applyID);
		rs=ps.executeQuery();
		if(rs.next()){
			result=true;
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	private JSONObject getStoreAuditBaseInfo(int applyID) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();		
		JSONArray sqlData=new JSONArray();		
		try{			
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT sa.id AS audit_id,sa.shopID AS store_id,"+
					" CASE WHEN operType=0 THEN '新增店铺信息' WHEN operType=1 THEN '修改店铺信息' END AS oper_type,"+
					" operTime AS oper_time,ui.nick_name AS oper_user_name,sa.operReason AS oper_reason,"+
					" auditTime AS audit_time,CASE WHEN auditStatus=1 THEN '通过' WHEN auditStatus=2 THEN '不通过' END AS audit_result,sa.auditID,"+
					" (SELECT ui2.nick_name FROM user_info ui2 WHERE sa.auditID=ui2.uid) AS audit_user_name,auditComment as audit_reason" +
					" FROM shop_audit sa,shop s,user_info ui WHERE  "+
					" sa.shopID=s.shop_id AND ui.id=sa.operatorID AND sa.id="+applyID);
//			AND sa.infotype=1 AND sa.auditStatus=0 这两个判断貌似没用。
			rs=ps.executeQuery();
			sqlData= CDataTransform.rsToJsonLabel(rs);
			result=sqlData.getJSONObject(0);			
		}catch(Exception e){			
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}

	private JSONObject getStoreRelatedInfo(int applyID, int i,int auditStatus) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		//这里在表中设置了一个有效性字段active，默认为待用，现在改为默认为有效，审核不通过为无效。
		try{			
			conn = DBConnect.getConn();
			if(auditStatus==0){
				ps=conn.prepareStatement("SELECT infoContent FROM shop_audit a WHERE EXISTS( SELECT 1 FROM " +
						"shop_audit b WHERE a.shopID=b.shopID AND b.id=?) "+
						" AND infoType=? AND a.id<? AND active=1 ORDER BY id DESC LIMIT 1");
						ps.setInt(1, applyID);
						ps.setInt(2, i);
						ps.setInt(3, applyID);
			}else{
//				在显示历史记录时，审核过的信息又分两种情况，一是新增记录，没有父节点；而是修改记录，有父节点。
				if(findOperType(applyID)==0){
					ps=conn.prepareStatement("SELECT infoContent FROM shop_audit a WHERE EXISTS( SELECT 1 FROM " +
							"shop_audit b WHERE a.shopID=b.shopID AND b.id=?) "+
							" AND infoType=? AND a.operType=0 AND a.active!=2");
							ps.setInt(1, applyID);
							ps.setInt(2, i);
				}else{
					ps=conn.prepareStatement("SELECT infoContent FROM shop_audit a WHERE EXISTS( SELECT 1 FROM " +
							"shop_audit b WHERE a.shopID=b.shopID AND b.id=? and a.id=b.parentID)");
							ps.setInt(1, applyID);							
				}
				
			}
						
			rs=ps.executeQuery();
			if(rs.next()){
				result=new JSONObject(rs.getString(1));
			}			
		}catch(Exception e){			
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}

	private int findOperType(int applyID) {
		// TODO Auto-generated method stub
		int	result=-1;		
		JSONArray sqlData=new JSONArray();		
		try{			
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT operType FROM shop_audit a WHERE id="+applyID);			
			rs=ps.executeQuery();
			if(rs.next()){
				result=rs.getInt(1);
			}			
		}catch(Exception e){			
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}

	private String getStoreAuditContent(int applyID) {
		// TODO Auto-generated method stub
		String	result=new String();	
		//如果是新增信息的话，就不许要显示修改内容。只有修改操作才需要。
		try{
			if(findOperType(applyID)==1){
				JSONObject originMessage=findOriginMessage(applyID);
				JSONObject newMessage=findNewMessage(applyID); 
				result=CompareMessage(originMessage,newMessage);
			}			
		}catch(Exception e){			
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}

	private String CompareMessage(JSONObject originMessage,
			JSONObject newMessage) throws JSONException {
		// TODO Auto-generated method stub
		StringBuilder sBuilder=new StringBuilder();
		Iterator<String> sIterator=newMessage.keys();
		while(sIterator.hasNext()){
			String key=sIterator.next();
			if(key.equals("store_id")||key.equals("storeId")){
				continue;
			}
//			System.out.println("key:"+key+";originMessage:"+originMessage.toString());			
			String orgValue=originMessage.getString(key);
			String newValue=newMessage.getString(key);
			if(orgValue!="" && !orgValue.equals(newValue)){
				sBuilder.append("将字段'"+StoreInfoToCN(key)+"'的值从：'"+orgValue+"',改为：'"+newValue+"';\n");
			}
		}
		return sBuilder.toString();
	}

	private String StoreInfoToCN(String key) {
		// TODO Auto-generated method stub
		switch(key) { 
		case "store_Name": 
			return "店面名称";
		case "contact_Name": 
			return "联系人姓名";
		case "contact_Phone": 
			return "联系人电话";
		case "category": 
			return "经营品类";
		case "storeAddress": 
			return "地址";
		case "keyWord": 
			return "店面关键词";
		case "introduction": 
			return "店面简介";
		case "store_Logo": 
			return "店面LOGO";
		case "identity_Photo": 
			return "身份证照片";
		case "identity_Name": 
			return "身份证姓名";
		case "identity_Num": 
			return "身份证号码";
		case "license_Type": 
			return "执照类型";
		case "license_Photo": 
			return "执照图片";
		case "license_Name": 
			return "执照名称";
		case "license_Num": 
			return "执照编号";
		case "license_Address": 
			return "执照注册地址";
		case "license_ExpiryDate": 
			return "执照有效期";
		case "permit_Photo": 
			return "许可证图片";
		case "permit_Name": 
			return "许可证名称";
		case "permit_Num": 
			return "许可证编号";
		case "permit_Address": 
			return "许可证注册地址";
		case "permit_ExpiryDate": 
			return "许可证有效期";
		case "account_Type": 
			return "账户类型";
		case "account_Name": 
			return "开户名";
		case "account_Num": 
			return "账号";
		case "account_Bank": 
			return "开户行";
		case "account_SubBank": 
			return "开户支行";
		default: 
			return ""; 
		} 
	}

	private JSONObject findNewMessage(int applyID) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();		
		JSONArray sqlData=new JSONArray();		
		try{			
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT infoContent FROM shop_audit a WHERE id="+applyID);			
			rs=ps.executeQuery();
			if(rs.next()){
				result=new JSONObject(rs.getString(1));
			}			
		}catch(Exception e){			
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}

	private JSONObject findOriginMessage(int applyID) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();			
		try{			
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT infoContent FROM shop_audit a WHERE EXISTS( SELECT 1 FROM shop_audit b "+
					" WHERE a.shopID=b.shopID AND a.infoType=b.infoType AND b.id=?) "+
					" AND auditStatus=1 and active!=2 and a.id<? ORDER BY id DESC LIMIT 1");	
			ps.setInt(1, applyID);
			ps.setInt(2, applyID);
			rs=ps.executeQuery();
			if(rs.next()){
				result=new JSONObject(rs.getString(1));
			}			
		}catch(Exception e){			
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}

	public JSONObject getStoreInfoAudit(JSONObject audit_Result) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		JSONObject data=audit_Result.getJSONObject("data");
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		int applyID=-1;
		String comment=null,auditResult=null,auditerID=null;
		int auditInt=0;
		try {
			applyID=data.getInt("audit_id");
			comment=data.getString("audit_reason");
			auditResult=data.getString("audit_result");
			auditInt=data.getInt("audit_result")==1?1:2;//0待审核，1，通过；2，驳回；
			auditerID=data.getString("user_id");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{					
			int infoType=findInfoType(applyID,auditResult);
			int re=updateStoreAuditTableInfo(data);
			if(re!=0){
				switch(infoType){
					case 0://新增数据	
						updateAuditStatus(applyID,auditInt);
						break;
					case 1://修改店面信息
						updateShopInfo(applyID);
						break;
					case 2://修改资质信息
						updateQualificationInfo(applyID);
						break;
					case 3://修改收款信息
						updateShopBankInfo(applyID);
						break;
					default://审核不通过
						break;	
				}
				tip = "信息获取成功";	
			}else{
				flag=1;
				tip = "还有未处理的审核信息";
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
	private void updateShopBankInfo(int applyID) {
		// TODO Auto-generated method stub
		JSONObject data=findNewMessage(applyID);
		storeDao.updateBankInfo(data);
	}

	private void updateAuditStatus(int applyID, int auditInt) {
		// TODO Auto-generated method stub
		//如果是新增信息，一次就要更新三条记录的标识。
		try{			
			conn = DBConnect.getConn();
			//更新店铺审核标识；
			ps=conn.prepareStatement("UPDATE shop s SET auditFlag=? WHERE" +
					" EXISTS (SELECT 1 FROM shop_audit sa WHERE sa.shopID=s.shop_id AND sa.id=?)");	
			ps.setInt(1, auditInt);
			ps.setInt(2, applyID);
			ps.executeUpdate();	
			//更新证件审核标识；
			ps=conn.prepareStatement("UPDATE certificate_info c,(SELECT CONCAT(operatorID,'_',shopID) " +
					"AS user_id FROM shop_audit sa WHERE id=?) sa SET c.auditFlag=? WHERE c.user_id=sa.user_id");	
			ps.setInt(1, applyID);
			ps.setInt(2, auditInt);
			ps.executeUpdate();
			ps=conn.prepareStatement("UPDATE bank b,shop_audit sa SET b.auditFlag=? WHERE b.store_id=sa.shopID AND sa.id=?");	
			ps.setInt(1, auditInt);
			ps.setInt(2, applyID);
			ps.executeUpdate();
			
			//更新支付（付款）信息审核标识；
		}catch(Exception e){			
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
	}

	private void updateQualificationInfo(int applyID) {
		// TODO Auto-generated method stub
		JSONObject data=findNewMessage(applyID);
		storeDao.updateQualificationInfo(data);
	}

	private void updateShopInfo(int applyID) {
		// TODO Auto-generated method stub
		JSONObject data=findNewMessage(applyID);
		storeDao.updateShopInfo(data);
	}

	private int updateStoreAuditTableInfo(JSONObject data) {
		// TODO Auto-generated method stub
		int result=0;
		try{			
			int flag=findOperType(data.getInt("audit_id"));
			if(flag==0){ //处理新增信息；
				conn = DBConnect.getConn();
				//不能用同一张表中的值来更新这张表，所以查询要嵌套好多层。
				ps=conn.prepareStatement("update shop_audit set auditStatus=?,auditID=?,auditTime=? where " +
						" id in (SELECT id FROM ( SELECT id FROM shop_audit c WHERE EXISTS " +
						"( SELECT 1 FROM shop_audit b WHERE b.shopID=c.shopID  AND b.id=? ) and active=1 AND c.operType=0 ) d)");	
				ps.setInt(1, data.getInt("audit_result")==1?1:2);
				ps.setString(2, data.getString("user_id"));
				java.sql.Timestamp audtiTime=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(3, audtiTime);
				ps.setInt(4, data.getInt("audit_id"));
				ps.executeUpdate();	
				result=1;
			}else if(!existUnAuthedInsertInfo(data.getInt("audit_id"))){ //处理更新信息；
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("update shop_audit set auditStatus=?,auditID=?,auditTime=?,active=? where id=?");	
				ps.setInt(1, data.getInt("audit_result")==1?1:2);
				ps.setString(2, data.getString("user_id"));
				java.sql.Timestamp audtiTime=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(3, audtiTime);
				ps.setInt(4, data.getInt("audit_result")==1?1:2);
				ps.setInt(5, data.getInt("audit_id"));
				ps.executeUpdate();	
				if(data.getInt("audit_result")==1){
					ps=conn.prepareStatement("UPDATE shop_audit sa,shop_audit b SET sa.active=3 WHERE  b.id=? AND" +
							" b.shopID=sa.shopID AND b.infoType=sa.infoType AND sa.auditStatus=1 and sa.id=b.parentID");					
					ps.setInt(1, data.getInt("audit_id"));
					ps.executeUpdate();	
				}
				result=2;
			}
		}catch(Exception e){			
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}		
		return result;
	}

	private boolean existUnAuthedInsertInfo(int audit_id) {
		// TODO 查询未处理的更新信息
		boolean result=false;
		try{			
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT id FROM shop_audit sa WHERE EXISTS(SELECT 1 "+
					" FROM shop_audit s WHERE s.shopID=sa.shopID AND" +
					" s.id=? AND s.infoType=sa.infoType and sa.id<s.id) AND auditStatus!=1 and operType=1 ");	
			ps.setInt(1, audit_id);
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

	private int findInfoType(int applyID, String auditResult) {
		// TODO Auto-generated method stub
		//如何审核通过：新增记录的话返回0；修改记录的话返回相应的记录类型。审核不通过返回-1；
		int	result=-1;	
		if(Integer.parseInt(auditResult)==1){
			try{			
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT infoType,operType FROM shop_audit a WHERE id="+applyID);				
				rs=ps.executeQuery();
				if(rs.next()){
					if(rs.getInt("operType")==0){
						result=0;
					}else{
						result=rs.getInt("infoType");
					}
				}
			
			}catch(Exception e){			
				e.printStackTrace();
			}finally{
				DBConnect.close(conn,ps,rs);
			}
		}
		return result;		
	}

	public static void main(String[] args) throws Exception {
		ShopAuditDao shopAuditDao=new ShopAuditDao();
		JSONObject result=new JSONObject();
		JSONObject data=new JSONObject();
		String tString="{\"data\":{\"audit_result\":\"1\",\"audit_id\":\"13\",\"store_id\":\"1\"," +
				"\"audit_reason\":\"\",\"user_id\":\"1000000035\",\"audit_status\":\"已审核\"}}";
//		data=new JSONObject(tString);
		data.put("audit_id", 10);
//		result=shopAuditDao.getStoreInfoAuditedList(data);
//		result=shopAuditDao.getStoreInfoAudit(data);
		result=shopAuditDao.getStoreInfoAuditDetail(data);
		System.out.println("结果是："+result.toString());
	}
	
	public int getStoreMsgCounter() throws SQLException{
		conn=DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		int count=0;
		//这里比较诡异的是新增信息一条中包含三条记录，更改信息才是1对1的关系。
		ps = conn.prepareStatement("SELECT a.counter+b.counter AS counter FROM "+
				"(SELECT COUNT(1) AS counter FROM shop_audit WHERE auditStatus=0 AND operType=1) a,"+
				"(SELECT COUNT(DISTINCT(shopID)) AS counter FROM shop_audit WHERE auditStatus=0 AND opertype=0) b");
		rs = ps.executeQuery();
		rdata = CDataTransform.rsToJson(rs);
		if(rdata != null && rdata.length()>0){
			try {
				count = rdata.getJSONObject(0).getInt("counter");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		DBConnect.close(conn,ps,rs);
		return count;
	}

	public JSONObject getStoreInfoAuditedList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray(),sqlData1=new JSONArray();	
		//获取待审核列表前要先判断信息提交是否完整。首次提交的信息要3条才算完整。
		try{			
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT sa.id AS audit_id,s.shop_name AS store_name,s.shop_contact_name AS contact_name,  "+
					" CASE WHEN sa.operType=0 THEN '新增店铺信息' WHEN sa.operType=1 THEN '修改店铺信息' END AS oper_type,  "+
					" sa.operTime AS oper_time,ui.nick_name AS oper_user_name, ui2.nick_name as audit_user_name,sa.audit_time,sa.audit_result " +
					" FROM (SELECT id,shopID,operatorID,operType,operTime," +
					"auditTime as audit_time,case when auditStatus=1 then '通过' when auditStatus=2 then '不通过' end as audit_result," +
					"auditID "+ 
					" FROM shop_audit WHERE auditStatus!=0 and operType=1 union SELECT id,shopID,operatorID,operType,operTime," +
					"auditTime as audit_time,case when auditStatus=1 then '通过' when auditStatus=2 then '不通过' end as audit_result," +
					"auditID "+ 
					" FROM shop_audit WHERE auditStatus!=0 and infoType=1 and operType=0 ) sa,shop s,user_info ui,user_info ui2 WHERE "+ 
					" sa.shopID=s.shop_id AND sa.operatorID=ui.id and sa.auditID=ui2.uid ");
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
}
