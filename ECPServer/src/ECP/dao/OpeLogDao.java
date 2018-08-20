package ECP.dao;

import java.sql.SQLException;

import org.json.JSONArray;

import ECP.model.OpeLog;
import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

public class OpeLogDao extends BaseDao{
	
	
	public OpeLogDao(){
		
	}
	
	public int insert(OpeLog log) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into ope_log(ope_time, user_id, content, type) values (?,?,?,?)");
			ps.setTimestamp(1, log.getOpeTime());
			ps.setString(2, log.getUserId());
			ps.setString(3, log.getContent());
			ps.setString(4, log.getType());
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
	
	public JSONArray findAll(){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select * from ope_log");
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
