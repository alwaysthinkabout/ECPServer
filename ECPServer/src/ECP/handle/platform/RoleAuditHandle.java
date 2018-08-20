package ECP.handle.platform;

import org.json.JSONObject;

import ECP.handle.CMsgBaseHandle;
import ECP.service.platform.RoleAuditService;
import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;

public class RoleAuditHandle extends CMsgBaseHandle {
		
		private RoleAuditService RoleAuditService;
		
		public RoleAuditHandle(){
			RoleAuditService = new RoleAuditService();
		}
		
		@Override
		public int handleMsg(CParam param) throws Exception {
			JSONObject data=getReqMessage(param);
			System.out.println("收到的请求信息为："+data.toString());
			String op=data.getString("op");
			JSONObject result=null;
			
			switch(op){
			case "getRoleInfoAuditList":           //待审核角色列表    
				result = RoleAuditService.getRoleInfoAuditList(data);
				break;
			case "getRoleInfoAuditDetail":        //某个角色待审核的所有信息    
				result = RoleAuditService.getRoleInfoAuditDetail(data);
				break;
			case "getRoleInfoAudit":             //具体某个审核的信息   
				result = RoleAuditService.getRoleInfoAudit(data);
				break;
			case "getRoleInfoAuditedList":             //具体某个审核的信息   
				result = RoleAuditService.getRoleInfoAuditedList(data);
				break;
			case "getRoleInfoAuditedDetail":             //具体某个审核的信息   
				result = RoleAuditService.getRoleInfoAuditedDetail(data);
				break;
			default:
				String str = "{\"result\":\"error\",\"resultTip\":\"请求参数出错\"}";
				result = new JSONObject(str);
			}
			param.respData.append(result.toString());
			return super.handleMsg(param);
		}
		
	}
