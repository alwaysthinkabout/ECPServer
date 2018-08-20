package ECP.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.Statement;

import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;
/*
 * 云制造投标表
 * @author durenshi
 * @create 2017-5-31 
 */
public class CloudMakeBidDao extends BaseDao{
	
	@Override
	public int insert(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into cloudmake_bid(bid_user_id,tender_user_id,task_id,bid_price,bid_status, deleteUser,bid_time) "
					+ " values (?,?,?,?,?, ?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, data.getString("userId"));
			ps.setString(2, data.has("tenderUserId")?data.getString("tenderUserId"):"");
			ps.setString(3, data.getString("taskId"));
			ps.setString(4, data.has("price")?data.getString("price"):"");	
			ps.setString(5, "未查看");
			ps.setString(6, "none");
			//获取当前时间--创建时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_date =  df.format(System.currentTimeMillis());
			java.util.Date ud = df.parse(create_date);
			java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
			ps.setTimestamp(7, st);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现异常。");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public JSONObject findBidToMeInfo(JSONObject data){
		conn = DBConnect.getConn();
		JSONObject resultObject = new JSONObject();
		StringBuffer sql =  new StringBuffer("select bid_id, bid_user_id,cb.task_id,ct.name,ct.status,ct.type,bid_time,bid_status,bid_price,qualification_proof "
				+ "from cloudmake_bid cb, cloudmake_task ct where cb.task_id = ct.task_id "
				+ "and cb.deleteUser != 'tenderUser'");
		try {
			if(data.has("userId")){
				sql.append(" AND cb.tender_user_id = '"+data.getString("userId")+"'");
			}
			sql.append(" order by bid_time desc ");
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			resultObject.put("opResult", "0");
			JSONArray rdata = CDataTransform.rsToJson(rs);
			resultObject.put("bid", rdata);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现异常。");
			e.printStackTrace();
			try {
				resultObject.put("opResult", "1");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return resultObject;
	}
	
	public JSONObject findMyBidInfo(JSONObject data){
		conn = DBConnect.getConn();
		JSONObject resultObject = new JSONObject();
		StringBuffer sql =  new StringBuffer("select bid_id, ct.tender_user_id,cb.task_id,ct.type,ct.name,ct.status,bid_time,bid_status,bid_price,qualification_proof "
				+ "from cloudmake_bid cb, cloudmake_task ct where cb.task_id = ct.task_id "
				+ "and cb.deleteUser != 'bidUser'");
		try {
			if(data.has("userId")){
				sql.append(" AND cb.bid_user_id = '"+data.getString("userId")+"'");
			}
			sql.append(" order by bid_time desc ");
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			resultObject.put("opResult", "0");
			JSONArray rdata = CDataTransform.rsToJson(rs);
			resultObject.put("bid", rdata);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现异常。");
			e.printStackTrace();
			try {
				resultObject.put("opResult", "1");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return resultObject;
	}
	
	@Override
	public int delete(JSONObject data){
		conn = DBConnect.getConn();
		int result = 0;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("delete from cloudmake_bid where bid_id = ?");
			ps.setString(1, data.getString("bidId"));
			result = ps.executeUpdate();
			conn.commit();
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
	
	public JSONArray findByBidId(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		String sql =  "select task_id,bid_user_id,bid_price,bid_status,bid_time,deleteUser from cloudmake_bid where bid_id = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, data.getString("bidId"));
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
	
	public JSONArray findByTaskId(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		String sql =  "select bid_id,task_id,bid_user_id,bid_price,bid_status,bid_time,deleteUser from cloudmake_bid where task_id = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, data.getString("taskId"));
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
	
	public int setDeleteUser(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update cloudmake_bid set deleteUser = ? where bid_id = ? ");
			ps.setString(1, data.getString("user"));
			ps.setString(2, data.getString("bidId"));
			result = ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现异常。");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public int setQualificationProof (JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update cloudmake_bid set qualification_proof = ? where bid_id = ? ");
			ps.setString(1, data.getString("qualification_proof"));
			ps.setString(2, data.getString("bidId"));
			result = ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现异常。");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public int setStatus(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update cloudmake_bid set bid_status = ?,bid_time = ? where bid_id = ? ");
			ps.setString(1, data.getString("updateStatus"));
			//获取当前时间--创建时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_date =  df.format(System.currentTimeMillis());
			java.util.Date ud = df.parse(create_date);
			java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
			ps.setTimestamp(2, st);
			ps.setString(3, data.getString("bidId"));
			result = ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现异常。");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public int setStatusByTaskId(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update cloudmake_bid set bid_status = ?,bid_time = ? where task_id = ? and bid_id != ?");
			ps.setString(1, data.getString("updateStatus"));
			//获取当前时间--创建时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_date =  df.format(System.currentTimeMillis());
			java.util.Date ud = df.parse(create_date);
			java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
			ps.setTimestamp(2, st);
			ps.setString(3, data.getString("taskId"));
			ps.setString(4, data.getString("bidId"));
			result = ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现异常。");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
}
