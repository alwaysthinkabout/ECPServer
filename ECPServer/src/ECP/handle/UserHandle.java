package ECP.handle;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;
import ECP.dao.UserDao;
import ECP.service.UserService;


public class UserHandle extends CMsgBaseHandle{
	private UserService userService;
	private UserDao userDao;
	public UserHandle(){
		userService = new UserService();
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
		
		case "login":               //企业用户登录平台
			result=userService.login(data);
			break;
		case "register":            //企业用户注册
			result=userService.register(data);
			break;
		case "getUserIds":          //获取账号
			result=userService.userIds(data);
			break;
		case "updateAccount":       //账号修改
			result=userService.updateUserInfo(data);
			break;
		case "updatePass":          //密码修改
			result=userService.updatePassword(data);
			break;
		case "commonLogin":         //客户端用户登录
			result=userDao.commonLogin(data);
			break;
		case "wechatLogin":
			result=userDao.wechatLogin(data);
			break;
		case "getRegister":         //用户注册
			result=userDao.getRegister(data);
			break;
		case "findFriend":          //查找好友
			result=userDao.findFriend(data);
			break;
		case "applyFriend":         //申请好友
			result=userDao.applyFriend(data);
			break;
		case "friendApplyAccept":   //同意申请好友
			result=userDao.friendApplyAccept(data);
			break;
		case "sendMessage":         //发送消息
			result=userDao.sendMessage(data);
			break;
		case "modifyInfo":          //修改属性
			result=userDao.modifyInfo(data);
			break;
		case "confirmPassword":
			result=userDao.confirmPassword(data);
			break;
		case "findPassword":       //招聘端找回密码
			result = userService.findPassword(data);
			break;
		case "getMailRegister":
			result = userDao.getMailRegister(data);
			break;
		default:
			String str = "{\"result\":\"error\",\"resultTip\":\"请求参数出错\"}";
			result = new JSONObject(str);
		}
		param.respData.append(result.toString());
		return super.handleMsg(param);
	}

}
