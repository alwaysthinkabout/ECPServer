package ECP.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

public class WebChatRecordDao extends BaseDao{
	public WebChatRecordDao(){
		
	}
    //记录web端聊天数据
	@Override
	public int insert(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into chat_record(user_id,org_id,chat_time,chat_content,is_read,msg_type) values (?,?,?,?,?,?)");
			ps.setString(1, data.has("user_id")?data.getString("user_id"):"");
			ps.setString(2, data.has("org_id")?data.getString("org_id"):"");
			//获取当前时间--创建时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_date =  df.format(System.currentTimeMillis());
			java.util.Date ud = df.parse(create_date);
			java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
			ps.setTimestamp(3, st);
			ps.setString(4, data.has("chat_content")?data.getString("chat_content"):"");
			ps.setString(5, data.has("is_read")?data.getString("is_read"):"");	
			ps.setString(6, data.has("msg_type")?data.getString("msg_type"):"");	
			ps.executeUpdate();
			result = 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}
	//根据求职端的用户id来查找该用户下所有聊天记录
	public JSONArray findById(String id,String org_id) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			/*ps = conn.prepareStatement("SELECT cr.user_id,cr.org_id,cr.chat_time,cr.chat_content,cr.is_read,cr.msg_type,pi.name "+
		            "from chat_record cr,person_info pi  where cr.user_id=pi.user_code and cr.user_id = ?");*/
			ps = conn.prepareStatement("SELECT user_code "+
            "from person_info  where user_code = ?");			
			ps.setString(1, id);
			rs = ps.executeQuery();
			JSONArray rdata1 = CDataTransform.rsToJson(rs);
			if(rdata1.length()>0)
			{
				ps = conn.prepareStatement("SELECT cr.user_id,cr.org_id,cr.chat_time,cr.chat_content,cr.is_read,cr.msg_type,pi.name "+
			            "from chat_record cr,person_info pi  where cr.user_id=pi.user_code and cr.user_id = ? and cr.org_id = ?");
				ps.setString(1, id);
				ps.setString(2, org_id);
				rs = ps.executeQuery();
				rdata = CDataTransform.rsToJson(rs);
			}else
				{
					ps = conn.prepareStatement("SELECT user_id,org_id,chat_time,chat_content,is_read,msg_type "+
				            "from chat_record   where user_id = ?");
					ps.setString(1, id);
					rs = ps.executeQuery();
					rdata = CDataTransform.rsToJson(rs);
					for(int i=0;i<rdata.length();i++)
					{
						try {
							rdata.getJSONObject(i).put("name", "");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//updateChat_recordStatu(id);
		DBConnect.close(conn,ps,rs);
		return rdata;
	}
	//根据user_id查找用户名
	public String findUser_nameById(String id) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		String name = "";
		try {
			ps = conn.prepareStatement("SELECT name "+
		            "from person_info where user_id = ?");
			ps.setString(1, id);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println(rdata.length());
			if(rdata.length()>0)
			{
				name = rdata.getJSONObject(0).getString("name");
			}else{
				name = id;//若是未完善初级信息的用户，则用账户代替姓名
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return name;
	}
	//查找招聘端好友
	public JSONArray findUserByOrg_id(String id) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("SELECT distinct max(cr.id) AS id,cr.user_id,cr.org_id,pi.name,ui.nick_name,ui.uid  from"+
                    " chat_record cr,person_info pi,user_info ui where cr.user_id=pi.user_code and "+
		            " pi.user_code in (select user_id from chat_record where org_id=?) and "+
                    "ui.uid in (select uo.user_id from user_org uo where uo.org_id=?) and cr.org_id=? GROUP BY cr.user_id ORDER BY id DESC");
			ps.setString(1, id);
			ps.setString(2, id);
			ps.setString(3, id);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
			ps = conn.prepareStatement("SELECT distinct cr.user_id,cr.org_id,ui.nick_name,ui.uid  from"+
										" chat_record cr,user_info ui where"+  
										 " not exists (select user_code from person_info where user_code = cr.user_id) and"+ 
										 " exists(select uo.user_id from user_org uo where uo.org_id=? and uo.user_id=ui.uid) and cr.org_id=?");
			ps.setString(1, id);
			ps.setString(2, id);
			rs = ps.executeQuery();
			JSONArray rdata1 = CDataTransform.rsToJson(rs);
			if(rdata1.length()>0)
			{
				for(int i=0;i<rdata1.length();i++)//添加没有完成出击信息的聊天求职者
				{
					try {
						JSONObject object = rdata1.getJSONObject(i);
						object.put("name", "");
						rdata.put(object);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return rdata;
	}
	
	//把和某个用户的聊天记录置为已读
	public int updateChat_recordStatu(JSONObject data) {
		conn = DBConnect.getConn();
		int result=0;
		try {
			ps = conn.prepareStatement("update chat_record set is_read = '已读'  where chat_record.user_id = ? and chat_record.org_id = ?");
			try {
				ps.setString(1, data.has("user_id")?data.getString("user_id"):"");
				ps.setString(2, data.has("org_id")?data.getString("org_id"):"");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}
	//返回用户类型，作为判断是否是发送给招聘端使用
	public String findUserTypeById(String id) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		String result="";
		try {
			ps = conn.prepareStatement("select user_type from user_info  where uid in (select user_id from user_org where org_id = ? )");
			ps.setString(1, id);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
			try {
				if(rdata != null && rdata.length()>0){
					result = rdata.getJSONObject(0).getString("user_type");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}
	
	//返回当前公司所有未读消息条数
	public int getMsgCounterByOrg_id(String id) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		int result=0;
		try {
			ps = conn.prepareStatement("select count(is_read) counter from chat_record WHERE org_id = ? and is_read='未读'");
			ps.setString(1, id);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
			try {
				if(rdata != null && rdata.length()>0){
					result = rdata.getJSONObject(0).getInt("counter");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}
	
	//返回当前求职者在该招聘端的未读信息条数
	public int getMsgCounterByUser_id(JSONObject data) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		int result=0;
		try {
			ps = conn.prepareStatement("select count(is_read) counter from chat_record WHERE org_id = ? and user_id=? and is_read='未读'");
			try {
				ps.setString(1, data.has("org_id")?data.getString("org_id"):"");
				ps.setString(2, data.has("user_id")?data.getString("user_id"):"");
				rs = ps.executeQuery();
				rdata = CDataTransform.rsToJson(rs);
				if(rdata != null && rdata.length()>0){
					result = rdata.getJSONObject(0).getInt("counter");
				}
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}
	
	//记录web端聊天数据
	public int insertOrg_friend(JSONObject data) {
		conn = DBConnect.getConn();
		int result = 0;
		try {
			ps = conn.prepareStatement("insert into org_friend(user_id,org_id,friend_date,org_name) values (?,?,?,?)");
			ps.setString(1, data.has("user_id")?data.getString("user_id"):"");
			ps.setString(2, data.has("org_id")?data.getString("org_id"):"");
			//获取当前时间--创建时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_date =  df.format(System.currentTimeMillis());
			java.util.Date ud = df.parse(create_date);
			java.sql.Timestamp st = new java.sql.Timestamp(ud.getTime());
			ps.setTimestamp(3, st);
			ps.setString(4, data.has("org_name")?data.getString("org_name"):"");	
			ps.executeUpdate();
			result = 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}
	
	//根据user_id查找和该用户所有有过聊天记录的企业
	public JSONArray findOrg_friendByUser_id(String id) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("select org_id,org_name from org_friend where user_id=?");
			ps.setString(1, id);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return rdata;
	}
	
	//根据org_id查找org_name
	public String findOrg_nameByOrg_id(String id) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		String result="";
		try {
			ps = conn.prepareStatement("select org_name from organization_info  where org_id=?");
			ps.setString(1, id);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
			try {
				if(rdata != null && rdata.length()>0){
					result = rdata.getJSONObject(0).getString("org_name");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}
	
	//安卓端删除企业好友
	public int deleteOrg_friend(JSONObject data){
		conn = DBConnect.getConn();
		int result = 0;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("delete from org_friend where user_id = ? and org_id = ?");
			ps.setString(1, data.has("userId")?data.getString("userId"):"");
			ps.setString(2, data.has("org_id")?data.getString("org_id"):"");
			result = ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("系统出现非数据库异常。");
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}
	//判断当前求职者的好友列表里面是否含有当前聊天企业
	public boolean IsExist(JSONObject data){
		conn = DBConnect.getConn();
		if(data.has("org_id")&&data.has("user_id")){
			try {
				ps = conn.prepareStatement("SELECT user_id from org_friend where user_id = ? and org_id = ?");
				ps.setString(1, data.getString("user_id"));
				ps.setString(2, data.getString("org_id"));
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
		


	//根据org_id查找Lisence_no
	public String findLisence_noByOrg_id(String id) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		String result="";
		try {
			ps = conn.prepareStatement("select license_no from organization_info  where org_id=?");
			ps.setString(1, id);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
			try {
				if(rdata != null && rdata.length()>0){
					result = rdata.getJSONObject(0).getString("license_no");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}
	//返回当前公司所有未读留言条数
	public int getLeaveMsgCountersByOrg_id(String id) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		int result=0;
		try {
			ps = conn.prepareStatement("select count(jl.is_read) counter from job_leave jl,job_info ji WHERE ji.license_no = ? and jl.job_info_id=ji.job_info_id and jl.is_read='未读'");
			ps.setString(1, id);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);
			try {
				if(rdata != null && rdata.length()>0){
					result = rdata.getJSONObject(0).getInt("counter");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}
	//把留言置为已读
	public int updateLeaveStatu(String id) {
		conn = DBConnect.getConn();
		int result=0;
		try {
			ps = conn.prepareStatement("update job_leave jl,job_info ji set jl.is_read = '已读'  WHERE ji.license_no = ? and jl.job_info_id=ji.job_info_id");
			ps.setString(1, id);			
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}
	
	//根据license_no查找留言
	public JSONArray findLeaveByLicense_no(String id) {
		conn = DBConnect.getConn();
		JSONArray rdata = new JSONArray();
		try {
			ps = conn.prepareStatement("SELECT jl.leave_id,jl.uid,jl.job_info_id,jl.leave_content,jl.leave_time,jl.is_read,pi.name,ji.job_name "+
										" from job_leave jl,person_info pi,job_info ji"+
										" where jl.uid=pi.user_code and"+ 
										" ji.job_info_id=jl.job_info_id and jl.job_info_id IN (select job_info_id from job_info where license_no= ?) ORDER BY jl.leave_id ASC");
			ps.setString(1, id);
			rs = ps.executeQuery();
			rdata = CDataTransform.rsToJson(rs);			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return rdata;
	}
}