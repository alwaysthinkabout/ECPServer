package ECP.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONObject;

import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

/*
 * 用户基本信息表（初级信息表）
 */
public class PersonInfoDao extends BaseDao{
	
	public PersonInfoDao() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean isExit(JSONObject data){
		conn = DBConnect.getConn();
		try {
			ps = conn.prepareStatement("select count(*) from person_info where user_code = ?");
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
			ps = conn.prepareStatement("INSERT INTO person_info(user_code,ID_cardNo,name,phone,email,sex,birth,degree,prof_title) VALUES(?,?,?,?,?,?,?,?,?)");
			ps.setString(1, data.has("userId")?data.getString("userId"):"");
			ps.setString(2, data.has("idCard")?data.getString("idCard"):"");
			ps.setString(3, data.has("name")?data.getString("name"):"");
			ps.setString(4, data.has("phone")?data.getString("phone"):"");
			ps.setString(5, data.has("email")?data.getString("email"):"");
			ps.setString(6, data.has("sex")?data.getString("sex"):"");
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String create_date =  data.has("birth")?data.getString("birth"):"1970-01-01";
			java.util.Date ud = df.parse(create_date);
			java.sql.Date st = new java.sql.Date(ud.getTime());
			ps.setDate(7, st);
			
			ps.setString(8, data.has("education")?data.getString("education"):"");
			ps.setString(9, data.has("title")?data.getString("title"):"");
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
			ps = conn.prepareStatement("update person_info set ID_cardNo = ?,name = ? ,phone = ? ,email = ? ,sex = ? ,"
					+ "birth = ? ,degree = ? ,prof_title = ? where user_code = ?");
			ps.setString(1, data.has("idCard")?data.getString("idCard"):(data.has("id_cardno")?data.getString("id_cardno"):""));
			ps.setString(2, data.has("name")?data.getString("name"):"");
			ps.setString(3, data.has("phone")?data.getString("phone"):"");
			ps.setString(4, data.has("email")?data.getString("email"):"");
			ps.setString(5, data.has("sex")?data.getString("sex"):"");
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String create_date =  data.has("birth")?data.getString("birth"):"1970-01-01";
			java.util.Date ud = df.parse(create_date);
			java.sql.Date st = new java.sql.Date(ud.getTime());
			ps.setDate(6, st);
			
			ps.setString(7, data.has("education")?data.getString("education"):(data.has("degree")?data.getString("degree"):""));
			ps.setString(8, data.has("title")?data.getString("title"):"");
			ps.setString(9, data.has("userId")?data.getString("userId"):"");
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
		String sql =  "select ID_cardNo,name,phone,email,sex,birth,degree,prof_title from person_info where user_code = ? ";
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
	
	public JSONArray findByUserId2(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		String sql =  "select ID_cardNo as idCard,ID_cardNo,name,phone,email,sex,birth,degree,degree education,prof_title title from person_info where user_code = ? ";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, data.getString("userId"));
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJsonLabel(rs);
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
	
	public int delete(String data){
		conn = DBConnect.getConn();
		int result = 0;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("delete from person_info where user_code = ?");
			ps.setString(1, data);
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
}
