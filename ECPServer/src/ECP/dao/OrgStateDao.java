package ECP.dao;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.Statement;

import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;


/*
 * @author durenshi
 * 企业状况表
 */
public class OrgStateDao extends BaseDao{
	
	public OrgStateDao(){
		
	}
	
	@Override
	public int insert(JSONObject data0) {
		conn = DBConnect.getConn();
		JSONObject data=null;
		try {
			data=data0.getJSONObject("state");
			System.out.println(data);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into org_state values (null,?,?,?,?,?,?, ?,?,?,?,? ,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, data.has("org_id")?data.getString("org_id"):"");//公司id
			ps.setString(2, data.has("license_no")?data.getString("license_no"):"");
			ps.setString(3, data.has("year")?data.getString("year"):"");
			ps.setString(4, data.has("employee_num")?data.getString("employee_num"):"0");
			ps.setString(5, data.has("techStaff_num")?data.getString("techStaff_num"):"0");
			
			ps.setString(6, data.has("revenue")?data.getString("revenue"):"");
			ps.setString(7, data.has("total_assets")?data.getString("total_assets"):"");
			ps.setString(8, data.has("total_indebt")?data.getString("total_indebt"):"");
			ps.setString(9, data.has("net_sales")?data.getString("net_sales"):"");
			ps.setString(10, data.has("current_assets")?data.getString("current_assets"):"");
			
			ps.setString(11, data.has("current_indebt")?data.getString("current_indebt"):"");
			ps.setString(12, data.has("operate_expense")?data.getString("operate_expense"):"");
			ps.setString(13, data.has("profit")?data.getString("profit"):"");
			ps.setString(14, data.has("loan_balance")?data.getString("loan_balance"):"");
			ps.setString(15, data.has("taxation")?data.getString("taxation"):"");
			ps.setString(16, data.has("RD_budget")?data.getString("RD_budget"):"");
			
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}
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
	
	@Override
	public JSONArray findById(String id){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("SELECT os.id,org_id,License_no,year,employee_num,techStaff_num,revenue,total_assets,total_indebt,net_sales, "
					+ "current_assets,current_indebt,operate_expense, profit, loan_balance, taxation,RD_budget,"
					+ "group_concat(CONCAT_WS(',' , url,CAST(cid as CHAR)) SEPARATOR ';') as uri "
					+ "from org_state os LEFT JOIN orgstate_cert oc on oc.state_id = os.id where org_id = ? group by year");
			ps.setString(1, id);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJsonLabel(rs);
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
	
	public JSONArray findByIdAnnual(String id){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("SELECT os.id,org_id,License_no,year,employee_num,techStaff_num,revenue,total_assets,total_indebt,net_sales,current_assets,current_indebt,operate_expense, "
					+ "profit, loan_balance, taxation,RD_budget,oc.cid,url "
					+ "from org_state os LEFT JOIN orgstate_cert oc on oc.state_id = os.id where os.id = ?");
			ps.setString(1, id);
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
	
	public JSONArray findStateNotFile(String id){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select id,year from org_state where org_id = ? and id not in( (select state_id from orgstate_cert ))");
			ps.setString(1, id);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		DBConnect.close(conn,ps,rs);
		return rdata;
	}
	
	public int delete(String data){
		conn = DBConnect.getConn();
		int result = 0;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("delete from org_state where id = ?");
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
	
	@Override
	public int update(JSONObject data0) {
		conn = DBConnect.getConn();
		int result = 0;
		JSONObject data=null;
		try {
			data=data0.getJSONObject("state");
			System.out.println(data);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ps = conn.prepareStatement("update org_State set license_no = ?, year=?, employee_num = ?, techStaff_num=?, revenue=?,"
					+ " total_assets=?, total_indebt=?, net_sales=?, current_assets=?,current_indebt=?, "
					+ "operate_expense = ?, profit =?, loan_balance=?, taxation = ? ,RD_budget = ? where id = ?");
			ps.setString(1, data.has("license_no")?data.getString("license_no"):"");
			ps.setString(2, data.has("year")?data.getString("year"):"");
			ps.setString(3, data.has("employee_num")?data.getString("employee_num"):"0");
			ps.setString(4, data.has("techStaff_num")?data.getString("techStaff_num"):"0");
			
			ps.setString(5, data.has("revenue")?data.getString("revenue"):"");
			ps.setString(6, data.has("total_assets")?data.getString("total_assets"):"");
			ps.setString(7, data.has("total_indebt")?data.getString("total_indebt"):"");
			ps.setString(8, data.has("net_sales")?data.getString("net_sales"):"");
			ps.setString(9, data.has("current_assets")?data.getString("current_assets"):"");
			
			ps.setString(10, data.has("current_indebt")?data.getString("current_indebt"):"");
			ps.setString(11, data.has("operate_expense")?data.getString("operate_expense"):"");
			ps.setString(12, data.has("profit")?data.getString("profit"):"");
			ps.setString(13, data.has("loan_balance")?data.getString("loan_balance"):"");
			ps.setString(14, data.has("taxation")?data.getString("taxation"):"");
			ps.setString(15, data.has("RD_budget")?data.getString("RD_budget"):"");
			ps.setString(16, data.getString("id"));
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
		} finally {
			DBConnect.close(conn, ps, rs);
		}
		return result;
	}
	
	public boolean isExit(String data){
		conn = DBConnect.getConn();
		try {
			ps = conn.prepareStatement("select count(*) from org_state where license_no = ?");
			ps.setString(1, data);
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)>0){
					DBConnect.close(conn,ps,rs);
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
	
	public int insertIntoOrgStateCert(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into orgstate_cert (state_id,cid,url) values (?,?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, data.has("state_id")?data.getString("state_id"):"");
			ps.setString(2, data.has("cid")?data.getString("cid"):"");
			ps.setString(3, data.has("url")?data.getString("url"):"");
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}
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
	
	public JSONArray findCidByStateId(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select cid from orgstate_cert where state_id = ?");
			ps.setString(1, data.getString("id"));
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
	
	public boolean isExitByIdYear(JSONObject data){
		conn = DBConnect.getConn();
		try {
			ps = conn.prepareStatement("select count(*) from org_state LEFT JOIN org_state_audit on org_info_id = org_id "
					+ "where org_id = ? and year = ? and audit_result = '通过'");
			ps.setString(1, data.getString("org_id"));
			ps.setString(2, data.getString("year"));
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
	
	public int updateAfterAudit(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update org_State set year=?, employee_num = ?, techStaff_num=?, revenue=?,"
					+ " total_assets=?, total_indebt=?, net_sales=?, current_assets=?,current_indebt=?, "
					+ "operate_expense = ?, profit =?, loan_balance=?, taxation = ? ,RD_budget = ? where id = ?");
			ps.setString(1, data.has("year")?data.getString("year"):"");
			ps.setString(2, data.has("employee_num")?data.getString("employee_num"):"0");
			ps.setString(3, data.has("techstaff_num")?data.getString("techstaff_num"):"0");
			
			ps.setString(4, data.has("revenue")?data.getString("revenue"):"");
			ps.setString(5, data.has("total_assets")?data.getString("total_assets"):"");
			ps.setString(6, data.has("total_indebt")?data.getString("total_indebt"):"");
			ps.setString(7, data.has("net_sales")?data.getString("net_sales"):"");
			ps.setString(8, data.has("current_assets")?data.getString("current_assets"):"");
			
			ps.setString(9, data.has("current_indebt")?data.getString("current_indebt"):"");
			ps.setString(10, data.has("operate_expense")?data.getString("operate_expense"):"");
			ps.setString(11, data.has("profit")?data.getString("profit"):"");
			ps.setString(12, data.has("loan_balance")?data.getString("loan_balance"):"");
			ps.setString(13, data.has("taxation")?data.getString("taxation"):"");
			ps.setString(14, data.has("rd_budget")?data.getString("rd_budget"):"");
			ps.setString(15, data.getString("org_state_id"));
			result = ps.executeUpdate();
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
	
}
