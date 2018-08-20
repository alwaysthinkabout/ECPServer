package ECP.handle;

import org.json.JSONObject;
import ECP.service.JobRoleService;
import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;

public class JobRoleHandle extends CMsgBaseHandle{

	private JobRoleService jobRoleService;
	
	public JobRoleHandle(){
		jobRoleService = new JobRoleService();
	}
	@Override
	public int handleMsg(CParam param) throws Exception {
		JSONObject data=getReqMessage(param);
		System.out.println("收到的请求信息为："+data.toString());
		String op=data.getString("op");
		JSONObject result=null;
		
		switch(op){
		case "login":                           //企业用户登录平台(加入角色判断)
			result = jobRoleService.login(data);
			break;
		case "jobRoleList":                     //角色列表
			result = jobRoleService.jobRoleList(data);
			break;
		case "jobUserRoleList":                 //用户角色列表
			result = jobRoleService.jobUserRoleList(data);
			break;
		case "jobRoleConfig":                   //角色配置
			result = jobRoleService.jobRoleUpdate(data);
			break;
		case "jobRoleUpdatePassword":           //修改密码
			result = jobRoleService.userUpdatePassword(data);
			break;
		case "userRoleUpdatePassword":          //角色用户密码修改
			result = jobRoleService.roleUpdatePassword(data);
			break;
		default:
			String str = "{\"result\":\"error\",\"resultTip\":\"请求参数出错\"}";
			result = new JSONObject(str);
		}
		param.respData.append(result.toString());
		return super.handleMsg(param);
	}
}
