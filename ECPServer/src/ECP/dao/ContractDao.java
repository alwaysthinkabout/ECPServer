package ECP.dao;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.Statement;

import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

public class ContractDao extends BaseDao{
	
	public ContractDao(){
		
	}
	
	@Override
	public JSONArray query(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		StringBuffer sql =  new StringBuffer("SELECT contract_id, license_no,id_cardno,payment,contract_time,contract_start_time,contract_end_time, "
				+ "update_time,probation_start,probation_end,contract_status,contract_attachment,contract_modelno "
				+ "from contract where 1=1 ");
		try {
			if(data.has("contract_status")){
				sql.append(" and contract_status = '"+data.getString("contract_status")+"'");
			}
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
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
		return rdata;
	}
	
	//update by lgp 多插入partA,partB,job_apply_id三个值,并返回插入合同的ID
	@Override
	public int insert(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into contract(contract_code,license_no,id_cardno,job_code,payment,"
					+ "contract_time,contract_start_time,contract_end_time,update_time,probation_start,"
					+ "probation_end,contract_status,contract_attachment,contract_modelno,partA,partB,job_apply_id,org_id,job_name)"
					+ "values (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, data.has("contract_code")?data.getString("contract_code"):"");
			ps.setString(2, data.has("license_no")?data.getString("license_no"):"");
			ps.setString(3, data.has("id_cardno")?data.getString("id_cardno"):"");
			ps.setString(4, data.has("job_code")?data.getString("job_code"):"");
			ps.setString(5, data.has("payment")?data.getString("payment"):"");
			
			ps.setString(6, data.has("contract_time")?data.getString("contract_time"):"");
			ps.setString(7, data.has("contract_start_time")?data.getString("contract_start_time"):"");
			ps.setString(8, data.has("contract_end_time")?data.getString("contract_end_time"):"");
			ps.setString(9, data.has("update_time")?data.getString("update_time"):"");
			ps.setString(10, data.has("probation_start")?data.getString("probation_start"):"");
			
			ps.setString(11, data.has("probation_end")?data.getString("probation_end"):"");
			ps.setString(12, data.has("contract_status")?data.getString("contract_status"):"编辑中");
			ps.setString(13, data.has("contract_attachment")?data.getString("contract_attachment"):"");
			ps.setString(14, data.has("contract_modelno")?data.getString("contract_modelno"):"");
			ps.setString(15, data.has("partA")?data.getString("partA"):"");
			ps.setString(16, data.has("partB")?data.getString("partB"):"");
			ps.setString(17, data.has("job_apply_id")?data.getString("job_apply_id"):"");
			ps.setString(18, data.has("org_id")?data.getString("org_id"):"");
			ps.setString(19, data.has("job_name")?data.getString("job_name"):"");
			//result = ps.executeUpdate();
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
	
	public int delete(String data){
		conn = DBConnect.getConn();
		int result = 0;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("delete from contract where contract_id = ?");
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
	
	//updat by lgp 增加了partA,partB,job_apply_id,expire_date,cinsure_ratio,partB_teleNum,partB_sex,partBID_no,partA_teleNum,practice_begin,practice_end等返回字段
	public JSONArray findById(String id) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("SELECT con.contract_id,con.license_no,con.id_cardno,con.payment,con.contract_start_time,"
					+ "con.contract_end_time,con.probation_start,con.probation_end,con.contract_status,con.contract_modelno,con.parta,con.partb,con.job_apply_id,"
					+ "con.cinsure_ratio,con.partb_telenum,con.partb_sex,con.parta_telenum,con.practice_begin,con.practice_end,contacta,con.contactb,"
					+ "con.work_time,con.fine,con.job_name,con.contract_background,con.contract_summary,con.org_id,tem.contract_content,tem.contract_name,ja.user_id "
					+ "from contract con,contract_template tem,job_apply ja where con.contract_modelno=tem.template_no and con.job_apply_id=ja.job_apply_id and con.contract_id = ?");
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
	
	@Override
	public int update(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update contract set payment = ?,license_no = ?,"
					+ "contract_start_time = ?,contract_end_time = ?,probation_start = ?,"
					+ "probation_end = ?,cinsure_ratio =? ,"
					+ "contract_background = ?,contract_summary=?,job_name = ?,work_time = ?,fine = ?,"
					+ "parta_telenum = ?,practice_begin = ?,practice_end = ?,contacta = ?"
					+ " where contract_id = ?");
			ps.setString(1, data.has("payment")?data.getString("payment"):"");
			ps.setString(2, data.has("license_no")?data.getString("license_no"):"");
			ps.setString(3, data.has("contract_start_time")?data.getString("contract_start_time"):"");
			ps.setString(4, data.has("contract_end_time")?data.getString("contract_end_time"):"");
			ps.setString(5, data.has("probation_start")?data.getString("probation_start"):"");			
			ps.setString(6, data.has("probation_end")?data.getString("probation_end"):"");
			ps.setString(7, data.has("cinsure_ratio")?data.getString("cinsure_ratio"):"");
			ps.setString(8, data.has("contract_background")?data.getString("contract_background"):"");
			ps.setString(9, data.has("contract_summary")?data.getString("contract_summary"):"");
			ps.setString(10, data.has("job_name")?data.getString("job_name"):"");
			ps.setString(11, data.has("work_time")?data.getString("work_time"):"");
			ps.setString(12, data.has("fine")?data.getString("fine"):"");
			ps.setString(13, data.has("parta_telenum")?data.getString("parta_telenum"):"");
			ps.setString(14, data.has("practice_begin")?data.getString("practice_begin"):"");
			ps.setString(15, data.has("practice_end")?data.getString("practice_end"):"");
			ps.setString(16, data.has("contacta")?data.getString("contacta"):"");
			ps.setString(17, data.getString("contract_id"));
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
	
	//update by lgp 求职端更新合同
	public int update1(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update contract set contactb =?,partb_sex =?,"
					+ "partb_telenum = ?,id_cardno = ?"
					+ " where contract_id = ?");
			ps.setString(1, data.has("contactb")?data.getString("contactb"):"");
			ps.setString(2, data.has("partb_sex")?data.getString("partb_sex"):"");
			ps.setString(3, data.has("partb_telenum")?data.getString("partb_telenum"):"");
			ps.setString(4, data.has("id_cardno")?data.getString("id_cardno"):"");
			ps.setString(5, data.getString("contract_id"));
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
	
	//update by lgp 更新合同状态
	public int setContract_status(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update contract set contract_status =?"
					+ " where contract_id = ?");
			ps.setString(1, data.has("contract_status")?data.getString("contract_status"):"");
			ps.setString(2, data.getString("contract_id"));
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	//add by lgp 获取合同列表
	public JSONArray findContractListByLicense_no(JSONObject data) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();		
		try {
			StringBuffer sql;
			try {
				sql = new StringBuffer("select contract_id,con.license_no,con.org_id,con.contract_status,contract_modelno,contract_name,partB from contract_template tem,contract con"
						+" where tem.org_id = con.org_id and tem.template_no = con.contract_modelno and con.org_id = '"+data.getString("org_id")+"'");
				if(data.has("contract_status")){
					sql.append(" and contract_status = '"+data.getString("contract_status")+"'");
				}else
					{
						sql.append(" and contract_status !='已签订'");
					}
				ps = conn.prepareStatement(sql.toString());
				rs = ps.executeQuery();
				rdata = CDataTransform.rsToJson(rs);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	//add by lgp 求职端获取合同列表
	public JSONArray getContractListByUserId(String id) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select con.contract_id,con.parta,con.contract_status,tem.contract_name from contract_template tem,contract con"+
										" where tem.template_no = con.contract_modelno and con.job_apply_id in (select job_apply_id from job_apply where user_id=?) and (con.contract_status = '签订中' or con.contract_status = '已签订')"+
										" GROUP BY contract_id");
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
	
	public boolean IsExist(JSONObject data){
		conn = DBConnect.getConn();
		if(data.has("template_no")){
			try {
				ps = conn.prepareStatement("SELECT contract_id from contract where contract_modelno = ?");
				ps.setString(1, data.getString("template_no"));
				rs = ps.executeQuery();
				if(rs.next()){
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("系统错误");
				e.printStackTrace();
			} finally {
				DBConnect.close(conn,ps,rs);
			}
		}
		return false;
	}
	
	public JSONArray findByJobApplyId(String id) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("SELECT contract_id, license_no,id_cardno,payment,contract_time,contract_start_time,contract_end_time, "
				+ "update_time,probation_start,probation_end,contract_status,contract_attachment,contract_modelno "
				+ "from contract where 1=1 and job_apply_id = ? and contract_status !='已签订'");
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
}
