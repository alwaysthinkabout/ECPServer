package ECP.dao;

import java.sql.SQLException;
import java.sql.Timestamp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.star.lib.uno.environments.java.java_environment;

import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

public class RoleAuditDao extends BaseDao{
	private ECP.service.common.CFindService service = new ECP.service.common.CFindService();
	public JSONObject getRoleInfoAuditList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();	
		//获取待审核列表前要先判断信息提交是否完整。首次提交的信息要3条才算完整。
		try{			
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT audit_id,r.role_name as role_type,user_name,oper_time,case when oper_type=0 then '注册身份'" +
					"when oper_type=1 then '申请身份' end as oper_type FROM user_audit ua,role r WHERE ua.role_type=r.rid and  auditStatus=0");
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

	public JSONObject getRoleInfoAuditDetail(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();	
		//获取待审核列表前要先判断信息提交是否完整。首次提交的信息要3条才算完整。
		try{			
			conn = DBConnect.getConn();
			ps=conn.prepareStatement(" SELECT audit_id,user_id,oper_time,CASE WHEN oper_type=0 THEN '注册身份'  "+
					" WHEN oper_type=1 THEN '申请身份' END AS oper_type,oper_user_name,oper_reason,ua.user_name,"+
					" ua.identity,ua.idCard,r.role_name AS role_type,ua.role_limits,ua.audit_content, (SELECT u.nick_name FROM user_info u WHERE u.uid=ua.oper_user_id) AS audit_user_name,"+  
					" audit_time,audit_reason, "+
					" CASE WHEN audit_result=1 THEN '通过' WHEN audit_result=2 THEN '不通过' END AS audit_result   "+
					" FROM user_audit ua,role r WHERE ua.auditStatus=? AND   "+
					" ua.role_type=r.rid  AND audit_id=?	");
			ps.setInt(1, data.has("auditStatus")?data.getInt("auditStatus"):0);
			ps.setInt(2, data.getInt("audit_id"));			
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

	public JSONObject getRoleInfoAudit(JSONObject dataReciv) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject result=new JSONObject();
		JSONObject data=new JSONObject();
		data=dataReciv.getJSONObject("data");
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();	
		//获取待审核列表前要先判断信息提交是否完整。首次提交的信息要3条才算完整。
		try{			
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("UPDATE user_audit SET audit_reason=?,audit_result=?,oper_user_id=?," +
					"audit_time=?,auditStatus=? WHERE audit_id=? ");
			System.out.println(data.getString("audit_reason"));
			System.out.println(data.getString("audit_result"));
			ps.setString(1, data.has("audit_reason")?data.getString("audit_reason"):"");
			ps.setInt(2, data.getInt("audit_result"));
			ps.setString(3, data.getString("user_id"));
			java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
			ps.setTimestamp(4, timestamp);
			ps.setInt(5, 1);
			ps.setInt(6, data.getInt("audit_id"));			
			ps.executeUpdate();
			if(data.getInt("audit_result")==1){
				ps=conn.prepareStatement("UPDATE user_role u,user_audit ua SET u.uid=ua.user_id,u.rid=ua.role_type WHERE ua.audit_id=? ");
				ps.setInt(1, data.getInt("audit_id"));
				ps.executeUpdate();
			}else{
				JSONObject dataPush=new JSONObject();
				JSONObject retdata=findUserRoleAndAccount(data.getInt("audit_id"));
				StringBuilder systemInfo=new StringBuilder(" 您申请的'"+retdata.getString("role")+"'身份未通过审核，请补充资料后再次申请。");
				dataPush.put("eventType", "审核通知");
				dataPush.put("module", "管理端");				
				dataPush.put("toAccount", retdata.getString("account"));
				dataPush.put("systemInfo", systemInfo);
				service.push(dataPush);
			}
			//sqlData= CDataTransform.rsToJsonLabel(rs);
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
	
	
	private JSONObject findUserRoleAndAccount(int int1) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();	
		//获取待审核列表前要先判断信息提交是否完整。首次提交的信息要3条才算完整。
		try{			
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT u.user_id AS account,r.role_name as role FROM user_audit u JOIN role r ON u.role_type=r.rid WHERE u.audit_id="+int1);
			rs=ps.executeQuery();
			if(rs.next()){
				result.put("account", rs.getString("account"));
				result.put("role", rs.getString("role"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}

	public int getRoleMsgCounter() throws SQLException{
		conn=DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		int count=0;
		//这里比较诡异的是新增信息一条中包含三条记录，更改信息才是1对1的关系。
		ps = conn.prepareStatement("SELECT COUNT(1) AS counter FROM user_audit WHERE auditStatus=0");
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
	public static void main(String[] args) throws Exception {
		System.out.println("测试roleAuditDao中的小函数");
		RoleAuditDao roleAuditDao = new RoleAuditDao();
		JSONObject result=new JSONObject();
		result=roleAuditDao.findUserRoleAndAccount(1);
		System.out.println(result.toString());
	}

	public JSONObject getRoleInfoAuditedList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();	
		//获取待审核列表前要先判断信息提交是否完整。首次提交的信息要3条才算完整。
		try{			
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT audit_id,r.role_name as role_type,ua.user_name,oper_time,case when oper_type=0 then '注册身份'" +
					"when oper_type=1 then '申请身份' end as oper_type,audit_time," +
					"case when audit_result=1 then '通过' when audit_result=2 then '不通过' end as audit_result," +
					"(select u.nick_name from user_info u where ua.oper_user_id=u.uid ) " +
					"as audit_user_name,audit_reason FROM user_audit ua," +
					"role r WHERE auditStatus=1 and ua.role_type=r.rid ");
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

	public JSONObject getRoleInfoAuditedDetail(JSONObject data) throws JSONException {
		// TODO Auto-generated method stub
		data.put("auditStatus", 1);
		return getRoleInfoAuditDetail(data);
	}
}
