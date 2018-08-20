/**
 * 
 */
package ECP.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ECP.model.OrgStateAudit;
import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

/**
 * @author durenshi
 * 企业资料审核数据访问层接口
 *
 */
public class OrgAuditDao extends BaseDao{
	public OrgAuditDao(){
		
	}
	
	public int insertOrgInfoAudit(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into org_info_audit(org_user_id, operation_type, operation_reason, operation_time, org_info_id, "
					+ "audit_status, audit_result, org_info) values (?,?,?,?,?, ?,?,?)");
			ps.setString(1, data.has("user_id")?data.getString("user_id"):"");
			ps.setString(2, data.has("operation_type")?data.getString("operation_type"):"");
			ps.setString(3, data.has("operation_reason")?data.getString("operation_reason"):"");
			//获取当前时间--创建时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_date =  df.format(System.currentTimeMillis());
			java.util.Date ud = df.parse(create_date);
			java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
			ps.setTimestamp(4, st);
			ps.setString(5, data.getString("org_id"));
			ps.setString(6, "未审核");
			ps.setString(7, "待审核");
			ps.setString(8, data.has("org_info")?data.getString("org_info"):"");
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统系统出现非数据库异常。");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
		
	public int insertOrgStateAudit(OrgStateAudit orgStateAudit) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into org_State_audit(org_user_id, operation_type, operation_reason, operation_time, org_state_id,"
					+ " audit_status, org_info_id, org_state_info, audit_result) values (?,?,?,?,?, ?,?,?,?)");
			ps.setString(1, orgStateAudit.getOrgUserId());
			ps.setString(2, orgStateAudit.getOperationType());
			ps.setString(3, orgStateAudit.getOperationReason());
			ps.setTimestamp(4, orgStateAudit.getOperationTime());
			ps.setString(5, orgStateAudit.getOrgStateId());
			ps.setString(6, orgStateAudit.getAuditStatus());
			ps.setString(7, orgStateAudit.getOrgInfoId());
			ps.setString(8, orgStateAudit.getOrgStateInfo());
			ps.setString(9, orgStateAudit.getAuditResult());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统系统出现非数据库异常。");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public int insertOrgCertAudit(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into org_cert_audit(org_user_id, operation_type, operation_reason, operation_time, org_cert_id, audit_status, org_info_id) values (?,?,?,?,?,?,?)");
			ps.setString(1, data.getString("user_id"));
			ps.setString(2, data.has("operation_type")?data.getString("operation_type"):"");
			ps.setString(3, data.has("operation_reason")?data.getString("operation_reason"):"");
			//获取当前时间--创建时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_date =  df.format(System.currentTimeMillis());
			java.util.Date ud = df.parse(create_date);
			java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
			ps.setTimestamp(4, st);
			ps.setString(5, data.getString("cid"));
			ps.setString(6, "未审核");
			ps.setString(7, data.getString("org_id"));
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统系统出现非数据库异常。");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public int updateOrgInfoAudit(JSONObject auditResult) throws JSONException {
		JSONObject data=auditResult.getJSONObject("data");
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update org_info_audit set audit_user_id = ?, audit_time = ?, audit_reason = ?, audit_status = ?, audit_result = ? where audit_id = ?");
			ps.setString(1, auditResult.getString("user_id"));
			//获取当前时间--创建时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_date =  df.format(System.currentTimeMillis());
			java.util.Date ud = df.parse(create_date);
			java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
			ps.setTimestamp(2, st);
			ps.setString(3, data.has("audit_reason")?data.getString("audit_reason"):"");
			ps.setString(4, data.has("audit_status")?data.getString("audit_status"):"未审核");
			ps.setString(5, data.has("audit_result")?data.getString("audit_result"):"未通过");
			ps.setString(6, data.getString("audit_id"));
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统系统出现非数据库异常。");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public int updateOrgAuditCertInfo(JSONObject data) throws JSONException {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update org_info_audit set org_cert_id = ?, org_user_id =?, operation_time = ? where audit_id = ?");
			ps.setString(1, data.has("cid")?data.getString("cid"):"");
			ps.setString(2, data.has("user_id")?data.getString("user_id"):"");
			//获取当前时间--创建时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_date =  df.format(System.currentTimeMillis());
			java.util.Date ud = df.parse(create_date);
			java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
			ps.setTimestamp(3, st);
			ps.setString(4, data.getString("audit_id"));
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统系统出现非数据库异常。");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public int updateOrgStateAudit(JSONObject auditResult) throws JSONException {
		JSONObject data=auditResult.getJSONObject("data");
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update org_state_audit set audit_user_id = ?, audit_time = ?, audit_reason = ?, audit_status = ?, audit_result = ? where audit_id = ?");
			ps.setString(1, auditResult.getString("user_id"));
			//获取当前时间--创建时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_date =  df.format(System.currentTimeMillis());
			java.util.Date ud = df.parse(create_date);
			java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
			ps.setTimestamp(2, st);
			ps.setString(3, data.has("audit_reason")?data.getString("audit_reason"):"");
			ps.setString(4, data.has("audit_status")?data.getString("audit_status"):"未审核");
			ps.setString(5, data.has("audit_result")?data.getString("audit_result"):"未通过");
			ps.setString(6, data.getString("audit_id"));
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统系统出现非数据库异常。");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public int updateStateAuditCertInfo(JSONObject data) throws JSONException {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update org_state_audit set org_user_id =?, operation_time = ? where audit_id = ?");
			ps.setString(1, data.has("user_id")?data.getString("user_id"):"");
			//获取当前时间--创建时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_date =  df.format(System.currentTimeMillis());
			java.util.Date ud = df.parse(create_date);
			java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
			ps.setTimestamp(2, st);
			ps.setString(3, data.getString("audit_id"));
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统系统出现非数据库异常。");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public int updateOrgCertAudit(JSONObject auditResult) throws JSONException {
		JSONObject data=auditResult.getJSONObject("data");
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update org_cert_audit set audit_user_id = ?, audit_time = ?, audit_reason = ?, audit_status = ?, audit_result = ? where audit_id = ?");
			ps.setString(1, auditResult.getString("user_id"));
			//获取当前时间--创建时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_date =  df.format(System.currentTimeMillis());
			java.util.Date ud = df.parse(create_date);
			java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
			ps.setTimestamp(2, st);
			ps.setString(3, data.has("audit_reason")?data.getString("audit_reason"):"");
			ps.setString(4, data.has("audit_status")?data.getString("audit_status"):"未审核");
			ps.setString(5, data.has("audit_result")?data.getString("audit_result"):"未通过");
			ps.setString(6, data.getString("audit_id"));
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统系统出现非数据库异常。");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public JSONArray findAudits(String status){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		StringBuffer sql = new StringBuffer("select org_id,org_name,license_no,(ifnull(info.number,0)+ifnull(state.number,0)+ifnull(cert.number,0)) as amount,DATE_FORMAT(GREATEST(ifnull(info.time,0),ifnull(state.time,0),ifnull(cert.time,0)),'20%y-%m-%d %H:%i:%s') time from organization_info oi "
				+ "left JOIN "
				+ "(select org_info_id,count(*) number,MAX(operation_time) time from org_info_audit oa "
				+ "INNER JOIN organization_info oi on oa.org_info_id = oi.org_id and audit_status = '"+status+"' GROUP BY org_info_id) info "
				+ "on info.org_info_id = oi.org_id "
				+ "left JOIN "
				+ "(select org_info_id,count(*) number,MAX(operation_time) time from org_state_audit os "
				+ "INNER JOIN organization_info oi on os.org_info_id = oi.org_id and audit_status = '"+status+"' GROUP BY org_info_id) state "
				+ "on state.org_info_id = oi.org_id "
				+ "left JOIN  "
				+ "(select org_info_id,count(*) number,MAX(operation_time) time from org_cert_audit oc "
				+ "INNER JOIN organization_info oi on oc.org_info_id = oi.org_id and audit_status = '"+status+"' GROUP BY org_info_id) cert "
				+ "on cert.org_info_id = oi.org_id");
		System.out.println(sql.toString());
		try {
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	public JSONArray findByOrgInfoAudit(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select oa.*,oi.*,url from org_info_audit oa "
					+ "LEFT JOIN organization_info oi on oa.org_info_id = oi.org_id "
					+ "LEFT JOIN org_cert oc on oc.cid = oa.org_cert_id "
					+ "where oa.org_info_id = oi.org_id and org_info_id = ? and audit_status = ?");
			ps.setString(1, data.getString("org_id"));
			ps.setString(2, data.getString("status"));
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	public JSONArray findByOrgInfoAudited(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select audit_id,org_id,license_no,org_name,operation_type,operation_type,operation_time,org_user_id,audit_result,audit_user_id,audit_time from org_info_audit oa "
					+ "LEFT JOIN organization_info oi on oi.org_id = oa.org_info_id "
					+ "where org_info_id = ? and audit_status = ? order by audit_time desc");
			ps.setString(1, data.getString("org_id"));
			ps.setString(2, data.getString("status"));
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	public JSONArray findByOrgStateAudit(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select oa.*, os.year, os.employee_num, os.revenue from org_state_audit oa LEFT JOIN org_state os on os.id = oa.org_state_id "
					+ "where org_info_id = ? and audit_status = ? ORDER BY operation_time desc");
			ps.setString(1, data.getString("org_id"));
			ps.setString(2, data.getString("status"));
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	public JSONArray findByOrgCertAudit(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select oa.*,cert_name,cert_type from org_cert_audit oa LEFT JOIN certificate_info ci on ci.id = oa.org_cert_id "
					+ "where org_info_id = ? and audit_status = ? ORDER BY operation_time desc");
			ps.setString(1, data.getString("org_id"));
			ps.setString(2, data.getString("status"));
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	public JSONArray findByOrgInfoAuditId(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select oa.*,oi.*,url from org_info_audit oa "
					+ "LEFT JOIN organization_info oi on oa.org_info_id = oi.org_id "
					+ "LEFT JOIN org_cert oc on oc.cid = oa.org_cert_id "
					+ "where audit_id = ?");
			ps.setString(1, data.getString("audit_id"));
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	public JSONArray findByOrgStateAuditId(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select oa.*,os.*,oc.*,group_concat(url) as urls from org_state_audit oa "
					+ "LEFT JOIN org_state os on oa.org_state_id = os.id "
					+ "LEFT JOIN orgstate_cert oc on oc.state_id = oa.org_state_id "
					+ "where audit_id = ? group by audit_id");
			ps.setString(1, data.getString("audit_id"));
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	public JSONArray findByOrgCertAuditId(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select oa.*,oc.url,cert_id,cert_name,license_no,auth_org,cert_expire,cert_date,"
					+ "cert_status,cert_type,cert_desc,cert_path from org_cert_audit oa "
					+ "LEFT JOIN org_cert oc on oc.cid = oa.org_cert_id "
					+ "LEFT JOIN certificate_info ci on ci.id = oa.org_cert_id "
					+ "where audit_id = ?");
			ps.setString(1, data.getString("audit_id"));
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	
	//计算企业待审核信息条数
	public int getOrgMsgCounter() throws SQLException{
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		int count=0;
		ps = conn.prepareStatement("select count(audit_id) counter from org_info_audit WHERE audit_status ='未审核' ");
		rs = ps.executeQuery();
		rdata = CDataTransform.rsToJson(rs);
		if(rdata != null && rdata.length()>0){
			try {
				count = rdata.getJSONObject(0).getInt("counter");
				ps = conn.prepareStatement("select count(audit_id) counter from org_state_audit WHERE audit_status ='未审核' ");
				rs = ps.executeQuery();
				rdata = CDataTransform.rsToJson(rs);
				if(rdata != null && rdata.length()>0){
					try {
						count += rdata.getJSONObject(0).getInt("counter");
						ps = conn.prepareStatement("select count(audit_id) counter from org_cert_audit WHERE audit_status ='未审核' ");
						rs = ps.executeQuery();
						rdata = CDataTransform.rsToJson(rs);
						if(rdata != null && rdata.length()>0){
							try {
								count += rdata.getJSONObject(0).getInt("counter");
							} catch (JSONException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		DBConnect.close(conn,ps,rs);
		return count;
	}
	
	public boolean isExistOrgInfoAudit(String orgId){
		conn = DBConnect.getConn();
		try {
			ps = conn.prepareStatement("select * from org_info_audit where org_info_id = ? and audit_status = '未审核'");
			ps.setString(1, orgId);
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
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return false;
	}
	
	public boolean isExistOrgStateAudit(String stateId){
		conn = DBConnect.getConn();
		try {
			ps = conn.prepareStatement("select * from org_state_audit where org_state_id = ? and audit_status = '未审核'");
			ps.setString(1, stateId);
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
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return false;
	}
	
	public JSONArray findByOrgIdRecent(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select * from org_info_audit oa where oa.operation_time = "
					+ "(select max(operation_time) from org_info_audit where org_info_id = ?) ");
			ps.setString(1, data.getString("org_id"));
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	public JSONArray findByStateIdRecent(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			String sql = "select oa.* from org_state_audit as oa where operation_time=(select max(b.operation_time) "
					+ "from org_state_audit as b where oa.org_state_id = b.org_state_id)"
					+ " and oa.org_state_id in( "+data.getString("stateIds")+" )";
			System.out.println(sql);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
}
