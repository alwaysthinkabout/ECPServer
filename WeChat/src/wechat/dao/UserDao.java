package wechat.dao;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.Statement;

import wechat.util.DBConnection;
import wechat.util.ResultSetDataTransform;

public class UserDao extends BaseDao{
	public int insert(JSONObject data){
		int result = -1;
		conn = DBConnection.getConnect();
		try {
			pst = conn.prepareStatement("insert into user_info values (null,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, data.getString("user_name"));
			pst.setString(2, data.getString("password"));
			pst.setString(3, data.has("picture")? data.getString("picture") : "");
			pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			if(rs.next()){
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnection.close(rs, pst, conn);
		}		
		return result;
	}
	
	public JSONArray login(JSONObject data){
		JSONArray result = null;
		conn = DBConnection.getConnect();
		try{
			pst = conn.prepareStatement("select user_name,user_id,picture from user_info where user_name = ? and password = ?");
			pst.setString(1, data.getString("user_name"));
			pst.setString(2, data.getString("password"));
			rs = pst.executeQuery();
			result = ResultSetDataTransform.rsToJson(rs);			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnection.close(rs, pst, conn);
		}		
		return result;
	}
	
	public int setPicture(String path,String id){
		int result = 0;
		conn = DBConnection.getConnect();
		try {
			pst = conn.prepareStatement("update user_info set picture = ? where user_id = ?");
			pst.setString(1, path);
			pst.setString(2, id);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnection.close(rs, pst, conn);
		}		
		return result;
	}
	
	/**
	 * 用户名是否存在
	 * @return true:存在 ; false:不存在
	 */
	public boolean IsExist(JSONObject data){
		conn = DBConnection.getConnect();
		try {
			//判断招聘站名称是否存在
			if(data.has("user_name")){
				pst = conn.prepareStatement("SELECT user_id,user_name from user_info where user_name = ?");
				pst.setString(1, data.getString("user_name"));
				rs = pst.executeQuery();
				if(rs.next()){
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBConnection.close(rs,pst,conn);
		}
		return false;
	}
	
	public JSONArray getUser_infoById(String user_id){
		JSONArray result = null;
		conn = DBConnection.getConnect();
		try{
			pst = conn.prepareStatement("select user_name,user_id,picture from user_info where user_id = ?");
			pst.setString(1, user_id);
			rs = pst.executeQuery();
			result = ResultSetDataTransform.rsToJson(rs);			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnection.close(rs, pst, conn);
		}		
		return result;
	}
	
	
}
