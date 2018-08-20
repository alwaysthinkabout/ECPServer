package ECP.handle;

import org.json.JSONObject;

import ECP.service.JobApplyService;
import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;

/**
 * @Description 
 * Created by durenshi on 2017-4-26
 */
public class JobApplyHandle extends CMsgBaseHandle {
	private JobApplyService jobApplyService;
	
	public JobApplyHandle(){
		jobApplyService = new JobApplyService();
	}
	
	@Override
	public int handleMsg(CParam param) throws Exception {
		JSONObject data=getReqMessage(param);
		System.out.println("收到的请求信息为："+data.toString());
		String op=data.getString("op");
		JSONObject result=null;
		
		switch(op){

		//浏览器端接口
		case "showApplys":              //查看岗位申请列表
			result=jobApplyService.jobApplyList(data);
			break;
		case "showApply":               //查看岗位详情
			result=jobApplyService.jobApplyDetail(data);
			break;
		case "dealApply":               //修改岗位申请状态
			result=jobApplyService.jobApplyStatusUpdate(data);
			break;
		case "jobAppliers":             //求职者列表
			result=jobApplyService.jobAppliersList(data);
			break;
		case "applysCount":             //求职者列表
			result=jobApplyService.findApplysCountById(data);
			break;
			
		//安卓端接口
		case "getUserDataLevel":       //查看用户注册级别
			result=jobApplyService.userDataLevel(data);
			break;
		case "getPrimaryData":         //获取用户初级资料
			result=jobApplyService.personPrimaryInfo(data);
			break;
		case "updatePrimaryData":      //完善或修改初级资料
			result=jobApplyService.personPrimaryInfoUpdate(data);
			break;
		case "addExperience":          //添加用户高级注册资料（个人经历资料）
			result=jobApplyService.personExperienceAdd(data);
			break;
		case "getExperienceList":      //查看用户高级注册资料列表（个人经历资料）
			result=jobApplyService.personExperienceListDetail(data);
			break;
		case "deleteExperienceList":   //删除用户高级注册资料列表（个人经历资料）
			result=jobApplyService.personExperienceListDelete(data);
			break;
		case "updateExperience":       //更新用户某个高级注册资料（个人经历资料）
			result=jobApplyService.personExperienceUpdate(data);
			break;
		case "getExperience":          //查看用户某个高级注册资料详情（个人经历资料）
			result=jobApplyService.personExperienceDetail(data);
			break;
		case "applyJob":               //申请岗位
			result=jobApplyService.applyJob(data);
			break;
		case "getHistoryApplyList":    //查看历史岗位申请记录
			result=jobApplyService.jobHistoryApplyList(data);
			break;
		case "getValidApplyList":      //查看有效岗位申请记录
			result=jobApplyService.jobValidApplyList(data);
			break;
		case "applyLeaveWord":         //添加岗位留言
			result=jobApplyService.jobLeaveAdd(data);
			break;
		default:
			String str = "{\"result\":\"error\",\"resultTip\":\"请求参数出错\"}";
			result = new JSONObject(str);
		}
		param.respData.append(result.toString());
		return super.handleMsg(param);
	}
}
