package ECP.dao;

import java.sql.SQLException;

import org.json.JSONArray;

import ECP.model.UserInfo;
import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

public class UserInfoDao extends BaseDao{
	
	public JSONArray query(UserInfo user){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			StringBuffer sql = new StringBuffer("select id,uid,nick_name,user_type,mail,picture,user_sex,user_phone,introduce,user_state "
					+ " from user_info where user_state=0 ");
			if (!user.getUid().equals("-1")) {
				sql.append("and uid = " + user.getUid());
			}
			ps = conn.prepareStatement(sql.toString());
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
	
	public int updateState(UserInfo user) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update user_info set user_state = ? where uid = ?");
			ps.setInt(1, user.getUserState());
			ps.setString(2, user.getUid());
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
}
