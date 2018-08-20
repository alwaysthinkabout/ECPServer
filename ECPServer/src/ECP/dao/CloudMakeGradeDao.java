package ECP.dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

/*
 * 云制造用户评分表
 * @author durenshi
 * @create 2017-5-31 
 */
public class CloudMakeGradeDao extends BaseDao{

	public JSONObject findGradeInfo(JSONObject data){
		conn = DBConnect.getConn();
		JSONObject resultObject = new JSONObject();
		StringBuffer sql =  new StringBuffer("select task_name,grade from cloudmake_grade where 1=1 ");
		try {
			if(data.has("userId")){
				sql.append(" AND user_id = '"+data.getString("userId")+"'");
			}
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			resultObject.put("opResult", "0");
			JSONArray rdata = CDataTransform.rsToJson(rs);
			resultObject.put("gradeInfo", rdata);
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
	
	public int insert(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into cloudmake_grade(task_name,user_id,grade) values(?,?,?)");
			ps.setString(1, data.getString("taskName"));
			ps.setString(2, data.getString("userId"));
			ps.setString(3, data.getString("score"));
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
