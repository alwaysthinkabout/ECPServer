package ECP.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import ECP.util.db.CDataTransform;
import ECP.util.db.DBConnect;

public class CacStudentDao extends BaseDao{
	SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat df =   new SimpleDateFormat("yyyyMMddHHmmss");
	public JSONObject newStudentEntryLogin(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			
		//用户的这些信息都不允许为空，为空的话下面的写法就有问题。	
			if(data.has("admissionTicketNo")){
				long admission_id=data.getLong("admissionTicketNo");
				if(findByAdmissionNo(admission_id)){
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("SELECT sName AS studentName,  SchoolNo AS Sno, dprtName AS studentCollege,"+
							"  majorName AS studentMajor, year AS studentGrade, duration AS lengthofschooling,"+
							"  isNetReport,isCheck as isAckPersonInfo, isNewStudent FROM stuff_info where "+
							"   admissionNo="+admission_id);
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="新手验证成功";
				}else{
					flag=1;
					tip="准考证号不存在";
				}
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

	private boolean findByAdmissionNo(long admission_id) {
		// TODO Auto-generated method stub
		boolean result=false;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("select admissionNo from stuff_info where admissionNo="+admission_id);
			rs=ps.executeQuery();
			if(rs.next()){
				result=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;	
	}

	public JSONObject checkInfoSuccess(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			
		//用户的这些信息都不允许为空，为空的话下面的写法就有问题。	
			if(data.has("admissionTicketNo")){
				long admission_id=data.getLong("admissionTicketNo");
				if(findByAdmissionNo(admission_id)){
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("update stuff_info set isCheck=1 where admissionNo="+admission_id);
					ps.executeUpdate();
//					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="确认新生信息成功";
				}else{
					flag=1;
					tip="准考证号不存在";
				}
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

	public JSONObject getStudentFee(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			
		//用户的这些信息都不允许为空，为空的话下面的写法就有问题。	
			if(data.has("Sno")){
				int admission_id=data.getInt("Sno");
				System.out.println("输出："+admission_id);
				if(findBySchoolNo(admission_id)){
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("select item_name as paymentItemName,item_sum as paymentItemValue from transaction_fee t,stuff_info si"+
							" where si.uid=t.user_id and si.SchoolNo="+admission_id);
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="返回学生需要交纳的分项费用";
				}else{
					flag=1;
					tip="学号不存在";
				}
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

	private boolean findBySchoolNo(int SchoolNo) {
		// TODO Auto-generated method stub
		boolean result=false;
		conn = DBConnect.getConn();
		try {
			ps=conn.prepareStatement("select admissionNo from stuff_info where SchoolNo="+SchoolNo);
			rs=ps.executeQuery();
			if(rs.next()){
				result= true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnect.close(conn,ps,rs);
		return result;	
	}
//支付这个暂时不做。
	public JSONObject payTuitionSuccess(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			
		//用户的这些信息都不允许为空，为空的话下面的写法就有问题。	
			if(data.has("Sno")){
				int admission_id=data.getInt("Sno");
				System.out.println("输出："+admission_id);
				if(findBySchoolNo(admission_id)){
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("select item_name,item_sum from transaction_fee t,per_experience p" +
							" where p.uid=t.user_id and p.SchoolNo="+admission_id);
					rs=ps.executeQuery();
//					sqlData= CDataTransform.rsToJsonRowLabel(rs);
					if(rs.next()){
						tip="确认学生已缴费成功";
					}else{
						flag=0;
						tip="没有查到缴费记录";
					}
				}else{
					flag=1;
					tip="学号不存在";
				}
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

	public JSONObject getStudentReportSucessInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			
		//用户的这些信息都不允许为空，为空的话下面的写法就有问题。	
			if(data.has("Sno")){
				int admission_id=data.getInt("Sno");
				System.out.println("输出："+admission_id);
				if(findBySchoolNo(admission_id)){
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("Select className,dormitoryName "+
							"from stuff_info where SchoolNo="+admission_id);
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="学生成功报到并缴费成功，返回学生分配的班级，宿舍信息";
				}else{
					flag=1;
					tip="学号不存在";
				}
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

	public JSONObject getStudentSelectCourses(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			
		//用户的这些信息都不允许为空，为空的话下面的写法就有问题。	
			if(data.has("Sno")){
				int admission_id=data.getInt("Sno");
//				System.out.println("输出："+admission_id);
				if(findBySchoolNo(admission_id)){
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("select distinct cs.courseName,courseCode,courseNature,courseCollege,teacherName,"+
							"null as coursePlace,null as courseTime,courseStartend,courseCredit,weeklyHours,courseCapacity as courseSize,courseAllowance "+
							"from class_schedule cs,per_experience c where (cs.courseNature='公选课' or cs.courseCollege=c.department) and c.SchoolNo="+admission_id);
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="查询学生的可选课程的课程信息";
				}else{
					flag=1;
					tip="学号不存在";
				}
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

	public JSONObject getStudentSchedule(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			
		//用户的这些信息都不允许为空，为空的话下面的写法就有问题。	
			if(data.has("Sno")){
				int admission_id=data.getInt("Sno");
//				System.out.println("输出："+admission_id);
				if(findBySchoolNo(admission_id)){
					conn = DBConnect.getConn();
					StringBuilder sql=new StringBuilder("SELECT cs.courseCode AS courseNO,cs.courseName,cs.courseEngName,cs.courseCredit,cs.courseCapacity,"+
							"cs.courseReaders,cs.courseLimit,cs.courseWeeklyHours,cs.courseTotalHours,cs.courseStartWeek,cs.courseEndWeek,"+
							"cs.classWeekTime,cs.classSectionTime,cs.coursePlaceBan,cs.coursePlaceRoom,teacherName,cs.teacherID AS teacherNO"+
							" FROM class_schedule cs,class_select c WHERE cs.courseCode=c.courseId AND c.Sno="+admission_id);
					
					ps=conn.prepareStatement(sql.toString());
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="查询学生的可选课程的课程信息";
				}else{
					flag=1;
					tip="学号不存在";
				}
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
//获取学生的签到信息，这里应该有进一步的逻辑给出判断（比如时间，空间距离约束），这里先做一个写入数据库的动作。
	@SuppressWarnings("unused")
	public JSONObject studentSign(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			
		//用户的这些信息都不允许为空，为空的话下面的写法就有问题。	这里只是允许学生签到，没有对学生的位置做进一步判断。
			if(data.has("Sno")){
				int admission_id=data.getInt("Sno");
//				System.out.println("输出："+admission_id);
				if(findBySchoolNo(admission_id)){
					int ClassNo= data.getInt("ClassNo");
					java.sql.Timestamp time = new java.sql.Timestamp(df.parse(data.getString("signTime").concat("00")).getTime());
					String site = data.has("signSite")?data.getString("signSite"):null;
					if(!findStudentSignRecord(admission_id,ClassNo,time)){
						int status =1;
						conn = DBConnect.getConn();
						ps=conn.prepareStatement("INSERT INTO class_attendance(Sno,courseNo,classTime,classPlace,signTime,signSite,STATUS," +
								"courseName,auditor,courseWeekTime,courseSectionTime,longitude,latitude) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
						
						ps.setInt(1, admission_id);
						ps.setInt(2, ClassNo);
						java.sql.Timestamp classTime=new java.sql.Timestamp(df.parse(data.getString("coursetime").concat("00")).getTime());
						ps.setTimestamp(3, classTime);
						ps.setString(4,data.getString("courseplace"));						
						ps.setTimestamp(5, time);
						ps.setString(6, site);
						ps.setInt(7, status);
						ps.setString(8, data.getString("coursesignname"));
						ps.setString(9, data.getString("courseteachername"));
						ps.setInt(10, data.getInt("courseweektime"));
						ps.setInt(11, data.getInt("courseSectionTime"));
						ps.setDouble(12, data.getDouble("signPlacelongitude"));
						ps.setDouble(13, data.getDouble("signPlacelatitude"));
						ps.executeUpdate();
//						sqlData= CDataTransform.rsToJsonRowLabel(rs);
						tip="签到成功";
					}
				}else{
					flag=1;
					tip="学号不存在";
				}
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

	private boolean findStudentSignRecord(int admission_id, int classNo,
			java.sql.Timestamp time) throws SQLException {
		// TODO Auto-generated method stub
		//如果在一堂课上已经有签到记录，则不能重复签到
		boolean result=false;
		//这里的signTime其实是上课时间
		conn = DBConnect.getConn();
		ps=conn.prepareStatement("SELECT * FROM class_attendance WHERE Sno="+admission_id+" AND courseNo="+classNo+
				" and classTime='"+time+"'");
		rs=ps.executeQuery();
		if(rs.next()){
			result=true;
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject studentLeave(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			
		//用户的这些信息都不允许为空，为空的话下面的写法就有问题。	
			if(data.has("Sno")){
				int admission_id=data.getInt("Sno");
//				System.out.println("输出："+admission_id);
				if(findBySchoolNo(admission_id)){
					int ClassNo= data.getInt("ClassNO");
					java.sql.Timestamp classTime=new java.sql.Timestamp(df.parse(data.getString("coursetime").concat("00")).getTime());
					//这里应该发起一个请假单审批的流程。
					if(findStudentSignRecord(admission_id, ClassNo, classTime)){
						int status =1;
						conn = DBConnect.getConn();
						ps=conn.prepareStatement("insert into class_attendance(Sno,courseNo,leaveStart,leaveEnd,contactName," +
								"contact,reason,status,classTime,classPlace,leaveType,courseWeekTime,courseSectionTime) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
						
						ps.setInt(1, admission_id);
						ps.setInt(2, ClassNo);
						if(data.has("studentleavestart")&&data.has("studentleaveend")){
							if(!data.getString("studentleavestart").equals("null")){
								java.sql.Timestamp start=new java.sql.Timestamp(df.parse(data.getString("studentleavestart").concat("00")).getTime());
								ps.setTimestamp(3, start);
							}
							if(!data.getString("studentleaveend").equals("null")){
								java.sql.Timestamp end =new java.sql.Timestamp(df.parse(data.getString("studentleavestart").concat("00")).getTime());							
								ps.setTimestamp(4, end);
							}
						}
						ps.setString(5, data.getString("emergencycontactname"));
						ps.setString(6, data.getString("emergencycontactphone"));
						ps.setString(7, data.getString("studentleavereason"));
						
						ps.setInt(8, status);
						ps.setTimestamp(9, classTime);
						ps.setString(10, data.getString("courseplace"));
						ps.setString(11, data.getString("studentleavetype"));
						ps.setInt(12, data.getInt("courseweektime"));
						ps.setInt(13, data.getInt("courseSectionTime"));
						ps.executeUpdate();
						
//						sqlData= CDataTransform.rsToJsonRowLabel(rs);
						tip="请假申请中";
					}else{						
						flag=1;
						tip="已经有记录，请假失败";
					}
				}else{
					flag=1;
					tip="学号不存在";
				}
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

	public JSONObject studentAttendance(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			
		//用户的这些信息都不允许为空，为空的话下面的写法就有问题。	
			if(data.has("Sno")){
				int admission_id=data.getInt("Sno");
//				System.out.println("输出："+admission_id);
				
				
				if(findBySchoolNo(admission_id)){
					conn = DBConnect.getConn();
					StringBuilder sql=new StringBuilder("SELECT c.courseName,c.teacherName,classTime AS coursetime,classPlace AS courseplace,signTime AS coursesigntime,signSite AS coursesignplace,"+
							"status AS coursesignstate FROM class_attendance ca JOIN (SELECT DISTINCT courseCode,courseName,teacherName FROM class_schedule) c ON ca.courseNo=c.courseCode"+
							" WHERE ca.Sno = "+admission_id);
					if(data.has("startTime")){
						if(data.getString("startTime")!=null && !data.getString("startTime").equals("null")){
							String startTime = sdf.parse(data.getString("startTime")).toString();
							sql.append(" AND ca.classTime >= '"+startTime+"'");
						}
					}
					if(data.has("endTime")){
						if(data.getString("endTime")!=null && !data.getString("endTime").equals("null")){
							String startTime = sdf.parse(data.getString("endTime")).toString();
							sql.append(" AND ca.classTime <= '"+startTime+"'");
						}
					}
					ps=conn.prepareStatement(sql.toString());
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="查询学生的考勤信息";
				}else{
					flag=1;
					tip="学号不存在";
				}
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

	public JSONObject getClassAssignment(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			
		//用户的这些信息都不允许为空，为空的话下面的写法就有问题。	
			if(data.has("Sno")){
				int admission_id=data.getInt("Sno");
//				System.out.println("输出："+admission_id);
				if(findBySchoolNo(admission_id)){									
					//这里应该发起一个请假单审批的流程。										
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("SELECT pp.id as paperID,pp.paperName AS classassignmentname,teacherName AS classteachername,startTime AS classassignmenttime,endTime AS classassignmentdeadline,"+
							"CASE WHEN cp.paperID IS NULL THEN 0 ELSE 1 END classassignmentstate "+ 
							"FROM class_select c,publish_paper pp LEFT JOIN class_paper cp ON  pp.id=cp.paperID WHERE c.courseId=pp.courseNo AND c.sno="+admission_id);
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="获取到学生的所有的课程作业信息";					
				}else{
					flag=1;
					tip="学号不存在";
				}
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

	public JSONObject getExamination(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			
		//用户的这些信息都不允许为空，为空的话下面的写法就有问题。	
			if(data.has("Sno")){
				int admission_id=data.getInt("Sno");
//				System.out.println("输出："+admission_id);
				if(findBySchoolNo(admission_id)){									
					//这里应该发起一个请假单审批的流程。										
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("SELECT examTime AS examinationtime,examSite AS examinationplace,requirement AS examinationrequirement,state AS examinationstate,"+
							"c.courseName AS examinationcoursename,length FROM class_select cs,class_exams ce JOIN (SELECT DISTINCT courseCode,courseName "+
							" FROM class_schedule) c ON ce.courseNo=c.courseCode WHERE cs.Sno="+admission_id);
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="获取到学生的所有的课程作业信息";					
				}else{
					flag=1;
					tip="学号不存在";
				}
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

	public JSONObject getExaminationResults(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			
		//用户的这些信息都不允许为空，为空的话下面的写法就有问题。	
			if(data.has("Sno")){
				int admission_id=data.getInt("Sno");
				
//				System.out.println("输出："+admission_id);
				if(findBySchoolNo(admission_id)){									
					//这里应该发起一个请假单审批的流程。										
					conn = DBConnect.getConn();
					StringBuilder sql= new StringBuilder("SELECT courseName AS courseexaminationname,courseCredit AS coursecredit,courseName,courseNo,"+
							"courseCredit,midExamTime as midtermTime,midGrades as midtermResults,endExamTime as finalTime,endGrades as finalResults,"+
							"commGrades as peaceResults,totalGrades as totalResults"+
							" FROM class_examsgrade WHERE Sno ="+admission_id);
					if(data.has("Semester")){
						int semesterID=data.getInt("Semester");
						sql.append(" and Semester="+semesterID);
					}
					ps=conn.prepareStatement(sql.toString());
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="获取到学生的所有的考试成绩信息";					
				}else{
					flag=1;
					tip="学号不存在";
				}
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

	public JSONObject getLeaveSchool(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			
		//用户的这些信息都不允许为空，为空的话下面的写法就有问题。	
			if(data.has("Sno")){
				int admission_id=data.getInt("Sno");
//				System.out.println("输出："+admission_id);
				if(findBySchoolNo(admission_id)){									
					//这里应该发起一个请假单审批的流程。										
					conn = DBConnect.getConn();
					ps=conn.prepareStatement("SELECT itemName AS leaveschoolname,content AS relateddescription,handlePlace,contactPerson,contact AS relatedphone,"+
							"CASE WHEN gp.status IS NULL THEN 0 ELSE 1 END handlestate "+
							"FROM graduation_formalities gf LEFT JOIN graduation_procedure gp ON gf.dprtID=gp.dprtID WHERE gp.sno="+admission_id);
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="获取到学生的所有的离校手续信息";					
				}else{
					flag=1;
					tip="学号不存在";
				}
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
	public static void main(String[] args) throws Exception {
		System.out.println("测试CacStudentDao中的小函数");
		CacStudentDao cacStudentDao = new CacStudentDao();
		JSONObject data=new JSONObject();
	//如果用单引号的话就会把2读成字符串，值为50
		data.put("Sno","2");
		data.put("admissionTicketNo","123");
		data.put("startTime", "2017120100:00:00");
		JSONObject result;
//		result = cacStudentDao.getStudentFee(data);
//		result = cacStudentDao.newStudentEntryLogin(data);
		result = cacStudentDao.getStudentSelectCourses(data);
//		result = cacStudentDao.getStudentSchedule(data);
//		result = cacStudentDao.getClassAssignment(data);
//		result = cacStudentDao.studentAttendance(data);
//		System.out.println("结果是："+cacStudentDao.findBySchoolNo(2));
		System.out.println("结果是："+result.toString());
		
	}

	public JSONObject submintSelectedCourse(JSONObject data) {
		// TODO Auto-generated method stub
		//学生选课还有预选的这一步。
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			
		//用户的这些信息都不允许为空，为空的话下面的写法就有问题。	
			if(data.has("Sno")){
				int admission_id=data.getInt("Sno");				
//				System.out.println("输出："+admission_id);
				if(findBySchoolNo(admission_id)){
					//先判断学号是否存在，再判断是否已经有选课记录，最后插入数据。
					JSONArray datarr=data.getJSONArray("data");
					for(int i=1;i<=datarr.length();i++){
						int courseNo =datarr.getJSONObject(i).getInt("coursecode");
						String courseName=datarr.getJSONObject(i).getString("coursename");
						int isBook=datarr.getJSONObject(i).getInt("isbookbook");
						if(!findStudentSelectedCourse(admission_id,courseNo)){
							setStudentSelectedCourse(admission_id,courseNo,courseName,isBook);
						}
					}
					tip="查询学生的可选课程的课程信息";
				}else{
					flag=1;
					tip="学号不存在";
				}
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

	private void setStudentSelectedCourse(int admission_id, int courseNo,
			String courseName, int isBook) throws SQLException {
		// TODO Auto-generated method stub
		boolean result=false;
		//这里的signTime其实是上课时间
		conn = DBConnect.getConn();
		ps=conn.prepareStatement("INSERT INTO class_select(Sno,courseId,courseName,isBook) VALUES("+admission_id+
				","+courseNo+",'"+courseName+"',"+isBook+")");
		ps.executeUpdate();		
		DBConnect.close(conn,ps,rs);		
	}

	private boolean findStudentSelectedCourse(int admission_id, int courseNo) throws SQLException {
		// TODO Auto-generated method stub
		boolean result=false;
		//这里的signTime其实是上课时间
		conn = DBConnect.getConn();
		ps=conn.prepareStatement("SELECT * FROM class_select WHERE Sno="+admission_id+" AND courseNo="+courseNo);
		rs=ps.executeQuery();
		if(rs.next()){
			result=true;
		}
		DBConnect.close(conn,ps,rs);
		return result;
	}

	public JSONObject getSelectedCourses(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			
		//用户的这些信息都不允许为空，为空的话下面的写法就有问题。	
			if(data.has("Sno")){
				int admission_id=data.getInt("Sno");
//				System.out.println("输出："+admission_id);
				if(findBySchoolNo(admission_id)){
					conn = DBConnect.getConn();
					StringBuilder sqlBuilder=new StringBuilder("SELECT"+
							"  cs.courseName,"+
							"  courseCode,"+
							"  courseNature,"+
							"  courseCollege,"+
							"  teacherName,"+
							"  coursePlaceRoom,"+
							"  classWeekTime,"+
							"  classSectionTime,"+
							"  courseStartWeek,"+
							"  courseEndWeek,"+
							"  courseCredit,"+
							"  weeklyHours,"+
							"  courseCapacity,"+
							"  courseAllowance"+
							" FROM class_schedule cs"+
							"  RIGHT JOIN class_select c"+
							"    ON cs.courseCode = c.courseId"+
							" WHERE c.Sno = "+admission_id);
					System.out.println(sqlBuilder.toString());
					ps=conn.prepareStatement(sqlBuilder.toString());
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="查询学生自己已经选上的选修课程信息";
				}else{
					flag=1;
					tip="学号不存在";
				}
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

	public JSONObject getStudentLeaves(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject	result= new JSONObject();
		int flag=0;
		String tip=null;
		JSONArray sqlData= new JSONArray();
		try{
			
		//用户的这些信息都不允许为空，为空的话下面的写法就有问题。	
			if(data.has("Sno")){
				int admission_id=data.getInt("Sno");
//				System.out.println("输出："+admission_id);
				if(findBySchoolNo(admission_id)){
					conn = DBConnect.getConn();
					StringBuilder sqlBuilder=new StringBuilder("SELECT"+
							"  leaveType  AS studentleavetype,"+
							"  courseName AS studentleavecourse,"+
							"  leaveStart AS studentleavestart,"+
							"  leaveEnd   AS studentleaveend,"+
							"  reason     AS studentleavereson,"+
							"  STATUS     AS studentleaveresult"+
							" FROM class_attendance"+
							" WHERE leaveStart IS NOT NULL AND sno = "+admission_id);
					System.out.println(sqlBuilder.toString());
					ps=conn.prepareStatement(sqlBuilder.toString());
					rs=ps.executeQuery();
					sqlData= CDataTransform.rsToJsonLabel(rs);
					tip="获取到学生的所有历史请假信息，以及审批结果";
				}else{
					flag=1;
					tip="学号不存在";
				}
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
	
}
