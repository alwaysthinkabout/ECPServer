package ECP.handle;

import org.json.JSONObject;

import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;
import ECP.service.OrganizationInfoService;

/**
 * @Description 
 * Created by durenshi on 2017-5-2
 */
public class OrganizationInfoHandle extends CMsgBaseHandle {

	private OrganizationInfoService organizationInfoService;
	
	public OrganizationInfoHandle(){
		organizationInfoService = new OrganizationInfoService();
	}
	
	@Override
	public int handleMsg(CParam param) throws Exception {
		JSONObject data=getReqMessage(param);
		System.out.println("收到的请求信息为："+data.toString());
		String op=data.getString("op");
		JSONObject result=null;
		
		switch(op){

		//浏览器端接口
		case "orgIntegrity":    //企业资料完整度
			result=organizationInfoService.orgIntegrity(data);
			break;
		case "storeList":       //招聘站列表
			result=organizationInfoService.storeList(data);
			break;
		case "storeDetail":     //招聘站详情
			result=organizationInfoService.storeDetail(data);
			break;
		case "storeAdd":        //招聘站添加(企业初级信息注册)
			result=organizationInfoService.storeAdd(data);
			break;
		case "storeDelete":     //招聘站删除
			result=organizationInfoService.storeDelete(data);
			break;
		case "storeUpdate":     //招聘站更新
			result=organizationInfoService.storeUpdate(data);
			break;
			
		case "orgStateDetailAnnual":   //企业状况年度信息详情
			result=organizationInfoService.OrgStateDetailAnnual(data);
			break;
		case "orgStateAdd":            //新增企业状况信息
			result=organizationInfoService.orgStateAdd(data);
			break;
		case "orgStateUpdate":         //修改企业状况信息
			result=organizationInfoService.orgStateUpdate(data);
			break;
		case "orgStateDelete":         //删除企业状况信息
			result=organizationInfoService.OrgStateDelete(data);
			break;
		case "orgCertDelete":          //删除企业高级注册信息
			result=organizationInfoService.deleteOrgCertInfo(data);
			break;
		case "orgAllDetail":           //查看企业所有级别注册信息
			result=organizationInfoService.OrgAllDetail(data);
			break;
		case "stateNotFileList":       //未上传文件的年度报表列表
			result=organizationInfoService.stateNotFileList(data);
			break;
			
		case "deleteLicenseFile":      //删除营业执照文件     
			result=organizationInfoService.deleteLicenseFile(data);
			break;
		case "deleteStateFile":        //删除年度报表文件      
			result=organizationInfoService.deleteStateFile(data);
			break;
		default:
			String str = "{\"result\":\"error\",\"resultTip\":\"请求参数出错\"}";
			result = new JSONObject(str);
		}
		param.respData.append(result.toString());
		return super.handleMsg(param);
	}
}
