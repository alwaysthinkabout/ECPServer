package ECP.handle.platform;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;
import ECP.dao.UserDao;
import ECP.handle.CMsgBaseHandle;
import ECP.service.UserService;
import ECP.service.platform.UserInfoService;


public class UserHandle extends CMsgBaseHandle{
	private UserService userService;
	private UserInfoService userInfoService;
	private UserDao userDao;
	public UserHandle(){
		userService = new UserService();
		userInfoService = new UserInfoService();
		userDao = new UserDao();
	}
	
	@Override
	public int handleMsg(CParam param) throws Exception {
		JSONObject data=getReqMessage(param);
		String op=data.getString("op");
		System.out.println("收到的请求信息为："+data.toString());
		JSONObject result=null;
		
		HttpServletRequest msgReq;
		msgReq = param.getMsgReq();
		msgReq.setCharacterEncoding("UTF-8");
		
		switch(op){
		case "getUserInfoList":           //平台管理用户管理列表信息
			result = userInfoService.userInfoList(data);
			break;
		case "getUserInfoDetail":         //平台管理用户详情
			result = userInfoService.userInfoDetail(data);
			break;
		case "getUserInfoDelete":        //平台管理用户删除
			result = userInfoService.userInfoDelete(data);
			break;
		case "getUserInfo":               //获取用户信息
			result=userService.getUserInfo(data);
			break;
		case "userPasswordAlter":         //修改密码
			result=userService.userPasswordAlter(data);
			break;
		case "userEmailAlter":            //修改邮箱
			result=userService.userEmailAlter(data);
			break;		
		case "modifyInfo":         //修改属性
			result=userDao.modifyInfo(data);
			break;		
		default:
			String str = "{\"result\":\"error\",\"resultTip\":\"请求参数出错\"}";
			result = new JSONObject(str);
		}
		param.respData.append(result.toString());
		return super.handleMsg(param);
	}

}
