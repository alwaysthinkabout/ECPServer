package ECP.dao;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import ECP.model.JobUserRole;
import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;
import ECP.util.helper.MD5;

public class JobRoleDao extends BaseDao{
	
	public JobRoleDao(){
		
	}
	
	public String loginByRole(JSONObject data){
		conn = DBConnect.getConn();
		String uid = "0";
		String firstLogin = "0";
		try {
			ps = conn.prepareStatement("select uid,ur.* from job_user_role ur LEFT JOIN user_info ui on ur.user_id = ui.uid "
					+ "where uid = ? and password = ? and user_type = ? and role_id = ?");
			ps.setString(1, data.getString("user_id"));
			ps.setString(2, MD5.EncoderByMd5(data.getString("password")));
			ps.setString(3, data.getString("user_type"));
			ps.setString(4, data.getString("role_id"));
			rs = ps.executeQuery();
			if(rs.next()){
				uid = rs.getString("uid");
				firstLogin = rs.getString("first_login");
			}
			if (uid.equals("0")) {
				ps = conn.prepareStatement("select ur.* from job_user_role ur LEFT JOIN user_info ui on ur.user_id = ui.uid "
						+ "where mail = ? and password = ? and user_type = ? and role_id = ?");
				ps.setString(1, data.getString("user_id"));
				ps.setString(2, MD5.EncoderByMd5(data.getString("password")));
				ps.setString(3, data.getString("user_type"));
				ps.setString(4, data.getString("role_id"));
				rs = ps.executeQuery();
				if(rs.next()){
					uid = rs.getString("user_id");
					firstLogin = rs.getString("first_login");
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
		return uid + ","+ firstLogin;
	}
	
	public JSONArray query(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		StringBuffer sql = new StringBuffer("select * from job_role where 1=1 ");
		try {
			if (data.has("role_id")) {
				sql.append("and id = " + data.getString("role_id"));
			}
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJsonLabel(rs);
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
	
	public JSONArray queryUserRole(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		StringBuffer sql = new StringBuffer("select * from job_role jr "
				+ "LEFT JOIN job_user_role ur on ur.role_id = jr.id where user_id = ?");
		try {
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, data.getString("user_id"));
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJsonLabel(rs);
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
	
	public int updateEnable(List<JobUserRole> jobRoles){
		int result = 0;
		conn = DBConnect.getConn();
		try {
			ps = conn.prepareStatement("update job_user_role set enable = ? where role_id = ? and user_id = ?");
			conn.setAutoCommit(false);
			for (int i = 0; i < jobRoles.size(); i++) {
				ps.setString(1, jobRoles.get(i).getEnable());
				ps.setString(2, jobRoles.get(i).getRoleId());
				ps.setString(3, jobRoles.get(i).getUserId());
				ps.addBatch();
			}
			ps.executeBatch();
            conn.commit();
            result = 1;
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
	
	public int updatePassword(JobUserRole data){
		int result = 0;
		conn = DBConnect.getConn();
		try {
			ps = conn.prepareStatement("update job_user_role set password = ? where user_id = ? and password = ? and role_id = ?");
			ps.setString(1, MD5.EncoderByMd5(data.getNewPassword()));
			ps.setString(2, data.getUserId());
			ps.setString(3, MD5.EncoderByMd5(data.getPassword()));
			ps.setString(4, data.getRoleId());
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
	
	public int updatePasswordByEnable(JobUserRole data){
		int result = 0;
		conn = DBConnect.getConn();
		try {
			ps = conn.prepareStatement("update job_user_role set password = ? where user_id = ? and role_id = ?");
			ps.setString(1, MD5.EncoderByMd5(data.getPassword()));
			ps.setString(2, data.getUserId());
			ps.setString(3, data.getRoleId());
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
	
	public int updateFirstLogin(JobUserRole data){
		int result = 0;
		conn = DBConnect.getConn();
		try {
			ps = conn.prepareStatement("update job_user_role set first_login = ? where user_id = ? and role_id = ?");
			ps.setString(1, data.getFirstLogin());
			ps.setString(2, data.getUserId());
			ps.setString(3, data.getRoleId());
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
	
	public int insertToJobUserRole(JSONObject data){
		int result = 0;
		conn = DBConnect.getConn();
		try {
			ps = conn.prepareStatement("insert into job_user_role(user_id,role_id,password,enable,first_login) values(?,?,?,?,?)");
			conn.setAutoCommit(false);
			for (int i = 1; i < 8; i++) {
				ps.setString(1, data.getString("user_id"));
				ps.setString(2, String.valueOf(i));
				if (i==1) {
					ps.setString(3, MD5.EncoderByMd5(data.getString("password")));
					ps.setString(4, "1");
					ps.setString(5, "1");
				}
				else{
					ps.setString(3, MD5.EncoderByMd5("123456"));
					ps.setString(4, "0");
					ps.setString(5, "0");
				}
				ps.addBatch();
			}
			ps.executeBatch();
            conn.commit();
            result = 1;
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
}
