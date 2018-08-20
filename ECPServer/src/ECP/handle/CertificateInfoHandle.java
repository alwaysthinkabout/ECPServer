package ECP.handle;

import org.json.JSONObject;

import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;
import ECP.service.CertificateInfoService;

/**
 * @Description 
 * Created by durenshi on 2017-5-5
 */
public class CertificateInfoHandle extends CMsgBaseHandle {

	private CertificateInfoService certificateInfoService;
	
	public CertificateInfoHandle(){
		certificateInfoService = new CertificateInfoService();
	}
	
	@Override
	public int handleMsg(CParam param) throws Exception {
		JSONObject data=getReqMessage(param);
		System.out.println("收到的请求信息为："+data.toString());
		String op=data.getString("op");
		JSONObject result=null;
		
		switch(op){

		//安卓端接口
		case "deletePersonalFile":        //删除用户已上传的文件
			result=certificateInfoService.deletePersonalFile(data);
			break;
		case "getPersonalFileState":      //获取用户已上传的文件
			result=certificateInfoService.personalFileState(data);
			break;
		default:
			String str = "{\"result\":\"error\",\"resultTip\":\"请求参数出错\"}";
			result = new JSONObject(str);
		}
		param.respData.append(result.toString());
		return super.handleMsg(param);
	}
}
