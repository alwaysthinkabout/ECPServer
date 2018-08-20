package ECP.dao;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

/*
 * @author durenshi
 * 招聘会信息表
 */
public class JobFairDao extends BaseDao{
	public JobFairDao(){
		
	}
	
	//请求招聘会列表数据
	@Override
	public JSONArray query(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		StringBuffer sql =  new StringBuffer("select job_fair_id fairId,job_fair_name fairName,job_fair_place fairPlage,"
				+ "job_fair_company cmpanyCount,job_fair_posts wordCount "
				+ "from job_fair where 1=1 ");
		try {
			if(data.has("city")){
				sql.append(" and job_fair_city = '"+data.getString("city")+"'");
			}
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
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
	
	//查看招聘会详情
	@Override
	public JSONArray findById(Integer id){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select job_fair_id fairId,job_fair_name fairName,job_fair_host fairSponsor,job_fair_time fairTime,"
					+ "job_fair_place fairPlace,job_fair_company companyCount,job_fair_posts workCount,job_fair_desc fairIntroduce "
					+ "from job_fair where 1=1 and job_fair_id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJsonLabel(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	public String findJobFairCode(Integer id){
		conn = DBConnect.getConn();
		String rdata = "";
		try {
			ps = conn.prepareStatement("select job_fair_code from job_fair WHERE job_fair_id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				rdata = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
}
