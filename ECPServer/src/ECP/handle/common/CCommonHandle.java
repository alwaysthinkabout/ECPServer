package ECP.handle.common;


import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;
import ECP.handle.CMsgBaseHandle;
import ECP.service.common.CFindService;


public class CCommonHandle extends CMsgBaseHandle{
	private CFindService cFindService;
	public CCommonHandle(){
		cFindService = new CFindService();
	}
	
	@Override
	public int handleMsg(CParam param) throws Exception {
		JSONObject data=getReqMessage(param);
		String op=data.getString("op");		
		JSONObject result;		
		HttpServletRequest msgReq;
		msgReq = param.getMsgReq();
		msgReq.setCharacterEncoding("UTF-8");
		
		switch(op){
		case "find":
			result = cFindService.find(data);
			break;
		case "login":
			result = cFindService.login(data);
			break;
		case "push":
			result = cFindService.push(data);
			break;
		case "webPush":
			result = cFindService.webPush(data);
			break;
		case "webChat":
			result = cFindService.webChat(data);
			break;
		case "apply":
			result = cFindService.friendApply(data);
			break;
		case "friend_apply_accept":
			result = cFindService.friendAccept(data);
			break;
		case "logout":
			result = cFindService.logout(data);
			break;
		case "send":
			result = cFindService.send(data);
			break;
		case "getOfflineInfo":
			result = cFindService.getOfflineInfo(data);
			break;
		case "send_picture":
			result = cFindService.handlePicture(data);
			break;
		case "email_verify":
			result = cFindService.emailVerify(data);
			break;
		case "register":
			result = cFindService.register(data);
			break;
		case "change_password":
			result = cFindService.change_password(data);
			break;
		case "modifyInfo":
			result = cFindService.modifyInfo(data);
			break;
		case "confirmPassword":
			result = cFindService.checkPassword(data);
			break;
		case "getClassify":
			result = cFindService.getClassify(data);
			break;
		case "subscribe":
			result = cFindService.subscribe(data);
			break;
		case "send_file":
			result = cFindService.sendFile(data);
			break;
		case "getServiceInfo":
			result = cFindService.getServiceInfo(data);
			break;
		case "getAllRoles":
			result = cFindService.getRoles(data);
			break;
		case "addRole":
			result = cFindService.addRoles(data);
			break;
		default:
			result = new JSONObject();
			break;
		}
		param.respData.append(result.toString());
		return super.handleMsg(param);
	}

}
