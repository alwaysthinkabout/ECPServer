package ECP.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;


public class StudentDao extends BaseDao{
 
	public StudentDao(){
		
	}
	
	@Override
	public JSONArray findAll(){
		conn = DBConnect.getConn();
		try {
			ps = conn.prepareStatement("select * from student");
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray rdata = CDataTransform.rsToJson(rs);
		DBConnect.close(conn,ps,rs);
		return rdata;
	}
	
	@Override
	public JSONArray findById(Integer id) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select * from student where id ="+id);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DBConnect.close(conn,ps,rs);
		return rdata;
	}

	@Override
	public int insert(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into student values (null,?,?,?,?,?)");
			ps.setString(1, data.getString("name"));
			ps.setString(2, data.getString("gender"));
			ps.setString(3, data.getString("year"));
			ps.setString(4, data.getString("language"));
			java.sql.Date sd;
			java.util.Date ud;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String strDate = data.getString("birthday");
			ud=sdf.parse(strDate);
			sd = new java.sql.Date(ud.getTime());
			ps.setDate(5, sd);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统系统出现非数据库异常。");
			e.printStackTrace();
		}
		
		DBConnect.close(conn,ps,rs);
		return result;
	}
	
	@Override
	public int delete(JSONObject data){
		conn = DBConnect.getConn();
		int result = 0;
		try {
			conn.setAutoCommit(false);
			JSONArray ids = data.getJSONArray("ids");
			for (int i = 0; i < ids.length(); i++) {
				ps = conn.prepareStatement("delete from student where id = ?");
				ps.setInt(1, ids.getInt(i));
				result += ps.executeUpdate();
			}
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统系统出现非数据库异常。");
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}
	
	@Override
	public int update(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update student set name = ? , gender = ? , year = ? , language = ?, birthday=?  where id = ?");
			ps.setString(1, data.getString("name"));
			ps.setString(2, data.getString("gender"));
			ps.setString(3, data.getString("year"));
			ps.setString(4, data.getString("language"));
			java.util.Date ud;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String strDate = data.getString("birthday");
			System.out.println(strDate);
			ud=sdf.parse(strDate);
			java.sql.Timestamp stp = new java.sql.Timestamp (ud.getTime());
			System.out.println(stp);
			ps.setTimestamp(5,stp);
			ps.setInt(6, data.getInt("id"));
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统系统出现非数据库异常。");
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}
	
	/**
	 * 条件查询
	 * @return [{"id":"44","birthday":"2016-09-13 11:28:00.0","name":"adu","gender":"","year":"","language":""}]
	 * @throws JSONException 
	 */
	@Override
	public JSONArray query(JSONObject data){
		conn = DBConnect.getConn();
		StringBuilder sql =  new StringBuilder("select * from student where 1=1");
		try{
			if(data.has("id")){
				String user_id;
				user_id = data.getString("id");
				if(user_id != null && !user_id.equals("")){
					sql.append(" and id = " + user_id);
				}
			}
			if(data.has("name")){
				String name = data.getString("name");
				if(name != null && !name.equals("")){
					sql.append(" and name = '" + name+ "'");
				}
			}
			if(data.has("gender")){
				String gender = data.getString("gender");
				if(gender != null && !gender.equals("")){
					sql.append(" and gender = '" + gender + "'");
				}
			}
			if(data.has("year")){
				String year = data.getString("year");
				if(year != null && !year.equals("")){
					sql.append(" and year = '" + year + "'");
				}
			}
			if(data.has("language")){
				String language = data.getString("language");
				if(language != null && !language.equals("")){
					sql.append(" and language = '" + language + "'");
				}
			}
			if(data.has("start_date")){
				String start_date = data.getString("start_date");
				if(start_date != null && !start_date.equals("") && !start_date.equals("NaN-aN-aN aN:aN:aN")){
					sql.append(" and birthday >= to_date('" + start_date + "','yyyy-MM-dd HH24:MI:SS')");
				}
			}
			if(data.has("end_date")){
				String end_date = data.getString("end_date");
				if(end_date != null && !end_date.equals("") && !end_date.equals("NaN-aN-aN aN:aN:aN")){
					sql.append(" and birthday <= to_date('" + end_date + "','yyyy-MM-dd HH24:MI:SS')");
				}
			}
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray rdata = CDataTransform.rsToJson(rs);
		DBConnect.close(conn,ps,rs);
		return rdata;
	}
}
