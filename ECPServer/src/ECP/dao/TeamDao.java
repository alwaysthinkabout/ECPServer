package ECP.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.star.lib.uno.environments.java.java_environment;





import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

public class TeamDao extends BaseDao{
	SimpleDateFormat df =   new SimpleDateFormat("yyyyMMdd");
	private IncubatorDao incubatorDao=new IncubatorDao();
	public JSONObject getAllTeamInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		MarketDao marketDao = new MarketDao();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");
				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(marketDao.findUserByID(user_id)){
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("SELECT teamId,memberType AS teamType,name,field FROM team WHERE memberId="+user_id);
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip = "信息获取成功";
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("测试TeamDao中的小函数");
		TeamDao teamDao = new TeamDao();
		JSONObject data=new JSONObject();
	//如果用单引号的话就会把2读成字符串，值为50
		data.put("userId","1");
		data.put("teamId","1");
		JSONObject result;
//		result = teamDao.getAllTeamInfo(data);
		result = teamDao.getTeamBasicInfo(data);
//		System.out.println("结果是："+TeamDao.findBySchoolNo(2));
		System.out.println("结果是："+result.toString());
	}

	public JSONObject getOneTeamInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		MarketDao marketDao = new MarketDao();
		try{
			//如果发送了userId字段。
			if(data.has("teamId")){
				int user_id = data.getInt("teamId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT DISTINCT name,field,createDate,projectId,projectName,grade,projectStatus " +
						" FROM team t,team_project p WHERE t.teamId=p.teamId AND t.teamId="+user_id);
				rs=ps.executeQuery();
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";
				
			}else{
				flag=1;
				tip="没有团队id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	public JSONObject publicRecrutingInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONObject sqlData=new JSONObject();		
		int ID=0;
		try{
			//如果发送了userId字段。
			if(data.has("teamId")){
				int team_id = data.getInt("teamId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("insert into team_recruit(teamID,recruitNum,requirement," +
						"intro,user_id,submitTime) values(?,?,?,?,?,?)");
				ps.setInt(1, team_id);
				ps.setInt(2, data.getInt("numbers"));
				ps.setString(3, data.getString("require"));
				ps.setString(4, data.getString("introduction"));
				ps.setInt(5, data.getInt("userId"));
				java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(6, timestamp);
				ps.executeUpdate();
				ps= conn.prepareStatement("SELECT LAST_INSERT_ID();");  
		        rs = ps.executeQuery();  
		        if(rs.next()){ 
		        	ID=rs.getInt(1);
		        }
		        sqlData.put("recruitId",ID);
//				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";
				
			}else{
				flag=1;
				tip="没有团队id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	public JSONObject myRecrutingInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		MarketDao marketDao = new MarketDao();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT tr.id as recruitId,t.teamId,t.name AS teamName,t.field,tr.region,tr.recruitNum AS numbers,t.logo AS teamImg"+
						" FROM team t,team_recruit tr WHERE t.teamId=tr.teamID AND t.memberType=1 AND t.memberID="+user_id);
				rs=ps.executeQuery();
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";
				
			}else{
				flag=1;
				tip="没有团队id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	public JSONObject otherRecrutingInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		MarketDao marketDao = new MarketDao();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT distinct tr.id as recruitId,t.teamId,t.name AS teamName,t.field,tr.region,tr.recruitNum AS numbers,t.logo AS teamImg"+
						" FROM team t,team_recruit tr WHERE t.teamId=tr.teamID and t.membertype=1 AND t.memberID !="+user_id);
				rs=ps.executeQuery();
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";
				
			}else{
				flag=1;
				tip="没有团队id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	private int findMaxTeamID() {
		// TODO Auto-generated method stub
		int result=0;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT max(teamId)+1 FROM team ");
			rs=ps.executeQuery();
			if(rs.next()){
				result=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		return result;
	}

	public JSONObject createTeam(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		MarketDao marketDao = new MarketDao();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");
				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(marketDao.findUserByID(user_id)){
					String name=data.getString("teamName");
					String field = data.getString("field");
					String address=data.getString("region");
					JSONArray memberArray=data.getString("teamMembers").equals("")?new JSONArray():data.getJSONArray("teamMembers");
					String userName=findNameByID(user_id);
					java.sql.Timestamp subTimestamp=new Timestamp(System.currentTimeMillis());
					
					int teamID=findMaxTeamID();
					conn = DBConnect.getConn();
					if(memberArray.length()>0){
						for(int i=0;i <memberArray.length();i++){
							JSONObject member = memberArray.getJSONObject(i);
							ps=conn.prepareStatement("INSERT INTO team(teamId,name,field,address,memberID,memberName,memberType,createDate,teamType,abstract) VALUES(?,?,?,?,?,?,?,?,?,?)");
							ps.setInt(1, teamID);
							ps.setString(2, name);
							ps.setString(3, field);
							ps.setString(4, address);
							ps.setInt(5, member.getInt("memberId"));
							ps.setString(6, member.getString("memberName"));
							ps.setInt(7, 0);
							ps.setTimestamp(8, subTimestamp);
							ps.setInt(9, 2);
							ps.setString(10, data.getString("teamDesc"));
							ps.executeUpdate();
						}
					}
					ps=conn.prepareStatement("INSERT INTO team(teamId,name,field,address,memberID,memberName,memberType,createDate,teamType,abstract) VALUES(?,?,?,?,?,?,?,?,?,?)");
					ps.setInt(1, teamID);
					ps.setString(2, name);
					ps.setString(3, field);
					ps.setString(4, address);
					ps.setInt(5, user_id);
					ps.setString(6, userName);
					ps.setInt(7, 1);
					ps.setTimestamp(8, subTimestamp);
					ps.setInt(9, 2);
					ps.setString(10, data.getString("teamDesc"));
					ps.executeUpdate();
					tip = "团队创建成功";
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	private String findNameByID(int user_id) {
		// TODO Auto-generated method stub
		String result=new String();
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("SELECT user_name FROM user_info where id= "+user_id);
			rs=ps.executeQuery();
			if(rs.next()){
				result=rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		
		return result;
	}

	public JSONObject getJoinTeamInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		MarketDao marketDao = new MarketDao();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");
				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(marketDao.findUserByID(user_id)){
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("SELECT teamId,name,field,logo AS teamImg,createDate AS teamCreateDate "+
							" FROM team t WHERE EXISTS (SELECT 1 FROM team t1 WHERE  t1.memberType=0 "+
							" AND t1.memberID="+user_id+" AND t1.teamId=t.teamId) AND t.memberType=1");
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip = "信息获取成功";
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	public JSONObject getCreateTeamInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		MarketDao marketDao = new MarketDao();
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");
				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				if(marketDao.findUserByID(user_id)){
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("SELECT teamId,name,field,logo AS teamImg,createDate as teamCreateDate" +
							" FROM team WHERE memberType=1 AND memberID="+user_id);
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip = "信息获取成功";
				}else{
					flag=1;
					tip="用户id不存在";
				}
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	public JSONObject getOneTeamGoingProject(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		MarketDao marketDao = new MarketDao();
		try{
			//如果发送了userId字段。
			if(data.has("teamId")){
				int team_id = data.getInt("teamId");
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("select projectId,projectName,grade,picture as projectLogo,progress," +
						"publishTime as startTime from project_detail where projectType=0 and projectState=0 and teamID="+team_id);
				rs=ps.executeQuery();
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	public JSONObject getOneTeamCompletedProject(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		
		try{
			//如果发送了userId字段。
			if(data.has("teamId")){
				int team_id = data.getInt("teamId");
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("select projectId,projectName,grade,picture as projectLogo," +
						"endTime as completedTime from project_detail pd where projectType=0 and projectState=4 and teamID="+team_id);
				rs=ps.executeQuery();
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";
				
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	public JSONObject getTeamBasicInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		MarketDao marketDao = new MarketDao();
		try{
			//如果发送了userId字段。
			if(data.has("teamId")){
				int team_id = data.getInt("teamId");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT t1.name,t1.field,t1.createDate,COUNT(DISTINCT t1.memberID) AS teamMembers,"+
						" a.* FROM team t1 ,(SELECT t.memberID AS teamLeaderId,u.nick_name AS teamLeaderName FROM team t,user_info u WHERE "+
						" t.teamId=? AND t.memberID=u.id and t.memberType=1 ) a WHERE t1.teamId=?");
				ps.setInt(1, team_id);
				ps.setInt(2, team_id);
				rs=ps.executeQuery();
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";				
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	public JSONObject dailyReport(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");
				int logType=1;
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("INSERT INTO team_log(memberId,memberName,workPlace,"+
						"logContent1,logContent2,progress,COMMENT,logType,submitTime,projectID) VALUES("+
						"?,?,?,?,?,?,?,?,?,?)");
				ps.setInt(1, user_id);
				ps.setString(2, data.getString("name"));
				ps.setString(3, data.getString("place"));
				ps.setString(4, data.getString("content"));
				ps.setString(5, data.getString("problem"));
				ps.setInt(6, data.getInt("progress"));
				ps.setString(7, data.getString("comm"));
				ps.setInt(8, logType);
				java.sql.Timestamp subTimestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(9, subTimestamp);
				ps.setInt(10, data.getInt("projId"));
				ps.executeUpdate();
//				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息提交成功";
				
			}else{
				flag=1;
				tip="没有团队id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	public JSONObject weeklyReport(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");
				int logType=2;//周报
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("INSERT INTO team_log(memberId,memberName,workPlace,"+
						"logContent1,logContent2,progress,COMMENT,logType,submitTime,logContent3,projectID) VALUES("+
						"?,?,?,?,?,?,?,?,?,?,?)");
				ps.setInt(1, user_id);
				ps.setString(2, data.getString("name"));
				ps.setString(3, data.getString("place"));
				ps.setString(4, data.getString("content"));
				ps.setString(5, data.getString("conclusion"));
				ps.setInt(6, data.getInt("progress"));
				ps.setString(7, data.getString("comm"));
				ps.setInt(8, logType);
				java.sql.Timestamp subTimestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(9, subTimestamp);
				ps.setString(10, data.getString("plan"));
				ps.setInt(11, data.getInt("projId"));
				ps.executeUpdate();
//				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息提交成功";
				
			}else{
				flag=1;
				tip="没有团队id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	public JSONObject monthlyReport(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");
				int logType=3;//月报
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("INSERT INTO team_log(memberId,memberName,workPlace,"+
						"logContent1,logContent2,progress,COMMENT,logType,submitTime,logContent3) VALUES("+
						"?,?,?,?,?,?,?,?,?,?)");
				ps.setInt(1, user_id);
				ps.setString(2, data.getString("name"));
				ps.setString(3, data.getString("place"));
				ps.setString(4, data.getString("content"));
				ps.setString(5, data.getString("conclusion"));
				ps.setInt(6, data.getInt("progress"));
				ps.setString(7, data.getString("comm"));
				ps.setInt(8, logType);
				java.sql.Timestamp subTimestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(9, subTimestamp);
				ps.setString(10, data.getString("plan"));
				ps.executeUpdate();
//				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息提交成功";
				
			}else{
				flag=1;
				tip="没有团队id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	public JSONObject meetingReport(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		
		try{
			//如果发送了userId字段。
			if(data.has("userId")){
				int user_id = data.getInt("userId");
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("INSERT INTO team_meeting(memberId,attend,absent,TIME,place,"+
						"theme,content,conclusion,comment,submitTime,projectId) VALUES(?,?,?,?,?,"+
						"?,?,?,?,?,?)");
				ps.setInt(1, user_id);
				ps.setString(2, data.getString("attend"));
				ps.setString(3, data.getString("absent"));
				java.sql.Timestamp time=new Timestamp(df.parse(data.getString("time")).getTime());
				ps.setTimestamp(4, time);
				ps.setString(5, data.getString("place"));
				ps.setString(6, data.getString("theme"));
				ps.setString(7, data.getString("content"));
				ps.setString(8, data.getString("conclusion"));
				ps.setString(9, data.getString("comm"));
				java.sql.Timestamp subTimestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(10, subTimestamp);
				ps.setInt(11, data.getInt("projId"));
				ps.executeUpdate();
//				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息提交成功";
				
			}else{
				flag=1;
				tip="没有团队id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	public JSONObject createProj(JSONObject data) throws JSONException {
		// TODO Auto-generated method stub
		
		JSONObject	result=new JSONObject();
		JSONObject data1=new JSONObject();
		data1.put("CTeamID", data.getInt("teamId"));
		data1.put("UserID", data.getInt("userId"));
		data1.put("CProjName", data.getString("projName"));
		data1.put("BriefDesc", data.getString("projDesc"));
		data1.put("projectType", 0);
		result=incubatorDao.setCreateCProjInfo(data1);			
		return result;
	}	
	public JSONObject getSearchTeamRelatedUsersList(JSONObject data) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		String key =data.getString("searchString");
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			ps=conn.prepareStatement("SELECT distinct u.id AS userId,uid AS account,nick_name AS userName "+ 
					" FROM user_info u LEFT JOIN team t ON u.id=t.memberid "+
					" WHERE ( uid LIKE '%"+key+"%' OR nick_name LIKE '%"+key+"%' ) ");			
			rs=ps.executeQuery();
			// 还缺向所有成员推送的一步。
			sqlData= CDataTransform.rsToJsonLabel(rs);
			tip = "信息获取成功";	
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	public JSONObject getTeamRecruitDetails(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();			
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			ps=conn.prepareStatement("SELECT tr.requirement AS demand,tr.intro as introduction,tr.submitTime AS publishTime,tr.recruitNum AS numbers,"+
					" t.*,CASE WHEN t1.HaveRecruited IS NULL THEN 0 ELSE t1.HaveRecruited END AS haveRecruited "+
					" FROM team_recruit tr ,(SELECT teamId,NAME AS teamName,field,"+
					" logo AS teamImg,abstract AS teamDesc,address AS region FROM team WHERE memberType=1 ) t,"+
					" (SELECT teamId,COUNT(*) AS HaveRecruited FROM team WHERE recruitID=? AND teamId=?) t1 "+
					" WHERE tr.teamID=t.teamId AND  tr.teamID=? ");
			ps.setInt(1, data.getInt("recruitId"));
			ps.setInt(2, data.getInt("teamId"));
			ps.setInt(3, data.getInt("teamId"));
			rs=ps.executeQuery();
			// 还缺向所有成员推送的一步。
			sqlData= CDataTransform.rsToJsonLabel(rs);
			tip = "信息获取成功";	
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}		
		return result;
	}

	public JSONObject projectDetail(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONObject tempdata=new JSONObject();
		JSONArray sqlData=new JSONArray();			
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			ps=conn.prepareStatement("SELECT projectName,briefDesc,progress,grade,publishTime,endTime " +
					" FROM project_detail pd WHERE pd.projectId=?");
			ps.setInt(1, data.getInt("projectId"));			
			rs=ps.executeQuery();
			if(rs.next()){
				tempdata.put("name", rs.getString("projectName"));
				tempdata.put("describe", rs.getString("briefDesc"));
				tempdata.put("progress", rs.getString("progress"));
				tempdata.put("grade", rs.getString("grade"));
				tempdata.put("beginTime", rs.getString("publishTime"));
				tempdata.put("endTime", rs.getString("endTime"));
			}
			// 还缺向所有成员推送的一步。
			ps=conn.prepareStatement("SELECT u.nick_name AS memName,pm.memberID as memId,responsibility AS taskBrief, projectState AS progress,taskDetail "+ 
					" FROM project_member pm join user_info u on pm.memberID=u.id WHERE pm.projectID=?");
			ps.setInt(1, data.getInt("projectId"));			
			rs=ps.executeQuery();
			sqlData= CDataTransform.rsToJsonLabel(rs);
			tempdata.put("taskAlloction", sqlData);
			tip = "信息获取成功";	
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", tempdata);
		}catch(Exception e){
			e.printStackTrace();
		}		
		return result;
	}

	public JSONObject getCTeamJoinedMembersList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			int org_id=data.getInt("CTeamID");
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT t.memberID AS UserID,u.nick_name AS UserName,t.createDate AS JoinedTime"+
					" FROM team t JOIN user_info u ON t.memberID=u.id "+
					" WHERE t.teamID="+org_id);
			rs=ps.executeQuery();
			sqlData= CDataTransform.rsToJsonLabel(rs);
			tip = "信息获取成功";				
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	public JSONObject setTaskAllocation(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			int teamId=data.getInt("teamId");
			int projId=data.getInt("projId");
			conn = DBConnect.getConn();
			JSONArray teamArray=new JSONArray(data.getString("taskAlloction"));
			for(int i=0;i<teamArray.length();i++){
				ps=conn.prepareStatement("Insert into project_member(projectID,teamID,memberID,responsibility) VALUES(?,?,?,?)");
				ps.setInt(1, projId);
				ps.setInt(2, teamId);
				ps.setInt(3, teamArray.getJSONObject(i).getInt("memId"));
				ps.setString(4, teamArray.getJSONObject(i).getString("taskBrief"));
				ps.executeUpdate();
			}
			
//			sqlData= CDataTransform.rsToJsonLabel(rs);
			tip = "信息获取成功";				
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	public JSONObject myReport(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			int user_id=data.getInt("userId");
			int projID=data.getInt("projId");
			conn = DBConnect.getConn();
			StringBuilder sql=new StringBuilder("SELECT logID AS logId,logType AS type,submitTime AS time,workPlace AS place," +
					"logContent1 AS content,logContent2 AS conclusion,logContent3 AS plan,"+
					"progress,COMMENT AS comm FROM team_log WHERE memberID=? AND projectID=? ");
			if(data.getInt("type")!=0){
				sql.append(" and logType="+data.getInt("type"));
			}
			if(!data.getString("startTime").equals("")){
//				java.sql.Timestamp time=new Timestamp(df.parse(data.getString("startTime")).getTime());
				String time=data.getString("startTime");
				sql.append(" AND submitTime>=str_to_date('"+time+"','%Y-%m-%d')");
			}
			if(!data.getString("endTime").equals("")){
//				java.sql.Timestamp time=new Timestamp(df.parse(data.getString("endTime")).getTime());
				String time=data.getString("endTime");
			    sql.append(" AND submitTime <=str_to_date('"+time+"','%Y-%m-%d')");
			}
//			System.out.println("sql:"+sql.toString());
			ps=conn.prepareStatement(sql.toString());
			ps.setInt(1, user_id);
			ps.setInt(2, projID);
			rs=ps.executeQuery();
			sqlData= CDataTransform.rsToJsonLabel(rs);
			tip = "信息获取成功";				
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	public JSONObject teamReport(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			
			int projID=data.getInt("projId");
			int teamID=findTeamIdByProjectID(projID);
			conn = DBConnect.getConn();
			StringBuilder sql=new StringBuilder("SELECT logID AS logId,logType AS type,submitTime AS time,workPlace AS place," +
					"NULL AS content,logContent1 AS problem,logContent2 AS conclusion,logContent3 AS plan,"+
					"progress,COMMENT AS comm FROM team_log WHERE teamId=? AND projectID=? ");
			if(data.getInt("type")!=0){
				sql.append(" and logType="+data.getInt("type"));
			}
			if(!data.getString("startTime").equals("")){
//				java.sql.Timestamp time=new Timestamp(df.parse(data.getString("startTime")).getTime());
				String time=data.getString("startTime");
				sql.append(" AND submitTime>=str_to_date('"+time+"','%Y-%m-%d')");
			}
			if(!data.getString("endTime").equals("")){
//				java.sql.Timestamp time=new Timestamp(df.parse(data.getString("endTime")).getTime());
				String time=data.getString("endTime");
			    sql.append(" AND submitTime <=str_to_date('"+time+"','%Y-%m-%d')");
			}
			ps=conn.prepareStatement(sql.toString());
			ps.setInt(1, teamID);
			ps.setInt(2, projID);
			rs=ps.executeQuery();
			sqlData= CDataTransform.rsToJsonLabel(rs);
			tip = "信息获取成功";				
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	private int findTeamIdByProjectID(int projID) throws SQLException {
		// TODO Auto-generated method stub
		int result=0;
		conn = DBConnect.getConn();
		ps=conn.prepareStatement("SELECT teamID FROM project_detail WHERE projectID="+projID);
		rs=ps.executeQuery();
		if(rs.next()){
			result=rs.getInt("teamID");
		}
		return result;
	}

	public JSONObject teamMeetingReport(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			int projID=data.getInt("projId");
			int teamID=findTeamIdByProjectID(projID);
			conn = DBConnect.getConn();
			StringBuilder sql=new StringBuilder("SELECT meetingId AS logId,4 AS type,attend,absent,time," +
					"place,theme,content,conclusion,COMMENT AS comm FROM team_meeting WHERE " +
					" teamId=? AND projectId=? ");
			if(!data.getString("startTime").equals("")){
//				java.sql.Timestamp time=new Timestamp(df.parse(data.getString("startTime")).getTime());
				String time=data.getString("startTime");
				sql.append(" AND time>=str_to_date('"+time+"','%Y-%m-%d')");
			}
			if(!data.getString("endTime").equals("")){
//				java.sql.Timestamp time=new Timestamp(df.parse(data.getString("endTime")).getTime());
				String time=data.getString("endTime");
			    sql.append(" AND time <=str_to_date('"+time+"','%Y-%m-%d')");
			}
			ps=conn.prepareStatement(sql.toString());
			ps.setInt(1, teamID);
			ps.setInt(2, projID);
			rs=ps.executeQuery();
			sqlData= CDataTransform.rsToJsonLabel(rs);
			tip = "信息获取成功";				
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
}
