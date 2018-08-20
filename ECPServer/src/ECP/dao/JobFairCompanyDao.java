package ECP.dao;

import java.sql.SQLException;

import org.json.JSONArray;

import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

/*
 * @author durenshi
 * 招聘会企业表
 */
public class JobFairCompanyDao extends BaseDao{
	
	public JobFairCompanyDao(){
		
	}
	
	@Override
	public JSONArray findById(String code){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("SELECT org_id stationId,org_name stationName,stand_number exhibitionNum,"
					+ "jfc.job_info_id workId,job_name workName,job_location workPlace,payment workSalary "
					+ "from job_fair_company jfc "
					+ "LEFT JOIN organization_info oi on jfc.license_no = oi.license_no "
					+ "LEFT JOIN job_fair jf on jfc.job_fair_code = jf.job_fair_code "
					+ "LEFT JOIN job_info ji on jfc.job_info_id = ji.job_info_id "
					+ "where jfc.job_fair_code = ?");
			ps.setString(1, code);
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
	
}
