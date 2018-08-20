package ECP.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;

import ECP.model.JobUserAudit;
import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

/*
 * 职位申请用户资料审核表
 * @author durenshi
 */
public class JobUserAuditDao extends BaseDao{
	
	public JobUserAuditDao(){
	
	}
	
	public JSONArray query(JobUserAudit jobUserAudit){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select audit_id, apply_user_id, name, sex, ope_type,"
					+ "DATE_FORMAT(ope_time,'20%y-%m-%d %H:%i:%s') ope_time, "
					+ "DATE_FORMAT(audit_time,'20%y-%m-%d %H:%i:%s') audit_time, audit_result, audit_user_id from job_user_audit ua "
					+ "LEFT JOIN person_info pi on ua.apply_user_id = pi.user_code where audit_status = ?");
			ps.setString(1, jobUserAudit.getAuditStatus());
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
	
	public JSONArray findById(JobUserAudit jobUserAudit){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select * from job_user_audit where audit_id = ?");
			ps.setString(1, jobUserAudit.getAuditId());
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
	
	public JSONArray findPerInfoById(JobUserAudit jobUserAudit){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select pi.*,audit_id,apply_user_id,ope_type,ope_time,user_info,audit_reason from job_user_audit ja "
					+ "left join person_info pi on pi.user_code = ja.apply_user_id where audit_id = ?");
			ps.setString(1, jobUserAudit.getAuditId());
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
	
	public int insertPerInfoAudit(JobUserAudit jobUserAudit) throws JSONException {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("insert job_user_audit(apply_user_id,ope_type,ope_time,audit_status,audit_result,user_info) values(?,?,?,?,?, ?)");
			ps.setString(1, jobUserAudit.getApplyUserId());
			ps.setString(2, jobUserAudit.getOpeType());
			ps.setTimestamp(3, jobUserAudit.getOpeTime());
			ps.setString(4, jobUserAudit.getAuditStatus());
			ps.setString(5, jobUserAudit.getAuditResult());
			ps.setString(6, jobUserAudit.getUserInfo());
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
	
	public int updatePerInfoAudit(JobUserAudit jobUserAudit) throws JSONException {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update job_user_audit set audit_user_id = ?, audit_time = ?, audit_reason = ?, audit_status = ?, audit_result = ? where audit_id = ?");
			ps.setString(1, jobUserAudit.getAuditUserId());
			//获取当前时间--创建时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_date =  df.format(System.currentTimeMillis());
			java.util.Date ud = df.parse(create_date);
			java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
			ps.setTimestamp(2, st);
			ps.setString(3, jobUserAudit.getAuditReason());
			ps.setString(4, jobUserAudit.getAuditStatus());
			ps.setString(5, jobUserAudit.getAuditResult());
			ps.setString(6, jobUserAudit.getAuditId());
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
	
	//计算求职者待审核信息条数
	public int getJobHunterMsgCounter() throws SQLException{
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		int count=0;
		ps = conn.prepareStatement("select count(audit_id) counter from job_user_audit WHERE audit_status ='未审核' ");
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

	public boolean isExistJobInfoAudit(String userId){
		conn = DBConnect.getConn();
		try {
			ps = conn.prepareStatement("select * from job_user_audit where apply_user_id = ? and audit_status = '未审核'");
			ps.setString(1, userId);
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
	
	public JSONArray findByUserIdRecent(String userId){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select * from job_user_audit ja where ja.ope_time = "
					+ "(select max(ope_time) from job_user_audit where apply_user_id = ?) ");
			ps.setString(1, userId);
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
