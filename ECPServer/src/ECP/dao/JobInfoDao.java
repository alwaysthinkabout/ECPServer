package ECP.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

/*
 * @author durenshi
 * 岗位信息表
 */
public class JobInfoDao extends BaseDao{
	public JobInfoDao() {
		
	}
	
	//请求招聘站列表数据
	@Override
	public JSONArray query(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		StringBuffer sql =  new StringBuffer("SELECT 0 as is_apply,org_id,org_name,job_info_id,job_name,job_location,payment "
				+ "from job_info ji LEFT JOIN organization_info oi "
				+ "ON ji.license_no = oi.license_no LEFT JOIN job_type jt on jt.type_id = ji.job_type_scode where 1=1 ");
		try {
			if(data.has("stationId")){
				sql.append(" and org_id = "+data.getString("stationId"));
			}
			if(data.has("city")){
				sql.append(" and job_city like '"+data.getString("city")+"%'");
			}
			if(data.has("profession")){
				sql.append(" and jt.job_type_name like '%"+data.getString("profession")+"%'");
			}
			if (data.has("salary")) {
				String payment = data.getString("salary");
				if (payment.equals("4000元以下")) {
					sql.append(" and payment < 4000 ");
				}
				else if (payment.equals("4000-6000")) {
					sql.append(" and payment between 4000 and 6000 ");
				}
				else if (payment.equals("6000-8000")) {
					sql.append(" and payment between 6000 and 8000");
				}
				else if (payment.equals("8000-10000")) {
					sql.append(" and payment between 8000 and 10000");
				}
				else if (payment.equals("10000-12000")) {
					sql.append(" and payment between 10000 and 12000");
				}
				else if (payment.equals("12000以上")) {
					sql.append(" and payment > 12000");
				}
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
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	//查看招聘站里的招聘岗位详情
	@Override
	public JSONArray findById(Integer id){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("SELECT job_info_id workId,job_name workName,org_name stationName,org_id,job_location workPlace,publish_time publicTime,end_time lineTime, "
					+ "number requestNum,attendacne_type workForm,payment salary,job_duty description, job_require abilityReq,payment_type salaryType, "
					+ "accommodation,job_city workCity,degree educationReq,ji.title titleReq,CONCAT_WS('-',jt.job_type_name,jt2.job_type_name)workType,other_payment welfare "
					+ "from job_info ji LEFT JOIN organization_info oi ON ji.license_no = oi.license_no "
					+ "LEFT JOIN job_type jt on ji.job_type_pcode = jt.type_id "
					+ "left join job_type jt2 on ji.job_type_scode = jt2.type_id "
					+ "where job_info_id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJsonLabel(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	@Override
	public int insert(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			data = data.getJSONObject("job");
			ps = conn.prepareStatement("insert into job_info values (null,?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?)");
			ps.setString(1, data.has("jobCode")?data.getString("jobCode"):"");
			ps.setString(2, data.has("license_no")?data.getString("license_no"):"");
			ps.setString(3, data.has("jobName")?data.getString("jobName"):"");
			ps.setString(4, data.has("job_type_pcode")?data.getString("job_type_pcode"):"");
			ps.setString(5, data.has("job_type_scode")?data.getString("job_type_scode"):"");
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String create_date =  data.has("publishTime")?data.getString("publishTime"):"1970-01-01";
			java.util.Date ud = df.parse(create_date);
			java.sql.Date st = new java.sql.Date(ud.getTime());
			ps.setDate(6, st);
			
			String end_date =  data.has("endTime")?data.getString("endTime"):"1970-01-01";
			ud = df.parse(end_date);
			st = new java.sql.Date(ud.getTime());
			ps.setDate(7, st);
			
			ps.setString(8, data.has("job_status")?data.getString("job_status"):"");
			ps.setString(9, (data.has("number")&&!data.getString("number").equals(""))?data.getString("number"):"1");
			ps.setString(10, data.has("keywords")?data.getString("keywords"):"");
			ps.setString(11, data.has("major_type_pid")?data.getString("major_type_pid"):"");
			ps.setString(12, data.has("major_type_sid")?data.getString("major_type_sid"):"");
			ps.setString(13, data.has("timeType")?data.getString("timeType"):"");
			ps.setString(14, data.has("payment")?data.getString("payment"):"");
			ps.setString(15, data.has("description")?data.getString("description"):"");
			ps.setString(16, data.has("jobLocation")?data.getString("jobLocation"):"");
			ps.setString(17, data.has("requirements")?data.getString("requirements"):"");
			ps.setString(18, data.has("jobCity")?data.getString("jobCity"):"");
			ps.setString(19, data.has("province")?data.getString("province"):"");
			ps.setString(20, data.has("other_payment")?data.getString("other_payment"):""); //其他待遇
			ps.setString(21, data.has("accommodation")?data.getString("accommodation"):""); //住宿
			ps.setString(22, data.has("degree")?data.getString("degree"):"");               //学历
			ps.setString(23, data.has("title")?data.getString("title"):"");                 //职称
			ps.setString(24, data.has("payment_type")?data.getString("payment_type"):"");   //工资类型
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
	
	@Override
	public int delete(Integer data){
		conn = DBConnect.getConn();
		int result = 0;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("delete from job_info where job_info_id = ?");
			ps.setInt(1, data);
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

	public JSONArray findByJobId(String id){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select job_info_id jobId,org_id storeId, job_type_scode,job_name jobName,publish_time publishTime, jt.type_id typeId, jt.job_type_name jobTypeName,"
					+ "end_time endTime,job_keyword keywords,attendacne_type timeType,job_city jobCity,job_province jobProvince, "
					+ "job_location jobLocation,number,job_duty description,payment,job_require requirements,other_payment,accommodation,degree,ji.title,payment_type "
					+ "from job_info ji LEFT JOIN organization_info oi on oi.license_no = ji.license_no "
					+ "LEFT JOIN job_type jt on jt.type_id = ji.job_type_pcode where job_info_id = ?");
			ps.setString(1, id);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJsonLabel(rs);
			if(rdata.length()>0)
			{
				JSONArray array = new JSONArray();
				ps = conn.prepareStatement("select job_type_name,type_id from job_info,job_type where type_id = job_type_scode and job_info_id=?");
				ps.setString(1, id);
				rs = ps.executeQuery();
				array = CDataTransform.rsToJsonLabel(rs);
				if(array.length()>0){
					try {
						rdata.getJSONObject(0).put("job_type_scode",array.getJSONObject(0).getString("type_id"));
						rdata.getJSONObject(0).put("job_stype_name",array.getJSONObject(0).getString("job_type_name"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
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
		return rdata;
	}
	
	@Override
	public int update(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			data = data.getJSONObject("job");
			ps = conn.prepareStatement("update job_info set job_code = ?,license_no = ?,job_name = ?,job_type_pcode = ?,job_type_scode=?,"
					+ " publish_time=? ,end_time = ?,job_status = ?, number = ?,job_keyword = ?,"
					+ " major_type_pid = ?,major_type_sid = ?, attendacne_type= ?,payment = ?,job_duty = ?,"
					+ " job_location = ?, job_require = ?,job_city = ?, job_province = ?,other_payment = ?,"
					+ " accommodation = ?, degree = ?, title = ?, payment_type=? where job_info_id = ?");
			ps.setString(1, data.has("jobCode")?data.getString("jobCode"):"");
			ps.setString(2, data.has("license_no")?data.getString("license_no"):"");
			ps.setString(3, data.has("jobName")?data.getString("jobName"):"");
			ps.setString(4, data.has("job_type_pcode")?data.getString("job_type_pcode"):"");
			ps.setString(5, data.has("job_type_scode")?data.getString("job_type_scode"):"");
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String create_date =  data.has("publishTime")?data.getString("publishTime"):"1970-01-01";
			java.util.Date ud = df.parse(create_date);
			java.sql.Date st = new java.sql.Date(ud.getTime());
			ps.setDate(6, st);
			
			String end_date =  data.has("endTime")?data.getString("endTime"):"1970-01-01";
			ud = df.parse(end_date);
			st = new java.sql.Date(ud.getTime());
			ps.setDate(7, st);
			
			ps.setString(8, data.has("job_status")?data.getString("job_status"):"");
			ps.setString(9, data.has("number")?data.getString("number"):"1");
			ps.setString(10, data.has("keywords")?data.getString("keywords"):"");
			ps.setString(11, data.has("major_type_pid")?data.getString("major_type_pid"):"");
			ps.setString(12, data.has("major_type_sid")?data.getString("major_type_sid"):"");
			ps.setString(13, data.has("timeType")?data.getString("timeType"):"");
			ps.setString(14, data.has("payment")?data.getString("payment"):"");
			ps.setString(15, data.has("description")?data.getString("description"):"");
			ps.setString(16, data.has("jobLocation")?data.getString("jobLocation"):"");
			ps.setString(17, data.has("requirements")?data.getString("requirements"):"");
			ps.setString(18, data.has("jobCity")?data.getString("jobCity"):"");
			ps.setString(19, data.has("province")?data.getString("province"):"");
			ps.setString(20, data.has("other_payment")?data.getString("other_payment"):""); //其他待遇
			ps.setString(21, data.has("accommodation")?data.getString("accommodation"):""); //住宿
			ps.setString(22, data.has("degree")?data.getString("degree"):"");               //学历
			ps.setString(23, data.has("title")?data.getString("title"):"");
			ps.setString(24, data.has("payment_type")?data.getString("payment_type"):"");   
			ps.setString(25, data.has("jobId")?data.getString("jobId"):"");
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
	
	public JSONArray findByLicenseNo(String licenseNo){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		StringBuffer sql =  new StringBuffer("select job_info_id jobId,org_id storeId, job_name jobName,publish_time publishTime,jt.job_type_name jobTypeName, "
				+ "end_time endTime,job_keyword keywords,attendacne_type timeType,job_city jobCity,job_province jobProvince, "
				+ "job_location jobLocation,number,job_duty description,payment,job_require requirements "
				+ "from job_info ji LEFT JOIN organization_info oi on oi.license_no = ji.license_no "
				+ "LEFT JOIN job_type jt on jt.type_id = ji.job_type_pcode where oi.license_no = ?");
		try {
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, licenseNo);
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
	
	public String findLicenseById(String jobId){
		conn = DBConnect.getConn();
		StringBuffer sql =  new StringBuffer("select license_no from job_info where job_info_id = ?");
		String license = "";
		try {
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, jobId);
			rs = ps.executeQuery();
			if (rs.next()) {
				license = rs.getString(1);
			}
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
		return license;
	}
	
	public JSONArray findByKeywordCity(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		StringBuffer sql =  new StringBuffer("select 0 as is_apply,job_info_id wordId,job_name workName,org_name workStation,job_location wordPlace,payment workSalary "
				+ "from job_info ji INNER JOIN organization_info oi on ji.license_no = oi.license_no "
				+ "where 1=1 ");
		try {
			if(data.has("keyword")){
				sql.append(" and job_keyword like '%"+data.getString("keyword")+"%"+"' or job_name like '%"+data.getString("keyword")+"%"+"'");
			}
			if(data.has("city")){
				sql.append(" and job_city like '%"+data.getString("city")+"%"+"'");
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
			System.out.println("系统系统出现非数据库异常。");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	public JSONArray findHotJob(JSONObject data) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			StringBuffer sqlBuffer = new StringBuffer("select 0 as is_apply,ji.job_info_id workId,job_name workName,org_name workStation,job_location workPlace,payment workSalary "
					+ "from job_info ji,organization_info oi "
					+ "where oi.license_no = ji.license_no and job_city like '%"+data.getString("city")+"%' "
					+ "order by ji.job_info_id desc limit 10 ");
			ps = conn.prepareStatement(sqlBuffer.toString());
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJsonLabel(rs);
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
	
	public JSONArray findSortWork(JSONObject data) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		StringBuffer sql = new StringBuffer("select 0 as is_apply,job_info_id workId,job_name workName,org_name workStation,job_location workPlace,payment workSalary "
					+ "from job_info ji,organization_info oi,job_type jt "
					+ "where  oi.license_no = ji.license_no and ji.job_type_pcode = jt.type_id ");
		try {
			if(data.has("city")){
				sql.append(" AND job_city like '%"+data.getString("city")+"%'");
			}
			if(data.has("sort")){
				sql.append(" AND jt.job_type_name like '%"+data.getString("sort")+"%'");
			}
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJsonLabel(rs);
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
	
	public JSONArray findJobTypeList(JSONObject data) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select type_id,job_type_name from job_type where job_type_pcode is NULL");
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
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
		return rdata;
	}
	
	public JSONArray findJobTypeSecondList(JSONObject data) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select type_id,job_type_name from job_type where job_type_pcode = ?");
			ps.setString(1, data.getString("root_id"));
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
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
		return rdata;
	}
	
	public String findJobTypeName(JSONObject data) {
		conn = DBConnect.getConn();
		String typeName = "";
		try {
			ps = conn.prepareStatement("select type_id,job_type_name from job_type where type_id = ?");
			ps.setString(1, data.getString("type_id"));
			rs = ps.executeQuery();
			if (rs.next()) {
				typeName = rs.getString("job_type_name");
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
		return typeName;
	}
	
}
