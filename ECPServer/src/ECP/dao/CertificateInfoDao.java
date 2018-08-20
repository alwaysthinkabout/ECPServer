package ECP.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mysql.jdbc.Statement;

import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

public class CertificateInfoDao extends BaseDao{
	
	public CertificateInfoDao(){
		
	}
	
	public int insert(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into certificate_info values (null,?,?,?,?,?, ?,?,?,?,? ,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, data.has("cert_id")?data.getString("cert_id"):"");
			ps.setString(2, data.has("cert_name")?data.getString("cert_name"):"");
			ps.setString(3, data.has("auth_org")?data.getString("auth_org"):"");
			ps.setString(4, data.has("cert_expire")?data.getString("cert_expire"):"0");
				
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String create_date =  data.has("cert_date")?data.getString("cert_date"):"1970-01-01";
			if (create_date.trim().equals("")) {
				create_date = "1970-01-01";
			}
			java.util.Date ud = df.parse(create_date);
			java.sql.Date sd = new java.sql.Date(ud.getTime());
			ps.setDate(5, sd);
			
			ps.setString(6, data.has("cert_path")?data.getString("cert_path"):"");
			ps.setString(7, data.has("cert_address")?data.getString("cert_address"):"");
			ps.setString(8, data.has("user_id")?data.getString("user_id"):"");
			ps.setString(9, data.has("cerLicense_no")?data.getString("cerLicense_no"):"");
			ps.setString(10, data.has("cert_status")?data.getString("cert_status"):"");
			ps.setString(11, data.has("cert_type")?data.getString("cert_type"):"");
			ps.setString(12, data.has("cert_desc")?data.getString("cert_desc"):"");
			ps.setString(13, "0");
			//获取当前时间--创建时间
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_time =  df.format(System.currentTimeMillis());
			ud = df.parse(create_time);
			java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
			ps.setTimestamp(14, st);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	@Override
	public int delete(JSONObject data){
		conn = DBConnect.getConn();
		int result = 0;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("delete from certificate_info where user_id = ? and cert_name =?");
			ps.setString(1, data.getString("userId"));
			ps.setString(2, data.getString("file"));
			result = ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统系统出现非数据库异常。");
			e.printStackTrace();
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	@Override
	public int delete(String id){
		conn = DBConnect.getConn();
		int result = 0;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("delete from certificate_info where id = ?");
			ps.setString(1, id);
			result = ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统系统出现非数据库异常。");
			e.printStackTrace();
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	@Override
	public JSONArray query(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		String sql =  "select id,cert_name,cert_path from certificate_info where user_id = ? and cert_name = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, data.getString("userId"));
			ps.setString(2, data.getString("file"));
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	public JSONArray findByUserId(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		String sql = "select cert_name,cert_path from certificate_info where user_id = ?";
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
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	public int findIDByUserId(String user_id,String certName){
		conn = DBConnect.getConn();
		int result=0;
		String sql =  "select id from certificate_info where user_id = ? and cert_name=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user_id);
			ps.setString(2, certName);
			rs = ps.executeQuery();
			if(rs.next()){
				result=rs.getInt(1);
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
		return result;
	}
	
	public boolean isExit(String data){
		conn = DBConnect.getConn();
		try {
			ps = conn.prepareStatement("select count(*) from certificate_info where license_no = ?");
			ps.setString(1, data);
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
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return false;
	} 
	
	public JSONArray findByLicenseNo(String data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		String sql =  "select id,cert_id,cert_name,license_no,auth_org,cert_expire,cert_date,cert_status,cert_type,cert_desc,cert_path from certificate_info where license_no = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, data);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
		} finally {
			DBConnect.close(conn,ps,rs);
		}		
		return rdata;
	}
	
	public int deleteById(JSONObject data){
		conn = DBConnect.getConn();
		int result = 0;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("delete from certificate_info where id = ?");
			ps.setString(1, data.getString("cert_id"));
			result = ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public JSONArray findByCertId(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		String sql =  "select cert_id,cert_name,license_no,auth_org,cert_expire,cert_date,cert_status from certificate_info where cert_id = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, data.getString("cert_id"));
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	public JSONArray findById(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		String sql =  "select id,cert_id,cert_name,license_no,auth_org,cert_expire,cert_date,cert_status,cert_path from certificate_info where id = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, data.getString("id"));
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	public JSONArray findByIds(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			String ids = data.getString("ids");
			if (ids.equals("")) {
				return rdata;
			}
			String sql =  "select id,cert_id,cert_name,license_no,auth_org,cert_expire,cert_date,cert_status,cert_path "
					+ "from certificate_info where id in ("+ids+")";
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql);
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
	
	public int deleteByOrgId(JSONObject data){
		conn = DBConnect.getConn();
		int result = 0;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("delete from org_cert where org_id = ?");
			ps.setString(1, data.getString("org_id"));
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
	
	public int deleteByStateId(JSONObject data){
		conn = DBConnect.getConn();
		int result = 0;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("delete from orgstate_cert where state_id = ? and cid = ?");
			ps.setString(1, data.getString("state_id"));
			ps.setString(2, data.getString("cert_id"));
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
	
	public boolean ifHasFile(JSONObject data){
		conn = DBConnect.getConn();
		StringBuffer sql = new StringBuffer("select count(*) from certificate_info where 1=1 ");
		try {
			sql.append(" and user_id = '"+data.getString("user_id")+"'");
			sql.append(" and cert_name = '"+data.getString("cert_name")+"'");
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)>0){
					DBConnect.close(conn,ps,rs);
					return true;
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
		return false;
	}
	
	//gj
	public int update(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update certificate_info set cert_id=?,cert_name=?,auth_org=?," +
					"cert_expire=?,cert_date=?,cert_path=?,cert_address=?,user_id=?,license_no=?," +
					"cert_status=? ,cert_type=?,cert_desc=?,auditFlag=? where id=?");
			ps.setString(1, data.has("cert_id")?data.getString("cert_id"):"");
			ps.setString(2, data.has("cert_name")?data.getString("cert_name"):"");
			ps.setString(3, data.has("auth_org")?data.getString("auth_org"):"");
			ps.setString(4, data.has("cert_expire")?data.getString("cert_expire"):"0");
				
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String create_date =  data.has("cert_date")?data.getString("cert_date"):"1970-01-01";
			if (create_date.trim().equals("")) {
				create_date = "1970-01-01";
			}
			java.util.Date ud = df.parse(create_date);
			java.sql.Date st = new java.sql.Date(ud.getTime());
			ps.setDate(5, st);
			
			ps.setString(6, data.has("cert_path")?data.getString("cert_path"):"");
			ps.setString(7, data.has("cert_address")?data.getString("cert_address"):"");
			ps.setString(8, data.has("user_id")?data.getString("user_id"):"");
			ps.setString(9, data.has("cerLicense_no")?data.getString("cerLicense_no"):"");
			ps.setString(10, data.has("cert_status")?data.getString("cert_status"):"");
			ps.setString(11, data.has("cert_type")?data.getString("cert_type"):"");
			ps.setString(12, data.has("cert_desc")?data.getString("cert_desc"):"");
			ps.setString(13, "0");
			ps.setString(14, data.getString("id"));
			ps.executeUpdate();			
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
	
	public int deleteByIds(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			if (data.getString("ids").equals("")) {
				return 1;
			}
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("delete from certificate_info where id in ("+ data.getString("ids") +")");
			result = ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
				System.out.println("SQL执行错误,事务回滚!");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统错误");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
}
