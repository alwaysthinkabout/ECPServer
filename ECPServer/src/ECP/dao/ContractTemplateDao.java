package ECP.dao;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

/*
 * 合同模板表
 * durenshi 2017-5-26
 */
public class ContractTemplateDao extends BaseDao{
	
	public ContractTemplateDao(){
		
	}
	
	@Override
	public int insert(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into contract_template(org_id,legal_rep,license_no,org_address,contract_time,"
					+ "probation_start,porbation_end,expire_date,insure_ratio,user_id,"
					+ "phone,name,id_cardno,sex,user_address,"
					+ "contract_term,contract_content,contract_type,contract_name,partA_sig,"
					+ "partA_date,partB_sig,partB_date,payment,practice_begin,practice_end,contract_background,"
					+" contract_summary,contract_start_time,contract_end_time,fine,work_time,job_name) "
					+ "values (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,? ,?,?,?,?,?,?,?,? ,?,?,?,?,?)");
			ps.setString(1, data.has("org_id")?data.getString("org_id"):"0");
			ps.setString(2, data.has("legal_rep")?data.getString("legal_rep"):"0");
			ps.setString(3, data.has("license_no")?data.getString("license_no"):"");
			ps.setString(4, data.has("org_address")?data.getString("org_address"):"0");
			ps.setString(5, data.has("contract_time")?data.getString("contract_time"):"0");
			
			ps.setString(6, data.has("probation_start")?data.getString("probation_start"):"0");
			ps.setString(7, data.has("porbation_end")?data.getString("porbation_end"):"0");
			ps.setString(8, data.has("expire_date")?data.getString("expire_date"):"0");
			ps.setString(9, data.has("insure_ratio")?data.getString("insure_ratio"):"0");
			ps.setString(10, data.has("user_id")?data.getString("user_id"):"0");
			
			ps.setString(11, data.has("phone")?data.getString("phone"):"0");
			ps.setString(12, data.has("name")?data.getString("name"):"0");
			ps.setString(13, data.has("id_cardno")?data.getString("id_cardno"):"0");
			ps.setString(14, data.has("sex")?data.getString("sex"):"0");
			ps.setString(15, data.has("user_address")?data.getString("user_address"):"0");
			
			ps.setString(16, data.has("contract_term")?data.getString("contract_term"):"0");
			ps.setString(17, data.has("contract_content")?data.getString("contract_content"):"0");
			ps.setString(18, data.has("contract_type")?data.getString("contract_type"):"0");
			ps.setString(19, data.has("contract_name")?data.getString("contract_name"):"0");
			ps.setString(20, data.has("partA_sig")?data.getString("partA_sig"):"0");
			
			ps.setString(21, data.has("partA_date")?data.getString("partA_date"):"0");
			ps.setString(22, data.has("partB_sig")?data.getString("partB_sig"):"0");
			ps.setString(23, data.has("partB_date")?data.getString("partB_date"):"0");
			ps.setString(24, data.has("payment")?data.getString("payment"):"0");
			ps.setString(25, data.has("practice_begin")?data.getString("practice_begin"):"0");
			ps.setString(26, data.has("practice_end")?data.getString("practice_end"):"0");
			ps.setString(27, data.has("contract_background")?data.getString("contract_background"):"0");
			ps.setString(28, data.has("contract_summary")?data.getString("contract_summary"):"0");
			ps.setString(29, data.has("contract_start_time")?data.getString("contract_start_time"):"0");
			ps.setString(30, data.has("contract_end_time")?data.getString("contract_end_time"):"0");
			ps.setString(31, data.has("fine")?data.getString("fine"):"0");
			ps.setString(32, data.has("work_time")?data.getString("work_time"):"0");
			ps.setString(33, data.has("job_name")?data.getString("job_name"):"0");		 
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
	
	@Override
	//update by lgp
	public int update(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			/*ps = conn.prepareStatement("update contract_template set org_id = ?,legal_rep = ?,license_no = ?,org_address = ?,contract_time = ?,"
					+ "probation_start = ?,porbation_end = ?,expire_date = ?,insure_ratio = ?,user_id = ?,"
					+ "phone = ?,name = ?,id_cardno = ?,sex = ?,user_address = ?,"
					+ "contract_term = ?,contract_content = ?,contract_type = ?,contract_name = ?,partA_sig = ?,"
					+ "partA_date = ?,partB_sig = ?,partB_date = ?,payment = ?,practice_begin = ?,practice_end = ? where template_no = ?");*/
			ps = conn.prepareStatement("update contract_template set contract_content = ?, contract_name = ? where template_no = ?");
			/*ps.setString(1, data.has("org_id")?data.getString("org_id"):"0");
			ps.setString(2, data.has("legal_rep")?data.getString("legal_rep"):"0");
			ps.setString(3, data.has("license_no")?data.getString("license_no"):"");
			ps.setString(4, data.has("org_address")?data.getString("org_address"):"0");
			ps.setString(5, data.has("contract_time")?data.getString("contract_time"):"0");
			
			ps.setString(6, data.has("probation_start")?data.getString("probation_start"):"0");
			ps.setString(7, data.has("porbation_end")?data.getString("porbation_end"):"0");
			ps.setString(8, data.has("expire_date")?data.getString("expire_date"):"0");
			ps.setString(9, data.has("insure_ratio")?data.getString("insure_ratio"):"0");
			ps.setString(10, data.has("user_id")?data.getString("user_id"):"0");
			
			ps.setString(11, data.has("phone")?data.getString("phone"):"0");
			ps.setString(12, data.has("name")?data.getString("name"):"0");
			ps.setString(13, data.has("id_cardno")?data.getString("id_cardno"):"0");
			ps.setString(14, data.has("sex")?data.getString("sex"):"0");
			ps.setString(15, data.has("user_address")?data.getString("user_address"):"0");
			
			ps.setString(16, data.has("contract_term")?data.getString("contract_term"):"0");*/
			ps.setString(1, data.has("contract_content")?data.getString("contract_content"):"0");
			//ps.setString(18, data.has("contract_type")?data.getString("contract_type"):"0");
			ps.setString(2, data.has("contract_name")?data.getString("contract_name"):"0");
			/*ps.setString(20, data.has("partA_sig")?data.getString("partA_sig"):"0");
			
			ps.setString(21, data.has("partA_date")?data.getString("partA_date"):"0");
			ps.setString(22, data.has("partB_sig")?data.getString("partB_sig"):"0");
			ps.setString(23, data.has("partB_date")?data.getString("partB_date"):"0");
			ps.setString(24, data.has("payment")?data.getString("payment"):"0");
			ps.setString(25, data.has("practice_begin")?data.getString("practice_begin"):"0");
			ps.setString(26, data.has("practice_end")?data.getString("practice_end"):"0");*/
			ps.setString(3, data.getString("template_no"));
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
	
	public JSONArray findByOrgId(String id) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select template_no,org_id,contract_name from contract_template where org_id = ?");
			ps.setString(1, id);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
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
	
	public JSONArray findById(String id) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select template_no,org_id,legal_rep,license_no,org_address,contract_time,"
					+ "probation_start,porbation_end,expire_date,insure_ratio,user_id,"
					+ "phone,name,id_cardno,sex,user_address,"
					+ "contract_term,contract_content,contract_type,contract_name,partA_sig,"
					+ "partA_date,partB_sig,partB_date,payment,practice_begin,practice_end,contract_background,"
					+ "contract_summary,contract_start_time,contract_end_time,fine,work_time,job_name "
					+ "from contract_template where template_no = ?");
			ps.setString(1, id);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
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
	
	public int delete(String data){
		conn = DBConnect.getConn();
		int result = 0;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("delete from contract_template where template_no = ?");
			ps.setString(1, data);
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
	
	
}
