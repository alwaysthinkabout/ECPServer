package ECP.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.Statement;

import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

/*
 * @author durenshi
 * 企业信息表
 */
public class OrganizationInfoDao extends BaseDao{
	
	public OrganizationInfoDao() {
		// TODO Auto-generated constructor stub
	}
	
	//查看某个招聘站详情
	@Override
	public JSONArray findById(Integer id){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("SELECT oi.org_id,org_name,license_no,reg_address,reg_time,org_type,staffCount,webSite,legal_rep,reg_capital,business_scope,site,title,"
					+ "contact,contact_phone,contact_email,reg_auth,aim,holder,fund_src,oc.cid,url "
					+ "from organization_info oi LEFT JOIN org_cert oc on oi.org_id = oc.org_id where oi.org_id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	@Override
	public JSONArray query(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		StringBuffer sql =  new StringBuffer("select org_id,org_name,license_no,business_scope,webSite from organization_info where 1=1 ");
		try {
//			if(data.has("")){
//				sql.append(" and job_fair_city = '"+data.getString("city")+"'");
//			}
			System.out.println(sql.toString());
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
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return rdata;
	}
	
	@Override
	public int insert(JSONObject data) {
		conn = DBConnect.getConn();
		JSONObject store=null;
		try {
			store=data.getJSONObject("store");
			System.out.println(store);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into organization_info values (null,?,?,?,?,?, ?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, store.has("properties")?store.getString("properties"):"");//公司性质
			ps.setString(2, store.has("storeName")?store.getString("storeName"):"");//单位名
			ps.setString(3, store.has("registerPerson")?store.getString("registerPerson"):"");//注册人
			ps.setString(4, store.has("registerRegion")?store.getString("registerRegion"):"");//注册地
			
			ps.setString(5, store.has("workPlace")?store.getString("workPlace"):"");//所在地
			ps.setString(6, store.has("workPlace")?store.getString("workPlace"):"");
			ps.setString(7, store.has("coreBusiness")?store.getString("coreBusiness"):"");//营业范围
			ps.setString(8, store.has("registerFund")?store.getString("registerFund"):"");//注册资金
			ps.setString(9, store.has("license_no")?store.getString("license_no"):"");//营业执照号

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(store.has("registerTime")){
				String strDate = store.getString("registerTime");
				if(strDate.equals("")){
					ps.setDate(10, null);  //注册时间
				}else{
					java.util.Date ud=sdf.parse(strDate);
					java.sql.Date sd = new java.sql.Date(ud.getTime());
					ps.setDate(10, sd);  //注册时间
				}
			}else{
				ps.setDate(10, null);  //注册时间
			}
			ps.setString(11, store.has("staffAmount")?store.getString("staffAmount"):"");//公司在职人数
			
			ps.setString(12, store.has("officialWebsite")?store.getString("officialWebsite"):"");//公司网址
			ps.setString(13, store.has("title")?store.getString("title"):"");//公司资质称号
			ps.setString(14, store.has("contact")?store.getString("contact"):"");//招聘联系人
			ps.setString(15, store.has("contactPhone")?store.getString("contactPhone"):"");//联系人电话
			ps.setString(16, store.has("contactEmail")?store.getString("contactEmail"):"");//联系人email
			ps.setString(17, store.has("regAuth")?store.getString("regAuth"):"");//公司登记机关
			ps.setString(18, store.has("aim")?store.getString("aim"):"");//宗旨
			ps.setString(19, store.has("holder")?store.getString("holder"):"");//举办单位
			ps.setString(20, store.has("fundSrc")?store.getString("fundSrc"):"");//资金来源
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
	
	public int insertIntoOrgCert(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into org_cert (org_id,cid,url) values (?,?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, data.has("org_id")?data.getString("org_id"):"");
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
	
	@Override
	public int delete(Integer data){
		conn = DBConnect.getConn();
		int result = 0;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("delete from organization_info where org_id = ?");
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
	
	@Override
	public int update(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			data = data.getJSONObject("store");
			ps = conn.prepareStatement("update organization_info set org_type = ?, org_name=?, "
					+ "legal_rep = ?, reg_address=?, site=?, business_scope=?, reg_capital=?, "
					+ "license_no = ?, reg_time =?, staffCount=?, webSite = ?, title = ?, "
					+ "contact = ?, contact_phone = ?, contact_email = ?, reg_auth =?, aim = ?, holder = ?, fund_src = ? where org_id = ?");
			ps.setString(1, data.has("properties")?data.getString("properties"):"");//公司性质
			ps.setString(2, data.has("storeName")?data.getString("storeName"):"");//单位名
			ps.setString(3, data.has("registerPerson")?data.getString("registerPerson"):"");//注册人
			ps.setString(4, data.has("registerRegion")?data.getString("registerRegion"):"");//注册地
			
			ps.setString(5, data.has("workPlace")?data.getString("workPlace"):"");//所在地
			ps.setString(6, data.has("coreBusiness")?data.getString("coreBusiness"):"");//营业范围
			ps.setString(7, data.has("registerFund")?data.getString("registerFund"):"");//注册资金
			ps.setString(8, data.has("license_no")?data.getString("license_no"):"");//营业执照号

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = data.has("registerTime")?data.getString("registerTime"):"1970-01-01";
			if(strDate.equals("")){
				ps.setDate(9, null);  
			}
			else{
				java.util.Date ud=sdf.parse(strDate);
				java.sql.Date sd = new java.sql.Date(ud.getTime());
				ps.setDate(9, sd);  //注册时间
			}
			ps.setString(10, data.has("staffAmount")?data.getString("staffAmount"):"");//公司在职人数
			ps.setString(11, data.has("officialWebsite")?data.getString("officialWebsite"):"");//公司网址
			ps.setString(12, data.has("title")?data.getString("title"):"");//公司资质称号
			ps.setString(13, data.has("contact")?data.getString("contact"):"");//招聘联系人
			ps.setString(14, data.has("contactPhone")?data.getString("contactPhone"):"");//联系人电话
			ps.setString(15, data.has("contactEmail")?data.getString("contactEmail"):"");//联系人email
			ps.setString(16, data.has("regAuth")?data.getString("regAuth"):"");//公司登记机关
			ps.setString(17, data.has("aim")?data.getString("aim"):"");//宗旨
			ps.setString(18, data.has("holder")?data.getString("holder"):"");//举办单位
			ps.setString(19, data.has("fundSrc")?data.getString("fundSrc"):"");//资金来源
			ps.setString(20, data.getString("storeId"));
			
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
	
	/**
	 * 判断招聘站或营业执照号是否存在
	 * @return true:存在 ; false:不存在
	 */
	public boolean IsExist(JSONObject data){
		conn = DBConnect.getConn();
		try {
			data = data.getJSONObject("store");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//判断招聘站名称是否存在
		if(data.has("storeName")){
			try {
				ps = conn.prepareStatement("SELECT org_id,org_name,license_no from organization_info oi "
						+ "LEFT JOIN org_info_audit oa on oa.org_info_id = oi.org_id where org_name = ? and oa.audit_result != '不通过'");
				ps.setString(1, data.getString("storeName"));
				rs = ps.executeQuery();
				if(rs.next()){
					DBConnect.close(conn,ps,rs);
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("系统错误");
				e.printStackTrace();
			}
			
		}
		//判断营业执照编号是否存在
		if(data.has("license_no")){
			try {
				ps = conn.prepareStatement("SELECT org_id,org_name,license_no from organization_info oi "
						+ "LEFT JOIN org_info_audit oa on oa.org_info_id = oi.org_id where license_no = ? and oa.audit_result != '不通过'");
				ps.setString(1, data.getString("license_no"));
				rs = ps.executeQuery();
				if(rs.next()){
					DBConnect.close(conn,ps,rs);
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("系统错误");
				e.printStackTrace();
			}
		}
		DBConnect.close(conn,ps,rs);
		return false;
	}
	
	public boolean IsExistByOrgId(JSONObject data){
		conn = DBConnect.getConn();
		try {
			ps = conn.prepareStatement("SELECT * from organization_info "
					+ "LEFT JOIN org_info_audit on org_id = org_info_id where org_id = ? and audit_result = '通过'");
			ps.setString(1, data.getString("org_id"));
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
	
	public boolean isExistByLicenseNO(JSONObject data){
		conn = DBConnect.getConn();
		try {
			ps = conn.prepareStatement("SELECT * from organization_info where license_no = ? and org_id != ?");
			ps.setString(1, data.getString("license_no"));
			ps.setString(2, data.getString("org_id"));
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
	
	public boolean isExistByName(JSONObject data){
		conn = DBConnect.getConn();
		try {
			ps = conn.prepareStatement("SELECT * from organization_info where org_name = ? and org_id != ? ");
			ps.setString(1, data.getString("org_name"));
			ps.setString(2, data.getString("org_id"));
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
	
	public boolean IsExistAndAuditedSuccess(JSONObject data){
		conn = DBConnect.getConn();
		try {
			ps = conn.prepareStatement("SELECT * from organization_info LEFT JOIN org_info_audit on org_id = org_info_id "
					+ "where org_name = ? and org_id != ? and audit_result = '通过'");
			ps.setString(1, data.getString("org_name"));
			ps.setString(2, data.getString("org_id"));
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
	
	public int insertIntoUserOrg(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into user_org(user_id, org_id) values (?,?)");
			ps.setString(1, data.getString("user_id"));
			ps.setString(2, data.getString("org_id"));
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
	
	public int updateAfterAudit(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update organization_info set org_type = ?, org_name=?, "
					+ "legal_rep = ?, reg_address=?, site=?, business_scope=?, reg_capital=?, "
					+ "license_no = ?, reg_time =?, staffCount=?, webSite = ?, title = ?, "
					+ "contact = ?, contact_phone = ?, contact_email = ?, reg_auth =?, aim = ?, holder = ?, fund_src = ? where org_id = ?");
			ps.setString(1, data.has("org_type")?data.getString("org_type"):"");//公司性质
			ps.setString(2, data.has("org_name")?data.getString("org_name"):"");//单位名
			ps.setString(3, data.has("legal_rep")?data.getString("legal_rep"):"");//注册人
			ps.setString(4, data.has("reg_address")?data.getString("reg_address"):"");//注册地
			
			ps.setString(5, data.has("site")?data.getString("site"):"");//所在地
			ps.setString(6, data.has("business_scope")?data.getString("business_scope"):"");//营业范围
			ps.setString(7, data.has("reg_capital")?data.getString("reg_capital"):"");//注册资金
			ps.setString(8, data.has("license_no")?data.getString("license_no"):"");//营业执照号

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = data.has("reg_time")?data.getString("reg_time"):"1970-01-01";
			if(strDate.equals("")){
				ps.setDate(9, null);  
			}
			else{
				java.util.Date ud=sdf.parse(strDate);
				java.sql.Date sd = new java.sql.Date(ud.getTime());
				ps.setDate(9, sd);  //注册时间
			}
			ps.setString(10, data.has("staffcount")?data.getString("staffcount"):"");//公司在职人数
			ps.setString(11, data.has("website")?data.getString("website"):"");//公司网址
			ps.setString(12, data.has("title")?data.getString("title"):"");//公司资质称号
			ps.setString(13, data.has("contact")?data.getString("contact"):"");//招聘联系人
			ps.setString(14, data.has("contact_phone")?data.getString("contact_phone"):"");//联系人电话
			ps.setString(15, data.has("contact_email")?data.getString("contact_email"):"");//联系人email
			ps.setString(16, data.has("reg_auth")?data.getString("reg_auth"):"");//公司登记机关
			ps.setString(17, data.has("aim")?data.getString("aim"):"");//宗旨
			ps.setString(18, data.has("holder")?data.getString("holder"):"");//举办单位
			ps.setString(19, data.has("fund_src")?data.getString("fund_src"):"");//资金来源
			ps.setString(20, data.getString("org_id"));
			
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
