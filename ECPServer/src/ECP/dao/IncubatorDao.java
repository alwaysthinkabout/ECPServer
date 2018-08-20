package ECP.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.star.lib.uno.environments.java.java_environment;

import ECP.Bean.IncubatorInfo;
import ECP.Bean.ProjectInfo;
import ECP.Bean.TeamInfo;
import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

public class IncubatorDao extends BaseDao {
	SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private ECP.service.common.CFindService service = new ECP.service.common.CFindService();
	private UserDao userDao=new UserDao();
	private IncubatorInfo incubatorInfo=new IncubatorInfo();
	private TeamInfo teamInfo=new TeamInfo();
	private ProjectInfo projectInfo=new ProjectInfo();
	public JSONObject insertIncubatorDetail(IncubatorInfo incubatorInfo) throws SQLException{
		String tip=new String();
		JSONArray sqlData=new JSONArray();
		JSONObject result=new JSONObject();
		int flag=0;
		try{
			if(notFindIncubatorByName(incubatorInfo.getIncubatorName())){
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("INSERT INTO incubator_detail(orgID,incubatorID,incubatorName,adminTeamName,adminTeamID," +
						"description,address,desPic,createTime,contactPhone,incubateNum,adminID,adminDoc,password,IndustryType," +
						"IncubatorType,contactEmail)"+
						"VALUES(?,null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				ps.setInt(1, incubatorInfo.getOrgID());
				ps.setInt(2, incubatorInfo.getIncubatorID());
				ps.setString(3, incubatorInfo.getIncubatorName());
				ps.setString(4, incubatorInfo.getAdminTeamName());
				ps.setInt(5, incubatorInfo.getAdminTeamID());
				ps.setString(6, incubatorInfo.getDescription());
				ps.setString(7, incubatorInfo.getAddress());
				ps.setTimestamp(8, incubatorInfo.getCreateTime());
				ps.setString(9, incubatorInfo.getContactPhone());
				ps.setInt(10, incubatorInfo.getIncubateNum());
				ps.setInt(11, incubatorInfo.getAminID());
				ps.setString(12, incubatorInfo.getAdminDoc());
				ps.setString(13, incubatorInfo.getPassword());
				ps.setString(14, incubatorInfo.getIndustryType());
				ps.setInt(15, incubatorInfo.getIncubatorType());
				ps.setString(16, incubatorInfo.getContactEmail());			
				ps.executeUpdate();
	//			sqlData= CDataTransform.rsToJsonLabel(rs);
				ps=conn.prepareStatement("select incubatorID as PIcbtID from incubator_detail " +
						" where incubatorName='"+incubatorInfo.getIncubatorName()+"'");
				rs=ps.executeQuery();				
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";
			}else{
				flag=1;
				tip = "孵化器已存在";
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
	public JSONObject updateIncubatorDetail(IncubatorInfo incubatorInfo){
		String tip=new String();
		JSONArray sqlData=new JSONArray();
		JSONObject result=new JSONObject();
		int flag=0;
		try{
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("update incubator_detail set orgID=?,incubatorName=?,adminTeamName=?,adminTeamID=?," +
					"description=?,address=?,desPic=?,createTime=?,contactPhone=?,incubateNum=?,adminID=?,adminDoc=?,password=?,IndustryType=?," +
					"IncubatorType=?,contactEmail=? where incubatorID=?");
			ps.setInt(1, incubatorInfo.getOrgID());
			ps.setInt(2, incubatorInfo.getIncubatorID());
			ps.setString(3, incubatorInfo.getIncubatorName());
			ps.setString(4, incubatorInfo.getAdminTeamName());
			ps.setInt(5, incubatorInfo.getAdminTeamID());
			ps.setString(6, incubatorInfo.getDescription());
			ps.setString(7, incubatorInfo.getAddress());
			ps.setTimestamp(8, incubatorInfo.getCreateTime());
			ps.setString(9, incubatorInfo.getContactPhone());
			ps.setInt(10, incubatorInfo.getIncubateNum());
			ps.setInt(11, incubatorInfo.getAminID());
			ps.setString(12, incubatorInfo.getAdminDoc());
			ps.setString(13, incubatorInfo.getPassword());
			ps.setString(14, incubatorInfo.getIndustryType());
			ps.setInt(15, incubatorInfo.getIncubatorType());
			ps.setString(16, incubatorInfo.getContactEmail());	
			ps.setInt(17, incubatorInfo.getIncubatorID());
			ps.executeUpdate();
			tip="信息更新成功";
		}catch(SQLException e){
			flag=1;
			tip="sql错误";
			e.printStackTrace();
		}catch (Exception e) {
			flag=1;
			tip="系统运行错误";
			// TODO: handle exception
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
	public JSONObject insertTeamInfo(TeamInfo teamInfo){
		String tip=new String();
		JSONArray sqlData=new JSONArray();
		JSONObject result=new JSONObject();
		int flag=0;
		try{
			if(notFindTeamName(teamInfo.getName())){
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("INSERT INTO team(id,teamId,memberName,memberID,memberType,name,field," +
						"createDate,address,logo,email,teamTel,introduce,abstract,teamType) "+
						" VALUES(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				ps.setInt(1, teamInfo.getTeamID());
				ps.setString(2, teamInfo.getMemberName());
				ps.setInt(3, teamInfo.getMemberID());
				ps.setInt(4, teamInfo.getMemberType());
				ps.setString(5, teamInfo.getName());
				ps.setString(6, teamInfo.getField());
				ps.setTimestamp(7, teamInfo.getCreateDate());
				ps.setString(8, teamInfo.getAddress());
				ps.setString(9, teamInfo.getLogo());
				ps.setString(10, teamInfo.getEmail());
				ps.setString(11, teamInfo.getTeamTel());
				ps.setString(12, teamInfo.getIntroduce());
				ps.setString(13, teamInfo.getAbstract());
				ps.setInt(14, teamInfo.getTeamType());
				ps.executeUpdate();
	//			sqlData= CDataTransform.rsToJsonLabel(rs);				
				ps=conn.prepareStatement("update team set teamId=id where name='"+teamInfo.getName()
						+"' and memberID="+teamInfo.getMemberID());
				ps.executeUpdate();					
				ps=conn.prepareStatement("select teamId as CTeamID from team where name='"+teamInfo.getName()+"'");
				rs=ps.executeQuery();				
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";
			}else{
				flag=1;
				tip = "孵化器已存在";
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
	public JSONObject updateTeamInfo(TeamInfo teamInfo){
		String tip=new String();
		JSONArray sqlData=new JSONArray();
		JSONObject result=new JSONObject();
		int flag=0;
		try{
			if(notFindTeamName(teamInfo.getName())){
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("INSERT INTO team set memberName=?,field=?," +
						"address=?,logo=?,email=?,teamTel=?,introduce=?,abstract=? where teamId=?");
				
				ps.setString(1, teamInfo.getMemberName());
				ps.setString(2, teamInfo.getField());				
				ps.setString(3, teamInfo.getAddress());
				ps.setString(4, teamInfo.getLogo());
				ps.setString(5, teamInfo.getEmail());
				ps.setString(6, teamInfo.getTeamTel());
				ps.setString(7, teamInfo.getIntroduce());
				ps.setString(8, teamInfo.getAbstract());
				ps.setInt(9, teamInfo.getTeamID());
				ps.executeUpdate();
				//			
				tip = "信息获取成功";
			}else{
				flag=1;
				tip = "孵化器已存在";
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
	public JSONObject insertProjectDetail(ProjectInfo projectInfo){
		String tip=new String();
		JSONArray sqlData=new JSONArray();
		JSONObject result=new JSONObject();
		int flag=0;
		try{
			if(notFindTeamName(teamInfo.getName())){
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("INSERT INTO project_detail(projectId,projectName,projectType,projectContent," +
						"bulletin,publishTime,protocalDoc,taskDoc,picture,teamID,IncubatorID,projectState," +
						"startTime,endTime,briefDesc,incubateStatus) "+
						" VALUES(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				ps.setString(1, projectInfo.getProjectName());
				ps.setInt(2, projectInfo.getProjectType());
				ps.setString(3, projectInfo.getProjectContent());
				ps.setString(4, projectInfo.getBulletin());
				ps.setTimestamp(5, projectInfo.getPublishTime());
				ps.setString(6, projectInfo.getProtocalDoc());
				ps.setString(7, projectInfo.getTaskDoc());
				ps.setString(8, projectInfo.getPicture());
				ps.setInt(9,projectInfo.getTeamID());
				ps.setInt(10, projectInfo.getIncubatorID());
				ps.setInt(11, projectInfo.getProjectState());
				ps.setTimestamp(12, projectInfo.getStartTime());
				ps.setTimestamp(13, projectInfo.getEndTime());
				ps.setString(14, projectInfo.getBriefDesc());
				ps.setInt(15, projectInfo.getIncubateStatus());
				ps.executeUpdate();
	//			sqlData= CDataTransform.rsToJsonLabel(rs);				
				ps=conn.prepareStatement("select projectId as CProjID from project_detail where projectName='"+projectInfo.getProjectName()+"'");
				rs=ps.executeQuery();				
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";
			}else{
				flag=1;
				tip = "孵化器已存在";
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
	public JSONObject updateProjectDetail(ProjectInfo projectInfo){
		String tip=new String();
		JSONArray sqlData=new JSONArray();
		JSONObject result=new JSONObject();
		int flag=0;
		try{
			if(notFindTeamName(teamInfo.getName())){
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("update project_detail set projectContent=?," +
						" bulletin=?,protocalDoc=?,taskDoc=?,picture=?,teamID=?,incubatorID=?,projectState=?," +
						" startTime=?,endTime=?,briefDesc=?,incubateStatus=? where projectId=?) "+
						" VALUES(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				
				ps.setString(1, projectInfo.getProjectContent());
				ps.setString(2, projectInfo.getBulletin());				
				ps.setString(3, projectInfo.getProtocalDoc());
				ps.setString(4, projectInfo.getTaskDoc());
				ps.setString(5, projectInfo.getPicture());
				ps.setInt(6,projectInfo.getTeamID());
				ps.setInt(7, projectInfo.getIncubatorID());
				ps.setInt(8, projectInfo.getProjectState());
				ps.setTimestamp(9, projectInfo.getStartTime());
				ps.setTimestamp(10, projectInfo.getEndTime());
				ps.setString(11, projectInfo.getBriefDesc());
				ps.setInt(12, projectInfo.getIncubateStatus());
				ps.setInt(13, projectInfo.getProjectId());
				ps.executeUpdate();
	//			sqlData= CDataTransform.rsToJsonLabel(rs);	
				tip = "信息获取成功";
			}else{
				flag=1;
				tip = "孵化器已存在";
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
	
	public JSONObject getProjectData(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。
			if(data.has("UserID")){
				int user_id = data.getInt("UserID");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT tp.projectId      AS CProjID,"+
						"  projectName       AS CProjName,"+
						"  pm.memberID       AS CProjLeaderID,"+
						"  tp.incubateStatus"+
						" FROM project_detail tp"+
						"  JOIN team pm"+
						"    ON tp.teamId = pm.teamID"+
						" WHERE EXISTS (SELECT 1 FROM team t WHERE t.memberID = "+user_id+" AND t.teamID=pm.teamID)"+
						" AND pm.memberType=1");
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

	public JSONObject getProjectCheckStatusInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。
			if(data.has("ProjectID")){
				int project_id = data.getInt("ProjectID");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT projectID,projectName,releaseStatus AS STATUS," +
						"remark AS advice FROM incubator_project_apply WHERE projectID="+project_id);
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

	public JSONObject getProjectDataDetail(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。
			if(data.has("ProjectID")){
				int project_id = data.getInt("ProjectID");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT projectId AS projectID,projectName,bulletin AS broadcastInfo,publishTime AS broadcastTime,protocalDoc AS protocolDocUrl,"+
						"taskDoc AS taskAssignDocUrl FROM project_detail WHERE IncubatorId IS NOT NULL AND projectId = "+project_id);
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

	public JSONObject setTodayJournal(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。
			if(data.has("CProjID")){
				int project_id = data.getInt("CProjID");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				int memberID = data.getInt("UserID");
				java.sql.Timestamp submitTime =new java.sql.Timestamp(System.currentTimeMillis());
				
				if(!findMemberTeamLog(project_id,memberID,submitTime)){
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("INSERT INTO team_log(projectID,memberName,memberID,workPlace,logContent1," +
							"progress,submitTime,logType) VALUES(?,?,?,?,?,?,?,?)");
					ps.setInt(1, project_id);
					ps.setString(2, data.getString("UserName"));
					ps.setInt(3, data.getInt("UserID"));
					ps.setString(4, data.getString("WorkPlace"));
					ps.setString(5, data.getString("WorkContent"));
					ps.setInt(6, data.getInt("WorkProgress"));					
					ps.setTimestamp(7, submitTime);
					ps.setInt(8, 1);	
					ps.executeUpdate();
	//				sqlData= CDataTransform.rsToJsonLabel(rs);
					tip = "信息获取成功";
				}else{
					//如果已经有日志信息，则
					tip = "信息已存在";
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

	private boolean findMemberTeamLog(int project_id, int memberID,
			Timestamp submitTime) throws SQLException {
		// TODO Auto-generated method stub
		boolean result=false;
		//这里的signTime其实是上课时间
		Date today = new Date(submitTime.getTime());
		Date nextDay = new Date(submitTime.getTime()+3600*24*1000);
		conn = DBConnect.getConn();
		ps=conn.prepareStatement("SELECT * FROM team_log WHERE projectID="+project_id+" AND memberID="+memberID
				+" AND submitTime >= '"+today.toString()+"' AND submitTime <= '"+nextDay.toString()+"'");
		rs=ps.executeQuery();
		if(rs.next()){
			result=true;
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject getHistoryJournalData(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。
			if(data.has("CProjID")){
				int project_id = data.getInt("CProjID");
				int user_id = data.getInt("UserID");	
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT memberName AS UserName,memberID AS UserID,submitTime AS JournalTime,workPlace AS WorkPlace,logContent1 AS WorkContent,"+
						"progress AS WorkProgress FROM team_log WHERE logType=1 and submitTime >DATE_ADD(NOW(),INTERVAL -8 DAY) AND projectID="+project_id+" AND memberID="+user_id);
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

	public JSONObject getIncubatorsList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT incubatorID as IcbtID,incubatorName as IcbtName,description as IcbtIntro,"+
					"adminTeamName as TeamName,address as IcbtAddr,desPic as IcbtPicUrl from incubator_detail ");
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

	public JSONObject getIncubatorData(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			if(data.has("IcbtID")){
				int incubate_id=data.getInt("IcbtID");
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT incubatorID AS IcbtID,incubatorName AS IcbtName,adminTeamID as TeamID,createTime,"+
						"adminTeamName AS TeamName,address AS IcbtAddr,contact AS IcbtTel,incubateNum as IncubatedProjectNum"+
						" FROM incubator_detail where incubatorID="+incubate_id);
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

	public JSONObject setSettleApplicationInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。
			if(data.has("CProjID")){
				int project_id = data.getInt("CProjID");
				int incubatorID = data.getInt("IcbtID");
				if(notFindIncubatorApplication(incubatorID,project_id)){
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("INSERT INTO incubator_project_apply(IncubatorID,ProjectID,Advice,protocolDoc,taskDoc,ApplyType,ApplyTime) VALUES(?,?,?,?,?,?,?)");
					ps.setInt(1, data.getInt("IcbtID"));
					ps.setInt(2, project_id);
					ps.setString(3, data.getString("Remark"));
					ps.setString(4, data.getString("ProtocolDocUrl"));
					ps.setString(5, data.getString("TaskAssignDocUrl"));	
					ps.setInt(6, 1);
					java.sql.Timestamp applyTime=new Timestamp(System.currentTimeMillis());
					ps.setTimestamp(7, applyTime);
					ps.executeUpdate();
	//				sqlData= CDataTransform.rsToJsonLabel(rs);
					tip = "信息获取成功";
				}else{
					tip ="已经在申请中";
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

	private boolean notFindIncubatorApplication(int incubatorID, int project_id) throws SQLException {
		// TODO Auto-generated method stub
		boolean result=true;
		//这里的signTime其实是上课时间
		conn = DBConnect.getConn();
		ps=conn.prepareStatement("select incubatorID from incubator_project_apply where incubatorID="+incubatorID+" and projectID="+project_id);
		rs=ps.executeQuery();
		if(rs.next()){
			result=false;
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject setCreateIcbtApplicationData(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。
			int orgID=data.getInt("UserID");
			String incubatorName=data.getString("IcbtName");
			if(notfindIncubatorByName(orgID,incubatorName)){
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("INSERT INTO incubator_detail(orgID,incubatorName,adminTeamID,address,concact,PASSWORD,adminDoc)"+
						"VALUES(?,?,?,?,?,?,?)");
				ps.setInt(1, data.getInt("UserID"));
				ps.setString(2, data.getString("IcbtName"));
				ps.setInt(3, data.getInt("TeamID"));
				ps.setString(4, data.getString("IcbtAddr"));
				ps.setString(5, data.getString("IcbtTel"));
				ps.setString(6, data.getString("IcbtPwd"));
				ps.setString(7, data.getString("ManagePlanDocUrl"));
				ps.executeUpdate();
//				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";
			}else{
				tip = "孵化器已存在";
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

	private boolean notfindIncubatorByName(int orgID, String incubatorName) throws SQLException {
		// TODO Auto-generated method stub
		boolean result=true;
		//这里的signTime其实是上课时间
		conn = DBConnect.getConn();
		ps=conn.prepareStatement("select incubatorName from incubator_detail where adminTeamID="+orgID+" and  incubatorName='"+incubatorName+"'");
		rs=ps.executeQuery();
		if(rs.next()){
			result=false;
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject getIncubatorDataList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			int org_id=data.getInt("UserID");
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT incubatorID AS IcbtID,incubatorName AS IncubatorName "+
					" FROM incubator_detail where orgID="+org_id);
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

	public JSONObject getProjectDataList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			int org_id=data.getInt("IcbtID");
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("select ProjectID,ProjectName,SettleStatus from incubator_project_apply  "+
					"where incubatorID="+org_id);
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

	public JSONObject getProjectDataApply(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			int org_id=data.getInt("ProjectID");
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT projectId AS ProjectID,projectName AS ProjectName,picture AS ProjectPicUrls,"+
					"projectContent AS ProjectDescription,protocalDoc AS ProtocolDocUrl,taskDoc AS TaskAssignDocUrl "+
					" FROM project_detail WHERE projectId="+org_id);
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
	public static void main(String[] args) throws Exception {
		System.out.println("测试CacStudentDao中的小函数");
		IncubatorDao incubatorDao = new IncubatorDao();
		JSONObject data=new JSONObject();
	//如果用单引号的话就会把2读成字符串，值为50
		int projectID=1;
		int memberID=1;
		Timestamp submitTime=new Timestamp(System.currentTimeMillis());
		JSONObject result;		
		System.out.println("结果是："+incubatorDao.setCreateCProjInfo(data));
		
	}

	public JSONObject setCreateCTeamInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();	
		int res=0;
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			String teamName=data.getString("CTeamName");
			int memberType=1;
			if(notFindTeamName(teamName)){
				try{
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("INSERT INTO team(memberID,NAME,FIELD,address,teamTel,abstract,email," +
							"memberType,createDate) VALUES(?,?,?,?,?,?,?,?,?)");
					ps.setInt(1, data.getInt("UserID"));
					ps.setString(2, data.getString("CTeamName"));
					ps.setString(3, data.getString("IndustryType"));
					ps.setString(4, data.getString("OfficeAddr"));
					ps.setString(5, data.getString("CTeamTel"));
					ps.setString(6, data.getString("BriefDesc"));
					ps.setString(7, data.getString("EmailAddr"));
					ps.setInt(8, memberType);
					java.sql.Timestamp createDate=new Timestamp(System.currentTimeMillis());
					ps.setTimestamp(9, createDate);
					ps.executeUpdate();
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					DBConnect.close(conn,ps,rs);
				}
				try{
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("update team set teamId=id where name='"+teamName+"' and memberID="+data.getInt("UserID"));
					ps.executeUpdate();
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					DBConnect.close(conn,ps,rs);
				}
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("select teamId as CTeamID from team where name='"+teamName+"'");
				rs=ps.executeQuery();				
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";		
			}else{
				flag=1;
				tip="团队名称已存在";
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
	

	private boolean notFindTeamName(String teamName) throws SQLException {
		// TODO Auto-generated method stub
		boolean result=true;
		//这里的signTime其实是上课时间
		conn = DBConnect.getConn();
		ps=conn.prepareStatement("select name from team where name='"+teamName+"'");
		rs=ps.executeQuery();
		if(rs.next()){
			result=false;
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject setCTeamInviteUserInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();	
		int res=0;
		try{		
			int state=0;
			int teamID=	data.getInt("CTeamID");
			int userID= data.getInt("InvitedUserID");
			if(notFindTeamInviteRecords(teamID,userID)){
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("INSERT INTO team_invite(teamID,userID,state,inviteTime,InviterID) VALUES(?,?,?,?,?)");
				ps.setInt(1, data.getInt("CTeamID"));
				ps.setInt(2, data.getInt("InvitedUserID"));
				ps.setInt(3, state);				
				java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(4, timestamp);
				ps.setInt(5, data.getInt("UserID"));
				ps.executeUpdate();
				JSONObject dataPush=new JSONObject();
				StringBuilder systemInfo=new StringBuilder(" 亲，有团队邀请您的加入，请尽快处理！");
				dataPush.put("fromAccount", data.getInt("UserID"));
				dataPush.put("eventType", "团队邀请");
				dataPush.put("module", "孵化器");				
				dataPush.put("toAccount", data.getInt("InvitedUserID"));
				dataPush.put("systemInfo", systemInfo);
				service.push(dataPush);
				
			}else{
				//反复邀请的话则只更新时间。
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("update team_invite set inviteTime=? where teamID=? and userID=?");
				java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(1, timestamp);
				ps.setInt(2, data.getInt("CTeamID"));
				ps.setInt(3, data.getInt("InvitedUserID"));						
				ps.executeUpdate();
			}
			//这里推送的文字和账号还需要处理一下。
			JSONObject dataPush=new JSONObject();
			String teamName=findNameByTeamID(teamID);
			String account=findAccoutByUserID(userID);
			StringBuilder systemInfo=new StringBuilder("邀请您加入团队："+teamName);
			dataPush.put("toAccount", account);
			dataPush.put("systemInfo", systemInfo);
			System.out.println(systemInfo.toString());
			service.push(dataPush);
//				sqlData= CDataTransform.rsToJsonLabel(rs);
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

	private boolean notFindTeamInviteRecords(int teamID, int userID) throws SQLException {
		// TODO Auto-generated method stub
		boolean result=true;
		//这里的signTime其实是上课时间
		conn = DBConnect.getConn();
		ps=conn.prepareStatement("select * from team_invite where teamID="+teamID+" and userID="+userID);
		rs=ps.executeQuery();
		if(rs.next()){
			result=false;
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}
	private String findAccoutByUserID(int userID) throws SQLException {
		// TODO Auto-generated method stub
		String result=null;
		//这里的signTime其实是上课时间
		conn = DBConnect.getConn();
		ps=conn.prepareStatement("select uid from user_info where id="+userID);
		rs=ps.executeQuery();
		if(rs.next()){
			result=rs.getString(1);
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	private String findNameByTeamID(int teamID) throws SQLException {
		// TODO Auto-generated method stub
		String result=null;
		//这里的signTime其实是上课时间
		conn = DBConnect.getConn();
		ps=conn.prepareStatement("select name from team where teamId="+teamID);
		rs=ps.executeQuery();
		if(rs.next()){
			result=rs.getString(1);
		}
		DBConnect.close(conn,ps,rs);
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
			ps=conn.prepareStatement("SELECT t.userID AS UserID,u.nick_name AS UserName,t.acceptTime AS JoinedTime"+
					" FROM team_invite t JOIN user_info u ON t.userID=u.id "+
					" WHERE t.state=1 and t.teamID="+org_id);
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

	public JSONObject getCTeamWaitedUsersList(JSONObject data) {
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
			ps=conn.prepareStatement("SELECT t.userID AS UserID,u.nick_name AS UserName,t.inviteTime AS InviteTime"+
					" FROM team_invite t JOIN user_info u ON t.userID=u.id "+
					" WHERE t.state=0 and t.teamID="+org_id);
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

	public JSONObject setCTeamCancelInviteUserInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			int org_id=data.getInt("CTeamID");
			int userID=data.getInt("UserID");
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("delete from team_invite "+
					" WHERE state=0 and teamID="+org_id+" and userID="+userID);
			ps.executeUpdate();
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

	public JSONObject getCreatedUnusedCTeamInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			
			int userID=data.getInt("UserID");
			if(hasUnUsedTeam(userID)){
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT memberID AS UserID,teamId AS CTeamID,NAME AS CTeamName,"+
						" FIELD AS IndustryType,address AS OfficeAddr,teamTel AS CTeamTel,"+
						" abstract AS BriefDesc,email AS EmailAddr,createDate AS CreateTime "+
						" FROM team WHERE memberType=1 AND memberID="+userID);
				rs=ps.executeQuery();
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";	
			}else{
				
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

	private boolean hasUnUsedTeam(int userID) {
		// TODO Auto-generated method stub
		// 根据用户ID查找用户是否创建了团队和项目。如果有团队但无项目，则返回true。
		return true;
	}

	public JSONObject setCreateCProjInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();	
		int projectID=0;
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			
			int state=0;
			int teamID=	data.getInt("CTeamID");
			int userID= data.getInt("UserID");
			String projectName=data.getString("CProjName");
			int projectType=data.has("projectType")?data.getInt("projectType"):1;
			if(notFindProjectByName(projectName)){
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("INSERT INTO project_detail(teamID,projectName,briefDesc,publishTime,projectType) VALUES(?,?,?,?,?)");
				ps.setInt(1, teamID);
				ps.setString(2, data.getString("CProjName"));
				ps.setString(3, data.getString("BriefDesc"));				
				java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(4, timestamp);
				ps.setInt(5, projectType);
				ps.executeUpdate();		
				ps=conn.prepareStatement("select projectId as CProjID from project_detail where projectName='"+projectName+"'");
				rs=ps.executeQuery();				
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "设置(创建创业项目)成功";		
			}else{
				flag=1;
				tip="项目名称已存在";
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

	private boolean notFindProjectByName(String projectName) throws SQLException {
		// TODO Auto-generated method stub
		boolean result=true;
		//这里的signTime其实是上课时间
		conn = DBConnect.getConn();
		ps=conn.prepareStatement("select projectName from project_detail where projectName='"+projectName+"'");
		rs=ps.executeQuery();
		if(rs.next()){
			result=false;
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject setCreateIcbtInfoByPerson(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();	
		int projectID=0;
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			
			int state=0;
			
			int userID= data.getInt("UserID");
			String incubatorName=data.getString("PIcbtName");
			if(notFindIncubatorByName(incubatorName)){
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("INSERT INTO incubator_detail(incubatorName,IncubatorType,contactPhone," +
						"contactEmail,description,createTime,adminID,IndustryType,address) VALUES(?,?,?,?,?,?,?,?,?)");
				ps.setString(1, incubatorName);
				ps.setInt(2, 0);
				ps.setString(3, data.getString("PIcbtTel"));
				ps.setString(4, data.getString("EmailAddr"));
				ps.setString(5, data.getString("BriefDesc"));
				java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(6, timestamp);
				ps.setInt(7, userID);
				ps.setString(8,data.getString("IndustryType"));
				ps.setString(9, data.getString("OfficeAddr"));
				//ps.setString(9, data.getString(""));
				ps.executeUpdate();	
				ps=conn.prepareStatement("select incubatorID as PIcbtID from incubator_detail where incubatorName='"+incubatorName+"'");
				rs=ps.executeQuery();				
				sqlData= CDataTransform.rsToJsonLabel(rs);
//				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "设置(创建创业项目)成功";		
			}else{
				flag=1;
				tip="项目名称已存在";
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

	private boolean notFindIncubatorByName(String incubatorName) throws SQLException {
		// TODO Auto-generated method stub
		boolean result=true;
		//这里的signTime其实是上课时间
		conn = DBConnect.getConn();
		ps=conn.prepareStatement("select incubatorName from incubator_detail where incubatorName='"+incubatorName+"'");
		rs=ps.executeQuery();
		if(rs.next()){
			result=false;
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject setCreateMTeamInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();	
		int res=0;
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			String teamName=data.getString("MTeamName");
			int memberType=1;
			int teamType=1;
			if(notFindTeamName(teamName)){
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("INSERT INTO team(memberID,NAME,FIELD,address,teamTel,abstract,email,memberType,teamType,createDate) VALUES(?,?,?,?,?,?,?,?,?,?)");
				ps.setInt(1, data.getInt("UserID"));
				ps.setString(2, data.getString("MTeamName"));
				ps.setString(3, data.getString("IndustryType"));
				ps.setString(4, data.getString("OfficeAddr"));
				ps.setString(5, data.getString("MTeamTel"));
				ps.setString(6, data.getString("BriefDesc"));
				ps.setString(7, data.getString("EmailAddr"));
				ps.setInt(8, memberType);
				ps.setInt(9, teamType);
				java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(10, timestamp);
				ps.executeUpdate();
				ps=conn.prepareStatement("update team set teamId=id where name='"+teamName+"'");
				ps.executeUpdate();	
				ps=conn.prepareStatement("select id as MTeamID from team where name='"+teamName+"'");
				rs=ps.executeQuery();				
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "设置(创建管理团队)成功";		
			}else{
				flag=1;
				tip="团队名称已存在";
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

	public JSONObject setMTeamInviteUserInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();	
		int res=0;
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			
			int state=0;
			int teamID=	data.getInt("MTeamID");
			int userID= data.getInt("InvitedUserID");
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("INSERT INTO team_invite(teamID,userID,state,inviteTime) VALUES(?,?,?,?)");
				ps.setInt(1, data.getInt("MTeamID"));
				ps.setInt(2, data.getInt("InvitedUserID"));
				ps.setInt(3, state);
				java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(4, timestamp);
				ps.executeUpdate();
				//这里推送的文字和账号还需要处理一下。
				JSONObject dataPush=new JSONObject();
				String teamName=findNameByTeamID(teamID);
				String account=findAccoutByUserID(userID);
				StringBuilder systemInfo=new StringBuilder("邀请您加入团队："+teamName);
				dataPush.put("toAccount", account);
				dataPush.put("systemInfo", systemInfo);
				service.push(dataPush);
//				sqlData= CDataTransform.rsToJsonLabel(rs);
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

	public JSONObject getMTeamJoinedMembersList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			int org_id=data.getInt("MTeamID");
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT t.userID AS UserID,u.nick_name AS UserName,t.acceptTime AS JoinedTime"+
					" FROM team_invite t JOIN user_info u ON t.userID=u.id "+
					" WHERE t.state=1 and t.teamID="+org_id);
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

	public JSONObject getMTeamWaitedUsersList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			int org_id=data.getInt("MTeamID");
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT t.userID AS UserID,u.nick_name AS UserName,t.inviteTime AS InviteTime"+
					" FROM team_invite t JOIN user_info u ON t.userID=u.id "+
					" WHERE t.state=0 and t.teamID="+org_id);
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

	public JSONObject setMTeamCancelInviteUserInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			int org_id=data.getInt("MTeamID");
			int userID=data.getInt("UserID");
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("delete from team_invite "+
					" WHERE t.state=0 and t.teamID="+org_id+" and t.userID="+userID);
			ps.executeUpdate();
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

	public JSONObject getCreatedUnusedMTeamInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			
			int userID=data.getInt("UserID");
			if(hasUnUsedTeam(userID)){
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT memberID AS UserID,teamId AS CTeamID,NAME AS CTeamName,"+
						" FIELD AS IndustryType,address AS OfficeAddr,teamTel AS CTeamTel,"+
						" abstract AS BriefDesc,email AS EmailAddr,createDate AS CreateTime "+
						" FROM team WHERE memberType=1 AND memberID="+userID);
				rs=ps.executeQuery();
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";	
			}else{
				
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

	public JSONObject setCreateIcbtInfoByTeam(JSONObject data) {
		// TODO Auto-generated method stub
		System.out.println("jinru****** ");
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			int org_id=data.getInt("MTeamID");
			int userID=data.getInt("UserID");
			String incubatorName =data.getString("TIcbtName");
			if(notfindIncubatorByName(org_id, incubatorName)){
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("INSERT INTO incubator_detail(orgID,adminTeamID,incubatorName,description,createTime,IncubatorType,adminID) VALUES(?,?,?,?,?,?,?)");
				ps.setInt(1,userID);
				ps.setInt(2, org_id);
				ps.setString(3, data.getString("TIcbtName"));
				ps.setString(4, data.getString("BriefDesc"));
				java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(5, timestamp);
				ps.setInt(6, 1);
				ps.setInt(7, userID);
				ps.executeUpdate();
				ps=conn.prepareStatement("select incubatorID as TIcbtID from incubator_detail where incubatorName='"+data.getString("TIcbtName")+"'");
				rs=ps.executeQuery();				
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";
			}else{
//			sqlData= CDataTransform.rsToJsonLabel(rs);
				flag=1;
				tip="名称已存在";	
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

	public JSONObject getCProjList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。
			if(data.has("UserID")){
				int user_id = data.getInt("UserID");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT distinct tp.projectId      AS CProjID,"+
						"  projectName       AS CProjName,"+
						"  pm.memberID       AS CProjLeaderID,"+
						"  tp.incubateStatus"+
						" FROM project_detail tp"+
						"  JOIN team pm"+
						"    ON tp.teamId = pm.teamID"+
						" WHERE EXISTS (SELECT 1 FROM team t WHERE t.memberID = "+user_id+" AND t.teamID=pm.teamID)"+
						" AND pm.memberType=1 " +
						" and tp.projectType!=0 ");
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

	public JSONObject getCProjCheckResult(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。
			if(data.has("CProjID")){
				int project_id = data.getInt("CProjID");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT projectID as CProjID,projectName as CProjName,releaseStatus AS Status," +
						"remark AS Advice FROM incubator_project_apply WHERE projectID="+project_id);
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

	public JSONObject getIncubatedCProjInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。
			if(data.has("CProjID")){
				int project_id = data.getInt("CProjID");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				//发布的信息需要允许为空。
				ps=conn.prepareStatement("SELECT pb.id, pd.projectId AS CProjID,pd.IncubatorID AS IcbtID,pd.projectName AS CProjName,t.memberID AS CProjLeaderID,t.name AS CTeamName,  "+
						"t.teamId AS CTeamID,t.field AS IndustryType,t.address AS OfficeAddr,t.teamTel AS CTeamTel,  "+
						"pd.briefDesc AS BriefDesc,t.email AS EmailAddr,  "+
						"pb.BroadcastInfo,pb.submitTime AS BroadcastTime,protocalDoc AS ProtocolDocUrl,  "+
						"pd.taskDoc AS TaskAssignDocUrl,ipa.ApplyType as SettleType,pd.publishTime as CProjCreateTime "+
						" FROM project_detail pd JOIN team t ON pd.teamID=t.teamId  "+
						"left JOIN project_broadcast pb ON pd.projectId=pb.projectID  "+
						"join incubator_project_apply ipa on pd.projectId=ipa.ProjectID "+
						"WHERE pd.IncubatorId IS NOT NULL  "+
						"AND t.memberType=1 AND pd.projectId =? "+
						"ORDER BY pb.id DESC LIMIT 1 ");
				ps.setInt(1, project_id);
				rs=ps.executeQuery();
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";
				
			}else{
				flag=1;
				tip="没有项目id";
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

	public JSONObject getUnsettledCProjInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。
			if(data.has("CProjID")){
				int project_id = data.getInt("CProjID");				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT projectId AS CProjID,projectName AS CProjName,t.memberID AS CProjLeaderID,t.name AS CTeamName,"+
						"t.teamId AS CTeamID,t.field AS IndustryType,t.address AS OfficeAddr,t.teamTel AS CTeamTel,"+
						"pd.briefDesc AS BriefDesc,t.email AS EmailAddr,pd.publishTime as CProjCreateTime "+
						" FROM project_detail pd JOIN team t ON pd.teamID=t.teamId "+
						" WHERE IncubatorId IS NULL and pd.projectType !=0 "+
						" AND t.memberType=1 AND projectId ="+project_id);
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

	public JSONObject getAvailableIncubatorsList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT incubatorID as IcbtID,incubatorName as IcbtName,description as IcbtIntro,"+
					"adminTeamName as TeamName,address as IcbtAddr,desPic as IcbtPicUrl from incubator_detail ");
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

	public JSONObject getIcbtDataByClient(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			if(data.has("IcbtID")){
				int incubate_id=data.getInt("IcbtID");
				if(findIncubatorTypeByID(incubate_id)==1){
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("SELECT incubatorID AS IcbtID,incubatorName AS IcbtName,IncubatorType as IcbtType,adminTeamID AS MTeamID,"+
							"t.name AS MTeamName,t.field as IndustryType ,t.address AS OfficeAddr,adminID as TIcbtCreatorUserID,t.teamTel AS MTeamTel,description AS TIcbtBriefDesc,"+
							"createTime AS TIcbtCreateTime,t.email AS EmailAddr "+
							" FROM incubator_detail ind,team t WHERE ind.adminTeamID=t.id and  incubatorID="+incubate_id);
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
				}else{
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("SELECT incubatorID AS IcbtID,incubatorName AS IcbtName,IncubatorType as IcbtType,adminID AS PIcbtCreatorUserID,"+
							"IndustryType ,address AS OfficeAddr,contactPhone AS PIcbtTel,description AS PIcbtBriefDesc,"+
							"createTime AS PIcbtCreateTime,contactEmail AS EmailAddr "+
							" FROM incubator_detail WHERE incubatorID="+incubate_id);
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
				}
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

	private int findIncubatorTypeByID(int incubate_id) {
		// TODO Auto-generated method stub
		int result=0;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("select IncubatorType from incubator_detail  "+
					"where incubatorID="+incubate_id);
			rs=ps.executeQuery();
			while(rs.next()){
//				System.out.println("结果是："+rs.getString(1));
				result = rs.getInt(1);
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}

	public JSONObject getIcbtsList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			int org_id=data.getInt("UserID");
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT incubatorID AS IcbtID,incubatorName AS IcbtName ,IncubatorType AS IcbtType,orgID AS IcbtCreatorUserID,"+
					"createTime AS IcbtCreateTime,description AS IcbtBriefDesc "+
					" FROM incubator_detail WHERE adminID="+org_id);
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

	public JSONObject getCProjDataByIcbtID(JSONObject data) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		int org_id=data.getInt("IcbtID");
		int settleStatus=findIncubatorSettleStatus(org_id);
		int ApplyType=findIncubatorApplyType(org_id);
		try{
			if(settleStatus==1){			
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT ipc.ProjectID AS CProjID,pd.projectName AS CProjName,SettleStatus ,"+
						"t.memberID AS CProjLeaderID,t.name AS CTeamName,t.teamId AS CTeamID,t.address AS OfficeAddr,"+
						"t.teamTel AS CTeamTel,pd.briefDesc AS BriefDesc,t.email AS EmailAddr,ipc.IncubateTime "+
						" FROM incubator_project_apply ipc "+
						" JOIN project_detail pd ON ipc.ProjectID=pd.projectId"+
						" JOIN team t ON pd.teamID=t.teamId"+						
						" WHERE ipc.incubatorID="+org_id+
						" AND t.memberType=1 and ipc.SettleStatus=1");
				rs=ps.executeQuery();
				flag=0;
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";	
			}else if(ApplyType==1){
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("select ipc.ApplyID,ipc.ProjectID as CProjID,pd.projectName as CProjName,ipc.SettleStatus,ipc.ApplyTime,ipc.ApplyType " +
						"from incubator_project_apply ipc JOIN project_detail pd ON ipc.ProjectID=pd.projectId "+
						"where ipc.SettleStatus=0 and ipc.ApplyType=1 and ipc.incubatorID="+org_id);
				rs=ps.executeQuery();
				flag=0 ;
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";	
			}else if(ApplyType==0){
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("select ApplyID,ipc.TeamID as CTeamID ,t.name as CTeamName,SettleStatus,ApplyTime,ApplyType from incubator_project_apply ipc join team t "+
						"on ipc.TeamID=t.teamId and t.memberType=1 where SettleStatus=0 and ApplyType=0 and incubatorID="+org_id);
				rs=ps.executeQuery();
				flag=0 ;
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";	
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

	private int findIncubatorApplyType(int org_id) {
		// TODO Auto-generated method stub
		int result=0;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("select ApplyType from incubator_project_apply  "+
					"where SettleStatus=0 and incubatorID="+org_id);
			rs=ps.executeQuery();
			while(rs.next()){
//				System.out.println("结果是："+rs.getString(1));
				result = rs.getInt(1);
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	private int findIncubatorSettleStatus(int org_id) {
		// TODO Auto-generated method stub
		int result=0;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("select SettleStatus from incubator_project_apply  "+
					"where incubatorID="+org_id);
			rs=ps.executeQuery();
			while(rs.next()){
//				System.out.println("结果是："+rs.getString(1));
				result = rs.getInt(1);
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}

	public JSONObject getCProjApplicationData(JSONObject data) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		int applyid=data.getInt("ApplyID");		
		try{
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT ipc.ProjectID AS CProjID,pd.ProjectName AS CProjName,"+
					" t.memberID AS CProjLeaderID,t.name AS CTeamName,t.teamId AS CTeamID,t.address AS OfficeAddr,"+
					" t.teamTel AS CTeamTel,pd.briefDesc AS CProjBriefDesc,t.email AS EmailAddr,pd.protocalDoc AS ProtocolDocUrl,"+
					" pd.taskDoc AS TaskAssignDocUrl "+
					" FROM incubator_project_apply ipc "+
					" JOIN project_detail pd ON ipc.ProjectID=pd.projectId "+
					" JOIN team t ON pd.teamID=t.teamId "+					
					" WHERE ipc.ApplyID="+applyid+
					" AND t.memberType=1 and ipc.SettleStatus=0 ");
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

	public JSONObject setCheckStatus(JSONObject data) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		int org_id=data.getInt("CProjID");		
		try{
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("UPDATE incubator_project_apply SET SettleStatus=? ,Advice=?,IncubateTime=? WHERE incubatorID=? "+
					"AND ProjectID=? ");
			//0表示退回
			if(data.getInt("CheckStatus")==0){
				ps.setInt(1, 2);
			}else{
				ps.setInt(1, data.getInt("CheckStatus"));
			}			
			ps.setString(2, data.getString("Advice"));
			java.sql.Timestamp checkTime=new Timestamp(System.currentTimeMillis());
			ps.setTimestamp(3, checkTime);
			ps.setInt(4, data.getInt("IcbtID"));			
			ps.setInt(5, data.getInt("CProjID"));
			ps.executeUpdate();
			int auditStatus=2;
			if(data.getInt("CheckStatus")==1){
				auditStatus=1;
				if(data.getInt("CheckStatus")==1){
					ps=conn.prepareStatement("UPDATE incubator_project_apply set SettleStatus=2 where ProjectID!=? and IncubatorID=?");
					ps.setInt(1, data.getInt("CProjID"));
					ps.setInt(2, data.getInt("IcbtID"));
					ps.executeUpdate();
				}
			}else if (data.getInt("CheckStatus")==0) {
				auditStatus=3;
			}
			ps=conn.prepareStatement("UPDATE project_detail SET incubateStatus=? ,IncubatorID=? WHERE "+
					"projectId=? ");
			ps.setInt(1, auditStatus);			
			ps.setInt(2, data.getInt("IcbtID"));
			ps.setInt(3, data.getInt("CProjID"));
			ps.executeUpdate();
//			sqlData= CDataTransform.rsToJsonLabel(rs);
			tip = "设置(保存)成功";	
			
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

	public JSONObject getIncubatedProjDataByManager(JSONObject data) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		int org_id=data.getInt("CProjID");		
		try{
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT ipc.ProjectID AS CProjID,pd.projectName AS CProjName,"+
					" t.memberID AS CProjLeaderID,t.name AS CTeamName,t.teamId AS CTeamID,t.address AS OfficeAddr,"+
					" t.teamTel AS CTeamTel,pd.briefDesc AS CProjBriefDesc,t.email AS EmailAddr,pd.protocalDoc AS ProtocolDocUrl,"+
					" pd.taskDoc AS TaskAssignDocUrl,t.field as IndustryType "+
					" FROM incubator_project_apply ipc "+
					" JOIN project_detail pd ON ipc.ProjectID=pd.projectId "+
					" JOIN team t ON pd.teamID=t.teamId "+					
					" WHERE ipc.ProjectID="+org_id+
					" AND t.memberType=1 ");
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

	public JSONObject getMembersListByCTeamID(JSONObject data) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		int org_id=data.getInt("CTeamID");		
		try{
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT distinct u.nick_name AS UserName,memberID AS UserID FROM team t,user_info u WHERE t.memberID=u.id and t.teamId="+org_id);
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

	public JSONObject getCProjMemberDetails(JSONObject data) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		int org_id=data.getInt("CProjID");	
		int memberID=data.getInt("UserID");
		try{
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT pm.memberID AS UserID,pm.memberPic AS UserPic,t.memberName AS UserName,"+
					" pm.inTime AS JoinedTime,t.introduce AS ProjExp,tl.logID"+
					" FROM project_member pm JOIN team t ON pm.teamID= t.teamId AND pm.memberID=t.memberID "+
					" JOIN team_log tl ON pm.teamID=tl.teamId AND pm.memberID=tl.memberID where pm.projectID="+org_id+
					" and pm.memberID="+memberID);
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

	public JSONObject getCProjTodayJournalsData(JSONObject data) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		int org_id=data.getInt("CProjID");			
		try{
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT u.nick_name AS UserName,memberID AS UserID,workPlace AS WorkPlace,"+
					" logContent1 AS WorkContent,progress AS WorkProgress "+
					" FROM team_log tl,user_info u WHERE tl.memberID=u.id and logType=1 and projectID="+org_id);
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

	public JSONObject setCProjBroadcastData(JSONObject data) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		int org_id=data.getInt("CProjID");			
		try{
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("INSERT INTO project_broadcast(projectID,BroadcastInfo,submitTime) VALUES(?,?,?)");
			ps.setInt(1, data.getInt("CProjID"));
			ps.setString(2, data.getString("BroadcastInfo"));
			java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
			ps.setTimestamp(3, timestamp);
			ps.executeUpdate();
			// 还缺向所有成员推送的一步。
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

	public JSONObject getSearchTeamRelatedUsersList(JSONObject data) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		int team_id=data.getInt("TeamID");	
		String key =data.getString("SearchString");
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			ps=conn.prepareStatement("SELECT u.id AS UserID,uid AS Account,nick_name AS UserName,t.teamId IS NOT NULL AS IsTeamMember "+ 
					" FROM user_info u LEFT JOIN team t ON u.id=t.memberid AND t.teamId="+team_id+
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
	public JSONObject getHotRecruitProjsList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			ps=conn.prepareStatement("SELECT projectId AS projID,pd.IncubatorID AS icbtID,projectName AS projName,"+
					" incubatorName AS icbtName,publishTime,briefDesc AS projDesc,applyReq,ind.IndustryType as industry " +
					" FROM project_detail pd JOIN incubator_detail ind "+
					" ON pd.incubatorID=ind.incubatorID where projectState=1 limit 10");			
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
	public JSONObject setPublishProjInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		
		try{
			int incubatorID=data.getInt("icbtID");
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			ps=conn.prepareStatement("insert into project_detail(projectName,briefDesc," +
					"incubatorID,projectState,publishTime,incubateStatus,applyReq) values(?,?,?,?,?,?,?)");	
			ps.setString(1, data.getString("projName"));
			//可能有问题。
//			ps.setInt(2, Integer.parseInt(data.getString("projType")));
			ps.setString(2, data.getString("projDesc"));
			ps.setInt(3, incubatorID);
			ps.setInt(4, 1);
			java.sql.Timestamp pubtime=new Timestamp(System.currentTimeMillis());
			ps.setTimestamp(5, pubtime);
			ps.setInt(6, 1);
			ps.setString(7, data.getString("applyReq"));
			ps.executeUpdate();
			// 还缺向所有成员推送的一步。
			ps=conn.prepareStatement("SELECT projectId AS projID FROM project_detail WHERE IncubatorID=?");	
			ps.setInt(1, incubatorID);
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
	public JSONObject setUpdatePublishProjInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			int projectID=data.getInt("projID");
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			ps=conn.prepareStatement("UPDATE project_detail SET projectName=?,briefDesc=?,applyReq=? WHERE projectState=1 and projectId=?");	
			ps.setString(1, data.getString("projName"));
//			ps.setInt(2, data.getInt("projType"));
			ps.setString(2, data.getString("projDesc"));
			ps.setString(3, data.getString("applyReq"));
			ps.setInt(4, projectID);
			ps.executeUpdate();
			// 还缺向所有成员推送的一步。
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
	public JSONObject getIcbtPublishProjInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			conn = DBConnect.getConn();
			int incubatorID=data.getInt("icbtID");
			//返回团队成员标识，1是，0否。
			ps=conn.prepareStatement("SELECT projectId AS projID,projectName AS projName,projectType AS projType,"+
					" briefDesc AS projDesc,IncubatorID AS icbtID,CASE WHEN projectState=1 THEN 0 ELSE 1 END AS isFinished,applyReq "+
					" FROM project_detail WHERE projectState !=5 AND IncubatorID="+incubatorID);			
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
	public JSONObject setDeletePublishProj(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			int projectID=data.getInt("projID");
			ps=conn.prepareStatement("update project_detail set projectState=5 where projectID="+projectID);			
			ps.executeUpdate();
			// 还缺向所有成员推送的一步。
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
	public JSONObject getAllRecruitProjsList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			ps=conn.prepareStatement("SELECT projectId AS projID,pd.IncubatorID AS icbtID,projectName AS projName,"+
					" incubatorName AS icbtName,publishTime,briefDesc AS projDesc,applyReq,ind.IndustryType as industry" +
					" FROM project_detail pd JOIN incubator_detail ind "+
					" ON pd.incubatorID=ind.incubatorID where projectState=1 ");			
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
	public JSONObject getRecruitProjDetails(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			int projectID=data.getInt("projID");
			ps=conn.prepareStatement("SELECT projectId AS projID,pd.IncubatorID AS icbtID,projectName AS projName,"+
					" incubatorName AS icbtName,publishTime,briefDesc AS projDesc,applyReq,ind.IndustryType as industry" +
					" FROM project_detail pd JOIN incubator_detail ind "+
					" ON pd.incubatorID=ind.incubatorID where projectId="+projectID);			
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
	public JSONObject getUnusedCTeamInfoList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			int userID=data.getInt("UserID");
			ps=conn.prepareStatement("SELECT ? AS UserID,teamId AS CTeamID,memberID AS CreatorID,NAME AS CTeamName,createDate AS CreateTime FROM team t "+
					" WHERE memberType=1 AND NOT EXISTS (SELECT 1 FROM project_detail pd WHERE pd.teamID=t.teamId) "+
					" AND EXISTS (SELECT 1 FROM team t1 WHERE memberID=? AND t1.teamId=t.teamId) " +
					" and t.teamType=0 ");
			ps.setInt(1, userID);
			ps.setInt(2, userID);
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
	public JSONObject getCTeamInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			int teamID=data.getInt("CTeamID");
			ps=conn.prepareStatement("SELECT teamId AS CTeamID,NAME AS CTeamName,memberID AS LeaderID,COUNT(1) AS MemberNum,"+
					" FIELD AS IndustryType,address AS OfficeAddr,teamTel AS CTeamTel,abstract AS BriefDesc,"+
					" email AS EmailAddr,createDate AS CreateTime FROM team WHERE teamId=? AND memberType=1");
			ps.setInt(1, teamID);			
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
	public JSONObject getCProjCheckRecord(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			int projectID=data.getInt("CProjID");
			ps=conn.prepareStatement("SELECT ApplyID,ApplyTime,IncubatorID AS IcbtID,SettleStatus AS CheckStatus,"+
					" IncubateTime AS CheckTime,Advice FROM incubator_project_apply WHERE ProjectID=?");
			ps.setInt(1, projectID);			
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
	public JSONObject getCTeamCheckRecord(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			int teamID=data.getInt("CTeamID");
			ps=conn.prepareStatement("SELECT ApplyID,ApplyTime,IncubatorID AS IcbtID,SettleStatus AS CheckStatus,"+
					" IncubateTime AS CheckTime,Advice FROM incubator_project_apply WHERE TeamID=?");
			ps.setInt(1, teamID);			
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
	public JSONObject setSettleApplicationInfoByCTeam(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。			
			ps=conn.prepareStatement("INSERT INTO incubator_project_apply(incubatorID,TeamID,ApplyTime," +
					"protocolDoc,taskDoc,remark,ApplyType,ProjectID) VALUES(?,?,?,?,?,?,?,?)");
			ps.setInt(1, data.getInt("IcbtID"));	
			ps.setInt(2, data.getInt("CTeamID"));
			java.sql.Timestamp applyTime=new Timestamp(System.currentTimeMillis());
			ps.setTimestamp(3, applyTime);
			ps.setString(4, data.getString("ProtocolDocUrl"));
			ps.setString(5, data.getString("TaskAssignDocUrl"));
			ps.setString(6, data.getString("Remark"));
			ps.setInt(7, 0);
			ps.setInt(8, data.getInt("CProjID"));
			ps.executeUpdate();
			// 还缺向所有成员推送的一步。
			ps=conn.prepareStatement("SELECT ApplyID FROM incubator_project_apply WHERE teamID=? and incubatorID=?");
			ps.setInt(1, data.getInt("CTeamID"));
			ps.setInt(2, data.getInt("IcbtID"));
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
	public JSONObject getCProjApplicationDataByCTeam(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			int applyID=data.getInt("ApplyID");
			ps=conn.prepareStatement("SELECT ipa.ProjectID AS CProjID,pd.projectName AS CProjName,ipa.protocolDoc AS ProtocolDocUrl,ipa.taskDoc AS TaskAssignDocUrl,"+
					" t.name AS CTeamName,ipa.TeamID AS CTeamID,t.abstract AS CTeamBriefDesc "+
					" FROM incubator_project_apply ipa,project_detail pd,team t WHERE ipa.ProjectID=pd.projectId AND ipa.TeamID=t.teamId AND ipa.ApplyID=?");
			ps.setInt(1, applyID);			
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
	public JSONObject setCheckStatusByCTeam(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。			
			int applyID=data.getInt("ApplyID");
			ps=conn.prepareStatement("UPDATE incubator_project_apply SET SettleStatus=?,Advice=?,IncubateTime=? WHERE ApplyID=?");
			if(data.getInt("CheckStatus")==0){
				ps.setInt(1,2);
			}else{
				ps.setInt(1, data.getInt("CheckStatus"));
			}
			ps.setString(2, data.getString("Advice"));
			java.sql.Timestamp checkTime=new Timestamp(System.currentTimeMillis());
			ps.setTimestamp(3, checkTime);
			ps.setInt(4, applyID);
			ps.executeUpdate();
			//通过了一个申请之后，将其余申请全部驳回。
			if(data.getInt("CheckStatus")==1){
				ps=conn.prepareStatement("UPDATE incubator_project_apply set SettleStatus=2 where applyID!=? and IncubatorID=?");
				ps.setInt(1, applyID);
				ps.setInt(2, data.getInt("IcbtID"));
				ps.executeUpdate();
			}
			ps=conn.prepareStatement("UPDATE project_detail pd,incubator_project_apply ipc SET pd.incubateStatus=1," +
					"pd.projectState=2,pd.teamID=ipc.TeamID WHERE pd.projectId=ipc.ProjectID AND pd.IncubatorID=ipc.IncubatorID AND ipc.ApplyID=?");
			ps.setInt(1, applyID);
			ps.executeUpdate();
			// 还缺向所有成员推送的一步。
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
	public JSONObject getUserInvitedMsgList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			int userID=data.getInt("UserID");
			ps=conn.prepareStatement("SELECT InviteID,inviteTime AS InviteTime,state AS MsgStatus,InviterID,u.nick_name AS InviterName,ti.teamID AS TeamID,t.name AS TeamName "+ 
					" FROM team_invite ti,user_info u,team t WHERE ti.InviterID=u.id AND ti.teamID=t.teamId and t.memberType=1 AND ti.userID=?");
			ps.setInt(1, userID);			
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
	public JSONObject setInviteStatusByInviteID(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			int state=data.getInt("InviteStatus");
			int inviteID=data.getInt("InviteID");
			ps=conn.prepareStatement("UPDATE team_invite SET state=? WHERE InviteID=?");
			if(state==0){
				ps.setInt(1, 2);	
			}else{
				ps.setInt(1, 1);
			}
			ps.setInt(2, inviteID);
			ps.executeUpdate();
			if(state==1){
				//如果同意邀请，则向团队表插入数据。
				ps=conn.prepareStatement("INSERT INTO team(teamId,memberID,memberType,createDate) SELECT teamID,userID,0,acceptTime FROM team_invite WHERE InviteID=?");
				ps.setInt(1, inviteID);				
				ps.executeUpdate();
			}
			// 还缺向所有成员推送的一步。
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
	public JSONObject getUserBasicInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			int userID=data.getInt("UserID");
			ps=conn.prepareStatement("SELECT id AS UserID,uid AS Account,nick_name AS UserName,"+
					"CASE WHEN user_sex=1 THEN '男' WHEN user_sex=0 THEN '女' END AS Gender,mail AS Email,"+
					"introduce AS Description FROM user_info u WHERE id=?");
			ps.setInt(1, userID);			
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
	public JSONObject getCProjInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			int userID=data.getInt("UserID");
			int projID=data.getInt("CProjID");
			ps=conn.prepareStatement("SELECT projectId AS CprojID,projectName AS CprojName,t.memberID AS LeaderID,"+
					" p.teamID AS CTeamID,t.name AS CTeamName,p.briefDesc AS BriefDesc "+
					" FROM project_detail p JOIN team t ON p.teamID=t.id WHERE t.memberType=1 AND p.projectId=?");
			ps.setInt(1, projID);			
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
	public JSONObject getPIcbtInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			int incubatorID=data.getInt("PIcbtID");
			ps=conn.prepareStatement("SELECT incubatorID AS PIcbtID,incubatorName AS PIcbtName,IndustryType,contactPhone AS PIcbtTel,"+
					"address AS OfficeAddr,contactEmail AS EmailAddr,description AS briefDesc FROM incubator_detail WHERE incubatorID=?");
			ps.setInt(1, incubatorID);			
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
	public JSONObject getUnusedMTeamInfoList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			int userID=data.getInt("UserID");
			ps=conn.prepareStatement("SELECT InviteID,inviteTime AS InviteTime,state AS MsgStatus,InviterID,u.nick_name AS InviterName,ti.teamID AS TeamID,t.name AS TeamName "+ 
					" FROM team_invite ti,user_info u,team t WHERE ti.InviterID=u.id AND ti.teamID=t.teamId AND ti.userID=?");
			ps.setInt(1, userID);			
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
	public JSONObject getTIcbtInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			int userID=data.getInt("UserID");
			ps=conn.prepareStatement("SELECT InviteID,inviteTime AS InviteTime,state AS MsgStatus,InviterID,u.nick_name AS InviterName,ti.teamID AS TeamID,t.name AS TeamName "+ 
					" FROM team_invite ti,user_info u,team t WHERE ti.InviterID=u.id AND ti.teamID=t.teamId AND ti.userID=?");
			ps.setInt(1, userID);			
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
	public JSONObject setWriteWeeklyReport(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			int teamId=findTeamIDbyProjID(data.getInt("ProjID"));
			if(notFindWeekReport(data.getInt("UserID"),data.getInt("ProjID"),data.getInt("WeekNo"),2)){
				conn = DBConnect.getConn();
				//返回团队成员标识，1是，0否。
				int userID=data.getInt("UserID");
				ps=conn.prepareStatement("INSERT INTO team_log(memberID,projectID,workPlace," +
						"logContent1,logContent2,logContent3,progress,COMMENT,submitTime,logType,weekNo,teamId) "+
						" VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
				ps.setInt(1, userID);
				ps.setInt(2, data.getInt("ProjID"));
				ps.setString(3, data.getString("Place"));
				ps.setString(4, data.getString("Content"));
				ps.setString(5, data.getString("Conclusion"));
				ps.setString(6, data.getString("Plan"));
				ps.setInt(7, data.getInt("Progress"));
				ps.setString(8, data.getString("Comm"));
				java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(9, timestamp);
				ps.setInt(10, 2);
				ps.setInt(11, data.getInt("WeekNo"));
				ps.setInt(12, teamId);
				ps.executeUpdate();
			}else{
				conn = DBConnect.getConn();
				//返回团队成员标识，1是，0否。
				int userID=data.getInt("UserID");
				ps=conn.prepareStatement("update team_log set workPlace=?," +
						"logContent1=?,logContent2=?,logContent3=?,progress=?,COMMENT=?,submitTime=?,logType=? where" +
						" memberID=? and projectID=? and weekNo=? ");				
				ps.setString(1, data.getString("Place"));
				ps.setString(2, data.getString("Content"));
				ps.setString(3, data.getString("Conclusion"));
				ps.setString(4, data.getString("Plan"));
				ps.setInt(5, data.getInt("Progress"));
				ps.setString(6, data.getString("Comm"));
				java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(7, timestamp);
				ps.setInt(8, 2);
				ps.setInt(9, userID);
				ps.setInt(10, data.getInt("ProjID"));
				ps.setInt(11, data.getInt("WeekNo"));
				ps.executeUpdate();
			}
			// 还缺向所有成员推送的一步。
//			sqlData= CDataTransform.rsToJsonLabel(rs);
			tip = "信息更新成功";	
			
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
	private boolean notFindWeekReport(int userID, int projID, int weekNo, int logType) {
		// TODO Auto-generated method stub
		boolean result=true;
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。			
			ps=conn.prepareStatement("SELECT weekNo FROM team_log WHERE memberID=? AND " +
					"projectID=? AND weekNo=? and logType=?");
			ps.setInt(1, userID);	
			ps.setInt(2, projID);
			ps.setInt(3, weekNo);
			ps.setInt(4, logType);
			rs=ps.executeQuery();
			// 还缺向所有成员推送的一步。
			if(rs.next()){
				result=false;
			}			
		}catch(Exception e){			
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	public JSONObject getMyWeeklyReportList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			int userID=data.getInt("UserID");
			int projID=data.getInt("ProjID");
			ps=conn.prepareStatement("SELECT logID AS ReportID,submitTime AS ReportTime,weekNo AS WeekNo,workPlace AS Place,"+
					" logContent1 AS Content,logContent2 AS Conclusion,logContent3 AS Plan,progress AS Progress,"+
					" COMMENT AS Comm FROM team_log WHERE memberID=? AND projectID=? and logType=2");
			ps.setInt(1, userID);	
			ps.setInt(2, projID);
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
	public JSONObject setWriteMonthlyReport(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			int teamId=findTeamIDbyProjID(data.getInt("ProjID"));
			if(notFindWeekReport(data.getInt("UserID"),data.getInt("ProjID"),data.getInt("MonthNo"),3)){
				conn = DBConnect.getConn();
				//返回团队成员标识，1是，0否。weekNo在logType为月报的情形时存放月份。
				int userID=data.getInt("UserID");
				ps=conn.prepareStatement("INSERT INTO team_log(memberID,projectID,workPlace," +
						"logContent1,logContent2,logContent3,progress,COMMENT,submitTime,logType,weekNo,teamId) "+
						" VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
				ps.setInt(1, userID);
				ps.setInt(2, data.getInt("ProjID"));
				ps.setString(3, data.getString("Place"));
				ps.setString(4, data.getString("Content"));
				ps.setString(5, data.getString("Conclusion"));
				ps.setString(6, data.getString("Plan"));
				ps.setInt(7, data.getInt("Progress"));
				ps.setString(8, data.getString("Comm"));
				java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(9, timestamp);
				ps.setInt(10, 3);
				ps.setInt(11, data.getInt("MonthNo"));
				ps.setInt(12, teamId);
				ps.executeUpdate();
			}else{
				conn = DBConnect.getConn();
				//返回团队成员标识，1是，0否。
				int userID=data.getInt("UserID");
				ps=conn.prepareStatement("update team_log set workPlace=?," +
						"logContent1=?,logContent2=?,logContent3=?,progress=?,COMMENT=?,submitTime=?,logType=? where" +
						" memberID=? and projectID=? and weekNo=? ");				
				ps.setString(1, data.getString("Place"));
				ps.setString(2, data.getString("Content"));
				ps.setString(3, data.getString("Conclusion"));
				ps.setString(4, data.getString("Plan"));
				ps.setInt(5, data.getInt("Progress"));
				ps.setString(6, data.getString("Comm"));
				java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(7, timestamp);
				ps.setInt(8, 2);
				ps.setInt(9, userID);
				ps.setInt(10, data.getInt("ProjID"));
				ps.setInt(11, data.getInt("MonthNo"));
				ps.executeUpdate();
			}
			// 还缺向所有成员推送的一步。
//			sqlData= CDataTransform.rsToJsonLabel(rs);
			tip = "信息更新成功";	
			
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
	public JSONObject getMyMonthlyReportList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			int userID=data.getInt("UserID");
			int projID=data.getInt("ProjID");
			ps=conn.prepareStatement("SELECT logID AS ReportID,submitTime AS ReportTime,weekNo AS MonthNo,workPlace AS Place,"+
					" logContent1 AS Content,logContent2 AS Conclusion,logContent3 AS Plan,progress AS Progress,"+
					" COMMENT AS Comm FROM team_log WHERE memberID=? AND projectID=? and logType=3");
			ps.setInt(1, userID);	
			ps.setInt(2, projID);
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
	public JSONObject getTeamMonthlyReportList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			int projID=data.getInt("ProjID");
			int weekNo=data.getInt("MonthNo");
			int teamID=findTeamIDbyProjID(projID);
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT t.*,tl.* FROM (SELECT t.memberID AS MemID,ui.nick_name AS MemName FROM team t join user_info ui on t.memberID=ui.id" +
					" WHERE teamId=?) t LEFT JOIN (SELECT tl.memberID,tl.logID AS ReportID,tl.submitTime "+
					" AS ReportTime,tl.weekNo AS WeekNo,tl.workPlace AS Place,"+
					" tl.logContent1 AS Content,tl.logContent2 AS Conclusion,tl.logContent3 AS Plan,tl.progress AS Progress,"+
					" tl.COMMENT AS Comm FROM team_log tl WHERE tl.projectID=? AND tl.weekNo=? AND tl.logType=3 AND tl.teamId=?) tl ON t.MemID=tl.memberID ");				
			ps.setInt(1, teamID);
			ps.setInt(2, projID);
			ps.setInt(3, weekNo);
			ps.setInt(4, teamID);
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
	public JSONObject setWriteMeetingReport(JSONObject data) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONObject sqlData=new JSONObject();	
		JSONObject content=new JSONObject();
		int userID=data.getInt("UserID");
		int projID=data.getInt("ProjID");
		String time=data.getString("Time");
		content=data;
		content.remove("UserID");
		content.remove("ProjID");
		content.remove("Time");
		int ID=0;
		try{
			if(notFindMeetingReport(userID,projID,time,4)){
				conn = DBConnect.getConn();
				//返回团队成员标识，1是，0否。				
				ps=conn.prepareStatement("INSERT INTO team_log(memberID,projectID," +
						"logContent1,submitTime,logType,Time) "+
						" VALUES(?,?,?,?,?,?)");
				ps.setInt(1, userID);
				ps.setInt(2, projID);
				ps.setString(3, content.toString());
				java.sql.Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(4, timestamp);
				ps.setInt(5, 4);
				ps.setString(6, time);
				ps.executeUpdate();
				ps= conn.prepareStatement("SELECT LAST_INSERT_ID();");  
		        rs = ps.executeQuery();  
		        if(rs.next()){ 
		        	ID=rs.getInt(1);
		        }
		        sqlData.put("ReportID",ID);
			}else{
				conn = DBConnect.getConn();
				//返回团队成员标识，1是，0否。				
				ps=conn.prepareStatement("update team_log set logContent1=? where memberID=?" +
						" and logType=4 and projectID=? ");				
				ps.setString(1, content.toString());
				ps.setInt(2, userID);
				ps.setInt(3, projID);
//				ps.setString(4, time);								
				ps.executeUpdate();
			}
			// 还缺向所有成员推送的一步。
//			sqlData= CDataTransform.rsToJsonLabel(rs);
			tip = "信息更新成功";	
			
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
	private boolean notFindMeetingReport(int userID, int projID, String time, int logType) {
		// TODO Auto-generated method stub
		boolean result=true;
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。			
			ps=conn.prepareStatement("SELECT submitTime FROM team_log WHERE memberID=? AND " +
					"projectID=? AND Time=? and logType=?");
			ps.setInt(1, userID);	
			ps.setInt(2, projID);
			ps.setString(3, time);
			ps.setInt(4, logType);
			rs=ps.executeQuery();
			// 还缺向所有成员推送的一步。
			if(rs.next()){
				result=false;
			}			
		}catch(Exception e){			
			e.printStackTrace();
		}finally{
			DBConnect.close(conn,ps,rs);
		}
		return result;
	}
	public JSONObject getTeamMeetingReportList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			conn = DBConnect.getConn();
			//返回团队成员标识，1是，0否。
			int userID=data.getInt("UserID");
			int projID=data.getInt("ProjID");
			String dateStr=data.getString("FromMonthNo");
			StringBuilder sqlString=new StringBuilder("SELECT logID AS ReportID,submitTime AS ReportTime," +
					"Time,logContent1 FROM team_log WHERE logType=4 and projectID= "+projID);
			if(!dateStr.equals("")){
				sqlString.append(" and time >="+dateStr);
			}
			ps=conn.prepareStatement(sqlString.toString());				
			rs=ps.executeQuery();
			// 还缺向所有成员推送的一步。
			while(rs.next()){
				JSONObject temp=new JSONObject();
				System.out.println("+++++:"+rs.getString("logContent1"));
				temp=new JSONObject(rs.getString("logContent1"));
				temp.put("Time",rs.getString("Time"));
				temp.put("ReportID", rs.getInt("ReportID"));
				temp.put("ReportTime", rs.getString("ReportTime"));
				sqlData.put(temp);
			}			
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
	public JSONObject getTeamWeeklyReportList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			
			//返回团队成员标识，1是，0否。			
			int projID=data.getInt("ProjID");
			int weekNo=data.getInt("WeekNo");
			int teamID=findTeamIDbyProjID(projID);
			conn = DBConnect.getConn();
			ps=conn.prepareStatement("SELECT t.*,tl.* FROM (SELECT t.memberID AS MemID,ui.nick_name AS MemName FROM team t join user_info ui on t.memberID=ui.id" +
					" WHERE teamId=?) t LEFT JOIN (SELECT tl.memberID,tl.logID AS ReportID,tl.submitTime "+
					" AS ReportTime,tl.weekNo AS WeekNo,tl.workPlace AS Place,"+
					" tl.logContent1 AS Content,tl.logContent2 AS Conclusion,tl.logContent3 AS Plan,tl.progress AS Progress,"+
					" tl.COMMENT AS Comm FROM team_log tl WHERE tl.projectID=? AND tl.weekNo=? AND tl.logType=2 AND tl.teamId=?) tl ON t.MemID=tl.memberID ");				
			ps.setInt(1, teamID);
			ps.setInt(2, projID);
			ps.setInt(3, weekNo);
			ps.setInt(4, teamID);
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
	private int findTeamIDbyProjID(int projID) throws SQLException {
		// TODO Auto-generated method stub
		int result=0;
		//这里的signTime其实是上课时间
		conn = DBConnect.getConn();
		ps=conn.prepareStatement("SELECT teamID FROM project_detail WHERE projectId="+projID);
		rs=ps.executeQuery();
		if(rs.next()){
			result=rs.getInt(1);
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}
	public JSONObject getUserApplyJoinMsgList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			int userID=data.getInt("UserID");
			int teamID=data.getInt("TeamID");
			if(isTeamLeader(userID,teamID)){
				conn = DBConnect.getConn();
				//返回团队成员标识，1是，0否。			
				ps=conn.prepareStatement("SELECT id AS ApplyID,ApplyTime,MsgStatus," +
						"ApplyerID,ApplyerName,CheckTime FROM team_recruit_resp WHERE TeamID=?");
				ps.setInt(1, teamID);			
				rs=ps.executeQuery();
				// 还缺向所有成员推送的一步。
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "信息获取成功";	
			}else{
				tip="非团队负责人";
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
	private boolean isTeamLeader(int userID, int teamID) throws SQLException {
		// TODO Auto-generated method stub
		boolean result=false;
		//这里的signTime其实是上课时间
		conn = DBConnect.getConn();
		ps=conn.prepareStatement("SELECT 1 FROM team WHERE teamId=? AND memberID=? AND memberType=1");
		ps.setInt(1, teamID);
		ps.setInt(2, userID);
		rs=ps.executeQuery();
		if(rs.next()){
			result=true;
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}
	public JSONObject setApplyJoinStatus(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();	
		int res=0;
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			int userID=data.getInt("UserID");			
			int memberType=1;			
			try{
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("UPDATE team_recruit_resp SET CheckTime=NOW(),AuditUser=?,MsgStatus=? WHERE id="+data.getInt("ApplyID"));	
				ps.setInt(1, userID);
				ps.setInt(2, data.getInt("ApplyStatus"));
				ps.executeUpdate();
//				如果通过审核，这里要把通过审核的成员插入到团队表中
				ps=conn.prepareStatement(" INSERT INTO team(memberID,teamId,memberType) SELECT tp.ApplyerID," +
						"tr.teamID,0 FROM team_recruit_resp tp,team_recruit tr WHERE tp.RecruitID=tr.id AND tp.id="+data.getInt("ApplyID"));				
				ps.executeUpdate();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				DBConnect.close(conn,ps,rs);
			}				
			tip = "信息设置成功";		
			
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
	public JSONObject setApplyJoinMsg(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();	
		int res=0;
		try{
			//如果发送了userId字段。			
			//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。	
			int userID=data.getInt("UserID");
			int recruitID=data.getInt("RecruitID");
			int memberType=1;
			if(notFindRecruitApplyRecord(userID,recruitID)){
				try{
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("INSERT INTO team_recruit_resp(RecruitID,ApplyTime,ApplyerID,ApplyerName,TeamID,CheckTime) "+
							" SELECT t.id,NOW(),u.id,u.nick_name,t.teamID,NOW() FROM user_info u,team_recruit t WHERE u.id=? AND t.id=? ");	
					ps.setInt(1, userID);
					ps.setInt(2, recruitID);
					ps.executeUpdate();
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					DBConnect.close(conn,ps,rs);
				}				
				tip = "信息设置成功";		
			}else{
				flag=1;
				tip="已存在申请信息，无需重复申请";
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
	private boolean notFindRecruitApplyRecord(int userID, int recruitID) throws SQLException {
		// TODO Auto-generated method stub
		boolean result=true;
		//这里的signTime其实是上课时间
		conn = DBConnect.getConn();
		ps=conn.prepareStatement("select 1 from team_recruit_resp where ApplyerID="+userID+
				" and  RecruitID="+recruitID);
		rs=ps.executeQuery();
		if(rs.next()){
			result=false;
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}
}
