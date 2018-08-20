package wechat.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONObject;

import wechat.util.DBConnection;
import wechat.util.ResultSetDataTransform;

public class RoomDao extends BaseDao{
	public int insert(JSONObject data){
		int result = 0;
		conn = DBConnection.getConnect();
		try {
			pst = conn.prepareStatement("insert into room values (null,?,?,?,?)");
			pst.setInt(1, data.getInt("user_id"));
			pst.setInt(2, data.has("max_persons")? data.getInt("max_persons") : 100);
			//获取当前时间--创建时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_date =  df.format(System.currentTimeMillis());
			java.util.Date ud = df.parse(create_date);
			java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
			pst.setTimestamp(3, st);
			pst.setString(4, data.getString("room_name"));
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
	
	public JSONArray query(JSONObject data){
		conn = DBConnection.getConnect();
		JSONArray rdata = new JSONArray();
		StringBuffer sql =  new StringBuffer("SELECT * from room where 1=1 ");
		try {
			if(data.has("room_id")){
				 sql.append("and room_id = " + data.getInt("room_id"));
			}
			System.out.println(sql.toString());
			pst = conn.prepareStatement(sql.toString());
			rs = pst.executeQuery();
			rdata = ResultSetDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统系统出现非数据库异常。");
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pst,conn);
		}
		return rdata;
	}
}
