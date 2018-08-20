package ECP.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONObject;

import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

public class CacTeacherDao extends BaseDao{
	SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat df =   new SimpleDateFormat("yyyyMMddHHmmss");
	public JSONObject submintTeacherSelectedCourse(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		MarketDao marketDao = new MarketDao();
		try{
			//如果发送了userId字段。
			if(data.has("teacherjobnumber")){
				int teacher_id = data.getInt("teacherjobnumber");
				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("INSERT INTO course_plan(teacherID,schoolYear,semester,courseName,courseNature,courseCategory,courseOwnership,"+
						"ExaminationMethod,schoolCampus,siteRequirements,weeklyHours,startWeek,endWeek,credit,courseCapacity,courseOrientedGrade,"+
						"courseOrientedCollege,courseOrientedMajor,courseSynopsis) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				ps.setInt(1, data.getInt("teacherjobnumber"));
				ps.setInt(2, data.getInt("schoolYear"));
				ps.setInt(3, data.getInt("semester"));
				ps.setString(4, data.getString("courseName"));
				ps.setString(5, data.getString("courseNature"));
				ps.setString(6, data.getString("courseCategory"));
				ps.setString(7, data.getString("courseOwnership"));
				ps.setString(8, data.getString("ExaminationMethod"));
				ps.setString(9, data.getString("schoolCampus"));
				ps.setString(10, data.getString("siteRequirements"));
				ps.setInt(11, data.getInt("courseWeeklyHours"));
				ps.setInt(12, data.getInt("startWeek"));
				ps.setInt(13, data.getInt("endWeek"));
				ps.setInt(14, data.getInt("courseCredit"));
				ps.setInt(15, data.getInt("courseCapacity"));
				ps.setInt(16, data.getInt("courseOrientedGrade"));
				ps.setString(17, data.getString("courseOrientedCollege"));
				ps.setString(18, data.getString("courseOrientedMajor"));
				ps.setString(19, data.getString("courseSynopsis"));
				ps.executeUpdate();
//					sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "教师成功上报该门课程";
				
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}
	//老师的信息也是写在个人经历表中。教工编号对应学生的学号。这个函数写的还有很多漏洞，比如学号需要和用户编号对应起来才有效。否则同一个学号或教工号的人会很多。
	//而且一个人的学号也有可能会和自己的教工号重合（不在同一个年份）。一个人在不同阶段的学号也有可能重合。如果使用编码的话，问题可能会少点，比如学号中包含年份信息的情况下。
	private boolean findBySchoolNo(int SchoolNo) {
		// TODO Auto-generated method stub
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("select admissionNo from per_experience where SchoolNo="+SchoolNo);
			rs=ps.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return false;	
	}
	public JSONObject getTeacherSelectedCourses(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		MarketDao marketDao = new MarketDao();
		try{
			//如果发送了userId字段。
			if(data.has("teacherjobnumber")){
				int teacher_id = data.getInt("teacherjobnumber");
				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				
				conn = DBConnect.getConn();
				StringBuilder sqlBuilder=new StringBuilder("SELECT distinct cp.courseName,cp.courseCode,cp.schoolCampus,cp.weeklyHours AS courseWeeklyHours,startWeek,"+
						"endWeek,credit AS courseCredit,cp.courseCapacity,courseSynopsis "+
						" FROM course_plan cp JOIN class_schedule cs ON cp.courseCode=cs.courseCode WHERE cp.teacherID="+teacher_id);
				System.out.println(sqlBuilder.toString());
				ps=conn.prepareStatement(sqlBuilder.toString());
				rs=ps.executeQuery();
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "查询返回教师已上报公选课信息";
				
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject getTeacherSchedule(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			
		//用户的这些信息都不允许为空，为空的话下面的写法就有问题。	
			if(data.has("teacherjobnumber")){
				int admission_id=data.getInt("teacherjobnumber");
//				System.out.println("输出："+admission_id);
				
				conn = DBConnect.getConn();
				StringBuilder sql=new StringBuilder("SELECT cs.courseCode AS courseNO,cs.courseName,cs.courseEngName,cs.courseCredit,cs.courseCapacity,"+
						"cs.courseReaders,cs.courseLimit,cs.courseWeeklyHours,cs.courseTotalHours,cs.courseStartWeek,cs.courseEndWeek,"+
						"cs.classWeekTime,cs.classSectionTime,cs.coursePlaceBan,cs.coursePlaceRoom "+
						" FROM class_schedule cs WHERE cs.teacherID="+admission_id);
				
				ps=conn.prepareStatement(sql.toString());
				rs=ps.executeQuery();
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip="查询学生的可选课程的课程信息";				
			}else{
				flag=1;
				tip="参数错误";
			}
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}
	

	public JSONObject getCurrentCourses(JSONObject data) {
		// TODO Auto-generated method stub
		return null;
	}

	public JSONObject getCurrentCoursesRoster(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		MarketDao marketDao = new MarketDao();
		try{
			//如果发送了userId字段。
			if(data.has("teacherjobnumber")){
				int teacher_id = data.getInt("teacherjobnumber");
				int course_id = data.getInt("courseCode");
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。			
				conn = DBConnect.getConn();
				
				ps=conn.prepareStatement("SELECT sn.studentname,sn.Sno,case when isnull(ca.status) then 0 else ca.status end as signstatus "+
						" FROM(SELECT sname AS studentname ,cs.Sno,cs.courseId "+
							"	FROM class_select cs LEFT JOIN stuff_info si ON si.SchoolNo=cs.sno "+
							"	WHERE cs.courseId="+course_id+" ) sn LEFT JOIN class_attendance ca "+
							"	ON sn.sno=ca.Sno AND sn.courseId=ca.courseNo AND ca.callTime>=NOW()");
				rs=ps.executeQuery();
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "查询返回当前时间的课程学生花名册";
				
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject rollCallByStudentName(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			
		//用户的这些信息都不允许为空，为空的话下面的写法就有问题。	
			if(data.has("teacherjobnumber")){
				int admission_id=data.getInt("teacherjobnumber");
//				System.out.println("输出："+admission_id);
				java.sql.Timestamp signTime=new java.sql.Timestamp(df.parse(data.getString("classTime").concat("00")).getTime());
				int courseNo=data.getInt("courseCode");
				int Sno=data.getInt("Sno");
				
				if(findStudentAttendance(signTime,courseNo,Sno)){
					conn = DBConnect.getConn();
					String status=data.getString("signstatus");
					ps=conn.prepareStatement("update class_attendance set status="+status+
							" where classTime='"+signTime+"' and courseNo="+courseNo+" and Sno="+Sno);
					ps.executeUpdate();
					
				}else{
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("INSERT INTO class_attendance(teacherID,Sno,uName,courseNo,STATUS,signTime,callTime,classTime) VALUES(?,?,?,?,?,?,?,?)");
					ps.setInt(1, admission_id);
					ps.setInt(2, data.getInt("Sno"));
					ps.setString(3, data.getString("studentname"));
					ps.setInt(4, data.getInt("courseCode"));
					ps.setInt(5, data.getInt("signstatus"));
					String now=sdf.format(System.currentTimeMillis());
					long nowDate = sdf.parse(now).getTime();
					java.sql.Timestamp strDate=new java.sql.Timestamp(nowDate);
					ps.setTimestamp(6, strDate);
					ps.setTimestamp(7, strDate);
					long tmpDate=df.parse(data.getString("classTime").concat("00")).getTime();
					java.sql.Timestamp classDate=new java.sql.Timestamp(tmpDate);
					ps.setTimestamp(8, classDate);
					ps.executeUpdate();
				}
//				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip="学生点名成功";				
			}else{
				flag=1;
				tip="参数错误";
			}
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	private boolean findStudentAttendance(java.sql.Timestamp signTime, int courseNo, int sno) throws SQLException {
		// TODO Auto-generated method stub
		boolean result=false;
		//这里的signTime其实是上课时间
		conn = DBConnect.getConn();
		ps=conn.prepareStatement("SELECT * FROM class_attendance WHERE sno= "+sno+
				" AND courseNo="+courseNo+" AND classTime='"+signTime+"'");
		rs=ps.executeQuery();
		if(rs.next()){
			result=true;
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}
	public JSONObject getStudentLeaveRequest(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		
		try{
			//如果发送了userId字段。
			if(data.has("teacherjobnumber")){
				int teacher_id = data.getInt("teacherjobnumber");
//				int course_id = data.getInt("courseCode");
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("SELECT ID AS leaveid,uName AS leavepersonname,Sno AS leavepersonsno,dprtName AS leavepersoncollege,majorName AS leavepersonmajor,"+
						"p.className AS leavepersonclass,leaveType,courseName AS leavecourse,courseWeekTime AS leaveCourseWeekTime,"+
						"courseSectionTime AS leaveCourseSectionTime,classPlace AS leaveCoursePlace,leaveStart AS leavestarttime,leaveEnd AS leaveendtime,reason AS leavereason,"+
						"rejectReason AS studentleaveresult FROM class_attendance ca,stuff_info p WHERE ca.sno=p.schoolno  AND processState=1 AND ca.teacherID="+teacher_id);
				rs=ps.executeQuery();
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "查询返回需要自己审核的学生请假消息";
				
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject studentLeaveResponse(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		MarketDao marketDao = new MarketDao();
		try{
			//如果发送了userId字段。
			if(data.has("teacherjobnumber")){
				int teacher_id = data.getInt("teacherjobnumber");
//				int course_id = data.getInt("courseCode");
				int action=data.getInt("action");
				String reason = data.getString("rejectresult");
				int id = data.getInt("leaveid");
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();	
				ps=conn.prepareStatement("update class_attendance set processState=2,status="+action+",rejectReason='"+
				reason+"' where id="+id);
				ps.executeUpdate();
//					sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "响应教师审批成功";				
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject arrageClassAssign(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。
			if(data.has("teacherjobnumber")){
				int teacher_id = data.getInt("teacherjobnumber");
				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("INSERT INTO publish_paper(teacherID,paperName,courseNo,courseName,startTime,endTime,requirement) VALUES(?,?,?,?,?,?,?)");
				ps.setInt(1, data.getInt("teacherjobnumber"));
				ps.setString(2, data.getString("classassignname"));
				ps.setInt(3, data.getInt("courseCode"));
				ps.setString(4, data.getString("coursename"));
				java.sql.Timestamp startTime=new java.sql.Timestamp(df.parse(data.getString("classassignstarttime").concat("00")).getTime());
				java.sql.Timestamp endTime=new java.sql.Timestamp(df.parse(data.getString("classassignendtime").concat("00")).getTime());
				ps.setTimestamp(5, startTime);
				ps.setTimestamp(6, endTime);
				ps.setString(7, data.getString("classassigncontent"));
				ps.executeUpdate();
//					sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "作业发布成功";
				
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject getArrageClassAssign(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		MarketDao marketDao = new MarketDao();
		try{
			//如果发送了userId字段。
			if(data.has("teacherjobnumber")){
				int teacher_id = data.getInt("teacherjobnumber");
				int course_id = data.getInt("coursecode");
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。				
				conn = DBConnect.getConn();
				StringBuilder sql=new StringBuilder("SELECT distinct pp.paperName AS classassignname,pp.courseName AS coursename,pp.id AS classassignid,pp.startTime AS classassignstarttime,"+
						"pp.endTime AS classassignendtime,pp.requirement AS classassigncontent,pp.paperPath1 as classassignimage1,pp.paperPath2 as classassignimage2, "+
						"pp.paperPath3 as classassignimage3,cs.courseReaders as coursetotal,count(distinct spID) as classassignsubmitnumber FROM publish_paper pp join class_schedule cs on pp.courseNo=cs.courseCode "+
						"left join class_paper cp on pp.id=cp.paperID "+
						"WHERE pp.teacherID="+teacher_id+" AND pp.courseNo="+course_id);
				if(data.has("classassignstarttime")){
					if(data.getString("classassignstarttime")!=null &&!data.getString("classassignstarttime").equals("null")){
						long startDate = df.parse(data.getString("classassignstarttime").concat("00")).getTime();
						java.sql.Timestamp start=new java.sql.Timestamp(startDate);
						sql.append(" and pp.endTime>='"+start+"'");
					}
				}
				if(data.has("classassignendtime")){
					if(data.getString("classassignendtime")!=null &&!data.getString("classassignendtime").equals("null")){
						long endDate = df.parse(data.getString("classassignendtime").concat("00")).getTime();
						java.sql.Timestamp end=new java.sql.Timestamp(endDate);
						sql.append(" and pp.endTime<='"+end+"'");
					}
				}
				sql.append(" GROUP BY pp.paperName, pp.courseName , pp.id , pp.startTime , pp.endTime , pp.requirement, pp.paperPath1 , pp.paperPath2 , pp.paperPath3 , cs.courseReaders ");
//				System.out.println(sql.toString());
				ps=conn.prepareStatement(sql.toString());
//							"AND endTime >= '"+startDate +"' AND endTime <= '"+endDate +"'");
				rs=ps.executeQuery();
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "可以对自己已经布置的作业查询和浏览";
				
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject getStuSubmClassAssign(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		MarketDao marketDao = new MarketDao();
		try{
			//如果发送了userId字段。
			if(data.has("teacherjobnumber")){
				int teacher_id = data.getInt("teacherjobnumber");
				int course_id = data.getInt("coursecode");
				String paperName=data.getString("classassignname");
				//由于不能传空值过来，作业编号空值对应的字符串是null，对应解析成0.
				int paper_id=0;
				if(!data.getString("classassignid").equals("null")){
					paper_id =data.getInt("classassignid");
				}
//				Date startDate = new Date(df.parse(data.getString("classassignstarttime").concat("00")).getTime());
//				Date endDate = new Date(df.parse(data.getString("classassignendtime").concat("00")).getTime());
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				//日期这里要设置默认值，否则不传值进来会出错。
				
				conn = DBConnect.getConn();
				StringBuilder sql=new StringBuilder("SELECT DISTINCT "+
								"  cp.paperName    AS classassignname,"+
								"  pp.courseName AS coursename,"+
								"  spID      AS classassignid,"+
								"  sName        AS studentname,"+
								"  grade        AS score,"+
								"  Sno ,"+
								"  submitState,"+
								"  readState    AS markingstate,"+
								"  paperContent AS classassigncontent,"+
								"  cp.paperPath1   AS classassignimage1,"+
								"  cp.paperPath2   AS classassignimage2,"+
								"  cp.paperPath3   AS classassignimage3,"+
								"  remark"+
								" FROM class_paper cp"+
								"   JOIN publish_paper pp"+
								"    ON cp.courseNo = pp.courseNo AND cp.paperID=pp.id"+
								" WHERE pp.teacherID = "+teacher_id+
								"    AND cp.courseNo = "+course_id);
				if(paper_id!=0){
								sql.append("    AND paperID="+paper_id);
				}
				if(data.has("classassignstarttime")){
					if(data.getString("classassignstarttime")!=null &&!data.getString("classassignstarttime").equals("null")){
						long startDate = df.parse(data.getString("classassignstarttime").concat("00")).getTime();
						java.sql.Timestamp start=new java.sql.Timestamp(startDate);
						sql.append(" and pp.endTime>='"+start+"'");
					}
				}
				if(data.has("classassignendtime")){
					if(data.getString("classassignendtime")!=null &&!data.getString("classassignendtime").equals("null")){
						long endDate = df.parse(data.getString("classassignendtime").concat("00")).getTime();
						java.sql.Timestamp end=new java.sql.Timestamp(endDate);
						sql.append(" and pp.endTime<='"+end+"'");
					}
				}
				System.out.println(sql.toString());
				ps=conn.prepareStatement(sql.toString());

				rs=ps.executeQuery();
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "可以对自己已经布置的作业查询和浏览";
				
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject markingStuSubmit(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。
			if(data.has("teacherjobnumber")){
				int teacher_id = data.getInt("teacherjobnumber");
				int course_id = data.getInt("courseCode");
				int paperID=data.getInt("classassignid");
				String reason = data.getString("remark");
				int grade = data.getInt("score");
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				//批阅状态1是已批阅，0 是未批阅。
				if(findBySchoolNo(teacher_id)){
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("UPDATE class_paper SET grade="+grade+",remark='"+reason
							+"',readState= 1 WHERE courseCode="+course_id+" AND paperID="+paperID);
					ps.executeUpdate();
//					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip = "学生作业批阅提交成功";
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
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject getStudentExamResults(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();
		MarketDao marketDao = new MarketDao();
		try{
			//如果发送了userId字段。
			if(data.has("teacherjobnumber")){
				int teacher_id = data.getInt("teacherjobnumber");
				int course_id = data.getInt("courseCode");
				
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				//默认课程编号是唯一的。区别不同课程可能要加一个课程分类号。
				
				conn = DBConnect.getConn();
				ps=conn.prepareStatement("select sName as studentname,Sno ,midGrades as midtermresults,commGrades as peacetimeresults," +
						"endGrades as finalexam,totalGrades as totalmark from class_examsgrade where courseNO="+course_id );
				rs=ps.executeQuery();
				sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "根据条件查询全部学生的考试成绩";				
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject submitStudentResults(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result=new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData=new JSONArray();		
		try{
			//如果发送了userId字段。
			if(data.has("teacherjobnumber")){
				int teacher_id = data.getInt("teacherjobnumber");
				int Sno=data.getInt("sno");
				int courseNo=data.getInt("coursenumber");
				//如果用户id不空{如果用户id存在，则从deliveryAddress表中查询数据}。否则报错。
				
				
				if(findStudentExamGrades(Sno,courseNo)){
					conn = DBConnect.getConn();
					String midGrades=data.getString("midtermresults").equals("null")?null:data.getString("midtermresults");
					String endGrades=data.getString("finalexam").equals("null")?null:data.getString("finalexam");
					String commGrades=data.getString("peacetimeresults").equals("null")?null:data.getString("peacetimeresults");
					String totalGrades=data.getString("totalmark").equals("null")?null:data.getString("totalmark");
					StringBuilder sql=new StringBuilder("UPDATE class_examsgrade SET midGrades="+midGrades+
				 " ,endGrades= "+endGrades+", commGrades="+commGrades+", totalGrades="+totalGrades+" WHERE sno= "+Sno +" AND courseNo="+ courseNo);
					System.out.println(sql.toString());
					ps=conn.prepareStatement(sql.toString());
					ps.executeUpdate();
					
				}else{
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("insert into class_examsgrade(teacherID,courseName,courseNo,sName,midGrades,commGrades,endGrades,totalGrades) values(?,?,?,?,?,?,?,?)");
					ps.setInt(1, data.getInt("teacherjobnumber"));
					ps.setString(2, data.getString("coursename"));
					ps.setInt(3, data.getInt("coursenumber"));
					ps.setString(4, data.getString("studentname"));					
					ps.setFloat(5, (float) data.getDouble("midtermresults"));
					ps.setFloat(6, (float) data.getDouble("peacetimeresults"));
					ps.setFloat(7, (float) data.getDouble("finalexam"));
					ps.setFloat(8, (float) data.getDouble("totalmark"));
					ps.executeUpdate();
				}
//					sqlData= CDataTransform.rsToJsonLabel(rs);
				tip = "学生成绩提交成功";
				
			}else{
				flag=1;
				tip="没有用户id";
			}
			
		}catch(Exception e){
			flag=1;
			tip="系统运行错误";
			e.printStackTrace();
		}
		try{
			result.put("flag", flag);
			result.put("tip", tip);
			result.put("data", sqlData);
		}catch(Exception e){
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}
	private boolean findStudentExamGrades(int sno,int courseNo) throws SQLException {
		// TODO Auto-generated method stub
		boolean result=false;
		conn = DBConnect.getConn();
		ps=conn.prepareStatement("SELECT * FROM class_examsgrade WHERE sno="+sno+" AND courseNo="+courseNo);
		rs=ps.executeQuery();
		if(rs.next()){
			result=true;
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}
	public static void main(String[] args) throws Exception {
		System.out.println("测试CacStudentDao中的小函数");
		CacTeacherDao cacTeacherDao = new CacTeacherDao();
		JSONObject data=new JSONObject();
		SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df =   new SimpleDateFormat("yyyyMMddHHmmss");
		long str= df.parse("20170517000000").getTime();
		java.sql.Timestamp strDate=new java.sql.Timestamp(str);
	//如果用单引号的话就会把2读成字符串，值为50
		data.put("Sno","3");
		data.put("studentname","dafag");
		data.put("classTime", "201705211055");
		data.put("courseCode", "2");
		data.put("teacherjobnumber", "1");
		data.put("signstatus", "6");
		data.put("finalexam", "45");
		data.put("midtermresults", "90");
		data.put("totalmark", "null");
		data.put("coursename", "计算机技术");
		data.put("peacetimeresults", "57");
		data.put("coursenumber", "1");
		
		JSONObject result;
//		result=cacTeacherDao.rollCallByStudentName(data);
		result=cacTeacherDao.rollCallByStudentName(data);
//		result=cacTeacherDao.submitStudentResults(data);
		System.out.println("结果是："+result.toString());
		
//		System.out.println("结果是："+ df.parse("20170521133700").toString());
//		System.out.println("结果是："+ strDate.toString());
	}
}
