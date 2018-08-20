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
 * 云制造任务表
 * durenshi
 */
public class CloudMakeTaskDao extends BaseDao{
	
	public CloudMakeTaskDao(){
		
	}
	
	public JSONObject findReleasableSubTasks(JSONObject data){
		conn = DBConnect.getConn();
		JSONObject resultObject = new JSONObject();
		StringBuffer sql =  new StringBuffer("select task_id,tender_user_id,bid_winner_user_id,father_task_id,is_confirm_subtask,status,time,"
				+ "name,type,is_decomposable,specification,`constraint`,quota,quality,qualification,price,dead_time,"
				+ "mod_request,requirement_doc,compose_templet,complete_body "
				+ "from cloudmake_task where 1=1 and is_confirm_subtask = 1 and status ='未招标'");
		try {
			if(data.has("userId")){
				sql.append(" AND tender_user_id = '"+data.getString("userId")+"'");
			}
			sql.append(" order by time desc ");
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			resultObject.put("opResult", "0");
			JSONArray rdata = CDataTransform.rsToJson(rs);
			resultObject.put("releasableSubtasks", rdata);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
			try {
				resultObject.put("opResult", "1");
				DBConnect.close(conn,ps,rs);
				return resultObject;
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return resultObject;
	}
	
	public JSONObject findComposableTasks(JSONObject data){
		conn = DBConnect.getConn();
		JSONObject resultObject = new JSONObject();
		StringBuffer sql =  new StringBuffer("select task_id,tender_user_id,bid_winner_user_id,father_task_id,is_confirm_subtask,status,time,"
				+ "name,type,is_decomposable,specification,`constraint`,quota,quality,qualification,price,dead_time,"
				+ "mod_request,requirement_doc,compose_templet,complete_body "
				+ "from cloudmake_task where 1=1 ");
		try {
			if(data.has("taskIds")&&!data.getString("taskIds").equals("")){
				sql.append(" AND task_id in ( "+data.getString("taskIds")+" )");
			}
			else {
				sql.append(" and task_id = '' ");
			}
			sql.append(" order by time desc ");
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			resultObject.put("opResult", "0");
			JSONArray rdata = CDataTransform.rsToJson(rs);
			resultObject.put("composableTasks", rdata);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
			try {
				resultObject.put("opResult", "1");
				DBConnect.close(conn,ps,rs);
				return resultObject;
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return resultObject;
	}
	
	public JSONObject findTenderingTasks(JSONObject data){
		conn = DBConnect.getConn();
		JSONObject resultObject = new JSONObject();
		StringBuffer sql =  new StringBuffer("select task_id,tender_user_id,bid_winner_user_id,father_task_id,is_confirm_subtask,status,time,"
				+ "name,type,is_decomposable,specification,`constraint`,quota,quality,qualification,price,dead_time,"
				+ "mod_request,requirement_doc,compose_templet,complete_body "
				+ "from cloudmake_task where 1=1 and (status ='招标中' or status ='用户放弃' or status ='申诉未通过')");
		try {
			if(data.has("userId")){
				sql.append(" AND tender_user_id = '"+data.getString("userId")+"'");
			}
			sql.append(" order by time desc ");
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			resultObject.put("opResult", "0");
			JSONArray rdata = CDataTransform.rsToJson(rs);
			resultObject.put("tenderingTasks", rdata);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
			try {
				resultObject.put("opResult", "1");
				DBConnect.close(conn,ps,rs);
				return resultObject;
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return resultObject;
	}
	
	public JSONObject findWinBidTasks(JSONObject data){
		conn = DBConnect.getConn();
		JSONObject resultObject = new JSONObject();
		StringBuffer sql =  new StringBuffer("select task_id,tender_user_id,bid_winner_user_id,father_task_id,is_confirm_subtask,status,time,"
				+ "name,type,is_decomposable,specification,`constraint`,quota,quality,qualification,price,dead_time,"
				+ "mod_request,requirement_doc,compose_templet,complete_body "
				+ "from cloudmake_task where 1=1 and (status !='未招标' and status !='招标中') and deleteUser != 'tenderUser' ");
		try {
			if(data.has("userId")){
				sql.append(" AND tender_user_id = '"+data.getString("userId")+"'");
			}
			sql.append(" order by time desc ");
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			resultObject.put("opResult", "0");
			JSONArray rdata = CDataTransform.rsToJson(rs);
			resultObject.put("winBidTasks", rdata);
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
			try {
				resultObject.put("opResult", "1");
				DBConnect.close(conn,ps,rs);
				return resultObject;
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return resultObject;
	}
	
	public JSONObject findComposedTasks(JSONObject data){
		conn = DBConnect.getConn();
		JSONObject resultObject = new JSONObject();
		StringBuffer sql =  new StringBuffer("select task_id,tender_user_id,bid_winner_user_id,father_task_id,is_confirm_subtask,status,time,"
				+ "name,type,is_decomposable,specification,`constraint`,quota,quality,qualification,price,dead_time,"
				+ "mod_request,requirement_doc,compose_templet,complete_body "
				+ "from cloudmake_task where 1=1 and status ='已合成'");
		try {
			if(data.has("userId")){
				sql.append(" AND tender_user_id = '"+data.getString("userId")+"'");
			}
			sql.append(" order by time desc ");
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			resultObject.put("opResult", "0");
			JSONArray rdata = CDataTransform.rsToJson(rs);
			resultObject.put("composedTasks", rdata);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
			try {
				resultObject.put("opResult", "1");
				DBConnect.close(conn,ps,rs);
				return resultObject;
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return resultObject;
	}
	
	public JSONObject findMyTasks(JSONObject data){
		conn = DBConnect.getConn();
		JSONObject resultObject = new JSONObject();
		StringBuffer sql =  new StringBuffer("select task_id,tender_user_id,bid_winner_user_id,father_task_id,is_confirm_subtask,status,time,"
				+ "name,type,is_decomposable,specification,`constraint`,quota,quality,qualification,price,dead_time,"
				+ "mod_request,requirement_doc,compose_templet,complete_body "
				+ "from cloudmake_task where 1=1 and (deleteUser != 'bidUser') ");
		try {
			if(data.has("userId")){
				sql.append(" AND bid_winner_user_id = '"+data.getString("userId")+"'");
			}
			if(data.has("inputStatus")){
				if (data.getString("inputStatus").equals("已分解")) {
					sql.append(" AND (status = '已分解'or status = '已完成' or status = '申诉通过' or status = '已合成')");
				}
				else 
					sql.append(" AND status = '"+data.getString("inputStatus")+"'");
			}
			sql.append(" order by time desc ");
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			resultObject.put("opResult", "0");
			JSONArray rdata = CDataTransform.rsToJson(rs);
			resultObject.put("myTasks", rdata);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
			try {
				resultObject.put("opResult", "1");
				DBConnect.close(conn,ps,rs);
				return resultObject;
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return resultObject;
	}
	
	public JSONObject findSubTasks(JSONObject data){
		conn = DBConnect.getConn();
		JSONObject resultObject = new JSONObject();
		StringBuffer sql =  new StringBuffer("select task_id,tender_user_id,bid_winner_user_id,father_task_id,is_confirm_subtask,status,time,"
				+ "name,type,is_decomposable,specification,`constraint`,quota,quality,qualification,price,dead_time,"
				+ "mod_request,requirement_doc,compose_templet,complete_body "
				+ "from cloudmake_task where 1=1");
		try {
			if(data.has("userId")){
				sql.append(" AND tender_user_id = '"+data.getString("userId")+"'");
			}
			if (data.has("taskId")) {
				sql.append(" and father_task_id = '"+data.getString("taskId")+"'");
			}
			sql.append(" order by time desc ");
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			resultObject.put("opResult", "0");
			JSONArray rdata = CDataTransform.rsToJson(rs);
			resultObject.put("subTasks", rdata);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
			try {
				resultObject.put("opResult", "1");
				DBConnect.close(conn,ps,rs);
				return resultObject;
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return resultObject;
	}
	
	public JSONObject findOneTask(JSONObject data){
		conn = DBConnect.getConn();
		JSONObject resultObject = new JSONObject();
		StringBuffer sql =  new StringBuffer("select task_id,tender_user_id,bid_winner_user_id,father_task_id,is_confirm_subtask,status,time,"
				+ "name,type,is_decomposable,specification,`constraint`,quota,quality,qualification,price,dead_time,"
				+ "mod_request,requirement_doc,compose_templet,complete_body,deleteUser "
				+ "from cloudmake_task where 1=1");
		try {
			if(data.has("taskId")){
				sql.append(" AND task_id = '"+data.getString("taskId")+"'");
			}
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			resultObject.put("opResult", "0");
			JSONArray rdata = CDataTransform.rsToJson(rs);
			resultObject.put("oneTask", rdata.getJSONObject(0));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
			try {
				resultObject.put("opResult", "1");
				DBConnect.close(conn,ps,rs);
				return resultObject;
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			DBConnect.close(conn,ps,rs);
		}
		return resultObject;
	}
	
	public JSONArray findByFatherTaskId(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		StringBuffer sql =  new StringBuffer("select task_id,tender_user_id,bid_winner_user_id,father_task_id,is_confirm_subtask,status,time,"
				+ "name,type,is_decomposable,specification,`constraint`,quota,quality,qualification,price,dead_time,"
				+ "mod_request,requirement_doc,compose_templet,complete_body,deleteUser "
				+ "from cloudmake_task where 1=1");
		try {
			if(data.has("taskId")){
				sql.append(" AND task_id = '"+data.getString("fatherId")+"'");
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
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into cloudmake_task(tender_user_id,name,type,is_decomposable,specification, `constraint`,quota,quality,qualification,price ,dead_time,time,status) "
					+ " values (?,?,?,?,?, ?,?,?,?,? ,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, data.getString("userId"));
			ps.setString(2, data.has("taskName")?data.getString("taskName"):"");
			ps.setString(3, data.has("taskType")?data.getString("taskType"):"");
			ps.setString(4, data.has("decomposable")?data.getString("decomposable"):"");
			
			ps.setString(5, data.has("taskSpecification")?data.getString("taskSpecification"):"");
			ps.setString(6, data.has("taskConstraint")?data.getString("taskConstraint"):"");
			ps.setString(7, data.has("taskQuota")?data.getString("taskQuota"):"");
			ps.setString(8, data.has("taskQuality")?data.getString("taskQuality"):"");
			ps.setString(9, data.has("taskQualification")?data.getString("taskQualification"):"");
			ps.setString(10, data.has("taskPrice")?data.getString("taskPrice"):"");
			ps.setString(11, data.has("taskDeadtime")?data.getString("taskDeadtime"):"");
			//获取当前时间--创建时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_date =  df.format(System.currentTimeMillis());
			java.util.Date ud = df.parse(create_date);
			java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
			ps.setTimestamp(12, st);
			ps.setString(13, data.has("status")?data.getString("status"):"招标中");
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现异常。");
			e.printStackTrace();
		}
		finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public int insertSubTask(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into cloudmake_task(tender_user_id,name,type,is_decomposable,specification, "
					+ "`constraint`,quota,quality,qualification,price, "
					+ "dead_time,time,status,bid_winner_user_id,father_task_id, is_confirm_subtask) "
					+ "values (?,?,?,?,?, ?,?,?,?,? ,?,?,?,?,? ,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, data.getString("tenderUserId"));
			ps.setString(2, data.has("taskName")?data.getString("taskName"):"");
			ps.setString(3, data.has("taskType")?data.getString("taskType"):"");
			ps.setString(4, data.has("decomposable")?data.getString("decomposable"):"");
			
			ps.setString(5, data.has("taskSpecification")?data.getString("taskSpecification"):"");
			ps.setString(6, data.has("taskConstraint")?data.getString("taskConstraint"):"");
			ps.setString(7, data.has("taskQuota")?data.getString("taskQuota"):"");
			ps.setString(8, data.has("taskQuality")?data.getString("taskQuality"):"");
			ps.setString(9, data.has("taskQualification")?data.getString("taskQualification"):"");
			ps.setString(10, data.has("taskPrice")?data.getString("taskPrice"):"");
			ps.setString(11, data.has("taskDeadtime")?data.getString("taskDeadtime"):"");
			//获取当前时间--创建时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_date =  df.format(System.currentTimeMillis());
			java.util.Date ud = df.parse(create_date);
			java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
			ps.setTimestamp(12, st);
			ps.setString(13, data.has("status")?data.getString("status"):"未招标");
			ps.setString(14, data.has("userId")?data.getString("userId"):"");
			ps.setString(15, data.has("taskId")?data.getString("taskId"):"");
			ps.setString(16, "0");
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现异常。");
			e.printStackTrace();
		}
		finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	@Override
	public int update(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update cloudmake_task set name = ?,type = ?,is_decomposable = ?,specification = ?, `constraint` = ?,"
					+ " quota = ?,quality = ?,qualification = ?,price =? ,dead_time = ?,"
					+ " time = ?,status = ?, compose_templet = ?, complete_body = ? where task_id = ? ");
			ps.setString(1, data.has("taskName")?data.getString("taskName"):"");
			ps.setString(2, data.has("taskType")?data.getString("taskType"):"");
			ps.setString(3, data.has("decomposable")?data.getString("decomposable"):"");
			ps.setString(4, data.has("taskSpecification")?data.getString("taskSpecification"):"");
			ps.setString(5, data.has("taskConstraint")?data.getString("taskConstraint"):"");
			
			ps.setString(6, data.has("taskQuota")?data.getString("taskQuota"):"");
			ps.setString(7, data.has("taskQuality")?data.getString("taskQuality"):"");
			ps.setString(8, data.has("taskQualification")?data.getString("taskQualification"):"");
			ps.setString(9, data.has("taskPrice")?data.getString("taskPrice"):"");
			ps.setString(10, data.has("taskDeadtime")?data.getString("taskDeadtime"):"");
		
			//获取当前时间--创建时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_date =  df.format(System.currentTimeMillis());
			java.util.Date ud = df.parse(create_date);
			java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
			ps.setTimestamp(11, st);
			ps.setString(12, data.has("status")?data.getString("status"):"招标中");
			ps.setString(13, data.has("compose_templet")?data.getString("compose_templet"):"");
			ps.setString(14, data.has("complete_body")?data.getString("complete_body"):"");
			ps.setString(15, data.getString("taskId"));
			result = ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现异常。");
			e.printStackTrace();
		}
		finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	
	public int updateSubTask(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update cloudmake_task set name = ?,type = ?,is_decomposable = ?,specification = ?, `constraint` = ?,"
					+ "quota = ?,quality = ?,qualification = ?,price = ?, "
					+ "dead_time = ?,time = ? where task_id = ? ");
			ps.setString(1, data.has("taskName")?data.getString("taskName"):"");
			ps.setString(2, data.has("taskType")?data.getString("taskType"):"");
			ps.setString(3, data.has("decomposable")?data.getString("decomposable"):"");
			ps.setString(4, data.has("taskSpecification")?data.getString("taskSpecification"):"");
			ps.setString(5, data.has("taskConstraint")?data.getString("taskConstraint"):"");
			
			ps.setString(6, data.has("taskQuota")?data.getString("taskQuota"):"");
			ps.setString(7, data.has("taskQuality")?data.getString("taskQuality"):"");
			ps.setString(8, data.has("taskQualification")?data.getString("taskQualification"):"");
			ps.setString(9, data.has("taskPrice")?data.getString("taskPrice"):"");
			ps.setString(10, data.has("taskDeadtime")?data.getString("taskDeadtime"):"");
			//获取当前时间--创建时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_date =  df.format(System.currentTimeMillis());
			java.util.Date ud = df.parse(create_date);
			java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
			ps.setTimestamp(11, st);
			ps.setString(12, data.getString("taskId"));
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
	
	public int setStatus(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update cloudmake_task set status = ? where task_id = ? ");
			ps.setString(1, data.getString("updateStatus"));
			ps.setString(2, data.getString("taskId"));
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
	
	public int setStatus2(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update cloudmake_task set status = ?,time = ? where task_id = ? ");
			ps.setString(1, data.getString("updateStatus"));
			//获取当前时间--创建时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_date =  df.format(System.currentTimeMillis());
			java.util.Date ud = df.parse(create_date);
			java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
			ps.setTimestamp(2, st);
			ps.setString(3, data.getString("taskId"));
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
	
	public int setStatus3(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update cloudmake_task set bid_winner_user_id = ?,price = ?,status = ?,time = ? where task_id = ? ");
			ps.setString(1, data.getString("bid_user_id"));
			ps.setString(2, data.getString("bid_price"));
			ps.setString(3, "等待接受");
			//获取当前时间--创建时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_date =  df.format(System.currentTimeMillis());
			java.util.Date ud = df.parse(create_date);
			java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
			ps.setTimestamp(4, st);
			ps.setString(5, data.getString("task_id"));
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
	
	public int setConfirmSubtask(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update cloudmake_task set is_confirm_subtask = ? where father_task_id = ? ");
			ps.setString(1, data.getString("is_confirm_subtask"));
			ps.setString(2, data.getString("taskId"));
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
	
	@Override
	public int delete(JSONObject data){
		conn = DBConnect.getConn();
		int result = 0;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("delete from cloudmake_task where task_id = ?");
			ps.setString(1, data.getString("taskId"));
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
	
	public int setDeleteUser(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update cloudmake_task set deleteUser = ? where task_id = ? ");
			ps.setString(1, data.getString("user"));
			ps.setString(2, data.getString("taskId"));
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
	
	public int setRequirementDoc(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update cloudmake_task set requirement_doc = ? where task_id = ? ");
			ps.setString(1, data.getString("requirement_doc"));
			ps.setString(2, data.getString("taskId"));
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
	
	public int setComposeTemplet(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update cloudmake_task set compose_templet = ? where task_id = ? ");
			ps.setString(1, data.getString("compose_templet"));
			ps.setString(2, data.getString("taskId"));
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
	
	public int setCompleteBody(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("update cloudmake_task set complete_body = ? where task_id = ? ");
			ps.setString(1, data.getString("complete_body"));
			ps.setString(2, data.getString("taskId"));
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
	
	public JSONArray findByUserId(JSONObject data){
		conn = DBConnect.getConn();
		JSONArray result = new JSONArray();
		StringBuffer sql =  new StringBuffer("select task_id,tender_user_id,bid_winner_user_id,father_task_id,is_confirm_subtask,status,time,"
				+ "name,type,is_decomposable,specification,`constraint`,quota,quality,qualification,price,dead_time,"
				+ "mod_request,requirement_doc,compose_templet,complete_body "
				+ "from cloudmake_task where 1=1 ");
		try {
			if(data.has("userId")){
				sql.append(" AND tender_user_id = '"+data.getString("userId")+"'");
			}
			sql.append(" order by time desc ");
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			result = CDataTransform.rsToJson(rs);
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
