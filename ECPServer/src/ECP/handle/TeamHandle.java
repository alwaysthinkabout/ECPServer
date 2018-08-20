package ECP.handle;

import org.json.JSONObject;

import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;

import ECP.service.TeamService;

public class TeamHandle extends CMsgBaseHandle{
	private TeamService teamService;
	
	public TeamHandle(){
		teamService = new TeamService();
	}	
	public int handleMsg(CParam param) throws Exception {
		System.out.println("进入TeamHandle的handleMsg");
		JSONObject data=getReqMessage(param);
		//这里初步设想是根据传入参数的长度来转发各种操作。或者像以前一样增加一个op的字段。
		String op=data.getString("op");
		System.out.println("收到的请求信息为："+data.toString());
		JSONObject result=new JSONObject();
		switch(op){
		case "getAllTeamInfo":
			result=teamService.getAllTeamInfo(data);
			break;
		case "getOneTeamInfo":
			result=teamService.getOneTeamInfo(data);
			break;
		case "getCreateTeamInfo":
			result=teamService.getCreateTeamInfo(data);
			break;
		case "getJoinTeamInfo":
			result=teamService.getJoinTeamInfo(data);
			break;
		case "createTeam":
			result=teamService.createTeam(data);
			break;
		case "otherRecrutingInfo":
			result=teamService.otherRecrutingInfo(data);
			break;
		case "myRecrutingInfo":
			result=teamService.myRecrutingInfo(data);
			break;
		case "publicRecrutingInfo":
			result=teamService.publicRecrutingInfo(data);
			break;
		case "getTeamBasicInfo":
			result=teamService.getTeamBasicInfo(data);
			break;
		case "getOneTeamCompletedProject":
			result=teamService.getOneTeamCompletedProject(data);
			break;
		case "getOneTeamGoingProject":
			result=teamService.getOneTeamGoingProject(data);
			break;
		case "dailyReport":
			result=teamService.dailyReport(data);
			break;
		case "weeklyReport":
			result=teamService.weeklyReport(data);
			break;
		case "monthlyReport":
			result=teamService.monthlyReport(data);
			break;
		case "meetingReport":
			result=teamService.meetingReport(data);
			break;
		case "createProj":
			result=teamService.createProj(data);
			break;
		case "getSearchTeamRelatedUsersList":
			result=teamService.getSearchTeamRelatedUsersList(data);
			break;
		case "getTeamRecruitDetails":
			result=teamService.getTeamRecruitDetails(data);
			break;
		case "projectDetail":
			result=teamService.projectDetail(data);
			break;
		case "getCTeamJoinedMembersList":
			result=teamService.getCTeamJoinedMembersList(data);
			break;
		case "setTaskAllocation":
			result=teamService.setTaskAllocation(data);
			break;
		case "myReport":
			result=teamService.myReport(data);
			break;
		case "teamReport":
			result=teamService.teamReport(data);
			break;
		case "teamMeetingReport":
			result=teamService.teamMeetingReport(data);
			break;
		}
	
		param.respData.append(result.toString());
//		System.out.println("输出数据："+param.respData);
		return super.handleMsg(param);
	}
}
