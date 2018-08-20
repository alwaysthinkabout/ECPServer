package ECP.handle;

import org.json.JSONObject;

import ECP.service.IncubatorService;
import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;

public class IncubatorHandle extends CMsgBaseHandle{
	private IncubatorService incubatorService;
	
	public IncubatorHandle(){
		incubatorService = new IncubatorService();
	}	
	public int handleMsg(CParam param) throws Exception {
		System.out.println("进入IncubatorHandle的handleMsg");
		JSONObject data=getReqMessage(param);
		//这里初步设想是根据传入参数的长度来转发各种操作。或者像以前一样增加一个op的字段。
		String op=data.getString("op");
		System.out.println("收到的请求信息为："+data.toString());
		JSONObject result=new JSONObject();
		switch(op){
		case "setCreateCTeamInfo":
			result=incubatorService.setCreateCTeamInfo(data);
			break;
		case "setCTeamInviteUserInfo":
			result=incubatorService.setCTeamInviteUserInfo(data);
			break;
		case "getCTeamJoinedMembersList":
			result=incubatorService.getCTeamJoinedMembersList(data);
			break;
		case "getCTeamWaitedUsersList":
			result=incubatorService.getCTeamWaitedUsersList(data);
			break;
		case "setCTeamCancelInviteUserInfo":
			result=incubatorService.setCTeamCancelInviteUserInfo(data);
			break;
		case "getCreatedUnusedCTeamInfo":
			result=incubatorService.getCreatedUnusedCTeamInfo(data);
			break;
		case "setCreateCProjInfo":
			result=incubatorService.setCreateCProjInfo(data);
			break;
		case "setCreateIcbtInfoByPerson":
			result=incubatorService.setCreateIcbtInfoByPerson(data);
			break;
		case "setCreateMTeamInfo":
			result=incubatorService.setCreateMTeamInfo(data);
			break;
		case "setMTeamInviteUserInfo":
			result=incubatorService.setMTeamInviteUserInfo(data);
			break;
		case "getMTeamJoinedMembersList":
			result=incubatorService.getMTeamJoinedMembersList(data);
			break;
		case "getMTeamWaitedUsersList":
			result=incubatorService.getMTeamWaitedUsersList(data);
			break;
		case "setMTeamCancelInviteUserInfo":
			result=incubatorService.setMTeamCancelInviteUserInfo(data);
			break;
		case "getCreatedUnusedMTeamInfo":
			result=incubatorService.getCreatedUnusedMTeamInfo(data);
			break;
		case "setCreateIcbtInfoByTeam":
			result=incubatorService.setCreateIcbtInfoByTeam(data);
			break;
		case "getCProjList":
			result=incubatorService.getCProjList(data);
			break;
		case "getCProjCheckResult":
			result=incubatorService.getCProjCheckResult(data);
			break;
		case "getIncubatedCProjInfo":
			result=incubatorService.getIncubatedCProjInfo(data);
			break;
		case "getUnsettledCProjInfo":
			result=incubatorService.getUnsettledCProjInfo(data);
			break;
		case "setTodayJournal":
			result=incubatorService.setTodayJournal(data);
			break;
		case "getHistoryJournalData":
			result=incubatorService.getHistoryJournalData(data);
			break;
		case "getAvailableIncubatorsList":
			result=incubatorService.getAvailableIncubatorsList(data);
			break;
		case "getIcbtDataByClient":
			result=incubatorService.getIcbtDataByClient(data);
			break;
		case "setSettleApplicationInfo":
			result=incubatorService.setSettleApplicationInfo(data);
			break;
		case "getIcbtsList":
			result=incubatorService.getIcbtsList(data);
			break;
		case "getCProjDataByIcbtID":
			result=incubatorService.getCProjDataByIcbtID(data);
			break;
		case "getCProjApplicationData":
			result=incubatorService.getCProjApplicationData(data);
			break;
		case "setCheckStatus":
			result=incubatorService.setCheckStatus(data);
			break;
		case "getIncubatedProjDataByManager":
			result=incubatorService.getIncubatedProjDataByManager(data);
			break;
		case "getMembersListByCTeamID":
			result=incubatorService.getMembersListByCTeamID(data);
			break;
		case "getCProjMemberDetails":
			result=incubatorService.getCProjMemberDetails(data);
			break;
		case "getCProjTodayJournalsData":
			result=incubatorService.getCProjTodayJournalsData(data);
			break;
		case "setCProjBroadcastData":
			result=incubatorService.setCProjBroadcastData(data);
			break;
		case "getSearchTeamRelatedUsersList":
			result=incubatorService.getSearchTeamRelatedUsersList(data);
			break;
		case "getHotRecruitProjsList":
			result=incubatorService.getHotRecruitProjsList(data);
			break;
		case "setPublishProjInfo":
			result=incubatorService.setPublishProjInfo(data);
			break;
		case "setUpdatePublishProjInfo":
			result=incubatorService.setUpdatePublishProjInfo(data);
			break;
		case "getIcbtPublishProjInfo":
			result=incubatorService.getIcbtPublishProjInfo(data);
			break;
		case "setDeletePublishProj":
			result=incubatorService.setDeletePublishProj(data);
			break;
		case "getAllRecruitProjsList":
			result=incubatorService.getAllRecruitProjsList(data);
			break;
		case "getRecruitProjDetails":
			result=incubatorService.getRecruitProjDetails(data);
			break;
		case "getUnusedCTeamInfoList":
			result=incubatorService.getUnusedCTeamInfoList(data);
			break;
		case "getCTeamInfo":
			result=incubatorService.getCTeamInfo(data);
			break;
		case "getCProjCheckRecord":
			result=incubatorService.getCProjCheckRecord(data);
			break;
		case "getCTeamCheckRecord":
			result=incubatorService.getCTeamCheckRecord(data);
			break;
		case "setSettleApplicationInfoByCTeam":
			result=incubatorService.setSettleApplicationInfoByCTeam(data);
			break;
		case "getCProjApplicationDataByCTeam":
			result=incubatorService.getCProjApplicationDataByCTeam(data);
			break;
		case "setCheckStatusByCTeam":
			result=incubatorService.setCheckStatusByCTeam(data);
			break;
		case "getUserInvitedMsgList":
			result=incubatorService.getUserInvitedMsgList(data);
			break;
		case "setInviteStatusByInviteID":
			result=incubatorService.setInviteStatusByInviteID(data);
			break;
		case "getUserBasicInfo":
			result=incubatorService.getUserBasicInfo(data);
			break;
		case "getCProjInfo":
			result=incubatorService.getCProjInfo(data);
			break;
		case "getPIcbtInfo":
			result=incubatorService.getPIcbtInfo(data);
			break;
		case "getUnusedMTeamInfoList":
			result=incubatorService.getUnusedMTeamInfoList(data);
			break;
		case "getTIcbtInfo":
			result=incubatorService.getTIcbtInfo(data);
			break;
		case "setWriteWeeklyReport":
			result=incubatorService.setWriteWeeklyReport(data);
			break;
		case "getMyWeeklyReportList":
			result=incubatorService.getMyWeeklyReportList(data);
			break;
		case "setWriteMonthlyReport":
			result=incubatorService.setWriteMonthlyReport(data);
			break;
		case "getMyMonthlyReportList":
			result=incubatorService.getMyMonthlyReportList(data);
			break;
		case "getTeamMonthlyReportList":
			result=incubatorService.getTeamMonthlyReportList(data);
			break;
		case "setWriteMeetingReport":
			result=incubatorService.setWriteMeetingReport(data);
			break;
		case "getTeamMeetingReportList":
			result=incubatorService.getTeamMeetingReportList(data);
			break;
		case "getTeamWeeklyReportList":
			result=incubatorService.getTeamWeeklyReportList(data);
			break;
		case "getUserApplyJoinMsgList":
			result=incubatorService.getUserApplyJoinMsgList(data);
			break;
		case "setApplyJoinStatus":
			result=incubatorService.setApplyJoinStatus(data);
			break;
		case "setApplyJoinMsg":
			result=incubatorService.setApplyJoinMsg(data);
			break;
		}
		param.respData.append(result.toString());
//		System.out.println("输出数据："+param.respData);
		return super.handleMsg(param);
	}

}
