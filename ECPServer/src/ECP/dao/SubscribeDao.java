/**
 * 
 */
package ECP.dao;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

/**
 * @author durenshi
 * 消息订阅表
 *
 */
public class SubscribeDao extends BaseDao{
	public SubscribeDao(){
		
	}
	
	public JSONArray findJobAppliers(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		StringBuffer sql = new StringBuffer("select userID from subscribe where 1=1");
		try {
			sql.append(" and subscribe_type2 like '%"+data.getString("key_type")+"%'");
			ps = conn.prepareStatement(sql.toString());
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
