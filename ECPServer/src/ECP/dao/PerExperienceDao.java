package ECP.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONObject;

import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

/*
 * 个人经历表
 * create by durenshi 
 */
public class PerExperienceDao extends BaseDao{

	public PerExperienceDao(){
		
	}
	
	public boolean isExit(JSONObject data){
		conn = DBConnect.getConn();
		try {
			ps = conn.prepareStatement("select count(*) from per_experience where uid = ?");
			ps.setString(1, data.getString("userId"));
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)>0){
					return true;
				}
			}
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
		return false;
	}
	
	public int insert(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("INSERT INTO per_experience(uid,start_date,end_date,experience,org_name,position_level,position_name,refer_id,refer_phone,refer_email,cert_id) VALUES(?,?,?,?,?, ?,?,?,?,?, ?)");
			ps.setString(1, data.has("userId")?data.getString("userId"):"");
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String create_date =  data.has("startTime")?data.getString("startTime"):"1970-01-01";
			java.util.Date ud = df.parse(create_date);
			java.sql.Date st = new java.sql.Date(ud.getTime());
			ps.setDate(2, st);
			
//			df = new SimpleDateFormat("yyyy-MM-dd");
//			String endTime =  data.has("endTime")?data.getString("endTime"):"1970-01-01";
//			ud = df.parse(endTime);
//			st = new java.sql.Date(ud.getTime());
//			ps.setDate(3, st);
			ps.setString(3, data.has("endTime")?data.getString("endTime"):"");
			
			ps.setString(4, data.has("activityType")?data.getString("activityType"):"");
			ps.setString(5, data.has("organization")?data.getString("organization"):"");
			ps.setString(6, data.has("jobLevel")?data.getString("jobLevel"):"");
			
			ps.setString(7, data.has("jobName")?data.getString("jobName"):"");
			ps.setString(8, data.has("certifierName")?data.getString("certifierName"):"");
			ps.setString(9, data.has("certifierPhone")?data.getString("certifierPhone"):"");
			ps.setString(10, data.has("certifierEmail")?data.getString("certifierEmail"):"");
			ps.setString(11, data.has("fileId")?data.getString("fileId"):"");
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
	
	public int update(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update per_experience set start_date = ?,end_date = ?,experience = ?,org_name = ?,position_level = ?,"
					+ "position_name = ?,refer_id = ?,refer_phone = ?,refer_email = ?,cert_id = ? where experience_id = ?");
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String create_date =  data.has("startTime")?data.getString("startTime"):"1970-01-01";
			java.util.Date ud = df.parse(create_date);
			java.sql.Date st = new java.sql.Date(ud.getTime());
			ps.setDate(1, st);
			ps.setString(2, data.has("endTime")?data.getString("endTime"):"");
			ps.setString(3, data.has("activityType")?data.getString("activityType"):"");
			ps.setString(4, data.has("organization")?data.getString("organization"):"");
			ps.setString(5, data.has("jobLevel")?data.getString("jobLevel"):"");
			
			ps.setString(6, data.has("jobName")?data.getString("jobName"):"");
			ps.setString(7, data.has("certifierName")?data.getString("certifierName"):"");
			ps.setString(8, data.has("certifierPhone")?data.getString("certifierPhone"):"");
			ps.setString(9, data.has("certifierEmail")?data.getString("certifierEmail"):"");
			ps.setString(10, data.has("fileId")?data.getString("fileId"):"");
			
			ps.setString(11, data.getString("experience_id"));
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
	
	public JSONArray findByUserId(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		String sql =  "select experience_id,uid,start_date,end_date,experience,org_name,position_level,position_name,refer_id,refer_phone,refer_email from per_experience where uid = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, data.getString("userId"));
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
	
	public JSONArray findByExpId(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		String sql =  "select experience_id,uid,start_date,end_date,experience,org_name,position_level,position_name,refer_id,refer_phone,refer_email from per_experience where experience_id = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, data.getString("experience_id"));
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
	
	@Override
	public int delete(JSONObject data){
		conn = DBConnect.getConn();
		int result = 0;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("delete from per_experience where uid = ? and experience_id = ?");
			ps.setString(1, data.getString("userId"));
			ps.setString(2, data.getString("experienceId"));
			result = ps.executeUpdate();
			conn.commit();
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
	
	public JSONArray findByUserIdExId(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		String sql =  "select experience_id,uid,cert_id,start_date,end_date,experience,org_name,position_level,position_name,refer_id,refer_phone,refer_email "
				+ "from per_experience where uid = ? and experience_id = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, data.getString("userId"));
			ps.setString(2, data.getString("experienceId"));
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
	
}
