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
 * 用户求职申请表
 */
public class JobApplyDao extends BaseDao{
	private JobInfoDao jobInfoDao;
	
	public JobApplyDao(){
		jobInfoDao = new JobInfoDao();
	}
	
	@Override
	public JSONArray query(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		StringBuffer sql =  new StringBuffer("select job_apply_id,job_name,pi.name user_name,apply_time,apply_desc,apply_attachment,apply_demand,apply_status_code,is_read "
				+ "from job_apply ja LEFT JOIN job_info ji ON ja.job_info_id = ji.job_info_id "
				+ "LEFT JOIN person_info pi on pi.user_code = ja.user_id "
				+ "LEFT JOIN user_info ui ON ja.user_id = ui.uid where 1=1 ");
		try {
			if(data.has("license_no")){
				sql.append(" AND ja.license_no = '"+data.getString("license_no")+"'");
			}
			if(data.has("is_read")){
				sql.append(" AND ja.is_read = '"+data.getString("is_read")+"'");
			}
			if(data.has("apply_status")){
				sql.append(" AND apply_status_code = '"+data.getString("apply_status")+"'");
			}
			if(data.has("searchInfo")){
				sql.append(" AND (pi.name like '%"+data.getString("searchInfo")+"%'");
				sql.append(" or job_name like '%"+data.getString("searchInfo")+"%')");
			}
			sql.append(" order by apply_time desc ");
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
	
	//update by lgp 求职申请若为未读，则再查看的时候置为已读
	@Override
	public JSONArray findById(Integer id) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		int result = 0;
		try {
			ps = conn.prepareStatement("select job_apply_id,job_name,ja.user_id,ja.is_read,user_name apply_name,apply_time, "
					+ "apply_desc,apply_demand,apply_status_code,pi.email,pi.sex,pi.phone,pi.ID_cardNo,pi.name "
					+ "from job_apply ja LEFT JOIN job_info ji on ja.job_info_id = ji.job_info_id "
					+ "LEFT JOIN user_info ui on ui.uid = ja.user_id "
					+ "LEFT JOIN person_info pi on pi.user_code = ui.uid "
					+ "where job_apply_id = ? order by apply_time desc ");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJsonLabel(rs);
			try {
				String is_read = rdata.getJSONObject(0).getString("is_read");
				if(is_read.equals("未读")){
					ps = conn.prepareStatement("update job_apply set is_read='已读' where job_apply_id = ?");
					ps.setInt(1, id);
					result = ps.executeUpdate();
					if(result>0){
						return rdata;
					}else{
						return null;
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	
	//批量插入岗位申请
	@Override
	public int insert(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into job_apply values (null,?,?,?,?,?, ?,?,?,?,? ,?,?,?)");
			for (int i =0;i<data.getJSONArray("workList").length();i++) {
				ps.setString(1, data.getJSONArray("workList").getJSONObject(i).getString("workId"));
				ps.setString(2, data.has("job_apply_code")?data.getString("job_apply_code"):"");
				ps.setString(3, data.has("id_cardNo")?data.getString("id_cardNo"):"");
				ps.setString(4, data.has("apply_status_code")?data.getString("apply_status_code"):"未受理");
				
				//获取当前时间--创建时间
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String create_date =  df.format(System.currentTimeMillis());
				java.util.Date ud = df.parse(create_date);
				java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
				ps.setTimestamp(5, st);
				
				ps.setString(6, data.has("apply_desc")?data.getString("apply_desc"):"");
				ps.setString(7, data.has("apply_attachment")?data.getString("apply_attachment"):"");
				ps.setString(8, data.has("personalizedReq")?data.getString("personalizedReq"):"");
				ps.setString(9, jobInfoDao.findLicenseById(data.getJSONArray("workList").getJSONObject(i).getString("workId")));
				ps.setString(10, data.getString("userId"));
				ps.setInt(11, 0);
				ps.setString(12, data.getJSONArray("workList").getJSONObject(i).getString("workId"));
				ps.setString(13, "未读");
			    ps.addBatch();
			}
			ps.executeBatch();
			result = 1;
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
	
	public int updateStatus(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update job_apply set apply_status_code = ? where job_apply_id = ?");
			ps.setString(1, data.has("apply_status2")?data.getString("apply_status2"):"未处理");
			ps.setString(2, data.getString("job_apply_id"));
			result = ps.executeUpdate();
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
	
	public JSONArray findByUserId(JSONObject data) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		StringBuffer sql =  new StringBuffer("select ja.job_info_id wordId, job_name workName, org_name workStation, "
				+ "apply_time applyTime, apply_status_code applyState "
				+ "from job_apply ja INNER JOIN job_info ji on ja.job_info_id = ji.job_info_id "
				+ "INNER JOIN organization_info oi on oi.license_no = ja.license_no "
				+ "where 1=1 ");
		try {
			if(data.has("userId")){
				sql.append(" and user_id = '"+data.getString("userId")+"'");
			}
			if(data.has("applyState")){
				sql.append(" and apply_status_code = '"+data.getString("applyState")+"'");
			}
			sql.append(" order by apply_time desc ");
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
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	public int insertLeave(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into job_leave values (null,?,?,?,?,?)");
			ps.setString(1, data.getString("workId"));
			ps.setString(2, data.getString("userId"));
			ps.setString(3, data.has("leave")?data.getString("leave"):"");
			//获取当前时间--创建时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_date =  df.format(System.currentTimeMillis());
			java.util.Date ud = df.parse(create_date);
			java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
			ps.setTimestamp(4, st);
			ps.setString(5, "未读");
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
	
	public boolean IsExist(JSONObject data){
		conn = DBConnect.getConn();
		StringBuffer sql = new StringBuffer("SELECT job_apply_id from job_apply where 1=1 ");
		try {
			sql.append(" and user_id = '"+data.getString("userId")+"'");
			JSONArray workList = data.getJSONArray("workList");
			String temp_ids = "";
			for(int i=0;i<workList.length();i++){
				temp_ids += workList.getJSONObject(i).getString("workId");
				temp_ids += ",";
			}
			temp_ids = temp_ids.substring(0,temp_ids.length()-1);
			sql.append(" and job_info_id in( "+temp_ids +" )");
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
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
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return false;
	}
	
	public JSONArray IsApplied(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray ret = new JSONArray();
		StringBuffer sql = new StringBuffer("SELECT distinct job_info_id from job_apply where 1=1 and user_id = ? and apply_status_code != '已退回'");
		try {
			String ids = data.has("ids")?data.getJSONArray("ids").join(","):"";
			if(!ids.equals("")) 
				sql.append(" and job_info_id in ("+ids+")");
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, data.getString("user_id"));
			rs = ps.executeQuery();
			ret = CDataTransform.rsToJsonLabel(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统错误");
			e.printStackTrace();
		} finally{
			DBConnect.close(conn,ps,rs);
		}
		return ret;
	}
	
	public JSONArray findAppliers(JSONObject data) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		StringBuffer sql =  new StringBuffer("select DISTINCT ja.user_id,ja.apply_time,ja.job_apply_id,name,ji.job_name,sex,pi.degree,phone,email from job_apply ja "
				+ "LEFT JOIN user_info ui on ui.uid = ja.user_id "
				+ "LEFT JOIN person_info pi on pi.user_code = ja.user_id "
				+ "LEFT JOIN job_info ji on ji.job_info_id = ja.job_info_id "
				+ "LEFT JOIN job_type jt on jt.type_id = ji.job_type_pcode "
				+ "where 1 = 1");
		try {
			if(data.has("license_no")){
				sql.append(" and ja.license_no != '"+data.getString("license_no")+"'");
			}
			if(data.has("type_id")&&!data.getString("type_id").equals("")){
				sql.append(" and jt.type_id = '"+data.getString("type_id")+"'");
			}
			if(data.has("degree")&&!data.getString("degree").equals("")){
				sql.append(" and pi.degree = '"+data.getString("degree")+"'");
			}
			if(data.has("attendacne_type")&&!data.getString("attendacne_type").equals("")){
				sql.append(" and ji.attendacne_type = '"+data.getString("attendacne_type")+"'");
			}
			//sql.append(" order by apply_time desc ");
			sql.append(" group by user_id ");
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
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	//获取当前招聘端所有未读求职申请条数
	public JSONArray findApplysCountById(Integer id){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("SELECT COUNT(job_apply_id) applysCount from organization_info oi LEFT JOIN job_apply ja on oi.license_no = ja.license_no where oi.org_id = ? and ja.is_read='未读'");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("程序出现异常");
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	public String findByJobInfoId(String id) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		String result = "";
		try {
			ps = conn.prepareStatement("select org_id from organization_info oi,job_info ji where oi.license_no = ji.license_no and ji.job_info_id = ?");
			ps.setString(1, id);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
			try {
				if(rdata != null && rdata.length()>0){
					result = rdata.getJSONObject(0).getString("org_id");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("程序出现异常");
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
}
